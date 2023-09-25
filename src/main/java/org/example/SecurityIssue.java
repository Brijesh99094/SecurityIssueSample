package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SecurityIssue {

  public  static  Scanner scanner = new Scanner(System.in);
  public  static DatabaseConnection db = new DatabaseConnection();
  public static void listIssues() {
    try (Connection conn = db.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM security_issues")) {
      System.out.println("Security Issues:");
      while (rs.next()) {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        String severity = rs.getString("severity");
        String owasp = rs.getString("owasp");
        String path = rs.getString("path");
        int startLine = rs.getInt("startLine");
        int endLine = rs.getInt("endLine");
        String codeLine = rs.getString("codeLine");
        System.out.println("ID: " + id + ", Title: " + title + ", Description: " + description +
            ", Severity: " + severity + ", OWASP: " + owasp + ", Path: " + path +
            ", Start Line: " + startLine + ", End Line: " + endLine + ", Code Line: " + codeLine);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void addIssue() {
    System.out.print("Enter title: ");
    String title = scanner.nextLine();
    System.out.print("Enter description: ");
    String description = scanner.nextLine();
    System.out.print("Enter severity: ");
    String severity = scanner.nextLine();
    System.out.print("Enter OWASP category: ");
    String owasp = scanner.nextLine();
    System.out.print("Enter file path: ");
    String path = scanner.nextLine();
    System.out.print("Enter start line number: ");
    int startLine = scanner.nextInt();
    System.out.print("Enter end line number: ");
    int endLine = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Enter line of code: ");
    String codeLine = scanner.nextLine();

    try (Connection conn = db.getConnection();
        Statement stmt = conn.createStatement()) {
      String insertQuery = "INSERT INTO security_issues (title, description, severity, owasp, path, startLine, endLine, codeLine) " +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement pstmt = conn.prepareStatement(insertQuery);
      pstmt.setString(1, title);
      pstmt.setString(2, description);
      pstmt.setString(3, severity);
      pstmt.setString(4, owasp);
      pstmt.setString(5, path);
      pstmt.setInt(6, startLine);
      pstmt.setInt(7, endLine);
      pstmt.setString(8, codeLine);
      pstmt.executeUpdate();
      System.out.println("Issue added successfully.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void editIssue() {
    System.out.print("Enter issue ID to edit: ");
    int id = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Enter new title: ");
    String title = scanner.nextLine();
    System.out.print("Enter new description: ");
    String description = scanner.nextLine();
    System.out.print("Enter new severity: ");
    String severity = scanner.nextLine();
    System.out.print("Enter new OWASP category: ");
    String owasp = scanner.nextLine();
    System.out.print("Enter new file path: ");
    String path = scanner.nextLine();
    System.out.print("Enter new start line number: ");
    int startLine = scanner.nextInt();
    System.out.print("Enter new end line number: ");
    int endLine = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Enter new line of code: ");
    String codeLine = scanner.nextLine();
    try (Connection conn = db.getConnection();
        Statement stmt = conn.createStatement()) {
      String updateQuery = "UPDATE security_issues SET title = '" + title + "', description = '" + description + "', severity = '" + severity + "', " +
          "owasp = '" + owasp + "', path = '" + path + "', startLine = " + startLine + ", endLine = " + endLine + ", codeLine = '" + codeLine + "' WHERE id = " + id;
      stmt.executeUpdate(updateQuery);

      System.out.println("Issue updated successfully.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void deleteIssue() {
    System.out.print("Enter issue ID to delete: ");
    int id = scanner.nextInt();

    try (Connection conn = db.getConnection();
        Statement stmt = conn.createStatement()) {
      String deleteQuery = String.format("DELETE FROM security_issues WHERE id = %d", id);
      stmt.executeUpdate(deleteQuery);
      System.out.println("Issue deleted successfully.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
