package ru.vspochernin.finance_management.utils;

import java.util.Map;

import ru.vspochernin.finance_management.entity.ExpenseInfo;
import ru.vspochernin.finance_management.entity.User;

public class InfoUtils {

    public static String generateReport(User user) {
        Map<String, Long> incomeByCategory = user.getIncomeByCategory();
        long totalIncome = incomeByCategory.values().stream()
                .mapToLong(Long::longValue)
                .sum();

        Map<String, ExpenseInfo> expenseInfoByCategory = user.getExpenseInfoByCategory();
        long totalExpense = expenseInfoByCategory.values().stream()
                .mapToLong(ExpenseInfo::expense)
                .sum();

        StringBuilder result = new StringBuilder();

        result.append("Общий доход: ")
                .append(MoneyUtils.convertToRubles(totalIncome))
                .append("\n");
        result.append("Доходы по категориям:\n");
        incomeByCategory.forEach((category, amount) ->
                result.append("  - ")
                        .append(category)
                        .append(": ")
                        .append(MoneyUtils.convertToRubles(amount))
                        .append("\n"));

        result.append("Общие расходы: ")
                .append(MoneyUtils.convertToRubles(totalExpense))
                .append("\n");
        result.append("Расходы по категориям:\n");
        expenseInfoByCategory.forEach(((category, expenseInfo) ->
                result.append("  - ")
                        .append(category)
                        .append(": ")
                        .append(MoneyUtils.convertToRubles(expenseInfo.expense()))
                        .append("\n")));

        result.append("Бюджет по категориям:\n");
        expenseInfoByCategory.entrySet().stream()
                .filter(entry -> entry.getValue().budget().isPresent())
                .forEach(entry -> {
                    String category = entry.getKey();
                    ExpenseInfo expenseInfo = entry.getValue();
                    long budget = expenseInfo.budget().get();
                    long expense = expenseInfo.expense();
                    result.append("  - ")
                            .append(category)
                            .append(": ")
                            .append(MoneyUtils.convertToRubles(budget))
                            .append(", оставшийся бюджет: ")
                            .append(MoneyUtils.convertToRubles(budget - expense))
                            .append("\n");
                });

        return result.toString();
    }
}
