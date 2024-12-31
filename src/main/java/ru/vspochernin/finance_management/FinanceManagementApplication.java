package ru.vspochernin.finance_management;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.vspochernin.finance_management.command.CommandLineInterface;

@SpringBootApplication
@RequiredArgsConstructor
public class FinanceManagementApplication implements CommandLineRunner {

    private final CommandLineInterface commandLineInterface;

    public static void main(String[] args) {
        SpringApplication.run(FinanceManagementApplication.class, args);
    }

    @Override
    public void run(String... args) {
        commandLineInterface.run();
    }
}
