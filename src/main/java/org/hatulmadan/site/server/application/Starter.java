package org.hatulmadan.site.server.application;

import org.hatulmadan.site.server.application.data.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Starter implements CommandLineRunner {
    @Autowired
    UserDAO userDAO;

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
        AppConfig.log.info("Успешно запущен");
    }

    @Override
    public void run(String... args) throws Exception {
        /*
		User userAdmin = new User();
		System.out.println("Ищем");
        userAdmin.setUsername("super");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userAdmin.setPassword(encoder.encode("meow!"));
        System.out.println("Пишем");
        userDAO.save(userAdmin);
        System.out.println("Готово");
         */
    }
}
