package ru.vspochernin.finance_management.command;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import ru.vspochernin.finance_management.context.FinanceManagementContext;
import ru.vspochernin.finance_management.exception.FinanceManagementException;
import ru.vspochernin.finance_management.handler.CommandHandler;
import ru.vspochernin.finance_management.utils.PrintUtils;

@Service
public class CommandLineInterface {

    private final Map<CommandType, CommandHandler> commandHandlersMap;

    public CommandLineInterface(List<CommandHandler> commandHandlers) {
        this.commandHandlersMap = commandHandlers.stream()
                .collect(Collectors.toMap(
                        CommandHandler::getHandlingCommandType,
                        Function.identity(),
                        (a, b) -> a,
                        () -> new EnumMap<>(CommandType.class)));
    }

    public void run() {
        PrintUtils.printHelpMessage();

        Command currentCommand;
        while (FinanceManagementContext.isRunning) {
            currentCommand = Command.getNext();
            try {
                Optional.ofNullable(commandHandlersMap.get(currentCommand.commandType()))
                        .orElse(commandHandlersMap.get(CommandType.UNKNOWN))
                        .validateAndHandle(currentCommand.arguments());
            } catch (FinanceManagementException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}
