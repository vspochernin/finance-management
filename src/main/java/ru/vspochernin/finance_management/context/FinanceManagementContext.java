package ru.vspochernin.finance_management.context;

import java.util.Optional;


public class FinanceManagementContext {

    public static boolean isRunning = true;
    public static Optional<String> currentUserLogin = Optional.empty();
}
