package ru.vspochernin.finance_management.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.vspochernin.finance_management.exception.FinanceManagementException;

@AllArgsConstructor
@Getter
public enum CategoryType {

    INCOME("income"),
    EXPENSE("expense");

    private static final Map<String, CategoryType> BY_CATEGORY_TYPE_STR_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(CategoryType::getCategoryTypeStr, Function.identity()));

    private final String categoryTypeStr;

    public static CategoryType parse(String categoryTypeStr) {
        return Optional.ofNullable(BY_CATEGORY_TYPE_STR_MAP.get(categoryTypeStr))
                .orElseThrow(() -> new FinanceManagementException("Некорректный тип категории: " + categoryTypeStr));
    }
}
