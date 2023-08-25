package com.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/banking";
        String username = "root";
        String password = "";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            BankingOperations bankingOperations = new BankingOperations(connection);

            Scanner scanner = new Scanner(System.in);

            int choice = -1;
            while (choice != 0) {
                System.out.println("\n------------------BANKING SYSTEM------------------\n");
                System.out.println("1> Create Account");
                System.out.println("2> Update Account Details");
                System.out.println("3> Delete Account");
                System.out.println("4> Get Account Details");
                System.out.println("5> Get All Account Details");
                System.out.println("6> Deposit Money");
                System.out.println("7> Withdraw Money");
                System.out.println("8> Transfer Money");
                System.out.println("0> Exit..\n");
                System.out.println("Enter your choice: ");
                
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    switch (choice) {
                        case 1:
                            bankingOperations.createAccount();
                            break;
                        case 2:
                            bankingOperations.updateAccountDetails();
                            break;
                        case 3:
                            bankingOperations.deleteAccount();
                            break;
                        case 4:
                            bankingOperations.getAccountDetails();
                            break;
                        case 5:
                            bankingOperations.getAllAccountDetails();
                            break;
                        case 6:
                            bankingOperations.depositMoney();
                            break;
                        case 7:
                            bankingOperations.withdrawMoney();
                            break;
                        case 8:
                            bankingOperations.transferMoney();
                            break;
                        case 0:
                            System.out.println("------------------YOU HAVE SUCCESSFULLY EXITED THE SYSTEM------------------");
                            break;
                        default:
                            System.out.println("------------------Invalid choice------------------");
                    }
                }
                else {
                    System.out.println("------------------Invalid input. Please enter a valid choice------------------");
                    scanner.nextLine(); // Consume invalid input
                }
            }
            connection.close();
        } 
        
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

