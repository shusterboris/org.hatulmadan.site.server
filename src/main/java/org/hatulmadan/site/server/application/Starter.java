package org.hatulmadan.site.server.application;

import org.hatulmadan.site.server.application.data.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Starter  {
    @Autowired
    UserDAO userDAO;

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
        AppConfig.log.info("Успешно запущен");
    }

}
