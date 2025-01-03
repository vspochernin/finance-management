package ru.vspochernin.finance_management.handler;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import ru.vspochernin.finance_management.command.CommandType;
import ru.vspochernin.finance_management.context.FinanceManagementContext;
import ru.vspochernin.finance_management.entity.Category;
import ru.vspochernin.finance_management.entity.Transaction;
import ru.vspochernin.finance_management.entity.User;
import ru.vspochernin.finance_management.repository.UserRepository;
import ru.vspochernin.finance_management.utils.MoneyUtils;
import ru.vspochernin.finance_management.utils.ValidationUtils;

@Service
public class InfoCertainCommandHandler implements CommandHandler {

    private final UserRepository userRepository;

    public InfoCertainCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void handle(List<String> arguments) {
        User user = userRepository.findByLogin(FinanceManagementContext.currentUserLogin.get()).get();

        Map<String, Category> categoryTitleToCategoryMap = user.getCategories().stream()
                .collect(Collectors.toMap(Category::getTitle, Function.identity()));

        arguments.forEach(title -> {
            Optional<Category> categoryO = Optional.ofNullable(categoryTitleToCategoryMap.get(title));

            if (categoryO.isEmpty()) {
                System.out.println("- Категории: " + title + " не найдено");
                return;
            }
            Category category = categoryO.get();

            System.out.println("- Категория: " + title + ", тип: " + category.getType().toRusType());
            category.getTransactions().stream()
                    .sorted(Comparator.comparing(Transaction::getDatetime))
                    .forEach(it -> System.out.println(
                            "  - Транзакция на сумму: " + MoneyUtils.convertToRubles(it.getAmount()) + ", произведена: "
                                    + it.getDatetime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy в HH:mm"))));
        });
    }

    @Override
    public void validate(List<String> arguments) {
        ValidationUtils.validateAuthentication();
        ValidationUtils.validateNotEmptyArguments(arguments);
    }

    @Override
    public CommandType getHandlingCommandType() {
        return CommandType.INFO_CERTAIN;
    }
}
