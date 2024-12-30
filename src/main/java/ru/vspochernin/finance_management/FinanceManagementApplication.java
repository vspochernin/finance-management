package ru.vspochernin.finance_management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinanceManagementApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FinanceManagementApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }
}
