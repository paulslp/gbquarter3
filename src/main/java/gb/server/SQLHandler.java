package gb.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLHandler {
    private static Connection connection;
    private static Statement statement;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPassword(String login, String password) {

        return getNickByFilter("WHERE login ='" + login + "' AND password = '" + password + "'");
    }

    public static boolean isExistsNick(String nickname) {

        return (getNickByFilter("WHERE nickname ='" + nickname + "'") != null);
    }

    public static String getNickByFilter(String sqlFilter) {

        try {
            ResultSet rs = statement.executeQuery("SELECT nickname FROM users " + sqlFilter);
            if (rs.next()) {
                return rs.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
