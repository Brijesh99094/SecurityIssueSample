package org.example;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    DatabaseConnection.initDatabase();
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("Security Issue Tracking System");
      System.out.println("1. List Issues");
      System.out.println("2. Add Issue");
      System.out.println("3. Edit Issue");
      System.out.println("4. Delete Issue");
      System.out.println("5. Exit");
      System.out.print("Enter your choice: ");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume the newline

      switch (choice) {
        case 1 -> SecurityIssue.listIssues();
        case 2 -> SecurityIssue.addIssue();
        case 3 -> SecurityIssue.editIssue();
        case 4 -> SecurityIssue.deleteIssue();
        case 5 -> {
          System.out.println("Exiting...");
          scanner.close();
          System.exit(0);
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

}