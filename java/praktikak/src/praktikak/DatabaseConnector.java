package praktikak;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String DB_URL = "jdbc:mysql://10.23.25.161:3307/alaiktumugi";
    private static final String USER = "xiker";
    private static final String PASSWORD = "pvlbtnse";

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Konektatuta egon da datu-basearekin.");
        } catch (SQLException e) {
            System.err.println("Errore bat gertatu da datu-basearekin konektatzerakoan.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void disconnect(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Datu-basearekin deskonektatu da.");
            } catch (SQLException e) {
                System.err.println("Errore bat gertatu da datu-basearekin deskonektatzerakoan.");
                e.printStackTrace();
            }
        }
    }
}
