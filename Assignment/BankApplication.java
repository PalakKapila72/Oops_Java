package com.bank;

import java.sql.Connection;
import java.sql.Statement;
import com.bank.util.DBConnection;

public class BankApplication {

    public static void main(String[] args) {

        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();

            // Create Customers Table
            String customersTable = "CREATE TABLE IF NOT EXISTS customers (" +
                    "customer_id INT PRIMARY KEY," +
                    "name VARCHAR(100))";

            // Create Accounts Table
            String accountsTable = "CREATE TABLE IF NOT EXISTS accounts (" +
                    "account_number BIGINT PRIMARY KEY," +
                    "balance DOUBLE," +
                    "customer_id INT," +
                    "FOREIGN KEY (customer_id) REFERENCES customers(customer_id))";

            // Create Loans Table
            String loansTable = "CREATE TABLE IF NOT EXISTS loans (" +
                    "loan_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "loan_amount DOUBLE," +
                    "interest_rate DOUBLE," +
                    "tenure INT," +
                    "customer_id INT," +
                    "FOREIGN KEY (customer_id) REFERENCES customers(customer_id))";

            // Execute queries
            stmt.executeUpdate(customersTable);
            stmt.executeUpdate(accountsTable);
            stmt.executeUpdate(loansTable);

            System.out.println("Tables created successfully!");

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
