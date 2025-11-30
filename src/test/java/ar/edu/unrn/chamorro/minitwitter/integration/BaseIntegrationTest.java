package ar.edu.unrn.chamorro.minitwitter.integration;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public abstract class BaseIntegrationTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        // Equivalente al truncate del enunciado
        jdbcTemplate.execute("DELETE FROM tweets");
        jdbcTemplate.execute("DELETE FROM usuarios");
    }
}
