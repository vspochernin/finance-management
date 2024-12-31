package ru.vspochernin.finance_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vspochernin.finance_management.entity.Category;
import ru.vspochernin.finance_management.entity.User;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByUserAndTitle(User user, String title);

    Optional<Category> findByUserAndTitle(User user, String title);
}
