package org.btg;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import io.agroal.api.AgroalDataSource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class MigrationTest {

  @Inject
  AgroalDataSource dataSource;

  @Test
  public void testTableExists() throws SQLException {
    try (Connection conn = dataSource.getConnection()) {
      ResultSet rs = conn.getMetaData().getTables(null, null, "order", null);
      assertTrue(rs.next(), "Table ORDER should exist after migration");
    }
  }

}
