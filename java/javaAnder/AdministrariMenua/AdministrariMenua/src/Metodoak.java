
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class Metodoak {

	/**
	 * Gidariak gehitzeko metodoa
	 * 
	 * @param posta
	 * @param pasahitza
	 * @param izena
	 * @param abizena
	 * @param nan
	 * @param matrikula
	 * @param edukiera
	 * @param egoera
	 * @param tarifa
	 * @return
	 */
	public static boolean gidariGehitu(String posta, String pasahitza, String izena, String abizena, String nan,
			String matrikula, int edukiera, String egoera, double tarifa) {

		Connection conn = null;

		try {

			// Konexioa lortu eta transakzioaren moduaren ezarri
			conn = Konexioa.getConnection();
			conn.setAutoCommit(false);

			int idErabiltzailea;

			// Erabiltzailea gehitzeko SQL kontsulta
			String sqlErabiltzailea = "INSERT INTO erabiltzailea (posta, pasahitza, rola) VALUES (?, ?, 'taxista')";

			// Erabiltzaile taulako kontsulta saiatu
			try (PreparedStatement psErab = conn.prepareStatement(sqlErabiltzailea,
					PreparedStatement.RETURN_GENERATED_KEYS)) {

				// Baloreak eman
				psErab.setString(1, posta);
				psErab.setString(2, pasahitza);

				// Exekutatu eta gorde
				int affectedRows = psErab.executeUpdate();

				// Aldaketak ez baldin badira egon
				if (affectedRows == 0) {

					throw new SQLException("Erabiltzailea sortzea huts egin du; ez dira erregistroak sartu.");

				}

				// Id-a jaso
				try (ResultSet generatedKeys = psErab.getGeneratedKeys()) {

					if (generatedKeys.next()) {

						// Id-a gorde
						idErabiltzailea = generatedKeys.getInt(1);

					} else {

						// Ez baldin bada lortzen erakutsi
						throw new SQLException("Erabiltzailearen IDa lortzea huts egin du.");

					}
				}
			}

			// Langilearen id-a gordetzeko aldagaia hasieratu
			int idLangilea;

			// Langilearen sql kontsulta prestatu
			String sqlLangilea = "INSERT INTO langilea (izena, abizena, nan, mota, erabiltzailea_iderabiltzailea) VALUES (?, ?, ?, 'taxista', ?)";

			// Langile taulako kontsulta egiten saiatu
			try (PreparedStatement psLang = conn.prepareStatement(sqlLangilea,
					PreparedStatement.RETURN_GENERATED_KEYS)) {

				// Baloreak eman inkognitei
				psLang.setString(1, izena);
				psLang.setString(2, abizena);
				psLang.setString(3, nan);
				psLang.setInt(4, idErabiltzailea);

				// Exekutatu eta afektatu diren hilarak gorde
				int affectedRows = psLang.executeUpdate();

				if (affectedRows == 0) {

					// Ez baldin badira aldaketak egon esan
					throw new SQLException("Langilea sortzea huts egin du; ez dira erregistroak sartu.");

				}
				// Id-a lortzen saiatu
				try (ResultSet generatedKeys = psLang.getGeneratedKeys()) {

					if (generatedKeys.next()) {

						// Aurkitzen bada gorde
						idLangilea = generatedKeys.getInt(1);

					} else {

						// Ez baldin bada aurkitzen esan
						throw new SQLException("Langilearen IDa lortzea huts egin du.");

					}
				}
			}

			// Taxistaren sql kontsulta prestatu
			String sqlTaxista = "INSERT INTO taxista (matrikula, edukiera, egoera, langilea_idlangilea, tarifa) VALUES (?, ?, ?, ?, ?)";

			// Kontsulta egiten saiatu
			try (PreparedStatement psTax = conn.prepareStatement(sqlTaxista)) {

				// Baloreak ezarri
				psTax.setString(1, matrikula);
				psTax.setInt(2, edukiera);
				psTax.setString(3, egoera);
				psTax.setInt(4, idLangilea);
				psTax.setDouble(5, tarifa);

				// Exekutatu eta afektatu diren hilarak gorde
				int affectedRows = psTax.executeUpdate();

				if (affectedRows == 0) {

					// Ez baldin badira aldaketak egon esan
					throw new SQLException("Taxista sortzea huts egin du; ez dira erregistroak sartu.");

				}
			}

			// Dena ondo joan baldin bada commit egin aldaketak gordetzeko
			conn.commit();

			// True bueltatu dena ondo joan baldin bada
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

			// Erroreak baldin badaude false bueltatu
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

	/**
	 * Langileak ezabatzeko metodoa
	 * 
	 * @param idlangilea ezabatu behar den langilearen id-a
	 * @return
	 */
	public static boolean gidariEzabatu(int idlangilea) {

		// Sql kontsulta prestatu
		String sql = "DELETE erabiltzailea, langilea FROM erabiltzailea JOIN langilea ON erabiltzailea.iderabiltzailea = langilea.erabiltzailea_iderabiltzailea  WHERE langilea.idlangilea = ?";

		// Kontsulta egiten saiatu
		try (Connection conn = Konexioa.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			// Id-a ezarri
			ps.setInt(1, idlangilea);

			// Exekutatu eta afektatu diren hilara kantitatea gorde
			int erregistroKopurua = ps.executeUpdate();

			// Aldaketak egon diren bueltatu true edo false
			return erregistroKopurua > 0;

		} catch (SQLException ex) {

			// Arazoak baldin badaude erakutsi eta false bueltatu
			ex.printStackTrace();
			return false;

		}
	}

}
