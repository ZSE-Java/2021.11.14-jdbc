package pl.edu.zse.jdbc;

import pl.edu.zse.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static Connection conn = null;

    public static void main(String[] args) {
        connect();
        /*User user = new User(1, "admin", "admin", "Janek");
        addUser(user);
        deleteUser(2);*/

        List<User> users = getAllUsers();
        System.out.println(users);
    }

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            App.conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test3?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=utf8","root","");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User user) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO tuser VALUES (null, '")
                    .append(user.getLogin())
                    .append("', '")
                    .append(user.getPass())
                    .append("', '")
                    .append(user.getName())
                    .append("');");

            Statement statement = App.conn.createStatement();
            statement.execute(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(int id) {
        try {
            String sql = "DELETE FROM tuser WHERE id = " + id;

            Statement statement = App.conn.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tuser";

            Statement statement = App.conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String login = rs.getString("login");
                String password = rs.getString("password");

                User user = new User();
                user.setId(id);
                user.setLogin(login);
                user.setName(name);
                user.setPass(password);

                result.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
