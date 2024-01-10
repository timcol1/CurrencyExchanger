package avlyakulov.timur;

import java.sql.*;
import java.util.Scanner;

public class App {


    public static void main(String[] args) {
        App app = new App();

        app.open();
    }


    void open() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sqlite/users.db")) {
            System.out.println("Connected");
            select(connection);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    void insert(Connection connection) {
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

    void select(Connection connection) {
        String query = "SELECT * from users;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("phone"));
                System.out.println(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Rows were taken");
    }
}

class User {
    private int id;
    private String name;

    private String phone;

    public User(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}