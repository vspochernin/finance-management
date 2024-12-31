package ru.vspochernin.finance_management.command;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandType {

    UNKNOWN("", List.of(), ""),
    HELP("help", List.of(), "вывод сообщения помощи"),
    EXIT("exit", List.of(), "выход из программы"),
    REGISTER("register", List.of("логин", "пароль"), "зарегистрироваться"),
    LOGIN("login", List.of("логин", "пароль"), "пройти аутентификацию"),
    ;

    private static final Map<String, CommandType> BY_COMMAND_TYPE_STR_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(CommandType::getCommandTypeStr, Function.identity()));

    private final String commandTypeStr;
    private final List<String> argumentsDescription;
    private final String commandDescription;

    public static CommandType parse(String commandTypeStr) {
        return Optional.ofNullable(BY_COMMAND_TYPE_STR_MAP.get(commandTypeStr))
                .orElse(UNKNOWN);
    }
}
