package ru.vspochernin.finance_management.command;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import ru.vspochernin.finance_management.context.FinanceManagementContext;
import ru.vspochernin.finance_management.entity.User;
import ru.vspochernin.finance_management.exception.FinanceManagementException;
import ru.vspochernin.finance_management.handler.CommandHandler;
import ru.vspochernin.finance_management.repository.UserRepository;
import ru.vspochernin.finance_management.utils.PrintUtils;

@Service
public class CommandLineInterface {

    private final Map<CommandType, CommandHandler> commandHandlersMap;
    private final UserRepository userRepository;

    public CommandLineInterface(
            List<CommandHandler> commandHandlers,
            UserRepository userRepository)
    {
        this.commandHandlersMap = commandHandlers.stream()
                .collect(Collectors.toMap(
                        CommandHandler::getHandlingCommandType,
                        Function.identity(),
                        (a, b) -> a,
                        () -> new EnumMap<>(CommandType.class)));
        this.userRepository = userRepository;
    }

    public void run() {
        PrintUtils.printHelpMessage();

        Command currentCommand;
        while (FinanceManagementContext.isRunning) {
            Optional<User> currentUser = FinanceManagementContext.currentUserLogin.flatMap(userRepository::findByLogin);
            currentUser.ifPresent(User::notifyAboutBudget);
            currentUser.ifPresent(User::notifyAboutExpense);
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
