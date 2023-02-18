package ru.netology.cloud_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.netology.cloud_api.dao.UserRepository;
import ru.netology.cloud_api.entity.UserDB;

@SpringBootApplication
public class CloudApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudApiApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(UserRepository users) {
        return args -> users.save(new UserDB(1,"user",null, "user"));
    }
}
