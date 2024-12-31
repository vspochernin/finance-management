package ru.vspochernin.finance_management.context;

import java.util.Optional;

import ru.vspochernin.finance_management.entity.User;

public class FinanceManagementContext {

    public static boolean isRunning = true;
    public static Optional<User> currentUser = Optional.empty();
}
