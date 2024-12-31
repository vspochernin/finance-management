package ru.vspochernin.finance_management.command;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import ru.vspochernin.finance_management.entity.User;

public record Command(
        CommandType commandType,
        List<String> arguments)
{

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String DELIMITER_REGEX = " +";

    public static Command getNext() {
        System.out.println("------------------------");
        System.out.println("Введите очередную команду [" + User.getCurrentUserLogin() + "]:");

        String str = SCANNER.nextLine().trim();
        List<String> strParts = Arrays.stream(str.split(DELIMITER_REGEX))
                .toList();

        CommandType commandType = strParts.stream()
                .findFirst()
                .map(CommandType::parse)
                .orElse(CommandType.UNKNOWN);
        List<String> arguments = strParts.stream()
                .skip(1)
                .toList();

        return new Command(commandType, arguments);
    }
}
