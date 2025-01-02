package ru.vspochernin.finance_management.entity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vspochernin.finance_management.context.FinanceManagementContext;
import ru.vspochernin.finance_management.utils.MoneyUtils;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    private static final String LOGOUT_USER_LOGIN_STUB = "неидентифицирован";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "links_id_seq")
    private int id;

    private String login;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Category> categories;

    public static String getCurrentUserLogin() {
        return FinanceManagementContext.currentUserLogin
                .orElse(LOGOUT_USER_LOGIN_STUB);
    }

    public Map<String, Long> getIncomeByCategory() {
        return categories.stream()
                .filter(category -> category.getType() == CategoryType.INCOME)
                .collect(Collectors.toMap(
                        Category::getTitle,
                        category -> category.getTransactions().stream()
                                .mapToLong(Transaction::getAmount)
                                .sum()));
    }

    public Map<String, ExpenseInfo> getExpenseInfoByCategory() {
        return categories.stream()
                .filter(category -> category.getType() == CategoryType.EXPENSE)
                .collect(Collectors.toMap(
                        Category::getTitle,
                        category -> {
                            long expense = category.getTransactions().stream()
                                    .mapToLong(Transaction::getAmount)
                                    .sum();
                            Optional<Long> budget = category.getBudgetO();
                            return new ExpenseInfo(expense, budget);
                        }
                ));
    }

    public void notifyAboutBudget() {
        Map<String, ExpenseInfo> expenseInfoByCategory = getExpenseInfoByCategory();
        expenseInfoByCategory.entrySet().stream()
                .filter(entry -> entry.getValue().budget().isPresent())
                .filter(entry -> entry.getValue().budget().get() < entry.getValue().expense())
                .forEach(entry -> System.out.println("Внимание! Превышен бюджет по категории: " + entry.getKey()
                        + " (" + MoneyUtils.convertToRubles(entry.getValue().expense()) + "/"
                        + MoneyUtils.convertToRubles(entry.getValue().budget().get()) + ")"));
    }
}
