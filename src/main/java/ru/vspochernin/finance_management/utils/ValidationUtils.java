package ru.vspochernin.finance_management.utils;

import java.util.List;

import ru.vspochernin.finance_management.exception.FinanceManagementException;

public class ValidationUtils {

    public static void validateEmptyArguments(List<String> arguments) {
        if (!arguments.isEmpty()) {
            throw new FinanceManagementException("Команда не поддерживает аргументы");
        }
    }
}
