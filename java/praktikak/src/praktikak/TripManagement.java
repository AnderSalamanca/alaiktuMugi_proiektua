package praktikak;

import java.sql.*;

public class TripManagement {
    public void manageTrips() {
        // Datu-basearekin konektatu
        Connection connection = DatabaseConnector.connect();
        
        // Bidaien lortzeko SQL kontsulta
        String query = "SELECT * FROM bidaiak";
        
        try (Statement stmt = connection.createStatement(); 
             ResultSet rs = stmt.executeQuery(query)) {
            
            System.out.println("Bidaia guztiak:");
            while (rs.next()) {
                int idbidaiak = rs.getInt("idbidaiak");
                int iderabiltzailea = rs.getInt("iderabiltzailea");
                int langilea_idlangilea = rs.getInt("langilea_idlangilea");
                String hasiera_ordua = rs.getString("hasiera_ordua");
                String bukaera_ordua = rs.getString("bukaera_ordua");
                System.out.println("Bidaia ID: " + idbidaiak 
                		+ ", Erabiltzailea ID: " + iderabiltzailea + ","
                		+ "  Gidari ID: " + langilea_idlangilea 
                		+ ", Irteera ordua: " + hasiera_ordua
                		+ ", Iristeko ordua: " + bukaera_ordua);
            }
        } catch (SQLException e) {
            System.err.println("Errore bat gertatu da bidaien datuak lortzerakoan.");
            e.printStackTrace();
        }
        
        // Datu-basearekin deskonektatu
        DatabaseConnector.disconnect(connection);
    }
    
    public void cancelTrip(int idbidaiak) {
        Connection connection = DatabaseConnector.connect();
        String query = "UPDATE Trips SET status_id = (SELECT status_id FROM Trip_Status WHERE status_name = 'anulado') WHERE idbidaiak = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idbidaiak);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Bidaia ID " + idbidaiak + " bertan behera utzi da.");
            } else {
                System.out.println("Ez da bidaia hori aurkitu.");
            }
        } catch (SQLException e) {
            System.err.println("Errore bat gertatu da bidaia bertan behera uztean.");
            e.printStackTrace();
        }
        
        DatabaseConnector.disconnect(connection);
    }
}
