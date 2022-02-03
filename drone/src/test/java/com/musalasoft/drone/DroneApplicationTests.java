package com.musalasoft.drone;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.PostgreSQLR2DBCDatabaseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;
import static java.lang.String.format;
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
        var postgresOptions = PostgreSQLR2DBCDatabaseContainer.getOptions(postgres);

        // R2DBC
        registry.add("spring.r2dbc.url", () ->
                format("r2dbc:postgresql://%s:%d/%s",
                        postgresOptions.getValue(HOST),
                        postgresOptions.getValue(PORT),
                        postgresOptions.getValue(DATABASE)));
        registry.add("spring.r2dbc.username", () -> postgresOptions.getValue(USER));
        registry.add("spring.r2dbc.password", () -> postgresOptions.getValue(PASSWORD));

        // LIQUIBASE
        registry.add("spring.liquibase.url", postgres::getJdbcUrl);
        registry.add("spring.liquibase.user", () -> postgresOptions.getValue(USER));
        registry.add("spring.liquibase.password", () -> postgresOptions.getValue(PASSWORD));
    }

    @Test
    @DisplayName("application should start with all dependencies configured")
    void contextLoads() {
    }

}
