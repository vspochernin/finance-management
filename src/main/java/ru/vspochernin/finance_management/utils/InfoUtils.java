package ru.vspochernin.finance_management.utils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import ru.vspochernin.finance_management.entity.Category;
import ru.vspochernin.finance_management.entity.CategoryType;
import ru.vspochernin.finance_management.entity.Transaction;
import ru.vspochernin.finance_management.entity.User;

public class InfoUtils {

    public static void generateReport(User user) {
        List<Category> categories = user.getCategories();

        Map<String, Long> incomeByCategory = categories.stream()
                .filter(category -> category.getType() == CategoryType.INCOME)
                .collect(Collectors.toMap(
                        Category::getTitle,
                        category -> category.getTransactions().stream()
                                .mapToLong(Transaction::getAmount)
                                .sum()));
        long totalIncome = incomeByCategory.values().stream()
                .mapToLong(Long::longValue)
                .sum();

        Map<String, ExpenseInfo> expenseInfoByCategory = categories.stream()
                .filter(category -> category.getType() == CategoryType.EXPENSE)
                .collect(Collectors.toMap(
                        Category::getTitle,
                        category -> {
                            long expense = category.getTransactions().stream()
                                    .mapToLong(Transaction::getAmount)
                                    .sum();
                            Optional<Long> budget = category.getBudgetO();
                            return new ExpenseInfo(expense, budget);
                        }
                ));
        long totalExpense = expenseInfoByCategory.values().stream()
                .mapToLong(ExpenseInfo::expense)
                .sum();

        System.out.println("Общий доход: " + MoneyUtils.convertToRubles(totalIncome));
        System.out.println("Доходы по категориям:");
        incomeByCategory.forEach((category, amount) ->
                System.out.println("  - " + category + ": " + MoneyUtils.convertToRubles(amount)));

        System.out.println("Общие расходы: " + MoneyUtils.convertToRubles(totalExpense));
        System.out.println("Расходы по категориям:");
        expenseInfoByCategory.forEach(((category, expenseInfo) ->
                System.out.println("  - " + category + ": " + MoneyUtils.convertToRubles(expenseInfo.expense()))));

        System.out.println("Бюджет по категориям:");
        expenseInfoByCategory.entrySet().stream()
                .filter(entry -> entry.getValue().budget().isPresent())
                .forEach(entry -> {
                    String category = entry.getKey();
                    ExpenseInfo expenseInfo = entry.getValue();
                    long budget = expenseInfo.budget.get();
                    long expense = expenseInfo.expense;
                    System.out.println("  - "
                            + category
                            + ": "
                            + MoneyUtils.convertToRubles(budget)
                            + ", оставшийся бюджет: " + MoneyUtils.convertToRubles(budget - expense));
                });
    }

    private record ExpenseInfo(
            long expense,
            Optional<Long> budget)
    {
    }
}
