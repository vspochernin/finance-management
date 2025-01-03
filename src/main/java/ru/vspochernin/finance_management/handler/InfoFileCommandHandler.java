package ru.vspochernin.finance_management.handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vspochernin.finance_management.command.CommandType;
import ru.vspochernin.finance_management.context.FinanceManagementContext;
import ru.vspochernin.finance_management.entity.User;
import ru.vspochernin.finance_management.exception.FinanceManagementException;
import ru.vspochernin.finance_management.repository.UserRepository;
import ru.vspochernin.finance_management.utils.InfoUtils;
import ru.vspochernin.finance_management.utils.ValidationUtils;

@Service
@RequiredArgsConstructor
public class InfoFileCommandHandler implements CommandHandler {

    private final UserRepository userRepository;

    @Override
    public void handle(List<String> arguments) {
        User user = userRepository.findByLogin(FinanceManagementContext.currentUserLogin.get()).get();

        Path path = Paths.get(arguments.get(0));
        if (Files.exists(path)) {
            throw new FinanceManagementException("Файл уже существует");
        }

        try {
            Files.write(path, InfoUtils.generateReport(user).getBytes());
        } catch (IOException e) {
            throw new FinanceManagementException("Невозможно записать информацию в данный файл");
        }

        System.out.println("Информация успешно записана в файл");
    }

    @Override
    public void validate(List<String> arguments) {
        ValidationUtils.validateAuthentication();
        ValidationUtils.validateArgumentsCount(arguments, getHandlingCommandType());
    }

    @Override
    public CommandType getHandlingCommandType() {
        return CommandType.INFO_FILE;
    }
}
