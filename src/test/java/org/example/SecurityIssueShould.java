package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class SecurityIssueShould {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayInputStream inContent = new ByteArrayInputStream("Test Title\nTest Description\nHigh\n".getBytes());
  private final InputStream originalIn = System.in;
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(new ByteArrayOutputStream()));
  }

  @After
  public void restoreStreams() {
    System.setIn(originalIn);
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @Before
  public void createDatabase() {
    // Create an SQLite database for testing
    String dbURL = "jdbc:sqlite:test_security_issues.db";
    try (Connection conn = DriverManager.getConnection(dbURL);
        Statement stmt = conn.createStatement()) {
      String sql = "CREATE TABLE IF NOT EXISTS security_issues ("
          + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
          + "title TEXT NOT NULL,"
          + "description TEXT,"
          + "severity TEXT NOT NULL)";
      stmt.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testAddIssue() throws SQLException {

    System.setIn(inContent);
    System.out.println(inContent.toString());

    // Save the original database connection and statement
    Connection testConnection = DatabaseConnection.getConnection();

    // Create a test database connectio

    // Verify that the issue was added successfully
    String expectedOutput = "Enter title: Enter description: Enter severity: Issue added successfully.\n";
    System.out.println(outContent);
    assertEquals(expectedOutput, outContent.toString());
    // Verify that the issue exists in the test database
    try (Connection conn = testConnection;
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM security_issues")) {
      resultSet.next();
      String title = resultSet.getString("title");
      String description = resultSet.getString("description");
      String severity = resultSet.getString("severity");
      assertEquals("Test Title", title);
      assertEquals("Test Description", description);
      assertEquals("High", severity);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}