import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Konexioa {
    // Cambia estos valores según tu configuración
    private static final String URL = "jdbc:mysql://localhost:3307/alaiktumugi"; // por ejemplo
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}