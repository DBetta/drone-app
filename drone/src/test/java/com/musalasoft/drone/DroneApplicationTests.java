package com.musalasoft.drone;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.testcontainers.utility.DockerImageName.parse;

@SpringBootTest
@Testcontainers
class DroneApplicationTests {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(parse("postgres:latest"))
            .withPassword("s3cr3t")
            .withUsername("postgres")
            .withDatabaseName("drone_app");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {

        // LIQUIBASE
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    @DisplayName("application should start with all dependencies configured")
    void contextLoads() {
    }

}
