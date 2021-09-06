package lesson2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void main(String[] args) {
        connect();
        try {
            System.out.println("Добавляем таблицу...");
            createTableBook();

            findAllBook();

            System.out.println("Добавляем записи...");
            insertBook("Alexander Pushkin", "Eugeni Onegin", 100);
            insertBook("Michel Lermontov", "Hero of our time", 300);

            findAllBook();

            System.out.println("Находим запись по полю и выводим в консоль...");
            findBookByTitle("Eugeni Onegin");

            System.out.println("Изменяем поле в записи");
            updateBookPageCount(2, 301);

            System.out.println("Находим измененную ранее запись по полю и выводим в консоль");
            findBookByTitle("Hero of our time");

            System.out.println("Находим и выводим в консоль все записи перед удалением");
            findAllBook();

            System.out.println("Удаляем запись с идентификатором 1");
            deleteBookById(1);

            System.out.println("Находим и выводим в консоль все записи после удаления");
            findAllBook();

            System.out.println("Удаляем таблицу book");
            dropTableBook();

            System.out.println("Пытаемся получить записи из таблицы книг, убеждаемся в том,"
                    + "что появилось сообщение об ошибки в связи с отсутствием таблицы book  в базе данных");
            findAllBook();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            disconnect();
        }
    }

    private static void createTableBook() throws SQLException {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS books (\n" +
                "    id    INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    author  TEXT ,\n" +
                "    title TEXT,\n" +
                "    page_count INTEGER\n" +
                ");");
    }

    private static void insertBook(String author, String title, int pageCount) throws SQLException {
        pstmt = connection.prepareStatement("INSERT INTO books (author, title, page_count) VALUES (?, ?, ?);");
        pstmt.setString(1, author);
        pstmt.setString(2, title);
        pstmt.setInt(3, pageCount);
        pstmt.executeUpdate();
    }

    private static void findBookByTitle(String title) throws SQLException {
        pstmt = connection.prepareStatement("SELECT * FROM books WHERE title = ?;");
        pstmt.setString(1, title);
        ResultSet rs = pstmt.executeQuery();
        printResultSet(rs);
    }

    private static void updateBookPageCount(int id, int pageCount) throws SQLException {
        pstmt = connection.prepareStatement("UPDATE books SET page_count = ? WHERE id = ?;");
        pstmt.setInt(1, pageCount);
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
    }

    private static void findAllBook() throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM books ORDER BY id;");
        printResultSet(rs);
    }

    private static void printResultSet(ResultSet rs) throws SQLException {
        System.out.println("Печать содержимого таблицы books:");
        boolean isRecordsExists = false;
        while (rs.next()) {
            isRecordsExists = true;
            System.out.println(rs.getInt("id") + " , "
                    + rs.getString("author") + " , "
                    + rs.getString("title") + " , "
                    + rs.getInt("page_count")
            );
        }
        if (!isRecordsExists) {
            System.out.println("Таблица books не содержит записей");
        }
    }

    private static void deleteBookById(int id) throws SQLException {
        pstmt = connection.prepareStatement("DELETE FROM books WHERE id = ?;");
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    private static void dropTableBook() throws SQLException {
        stmt.executeUpdate("DROP TABLE IF EXISTS books;");
    }

}
