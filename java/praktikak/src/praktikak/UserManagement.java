package praktikak;

import java.sql.*;

public class UserManagement {

    // Erabiltzaile guztiak erakutsi
    public void manageUsers() {
        Connection connection = DatabaseConnector.connect();
        
        String query = """
            SELECT 
                e.iderabiltzailea,
                e.posta,
                e.rola,
                l.izena,
                l.abizena,
                l.nan
            FROM erabiltzailea e
            LEFT JOIN langilea l ON e.iderabiltzailea = l.erabiltzailea_iderabiltzailea
        """;

        try (Statement stmt = connection.createStatement(); 
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Erabiltzaile guztiak:");
            while (rs.next()) {
                int id = rs.getInt("iderabiltzailea");
                String posta = rs.getString("posta");
                String rola = rs.getString("rola");
                String izena = rs.getString("izena");
                String abizena = rs.getString("abizena");
                String nan = rs.getString("nan");

                System.out.println("ID: " + id + ", Izena: " + izena + " " + abizena +
                        ", NAN: " + nan + ", Eposta: " + posta + ", Rola: " + rola);
            }

        } catch (SQLException e) {
            System.err.println("Errorea erabiltzaileen datuak lortzerakoan:");
            e.printStackTrace();
        }

        DatabaseConnector.disconnect(connection);
    }

    // Erabiltzaile bat ezabatu (taula guztietan)
    public void deleteUser(int iderabiltzailea) {
        Connection connection = null;

        try {
            connection = DatabaseConnector.connect();
            connection.setAutoCommit(false);

            // Langilea ezabatu, gero erabiltzailea
            String deleteLangilea = "DELETE FROM langilea WHERE erabiltzailea_iderabiltzailea = ?";
            String deleteErabiltzailea = "DELETE FROM erabiltzailea WHERE iderabiltzailea = ?";

            try (
                PreparedStatement stmtLangilea = connection.prepareStatement(deleteLangilea);
                PreparedStatement stmtErabiltzailea = connection.prepareStatement(deleteErabiltzailea)
            ) {
                stmtLangilea.setInt(1, iderabiltzailea);
                stmtLangilea.executeUpdate();

                stmtErabiltzailea.setInt(1, iderabiltzailea);
                int rowsAffected = stmtErabiltzailea.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Erabiltzaile ID " + iderabiltzailea + " ezabatu da.");
                } else {
                    System.out.println("Ez da erabiltzaile hori aurkitu.");
                }
            }

            connection.commit();

        } catch (SQLException e) {
            System.err.println("Errorea erabiltzailea ezabatzerakoan:");
            e.printStackTrace();
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DatabaseConnector.disconnect(connection);
        }
    }
}
