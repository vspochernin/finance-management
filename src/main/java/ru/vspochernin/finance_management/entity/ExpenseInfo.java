package ru.vspochernin.finance_management.entity;

import java.util.Optional;

public record ExpenseInfo(
        long expense,
        Optional<Long> budget)
{
}
