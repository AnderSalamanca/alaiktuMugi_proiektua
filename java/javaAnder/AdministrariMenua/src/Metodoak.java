
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Metodoak {

	// Metodoa: Gidari bat gehitu (izena, adina eta lizentzia)
	public static boolean gidariGehitu(String posta, String pasahitza, String izena, String abizena,
			String nan, String matrikula, int edukiera, String egoera, double tarifa) {
		Connection conn = null;
		try {
// 1. Konexioa lortu eta transakzioaren moduaren ezarri
			conn = Konexioa.getConnection();
			conn.setAutoCommit(false);

			int idErabiltzailea;
// 2. Erabiltzailea gehitzeko SQL: erabiltzailea (posta, pasahitza, rola)
			String sqlErabiltzailea = "INSERT INTO erabiltzailea (posta, pasahitza, rola) VALUES (?, ?, 'taxista')";
			try (PreparedStatement psErab = conn.prepareStatement(sqlErabiltzailea,
					PreparedStatement.RETURN_GENERATED_KEYS)) {
				psErab.setString(1, posta);
				psErab.setString(2, pasahitza);

				int affectedRows = psErab.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Erabiltzailea sortzea huts egin du; ez dira erregistroak sartu.");
				}
				try (ResultSet generatedKeys = psErab.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						idErabiltzailea = generatedKeys.getInt(1);
					} else {
						throw new SQLException("Erabiltzailearen IDa lortzea huts egin du.");
					}
				}
			}

			int idLangilea;
// 3. Langilea gehitzeko SQL: langilea (izena, abizena, nan, mota, erabiltzailea_iderabiltzailea)
			String sqlLangilea = "INSERT INTO langilea (izena, abizena, nan, mota, erabiltzailea_iderabiltzailea) VALUES (?, ?, ?, 'taxista', ?)";
			try (PreparedStatement psLang = conn.prepareStatement(sqlLangilea,
					PreparedStatement.RETURN_GENERATED_KEYS)) {
				psLang.setString(1, izena);
				psLang.setString(2, abizena);
				psLang.setString(3, nan);
				psLang.setInt(4, idErabiltzailea);

				int affectedRows = psLang.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Langilea sortzea huts egin du; ez dira erregistroak sartu.");
				}
				try (ResultSet generatedKeys = psLang.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						idLangilea = generatedKeys.getInt(1);
					} else {
						throw new SQLException("Langilearen IDa lortzea huts egin du.");
					}
				}
			}

// 4. Taxista gehitzeko SQL: taxista (matrikula, edukiera, egoera, langilea_idlangilea, tarifa)
			String sqlTaxista = "INSERT INTO taxista (matrikula, edukiera, egoera, langilea_idlangilea, tarifa) VALUES (?, ?, ?, ?, ?)";
			try (PreparedStatement psTax = conn.prepareStatement(sqlTaxista)) {
				psTax.setString(1, matrikula);
				psTax.setInt(2, edukiera);
				psTax.setString(3, egoera);
				psTax.setInt(4, idLangilea);
				psTax.setDouble(5, tarifa);

				int affectedRows = psTax.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Taxista sortzea huts egin du; ez dira erregistroak sartu.");
				}
			}

// 5. Beharrezkoa denean, transakzioa onartu
			conn.commit();
			return true;

		} catch (SQLException ex) {
// Errorea gertatzen bada, rollback egin
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex2) {
					ex2.printStackTrace();
				}
			}
			ex.printStackTrace();
			return false;
		} finally {
// Auto-commit berriz ezarri eta konexioa itxi
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	// Metodoa: Gidari bat eguneratu (ID-ren bidez, izena, adina eta lizentzia
	// berriak)
	public static boolean gidariAldatu(int id, String izena, int adina, String lizentzia) {
		String sql = "UPDATE conductores SET nombre = ?, edad = ?, licencia = ? WHERE id = ?";
		try (Connection conn = Konexioa.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, izena);
			ps.setInt(2, adina);
			ps.setString(3, lizentzia);
			ps.setInt(4, id);

			int erregistroKopurua = ps.executeUpdate();
			return erregistroKopurua > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	// Metodoa: ID-ren bidez gidaria ezabatu
	public static boolean gidariEzabatu(int id) {
		String sql = "DELETE FROM conductores WHERE id = ?";
		try (Connection conn = Konexioa.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);
			int erregistroKopurua = ps.executeUpdate();
			return erregistroKopurua > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	
	
}

