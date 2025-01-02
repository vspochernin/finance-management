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
import ru.vspochernin.finance_management.repository.UserRepository;
import ru.vspochernin.finance_management.utils.ParsingUrils;
import ru.vspochernin.finance_management.utils.ValidationUtils;

@Service
@RequiredArgsConstructor
public class CategoryCommandHandler implements CommandHandler {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void handle(List<String> arguments) {
        User user = userRepository.findByLogin(FinanceManagementContext.currentUserLogin.get()).get();
        String title = ParsingUrils.parseCategoryTitle(arguments.get(1));

        if (categoryRepository.existsByUserAndTitle(user, title)) {
            throw new FinanceManagementException("У данного пользователя уже существует категория с таким названием");
        }

        CategoryType categoryType = CategoryType.parse(arguments.get(0));
        Category category = Category.builder()
                .user(user)
                .title(title)
                .type(categoryType)
                .build();

        categoryRepository.save(category);
        System.out.println("Категория успешно создана");
    }

    @Override
    public void validate(List<String> arguments) {
        ValidationUtils.validateAuthentication();
        ValidationUtils.validateArgumentsCount(arguments, getHandlingCommandType());
        ValidationUtils.validateCategoryTitleLength(arguments.get(1));
    }

    @Override
    public CommandType getHandlingCommandType() {
        return CommandType.CATEGORY;
    }
}
