package ru.vspochernin.finance_management.utils;

import java.util.List;

import ru.vspochernin.finance_management.command.CommandType;
import ru.vspochernin.finance_management.context.FinanceManagementContext;
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

    public static void validateLoginLength(String login) {
        int len = login.length();
        if (len < 3 || len > 32) {
            throw new FinanceManagementException("Логин должен содержать от 3 до 32 символов");
        }
    }

    public static void validatePasswordLength(String password) {
        int len = password.length();
        if (len < 6 || len > 32) {
            throw new FinanceManagementException("Пароль должен содержать от 6 до 32 символов");
        }
    }

    public static void validateAuthentication() {
        if (FinanceManagementContext.currentUser.isEmpty()) {
            throw new FinanceManagementException("Необходимо пройти аутентификацию");
        }
    }

    public static void validateCategoryTitleLength(String categoryTitle) {
        int len = categoryTitle.length();
        if (len > 50) {
            throw new FinanceManagementException("Название категории не должно превышать 50 символов");
        }
    }

    public static void validateMoneyLength(String money) {
        int len = money.length();
        if (len > 15) {
            throw new FinanceManagementException("Денежное значение не может превышать 15 символов");
        }
    }
}
