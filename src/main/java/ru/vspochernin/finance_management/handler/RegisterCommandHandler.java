package ru.vspochernin.finance_management.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.vspochernin.finance_management.command.CommandType;
import ru.vspochernin.finance_management.utils.ValidationUtils;

@Service
public class RegisterCommandHandler implements CommandHandler {

    @Override
    public void handle(List<String> arguments) {
        System.out.println("TODO: регистрация");
    }

    @Override
    public void validate(List<String> arguments) {
        ValidationUtils.validateArgumentsCount(arguments, getHandlingCommandType());
        ValidationUtils.validateLoginLength(arguments.get(0));
        ValidationUtils.validatePasswordLength(arguments.get(1));
    }

    @Override
    public CommandType getHandlingCommandType() {
        return CommandType.REGISTER;
    }
}
