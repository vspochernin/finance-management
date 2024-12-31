package ru.vspochernin.finance_management.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.vspochernin.finance_management.command.CommandType;
import ru.vspochernin.finance_management.context.FinanceManagementContext;
import ru.vspochernin.finance_management.utils.ValidationUtils;

@Service
public class ExitCommandHandler implements CommandHandler {

    @Override
    public void handle(List<String> arguments) {
        System.out.println("Будет выполнен выход из программы");
        FinanceManagementContext.isRunning = false;
    }

    @Override
    public void validate(List<String> arguments) {
        ValidationUtils.validateEmptyArguments(arguments);
    }

    @Override
    public CommandType getHandlingCommandType() {
        return CommandType.EXIT;
    }
}
