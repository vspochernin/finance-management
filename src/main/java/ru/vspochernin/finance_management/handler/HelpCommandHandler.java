package ru.vspochernin.finance_management.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.vspochernin.finance_management.command.CommandType;
import ru.vspochernin.finance_management.utils.PrintUtils;
import ru.vspochernin.finance_management.utils.ValidationUtils;

@Service
public class HelpCommandHandler implements CommandHandler {

    @Override
    public void handle(List<String> arguments) {
        PrintUtils.printHelpMessage();
    }

    @Override
    public void validate(List<String> arguments) {
        ValidationUtils.validateArgumentsCount(arguments, getHandlingCommandType());
    }

    @Override
    public CommandType getHandlingCommandType() {
        return CommandType.HELP;
    }
}
