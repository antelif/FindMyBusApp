package com.antelif.findmybusapp;

import com.antelif.findmybusapp.config.ConfiguredPostgresqlContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class BaseIntegrationTest {

  private static final ConfiguredPostgresqlContainer postgres;

  static {
    postgres = ConfiguredPostgresqlContainer.getInstance();
    postgres.start();
  }


}
