package ru.vspochernin.finance_management.command;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandType {

    UNKNOWN(""),
    ;

    private static final Map<String, CommandType> BY_COMMAND_TYPE_STR_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(CommandType::getCommandTypeStr, Function.identity()));

    private final String commandTypeStr;

    public static CommandType parse(String commandTypeStr) {
        return Optional.ofNullable(BY_COMMAND_TYPE_STR_MAP.get(commandTypeStr))
                .orElse(UNKNOWN);
    }
}
