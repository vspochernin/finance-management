package ru.vspochernin.finance_management.utils;

import ru.vspochernin.finance_management.command.CommandType;

public class PrintUtils {

    public static void printHelpMessage() {
        StringBuilder helpMessage = new StringBuilder()
                .append("----------\n")
                .append("Система управления личными финансами поддерживает следующие команды:\n");
        helpMessage.append("-----\n");

        for (var commandType : CommandType.values()) {
            if (commandType == CommandType.UNKNOWN) {
                continue;
            }

            helpMessage
                    .append("'")
                    .append(commandType.getCommandTypeStr());

            commandType.getArgumentsDescription().forEach(argument -> helpMessage
                    .append(" [")
                    .append(argument)
                    .append("]"));

            helpMessage
                    .append("' - ")
                    .append(commandType.getCommandDescription())
                    .append(",\n");
        }
        helpMessage.append("-----\n");

        helpMessage.append("- Логин должен содержать от 3 до 32 символов");
        helpMessage.append("- Пароль должен содержать от 6 до 32 символов");

        helpMessage.append("----------");

        System.out.println(helpMessage);
    }
}
