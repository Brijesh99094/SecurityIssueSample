package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
  private static final String DB_URL = "jdbc:sqlite:security_issues.db";

  public static Connection getConnection() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(DB_URL);
    }catch(SQLException e){
      e.printStackTrace();
    }
    return connection;
  }

  public static void initDatabase() {
    try {
      Connection conn = DriverManager.getConnection(DB_URL);
      Statement stmt = conn.createStatement();
      String sql = "CREATE TABLE IF NOT EXISTS security_issues ("
          + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
          + "title TEXT NOT NULL,"
          + "description TEXT,"
          + "severity TEXT NOT NULL,"
          + "owasp TEXT,"
          + "path TEXT,"
          + "startLine INTEGER,"
          + "endLine INTEGER,"
          + "codeLine TEXT"
          + ")";
      stmt.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
