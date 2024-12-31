package ru.vspochernin.finance_management.utils;

import java.util.List;

import ru.vspochernin.finance_management.command.CommandType;
import ru.vspochernin.finance_management.exception.FinanceManagementException;

public class ValidationUtils {

    public static void validateArgumentsCount(List<String> arguments, CommandType commandType) {
        int expected = commandType.getArgumentsDescription().size();
        int actual = arguments.size();
        if (actual != expected) {
            throw new FinanceManagementException(
                    "Некорректное число аргументов, ожидается: " + expected + " считано: " + actual);
        }
    }
}
