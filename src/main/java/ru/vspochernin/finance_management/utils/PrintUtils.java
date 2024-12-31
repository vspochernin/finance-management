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

        helpMessage.append("- Логин должен содержать от 3 до 32 символов\n");
        helpMessage.append("- Пароль должен содержать от 6 до 32 символов\n");
        helpMessage.append("- Название категории не должно превышать 50 символов\n");
        helpMessage.append("- Если название категории состоит из нескольких слов, слова должны быть разделены символом \"-\"\n");
        helpMessage.append("- Тип категории должен быть одним из двух значений: income (доход), либо expense (расход)\n");

        helpMessage.append("----------");

        System.out.println(helpMessage);
    }
}
