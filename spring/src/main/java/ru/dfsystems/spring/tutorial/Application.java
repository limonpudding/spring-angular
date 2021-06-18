package ru.dfsystems.spring.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Application {

    /**
     * Точка входа в приложение, старт инициализации всех компонентов приложения.
      * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
