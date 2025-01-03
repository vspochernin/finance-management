package ru.vspochernin.finance_management.handler;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vspochernin.finance_management.command.CommandType;
import ru.vspochernin.finance_management.entity.User;
import ru.vspochernin.finance_management.exception.FinanceManagementException;
import ru.vspochernin.finance_management.repository.UserRepository;
import ru.vspochernin.finance_management.utils.PasswordUtils;
import ru.vspochernin.finance_management.utils.ValidationUtils;

@Service
@RequiredArgsConstructor
public class RegisterCommandHandler implements CommandHandler {

    private final UserRepository userRepository;

    @Override
    public void handle(List<String> arguments) {
        String login = arguments.get(0);
        if (userRepository.existsByLogin(login)) {
            throw new FinanceManagementException("Пользователь с таким логином уже существует");
        }

        String password = arguments.get(1);
        User user = User.builder()
                .login(login)
                .password(PasswordUtils.hashPassword(password))
                .build();

        userRepository.save(user);
        System.out.println("Регистрация прошла успешно");
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
