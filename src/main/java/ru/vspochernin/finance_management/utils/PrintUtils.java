package ru.vspochernin.finance_management.utils;

import ru.vspochernin.finance_management.command.CommandType;

public class PrintUtils {

    public static void printHelpMessage() {
        StringBuilder sb = new StringBuilder()
                .append("----------\n")
                .append("Система управления личными финансами поддерживает следующие команды:\n")
                .append("-----\n");
        for (var commandType : CommandType.values()) {
            if (commandType == CommandType.UNKNOWN) {
                continue;
            }
            sb
                    .append("'")
                    .append(commandType.getCommandTypeStr())
                    .append("' - ")
                    .append(commandType.getDescription())
                    .append("\n");
        }
        sb
                .append("-----\n")
                .append("----------");
        System.out.println(sb);
    }
}
