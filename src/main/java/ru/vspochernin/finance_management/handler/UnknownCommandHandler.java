package ru.vspochernin.finance_management.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.vspochernin.finance_management.command.CommandType;
import ru.vspochernin.finance_management.exception.FinanceManagementException;

@Service
public class UnknownCommandHandler implements CommandHandler {

    @Override
    public void handle(List<String> arguments) {
        throw new FinanceManagementException("Неизвестная команда");
    }

    @Override
    public void validate(List<String> arguments) {
    }

    @Override
    public CommandType getHandlingCommandType() {
        return CommandType.UNKNOWN;
    }
}
