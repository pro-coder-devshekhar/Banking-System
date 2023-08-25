package com.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class BankingOperations {
    private Connection connection;

    public BankingOperations(Connection connection) {
        this.connection = connection;
    }

    public void createAccount() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter account number: ");
            int accountNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            // Check if the account number already exists
            String checkQuery = "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, accountNumber);
            ResultSet checkResult = checkStatement.executeQuery();

            if (checkResult.next()) {
                System.out.println(
                        "------------------An account with this account number already exists------------------");
                checkStatement.close();
                return;
            }
            checkStatement.close();

            System.out.println("Enter customer's name: ");
            String customerName = scanner.nextLine();

            System.out.println("Enter account type: ");
            String accountType = scanner.nextLine();

            System.out.println("Enter initial balance: ");
            double initialBalance = scanner.nextDouble();
            scanner.nextLine(); // Consume newline character

            System.out.println("Enter Aadhar card number: ");
            String aadharNumber = scanner.nextLine();

            String insertQuery = "INSERT INTO accounts (customer_name, account_number, account_type, balance, aadhar_number) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, customerName);
            preparedStatement.setInt(2, accountNumber);
            preparedStatement.setString(3, accountType);
            preparedStatement.setDouble(4, initialBalance);
            preparedStatement.setString(5, aadharNumber);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("------------------Account created successfully------------------");
            } else {
                System.out.println("------------------Failed to create account------------------");
            }

            preparedStatement.close();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        catch (RuntimeException e) {
            System.out.println("------------------Invalid input------------------");
        }
    }

    public void updateAccountDetails() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter account number: ");
            int accountNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            // Check if the account exists
            String checkQuery = "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, accountNumber);
            ResultSet checkResult = checkStatement.executeQuery();

            if (!checkResult.next()) {
                System.out.println("------------------Account not found------------------");
                checkStatement.close();
                return;
            }
            checkStatement.close();

            System.out.println("Enter new customer's name: ");
            String newCustomerName = scanner.nextLine();

            System.out.println("Enter account type: ");
            String accountType = scanner.nextLine();

            String updateQuery = "UPDATE accounts SET customer_name = ?, account_type = ? WHERE account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, newCustomerName);
            preparedStatement.setString(2, accountType);
            preparedStatement.setInt(3, accountNumber);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("------------------Account details updated successfully------------------");
            } else {
                System.out.println("------------------Failed to update account details------------------");
            }

            preparedStatement.close();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        catch (RuntimeException e) {
            System.out.println("------------------Invalid input------------------");
        }
    }

    public void deleteAccount() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter account number: ");
            int accountNumber = scanner.nextInt();

            // Check if the account exists
            String checkQuery = "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, accountNumber);
            ResultSet checkResult = checkStatement.executeQuery();

            if (!checkResult.next()) {
                System.out.println("------------------Account not found------------------");
                checkStatement.close();
                return;
            }
            checkStatement.close();

            String deleteQuery = "DELETE FROM accounts WHERE account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, accountNumber);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("------------------Account deleted successfully------------------");
            } else {
                System.out.println("------------------Failed to delete account------------------");
            }

            preparedStatement.close();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        catch (RuntimeException e) {
            System.out.println("------------------Invalid input------------------");
        }
    }

    public void getAccountDetails() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter account number: ");
            int accountNumber = scanner.nextInt();

            // Check if the account exists
            String checkQuery = "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, accountNumber);
            ResultSet checkResult = checkStatement.executeQuery();

            if (!checkResult.next()) {
                System.out.println("------------------Account not found------------------");
                checkStatement.close();
                return;
            }
            checkStatement.close();

            String selectQuery = "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String customerName = resultSet.getString("customer_name");
                String accountType = resultSet.getString("account_type");
                double balance = resultSet.getDouble("balance");
                String aadharNumber = resultSet.getString("aadhar_number");

                System.out.println("-------------------------------");
                System.out.println("Customer Name: " + customerName);
                System.out.println("Account Type: " + accountType);
                System.out.println("Balance: " + balance);
                System.out.println("Aadhar Number: " + aadharNumber);
                System.out.println("-------------------------------");
            }

            preparedStatement.close();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        catch (RuntimeException e) {
            System.out.println("------------------Invalid input------------------");
        }
    }

    public void getAllAccountDetails() {
        try {
            String selectAllQuery = "SELECT * FROM accounts";
            PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean hasAccounts = false;

            // System.out.println("Customer Name | Account Number | Account Type | Balance |
            // Aadhar Number");
            // System.out.println("--------------------------------------------------------------------------------");

            // while (resultSet.next()) {
            // hasAccounts = true;
            // String customerName = resultSet.getString("customer_name");
            // int accountNumber = resultSet.getInt("account_number");
            // String accountType = resultSet.getString("account_type");
            // double balance = resultSet.getDouble("balance");
            // String aadharNumber = resultSet.getString("aadhar_number");

            // System.out.println(customerName + " | " + accountNumber + " | " + accountType
            // + " | " + balance + " | " + aadharNumber);
            // System.out.println("--------------------------------------------------------------------------------");
            // }

            System.out.println(String.format("%-15s %-25s %-15s %-15s %-15s",
                    "Account Number", "Customer Name", "Account Type", "Balance", "Aadhar Number"));
            System.out.println("---------------------------------------------------------------------------------------");

            while (resultSet.next()) {
                hasAccounts = true;
                int accountNumber = resultSet.getInt("account_number");
                String customerName = resultSet.getString("customer_name");
                String accountType = resultSet.getString("account_type");
                double balance = resultSet.getDouble("balance");
                String aadharNumber = resultSet.getString("aadhar_number");

                System.out.println(String.format("%-15d %-25s %-15s %-15.2f %-15s",
                        accountNumber, customerName, accountType, balance, aadharNumber));
                System.out.println("---------------------------------------------------------------------------------------");
            }

            if (!hasAccounts) {
                System.out.println("------------------No accounts found------------------");
            }

            preparedStatement.close();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        catch (RuntimeException e) {
            System.out.println("------------------Invalid input------------------");
        }
    }

    public void depositMoney() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter account number: ");
            int accountNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            // Check if the account exists
            String checkQuery = "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, accountNumber);
            ResultSet checkResult = checkStatement.executeQuery();

            if (!checkResult.next()) {
                System.out.println("------------------Account not found------------------");
                checkStatement.close();
                return;
            }
            checkStatement.close();

            System.out.println("Enter amount to deposit: ");
            double depositAmount = scanner.nextDouble();

            // Check if the deposit amount is non-negative
            if (depositAmount <= 0) {
                System.out.println("------------------Invalid deposit amount------------------");
                return;
            }

            String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(depositQuery);
            preparedStatement.setDouble(1, depositAmount);
            preparedStatement.setInt(2, accountNumber);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("------------------Amount deposited successfully------------------");
            } else {
                System.out.println("------------------Failed to deposit amount------------------");
            }

            preparedStatement.close();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        catch (RuntimeException e) {
            System.out.println("------------------Invalid input------------------");
        }
    }

    public void withdrawMoney() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter account number: ");
            int accountNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            // Check if the account exists
            String checkQuery = "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, accountNumber);
            ResultSet checkResult = checkStatement.executeQuery();

            if (!checkResult.next()) {
                System.out.println("------------------Account not found------------------");
                checkStatement.close();
                return;
            }
            checkStatement.close();

            System.out.println("Enter amount to withdraw: ");
            double withdrawAmount = scanner.nextDouble();

            // Check if the withdrawal amount is non-negative
            if (withdrawAmount <= 0) {
                System.out.println("------------------Invalid withdrawal amount------------------");
                return;
            }

            // Check if the account has sufficient balance
            String balanceQuery = "SELECT balance FROM accounts WHERE account_number = ?";
            PreparedStatement balanceStatement = connection.prepareStatement(balanceQuery);
            balanceStatement.setInt(1, accountNumber);
            ResultSet balanceResult = balanceStatement.executeQuery();

            if (balanceResult.next()) {
                double currentBalance = balanceResult.getDouble("balance");
                if (withdrawAmount > currentBalance) {
                    System.out.println("------------------Insufficient balance------------------");
                    return;
                }
            }
            balanceStatement.close();

            String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(withdrawQuery);
            preparedStatement.setDouble(1, withdrawAmount);
            preparedStatement.setInt(2, accountNumber);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("------------------Amount withdrawn successfully------------------");
            } else {
                System.out.println("------------------Failed to withdraw amount------------------");
            }

            preparedStatement.close();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        catch (RuntimeException e) {
            System.out.println("------------------Invalid input------------------");
        }
    }

    public void transferMoney() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter source account number: ");
            int sourceAccountNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            System.out.println("Enter target account number: ");
            int targetAccountNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            // Check if the source account exists
            String sourceCheckQuery = "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement sourceCheckStatement = connection.prepareStatement(sourceCheckQuery);
            sourceCheckStatement.setInt(1, sourceAccountNumber);
            ResultSet sourceCheckResult = sourceCheckStatement.executeQuery();

            if (!sourceCheckResult.next()) {
                System.out.println("------------------Source account not found------------------");
                sourceCheckStatement.close();
                return;
            }
            sourceCheckStatement.close();

            // Check if the target account exists
            String targetCheckQuery = "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement targetCheckStatement = connection.prepareStatement(targetCheckQuery);
            targetCheckStatement.setInt(1, targetAccountNumber);
            ResultSet targetCheckResult = targetCheckStatement.executeQuery();

            if (!targetCheckResult.next()) {
                System.out.println("------------------Target account not found------------------");
                targetCheckStatement.close();
                return;
            }
            targetCheckStatement.close();

            System.out.println("Enter amount to transfer: ");
            double transferAmount = scanner.nextDouble();

            // Check if the transfer amount is non-negative
            if (transferAmount <= 0) {
                System.out.println("------------------Invalid transfer amount------------------");
                return;
            }

            // Check if the source account has sufficient balance
            String sourceBalanceQuery = "SELECT balance FROM accounts WHERE account_number = ?";
            PreparedStatement sourceBalanceStatement = connection.prepareStatement(sourceBalanceQuery);
            sourceBalanceStatement.setInt(1, sourceAccountNumber);
            ResultSet sourceBalanceResult = sourceBalanceStatement.executeQuery();

            if (sourceBalanceResult.next()) {
                double sourceBalance = sourceBalanceResult.getDouble("balance");
                if (transferAmount > sourceBalance) {
                    System.out.println("------------------Insufficient balance in source account------------------");
                    return;
                }
            }
            sourceBalanceStatement.close();

            // Perform the transfer
            String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            PreparedStatement withdrawPreparedStatement = connection.prepareStatement(withdrawQuery);
            withdrawPreparedStatement.setDouble(1, transferAmount);
            withdrawPreparedStatement.setInt(2, sourceAccountNumber);

            String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement depositPreparedStatement = connection.prepareStatement(depositQuery);
            depositPreparedStatement.setDouble(1, transferAmount);
            depositPreparedStatement.setInt(2, targetAccountNumber);

            connection.setAutoCommit(false);

            int rowsUpdatedWithdraw = withdrawPreparedStatement.executeUpdate();
            int rowsUpdatedDeposit = depositPreparedStatement.executeUpdate();

            if (rowsUpdatedWithdraw > 0 && rowsUpdatedDeposit > 0) {
                connection.commit();
                System.out.println("------------------Money transferred successfully------------------");
            } else {
                connection.rollback();
                System.out.println("------------------Failed to transfer money------------------");
            }

            withdrawPreparedStatement.close();
            depositPreparedStatement.close();
            connection.setAutoCommit(true);
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        catch (RuntimeException e) {
            System.out.println("------------------Invalid input------------------");
        }
    }
}