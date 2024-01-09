package avlyakulov.timur;

import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) {
        App app = new App();

        app.open();
        app.insert();
        app.close();
    }

    Connection connection;

    void open() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:sqlite/currencies.db");
            System.out.println("Connected");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void insert() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user's name:");
        String name = scanner.nextLine();
        System.out.print("Enter user's phone:");
        String phone = scanner.nextLine();
        String query = "insert into users (name, phone) " +
                "values (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Rows added");
    }


    void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}