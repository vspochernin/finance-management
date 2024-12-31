package ru.vspochernin.finance_management.handler;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vspochernin.finance_management.command.CommandType;
import ru.vspochernin.finance_management.context.FinanceManagementContext;
import ru.vspochernin.finance_management.entity.User;
import ru.vspochernin.finance_management.exception.FinanceManagementException;
import ru.vspochernin.finance_management.repository.UserRepository;
import ru.vspochernin.finance_management.utils.PasswordUtils;
import ru.vspochernin.finance_management.utils.ValidationUtils;

@Service
@RequiredArgsConstructor
public class LoginCommandHandler implements CommandHandler {

    private final UserRepository userRepository;

    @Override
    public void handle(List<String> arguments) {
        String login = arguments.get(0);
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new FinanceManagementException("Неправильный логин или пароль"));

        String password = arguments.get(1);
        String hashedPassword = user.getPassword();

        if (!PasswordUtils.checkPassword(password, hashedPassword)) {
            throw new FinanceManagementException("Неправильный логин или пароль");
        }

        FinanceManagementContext.currentUser = Optional.of(user);
        System.out.println("Аутентификация прошла успешно");
    }

    @Override
    public void validate(List<String> arguments) {
        ValidationUtils.validateArgumentsCount(arguments, getHandlingCommandType());
        ValidationUtils.validateLoginLength(arguments.get(0));
        ValidationUtils.validatePasswordLength(arguments.get(1));
    }

    @Override
    public CommandType getHandlingCommandType() {
        return CommandType.LOGIN;
    }
}
