package ru.vspochernin.finance_management.handler;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vspochernin.finance_management.command.CommandType;
import ru.vspochernin.finance_management.context.FinanceManagementContext;
import ru.vspochernin.finance_management.entity.Category;
import ru.vspochernin.finance_management.entity.CategoryType;
import ru.vspochernin.finance_management.entity.User;
import ru.vspochernin.finance_management.exception.FinanceManagementException;
import ru.vspochernin.finance_management.repository.CategoryRepository;
import ru.vspochernin.finance_management.utils.ParsingUrils;
import ru.vspochernin.finance_management.utils.ValidationUtils;

@Service
@RequiredArgsConstructor
public class BudgetCommandHandler implements CommandHandler {

    private final CategoryRepository categoryRepository;

    @Override
    public void handle(List<String> arguments) {
        User user = FinanceManagementContext.currentUser.get();
        String title = ParsingUrils.parseCategoryTitle(arguments.get(0));

        Category category = categoryRepository.findByUserAndTitle(user, title)
                .orElseThrow(() -> new FinanceManagementException(
                        "У данного пользователя не существует категории с таким названием"));

        if (category.getType().equals(CategoryType.INCOME)) {
            throw new FinanceManagementException("Бюджет можно установить только на категорию расходов");
        }

        category.setBudget(ParsingUrils.parseMoney(arguments.get(1)));

        categoryRepository.save(category);
        System.out.println("Бюджет успешно установлен");
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
        return CommandType.BUDGET;
    }
}
