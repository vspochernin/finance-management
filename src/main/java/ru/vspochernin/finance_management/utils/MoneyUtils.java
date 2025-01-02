package ru.vspochernin.finance_management.utils;

import ru.vspochernin.finance_management.exception.FinanceManagementException;

public class MoneyUtils {

    public static long parseMoney(String str) {
        try {
            long money = doParseMoney(str.trim());
            if (money <= 0) {
                throw new FinanceManagementException("Денежное значение должно быть положительным");
            }
            return money;
        } catch (NumberFormatException e) {
            throw new FinanceManagementException("Ошибка обработки денежного значения");
        }
    }

    public static String convertToRubles(long amount) {
        long rubles = amount / 100;
        long kopecks = Math.abs(amount) % 100;

        return rubles + "." + (kopecks < 10 ? "0" + kopecks : kopecks);
    }

    private static long doParseMoney(String str) {
        if (str == null || str.isBlank()) {
            throw new FinanceManagementException("Денежное значение не может задаваться пустой строкой");
        }

        if (str.contains(".")) {
            String[] parts = str.split("\\.");
            if (parts.length != 2) {
                throw new FinanceManagementException("Некорректно задано дробное денежное значение");
            }

            String rublesPart = parts[0];
            String kopecksPart = parts[1];

            if (kopecksPart.length() > 2) {
                throw new FinanceManagementException("Некорректно задана часть копеек денежного значения");
            }

            return Long.parseLong(rublesPart) * 100 + Long.parseLong(kopecksPart);
        }

        return Long.parseLong(str) * 100;
    }
}
