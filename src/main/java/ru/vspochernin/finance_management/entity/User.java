package ru.vspochernin.finance_management.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Category> categories;

    public static String getCurrentUserLogin() {
        return FinanceManagementContext.currentUser
                .map(User::getLogin)
                .orElse(LOGOUT_USER_LOGIN_STUB);
    }
}
