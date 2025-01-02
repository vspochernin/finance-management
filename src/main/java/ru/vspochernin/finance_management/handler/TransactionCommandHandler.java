package ru.vspochernin.finance_management.handler;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vspochernin.finance_management.command.CommandType;
import ru.vspochernin.finance_management.context.FinanceManagementContext;
import ru.vspochernin.finance_management.entity.Category;
import ru.vspochernin.finance_management.entity.Transaction;
import ru.vspochernin.finance_management.entity.User;
import ru.vspochernin.finance_management.exception.FinanceManagementException;
import ru.vspochernin.finance_management.repository.CategoryRepository;
import ru.vspochernin.finance_management.repository.TransactionRepository;
import ru.vspochernin.finance_management.repository.UserRepository;
import ru.vspochernin.finance_management.utils.MoneyUtils;
import ru.vspochernin.finance_management.utils.ParsingUrils;
import ru.vspochernin.finance_management.utils.ValidationUtils;

@Service
@RequiredArgsConstructor
public class TransactionCommandHandler implements CommandHandler {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public void handle(List<String> arguments) {
        User user = userRepository.findByLogin(FinanceManagementContext.currentUserLogin.get()).get();
        String title = ParsingUrils.parseCategoryTitle(arguments.get(0));

        Category category = categoryRepository.findByUserAndTitle(user, title)
                .orElseThrow(() -> new FinanceManagementException(
                        "У данного пользователя не существует категории с таким названием"));

        Transaction transaction = Transaction.builder()
                .category(category)
                .amount(MoneyUtils.parseMoney(arguments.get(1)))
                .datetime(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);
        System.out.println("Транзакция успешно записана");
    }

    @Override
    public void validate(List<String> arguments) {
        ValidationUtils.validateAuthentication();
        ValidationUtils.validateArgumentsCount(arguments, getHandlingCommandType());
        ValidationUtils.validateCategoryTitleLength(arguments.get(0));
        ValidationUtils.validateMoneyLength(arguments.get(1));
    }

    @Override
    public CommandType getHandlingCommandType() {
        return CommandType.TRANSACTION;
    }
}
