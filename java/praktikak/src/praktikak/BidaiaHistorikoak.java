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
                int idhistoriala = rs.getInt("idhistoriala");
                int idbidaiak = rs.getInt("idabidaiak");
                Timestamp hasieraOrdua = rs.getTimestamp("hasiera_ordua");
                Timestamp amaieraOrdua = rs.getTimestamp("amaiera_ordua");

                // String batean gordetzen dugu historialaren informazioa
                String historialInfo = "History ID: " + idhistoriala + 
                					   ", Trip ID: " + idbidaiak + 
                                       ", Hasiera: " + hasieraOrdua + 
                                       ", Amaiera: " + amaieraOrdua ;
                                      
                historial.add(historialInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historial;
    }

    // Bidaia historiko bat gehitu
    public void gehituBidaiaHistorikoa(int idbidaiak, Timestamp hasieraOrdua, Timestamp amaieraOrdua) {
        String sql = "INSERT INTO Bidaia_Historikoak (idbidaiak, hasiera_ordua, amaiera_ordua, ) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idbidaiak);
            pstmt.setTimestamp(2, hasieraOrdua);
            pstmt.setTimestamp(3, amaieraOrdua);
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
