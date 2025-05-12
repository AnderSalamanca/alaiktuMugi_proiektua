package praktikak;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BidaiaHistorikoak {
    private Connection connection;

    // Constructorra: datu basearekin konektatzeko
    public BidaiaHistorikoak(Connection connection) {
        this.connection = connection;
    }

    // Bidaia historikoak irakurri
    public List<String> lortuBidaiaHistorikoak() {
        List<String> historial = new ArrayList<>();
        String sql = "SELECT * FROM Bidaia_Historikoak"; // SQL kontsulta

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Datuak irakurri eta gorde
            while (rs.next()) {
                int historyId = rs.getInt("history_id");
                int tripId = rs.getInt("trip_id");
                Timestamp hasieraOrduna = rs.getTimestamp("hasiera_ordua");
                Timestamp amaieraOrduna = rs.getTimestamp("amaiera_ordua");
                String oharrak = rs.getString("oharrak");

                // String batean gordetzen dugu historialaren informazioa
                String historialInfo = "History ID: " + historyId + ", Trip ID: " + tripId + 
                                       ", Hasiera: " + hasieraOrduna + ", Amaiera: " + amaieraOrduna +
                                       ", Oharrak: " + oharrak;
                historial.add(historialInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historial;
    }

    // Bidaia historiko bat gehitu
    public void gehituBidaiaHistorikoa(int tripId, Timestamp hasieraOrduna, Timestamp amaieraOrduna, String oharrak) {
        String sql = "INSERT INTO Bidaia_Historikoak (trip_id, hasiera_ordua, amaiera_ordua, oharrak) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, tripId);
            pstmt.setTimestamp(2, hasieraOrduna);
            pstmt.setTimestamp(3, amaieraOrduna);
            pstmt.setString(4, oharrak);
            pstmt.executeUpdate();
            System.out.println("Bidaia historikoa gehitu da!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Bidaia historiko bat ezabatu
    public void ezabatuBidaiaHistorikoa(int historyId) {
        String sql = "DELETE FROM Bidaia_Historikoak WHERE history_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, historyId);
            pstmt.executeUpdate();
            System.out.println("Bidaia historikoa ezabatu da!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
