package ru.netology.cloud_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CloudApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudApiApplication.class, args);
    }

}
