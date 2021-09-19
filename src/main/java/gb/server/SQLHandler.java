package gb.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static gb.server.MainServer.LOGGER;

public class SQLHandler {
    private static Connection connection;
    private static Statement statement;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            statement = connection.createStatement();
            LOGGER.config("Соединение с базой данных установлено");
        } catch (Exception e) {
            LOGGER.severe("Ошибка при соединении с базой данных: " + e.getMessage());
        }
    }

    public static void disconnect() {
        try {
            connection.close();
            LOGGER.info("Соединение с базой данных закрыто");
        } catch (SQLException e) {
            LOGGER.severe("Ошибка при закрытии соединения с базой данных: " + e.getMessage());
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
                String nickName = rs.getString("nickname");
                LOGGER.config("Пользователь " + nickName + " найден в базе данных");
                return nickName;
            }
        } catch (SQLException e) {
            LOGGER.severe("Ошибка при поиске ника пользователя в базе данных по фильтру " + sqlFilter);
        }
        return null;
    }
}
