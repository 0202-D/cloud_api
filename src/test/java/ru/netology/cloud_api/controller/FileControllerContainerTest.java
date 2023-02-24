package ru.netology.cloud_api.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import ru.netology.cloud_api.dto.AuthRq;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileControllerContainerTest {

    @Autowired
    TestRestTemplate template;
    public static GenericContainer<?> app = new GenericContainer("cloud_api").withExposedPorts(9090);

    @BeforeAll
    public static void setUp() {
        app.start();
    }

    @Test
    void unauthorized_expect401() {
        ResponseEntity<String> response = template.getForEntity("/list", String.class);
        var expected = HttpStatus.UNAUTHORIZED.value();
        var actual = response.getStatusCodeValue();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void whenGetUser_thenCorrect() {
        AuthRq authRq = AuthRq.builder().login("user").password("user").build();

        ResponseEntity<String> response = template
                .postForEntity("/login", authRq, String.class);

        var expected = HttpStatus.OK.value();
        var actual = response.getStatusCodeValue();
        Assertions.assertEquals(expected, actual);
    }
}
