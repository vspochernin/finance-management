package ru.vspochernin.finance_management.utils;

import java.util.Map;

import ru.vspochernin.finance_management.entity.ExpenseInfo;
import ru.vspochernin.finance_management.entity.User;

public class InfoUtils {

    public static void generateReport(User user) {
        Map<String, Long> incomeByCategory = user.getIncomeByCategory();
        long totalIncome = incomeByCategory.values().stream()
                .mapToLong(Long::longValue)
                .sum();

        Map<String, ExpenseInfo> expenseInfoByCategory = user.getExpenseInfoByCategory();
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
                    long budget = expenseInfo.budget().get();
                    long expense = expenseInfo.expense();
                    System.out.println("  - "
                            + category
                            + ": "
                            + MoneyUtils.convertToRubles(budget)
                            + ", оставшийся бюджет: " + MoneyUtils.convertToRubles(budget - expense));
                });
    }
}
