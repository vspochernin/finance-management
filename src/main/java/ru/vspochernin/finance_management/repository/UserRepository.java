package ru.vspochernin.finance_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vspochernin.finance_management.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);
}
