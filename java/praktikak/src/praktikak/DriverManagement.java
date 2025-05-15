package praktikak;

import java.sql.*;

public class DriverManagement {

    // Metodoa: Gidari guztiak erakutsi
    public void manageDrivers() {
        Connection connection = null;
        try {
            connection = DatabaseConnector.connect();

            String query = """
                SELECT 
                    l.idlangilea,
                    l.izena,
                    l.abizena,
                    l.nan,	
                    e.posta,
                    t.matrikula,
                    t.edukiera
                FROM langilea l
                JOIN erabiltzailea e ON l.erabiltzailea_iderabiltzailea = e.iderabiltzailea
                JOIN taxista t ON l.idlangilea = t.langilea_idlangilea
            """;

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                System.out.println("Gidari guztiak:");
                while (rs.next()) {
                    int id = rs.getInt("idlangilea");
                    String izena = rs.getString("izena");
                    String abizena = rs.getString("abizena");
                    String nan = rs.getString("nan");
                    String posta = rs.getString("posta");
                    String matrikula = rs.getString("matrikula");
                    String edukiera = rs.getString("edukiera");

                    System.out.println("ID: " + id + 
                    		", Izena: " + izena +
                    		", Abizena" + abizena +
                            ", NAN: " + nan + 
                            ", Emaila: " + posta + 
                            ", matrikula: " + matrikula +
                            ", Edukiera: " + edukiera);
                }
            }

        } catch (SQLException e) {
            System.err.println("Errorea gidarien datuak lortzerakoan:");
            e.printStackTrace();
        } finally {
            DatabaseConnector.disconnect(connection);
        }
    }

    // Metodoa: Gidari bat ezabatu (3 taulatan)
    public void deleteDriver(int langileaId) {
        Connection connection = null;
        try {
            connection = DatabaseConnector.connect();
            connection.setAutoCommit(false);

            // Lortu erabiltzailea_id
            String selectQuery = "SELECT erabiltzailea_iderabiltzailea FROM langilea WHERE idlangilea = ?";
            int erabiltzaileaId = -1;
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setInt(1, langileaId);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        erabiltzaileaId = rs.getInt("erabiltzailea_iderabiltzailea");
                    } else {
                        System.out.println("Ez da gidari hori aurkitu.");
                        return;
                    }
                }
            }

            // Ezabatu taxista -> langilea -> erabiltzailea
            String deleteTaxista = "DELETE FROM taxista WHERE langilea_idlangilea = ?";
            String deleteLangilea = "DELETE FROM langilea WHERE idlangilea = ?";
            String deleteErabiltzailea = "DELETE FROM erabiltzailea WHERE iderabiltzailea = ?";

            try (
                PreparedStatement delTaxista = connection.prepareStatement(deleteTaxista);
                PreparedStatement delLangilea = connection.prepareStatement(deleteLangilea);
                PreparedStatement delErabiltzailea = connection.prepareStatement(deleteErabiltzailea)
            ) {
                delTaxista.setInt(1, langileaId);
                delTaxista.executeUpdate();

                delLangilea.setInt(1, langileaId);
                delLangilea.executeUpdate();

                delErabiltzailea.setInt(1, erabiltzaileaId);
                delErabiltzailea.executeUpdate();

                connection.commit();
                System.out.println("Gidaria ondo ezabatu da.");
            }

        } catch (SQLException e) {
            System.err.println("Errorea gidaria ezabatzerakoan:");
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
