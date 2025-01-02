package ru.vspochernin.finance_management.handler;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vspochernin.finance_management.command.CommandType;
import ru.vspochernin.finance_management.context.FinanceManagementContext;
import ru.vspochernin.finance_management.entity.User;
import ru.vspochernin.finance_management.repository.UserRepository;
import ru.vspochernin.finance_management.utils.InfoUtils;
import ru.vspochernin.finance_management.utils.ValidationUtils;

@Service
@RequiredArgsConstructor
public class InfoCommandHandler implements CommandHandler {

    private final UserRepository userRepository;

    @Override
    public void handle(List<String> arguments) {
        User user = userRepository.findByLogin(FinanceManagementContext.currentUserLogin.get()).get();
        InfoUtils.generateReport(user);
    }

    @Override
    public void validate(List<String> arguments) {
        ValidationUtils.validateAuthentication();
        ValidationUtils.validateArgumentsCount(arguments, getHandlingCommandType());
    }

    @Override
    public CommandType getHandlingCommandType() {
        return CommandType.INFO;
    }
}
