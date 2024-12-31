package ru.vspochernin.finance_management.handler;

import java.util.List;

import ru.vspochernin.finance_management.command.CommandType;

public interface CommandHandler {

    default void validateAndHandle(List<String> arguments) {
        validate(arguments);
        handle(arguments);
    }

    void handle(List<String> arguments);

    void validate(List<String> arguments);

    CommandType getHandlingCommandType();
}
