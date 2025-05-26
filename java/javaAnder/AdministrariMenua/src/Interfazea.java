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

public class Interfazea extends JFrame {

	// Gidariaren ataleko osagaiak:
	
	// Botoiak
	private JButton btnGidariGehitu;
	private JButton btnGidariAldatu;
	private JButton btnGidariEzabatu;
	private JButton btnFiltratuGidariak;
	
	// Aldaketa pendienteak gordetzeko hasmap-a sortu
	private Map<Integer, Map<String, String>> aldaketaPendienteak = new HashMap<>();
	
	// Taularen zutabeak definitu
	private String[] zutabeLangilea = { "Langile ID", "Izena", "Abizena", "NAN", "Posta", "Pasahitza", "Matrikula",
			"Edukiera", "Egoera", "Tarifa" };
	
	// Taula eta bere modelua sortu
	private JTable tableGidaria;
	public DefaultTableModel modelGidaria;
	
	// Filtroen textfield-ak hasieratu
	private JTextField txtFiltroIzena, txtFiltroAbizena;
	

	// Historialaren ataleko osagaiak:
	
	// Taula eta bere modelua sortu
	private JTable tableHistorial;
	protected DefaultTableModel modelHistorial;
	
	// Filtroeen textfield-ak hasieratu
	private JTextField txtFiltroBezeroa, txtFiltroGidaria, txtFiltroPrezioa;
	
	// Filtroarentzako botoia
	private JButton btnFiltratuHistoriala;


	public Interfazea() {
		
		// Aplikazioaren titulua
		super("Administrazio Interfazea");
		
		// Interfazea deitu
		initComponents();
		
		// Listenerrak deitu
		eventListenerrakGehitu();
		
		// Historiala filtro gabe deitu
		historialaKargatu("", "", "");
		
		// Gidariak filtro gabe deitu
		gidariakKargatu("", "");
	}

	private void initComponents() {
		
		// Leihoaren konfiguraketa
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		// TabbedPane printzipalaren hasierapena
		JTabbedPane tabbedPane = new JTabbedPane();

		// Gidariak kudeatzeko atala:
		
		// Panelak
		JPanel panelGidariak = new JPanel(new BorderLayout());
		JPanel panelBotoiakGidariak = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panelFiltroGidariak = new JPanel();

		
		// Filtroen textfield-ak hasieratu
		txtFiltroIzena = new JTextField(20);
		txtFiltroAbizena = new JTextField(20);
		
		// Buttonaei baloreak eman
		btnGidariGehitu = new JButton("Gidari Gehitu");
		btnGidariEzabatu = new JButton("Gidari Ezabatu");
		btnGidariAldatu = new JButton("Gidarien aldaketak gorde");
		btnFiltratuGidariak = new JButton("Bilaketa");
		
		// Gidarien filtroen panelean labelak eta textfield-ak gehitu
		panelFiltroGidariak.add(new JLabel("Izena"));
		panelFiltroGidariak.add(txtFiltroIzena);
		panelFiltroGidariak.add(new JLabel("Abizena"));
		panelFiltroGidariak.add(txtFiltroAbizena);
		panelFiltroGidariak.add(btnFiltratuGidariak);

		// Gidarien botoien panelean botoiak gehitu
		panelBotoiakGidariak.add(btnGidariGehitu);
		panelBotoiakGidariak.add(btnGidariEzabatu);
		panelBotoiakGidariak.add(btnGidariAldatu);
		
		// Modelua editatu
				modelGidaria = new DefaultTableModel(zutabeLangilea, 0) {
					@Override
					// Zeldak editatzea ahalbidetu, langilearen id-a izan ezik
					public boolean isCellEditable(int row, int column) {
						
						return column != 0;

					}
				};
		
		// Taulari modelua ezarri eta scrolleatzea ahalbidetu
		tableGidaria = new JTable(modelGidaria);
		JScrollPane scrollLangileak = new JScrollPane(tableGidaria);
		
		// Gidariaren panel nagusian gure hiru panelak gehitu
		panelGidariak.add(panelBotoiakGidariak, BorderLayout.NORTH);
		panelGidariak.add(scrollLangileak, BorderLayout.CENTER);
		panelGidariak.add(panelFiltroGidariak, BorderLayout.SOUTH);

		// TabbedPane-an gidarien panela gehitu
		tabbedPane.addTab("Gidariak Kudeatu", panelGidariak);
		
		

		// Historiala kudeatzeko atala:
		
		
		// Panelak
		JPanel panelFiltroHistoriala = new JPanel();
		JPanel panelHistoria = new JPanel(new BorderLayout());
		
		// Filtroen textfield-ak hasieratu
		txtFiltroBezeroa = new JTextField(10);
		txtFiltroGidaria = new JTextField(5);
		txtFiltroPrezioa = new JTextField(5);
		
		// Buttonari balorea eman
		btnFiltratuHistoriala = new JButton("Bilaketa");
		
		// Historialaren filtroen panelean labelak eta textfield-ak gehitu
		panelFiltroHistoriala.add(new JLabel("Bezero izena edo abizena:"));
		panelFiltroHistoriala.add(txtFiltroBezeroa);
		panelFiltroHistoriala.add(new JLabel("Gidari izena edo abizena:"));
		panelFiltroHistoriala.add(txtFiltroGidaria);
		panelFiltroHistoriala.add(new JLabel("Prezioa min:"));
		panelFiltroHistoriala.add(txtFiltroPrezioa);
		panelFiltroHistoriala.add(btnFiltratuHistoriala);
		
		
		// Historialaren taularen zutabeak definitu
		String[] zutabeHistorial = { "ID", "Hasiera Kokapena", "Helmuga Kokapena", "Data", "Hasiera Ordua",
				"Bukaera Ordua", "Xehetasunak", "Gidaria", "Bezeroa", "Bidaiak", "Prezioa" };
		
		// Modelua editatu
		modelHistorial = new DefaultTableModel(zutabeHistorial, 0) {
			
			@Override
			// Zeldak ez editagarriak izatea
			public boolean isCellEditable(int row, int column) {
				
				return false; 

			}

			// Zutabe bakoitzaren datu mota definitu
			public Class<?> getColumnClass(int columnIndex) {
				
				switch (columnIndex) {
				
				case 0: // ID Historiala
				case 7: // ID Gidaria
				case 8: // ID Bezeroa
				case 9: // Bidaiak
					return Integer.class;
				case 10: // Prezioa
					return Double.class;
				default:
					
					return String.class; // Beste guztiak String
					
				}
			}
		};
		
		// Historialen taula sortu gure modelua erabilita, zutabeka ordenatzeko ahalbidetu
		tableHistorial = new JTable(modelHistorial);
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelHistorial);
		tableHistorial.setRowSorter(sorter);
		JScrollPane scrollHistorial = new JScrollPane(tableHistorial);
		
		// Historialaren panelean gure bi panelak gehitu
		panelHistoria.add(panelFiltroHistoriala, BorderLayout.NORTH);
		panelHistoria.add(scrollHistorial, BorderLayout.CENTER);
		
		// TabbedPane-an historialaren paneña gehitu
		tabbedPane.addTab("Historiala", panelHistoria);

		// Tabbed pane-a gehitu
		add(tabbedPane, BorderLayout.CENTER);
		
		// Defektuzko botoien baloreak aldatu
		UIManager.put("OptionPane.okButtonText", "Onartu");
		UIManager.put("OptionPane.cancelButtonText", "Ezeztatu");

		

	}

	// Eventuak erregistratzeko metodoa
	private void eventListenerrakGehitu() {
		
		// Gidariak gehitzeko botoiaren listenerra
		btnGidariGehitu.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				LangileaForm form = new LangileaForm(Interfazea.this);
		        form.setVisible(true);
			}
		});

		
		// Gidaria aldatzeko botoiaren listenerra
		btnGidariAldatu.addActionListener(e -> {
			
			// Aldaketa pendienteetako hashmap-a hutsik baldin badago
			if (aldaketaPendienteak.isEmpty()) {
				
				// Ez daudela aldaketa pendienterik esan eta akzioa bukatu
				JOptionPane.showMessageDialog(Interfazea.this, "Ez dago aldaketa pendienterik.");
				return;
				
			}

			// HashMap-eko aldaketa guztiak igarotu for baten bitartez
			for (Map.Entry<Integer, Map<String, String>> entry : aldaketaPendienteak.entrySet()) {
				
				// Id-a jaso
				int id = entry.getKey();
				
				// Id hortako baloreak jaso
				Map<String, String> baloreak = entry.getValue();

				// Aldaketa bakoitzeko langilea editatzeko metodoari deitu
				for (Map.Entry<String, String> aldaketa : baloreak.entrySet()) {
					
					// Langilearen id-a, zutabearen izena eta balore berria gorde
					langileaAldatu(id, aldaketa.getKey(), aldaketa.getValue());
					
				}
			}

			// HashMap-a garbitu eta gidarien taula errefreskatu
			aldaketaPendienteak.clear();
			gidariakKargatu("", "");
		});

		// Gidaria ezabatzeko botoiaren listenerra
		btnGidariEzabatu.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				// Ezabatu behar den gidariaren id-a jaso string moduan
				String idStr = JOptionPane.showInputDialog(Interfazea.this, "Sartu ezabatzeko gidariaren ID-a:");
				
				// Ez baldin bada balorerik sartu mezua erakutsi
				if (idStr == null || idStr.trim().isEmpty()) {
					
					JOptionPane.showMessageDialog(Interfazea.this, "ID-a beharrezkoa da.");
					return;
					
				}
				
				// Konfirmatzeko mezua
				int konfirm = JOptionPane.showConfirmDialog(Interfazea.this,
						"Ziur zaude aukeratutako gidaria ezabatu nahi duzula?", "Ezabatzeko berreskurapena",
						JOptionPane.YES_NO_OPTION);
				
				// Baietz esaten baldin bada
				if (konfirm == JOptionPane.YES_OPTION) {
					
					try {
						
						// Id-a parseatu int izateko
						int id = Integer.parseInt(idStr);
						
						// Boolean baten gorde gure gidariak borratzeko metodoari deituz
						boolean ondo = Metodoak.gidariEzabatu(id);
						
						// Funtzionatu baldin badu
						if (ondo) {
							
							// Gidaria borratu dela esan
							JOptionPane.showMessageDialog(Interfazea.this, "Gidaria ondo ezabatu da.");
							
						} else {
							
							// Bestela gidari hori ez dela aurkitu esan
							JOptionPane.showMessageDialog(Interfazea.this, "Ez da aurkitu id hori duen gidaria.");
							
						}
					} catch (NumberFormatException ex) {
						
						// Zenbaki bat ez baldin bada sartu erakutsi
						JOptionPane.showMessageDialog(Interfazea.this, "ID-a zenbaki bat izan behar da.");
						
					}
				}
			}
		});

		// Historiala filtratzeko botoiaren listenerra
		btnFiltratuHistoriala.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				// Jasotako baloreak gorde
				String dataFiltro = txtFiltroBezeroa.getText().trim();
				String gidariaFiltro = txtFiltroGidaria.getText().trim();
				String prezioaFiltro = txtFiltroPrezioa.getText().trim();
				
				// Historiala kargatzeko metodoari deitu jasotako baloreekin
				historialaKargatu(dataFiltro, gidariaFiltro, prezioaFiltro);
				
			}
		});

		// Gidariak filtratzeko botoiaren listenerra
		btnFiltratuGidariak.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				// Jasotako baloreak gorde
				String izena = txtFiltroIzena.getText().trim();
				String abizena = txtFiltroAbizena.getText().trim();
				
				// Gidariak kargatzeko metodoari deitu jasotako baloreekin
				gidariakKargatu(izena, abizena);
				
			}
			
		});

		modelGidaria.addTableModelListener(e -> {
			
			// Editatutako hilara eta zutabea jaso
			int hilara = e.getFirstRow();
			int zutabe = e.getColumn();

			// Id-an aldaketarik ez egoteko
			if (zutabe > 0) {
				
				// id-a gorde
				int id = (int) modelGidaria.getValueAt(hilara, 0);
				
				// Zutabearen izena gorde
				String zutabeIzena = zutabeLangilea[zutabe];
				
				// Balore berria String moduan gorde
				String baloreBerria = modelGidaria.getValueAt(hilara, zutabe).toString();

				// HashMap-ean dena gorde
				aldaketaPendienteak.computeIfAbsent(id, k -> new HashMap<>()).put(zutabeIzena, baloreBerria);
			}
		});

	}

	/**
	 * Historiala kargatzeko funtzioa, parametroak jasotzen ditu filtroak erabili baldin badira
	 * @param bezeroFiltro Erabiltzaileak sartutako data
	 * @param gidariaFiltro
	 * @param prezioaFiltro
	 */
	private void historialaKargatu(String bezeroFiltro, String gidariaFiltro, String prezioaFiltro) {
		
		// Taula hustu
		modelHistorial.setRowCount(0);

		// Historialaren select-a 
		String sql = "SELECT idhistoriala, hasiera_kokapena, helmuga_kokapena, data, "
		           + "hasiera_ordua, bukaera_ordua, xehetasunak, h.idgidaria, h.idbezeroa, "
		           + "bidaiak_idbidaiak, prezioa, CONCAT(b.izena, ' ', b.abizena) AS bezeroa, "
		           + "CONCAT(l.izena, ' ', l.abizena) AS gidaria "
		           + "FROM historiala h "
		           + "LEFT JOIN langilea l ON h.idgidaria = l.idlangilea "
		           + "LEFT JOIN bezeroa b ON h.idbezeroa = b.idbezeroa "
		           + "WHERE 1=1";
		// Jasotako baloreen arabera WHERE-eri gauzak gehitu
		if (!bezeroFiltro.isEmpty())
		    sql += " AND (b.izena LIKE ? OR b.abizena LIKE ?)";

		if (!gidariaFiltro.isEmpty())
		    sql += " AND (l.izena LIKE ? OR l.abizena LIKE ?)";

		if (!prezioaFiltro.isEmpty())
		    sql += " AND prezioa >= ?";

		try (Connection conn = Konexioa.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			// Indexa hasieratu
			int index = 1;
			
			if (!bezeroFiltro.isEmpty()) {
		        ps.setString(index++, "%" + bezeroFiltro + "%"); // Izenak bilatzeko LIKE erabili
		        ps.setString(index++, "%" + bezeroFiltro + "%"); // Abizenak ere bilatzeko LIKE
		    }

		    if (!gidariaFiltro.isEmpty()) {
		        ps.setString(index++, "%" + gidariaFiltro + "%"); // Izenak bilatzeko LIKE erabili
		        ps.setString(index++, "%" + gidariaFiltro + "%"); // Abizenak ere bilatzeko LIKE
		    }

		    if (!prezioaFiltro.isEmpty())
		        ps.setDouble(index++, Double.parseDouble(prezioaFiltro));

			// Kontsulta exekutatu eta resultset batean gordetzen saiatu
			try (ResultSet rs = ps.executeQuery()) {
				
				// ResultSet-a igarotu
				while (rs.next()) {
					
					// Hilarak sortu datu basetik lortutako baloreekin
					Object[] row = { rs.getInt("idhistoriala"), rs.getString("hasiera_kokapena"),
		                    rs.getString("helmuga_kokapena"), rs.getString("data"), rs.getString("hasiera_ordua"),
		                    rs.getString("bukaera_ordua"), rs.getString("xehetasunak"), rs.getString("gidaria"),
		                    rs.getString("bezeroa"), rs.getInt("bidaiak_idbidaiak"), rs.getDouble("prezioa") };
					
					// Hilarak gehitu
					modelHistorial.addRow(row);
				}
			}
		} catch (SQLException ex) {
			
			ex.printStackTrace();
			
			// Errorea egon baldin bada erakutsi
			JOptionPane.showMessageDialog(this, "Errorea bilaketa egitean: " + ex.getMessage());
		}
	}

	/**
	 * Gidariak kargatzeko metodoa
	 * @param izena gidariaren izena filtroetatik jasota
	 * @param abizena gidariaren abizena filtroetatik jasota
	 */
	private void gidariakKargatu(String izena, String abizena) {
		
		// Taula hustu
		modelGidaria.setRowCount(0);

		// Sql kontsulta langile, erabiltzaile eta taxista taulatik datuak jasotzeko
		String sql = "SELECT * FROM langilea JOIN erabiltzailea ON langilea.erabiltzailea_iderabiltzailea = erabiltzailea.iderabiltzailea JOIN taxista ON langilea.idlangilea= taxista.langilea_idlangilea WHERE langilea.mota = 'taxista'";

		// Parametroak informazioarekin iritsi baldin badira kontsultari gehitu
		if (!izena.isEmpty())
			sql += " AND izena = ?";
		if (!abizena.isEmpty())
			sql += " AND abizena = ?";

		// PreparedStatement bat prestatzen saiatu gure kontsultarekin
		try (Connection conn = Konexioa.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			// Indexa hasieratu
			int index = 1;
			
			// Kontsultan filtroetako informazioa gehitu
			if (!izena.isEmpty())
				ps.setString(index++, izena);
			if (!abizena.isEmpty())
				ps.setString(index++, abizena);

			// ResultSet baten gorde gure exekuzioa
			try (ResultSet rs = ps.executeQuery()) {
				
				// Hilaraka igarotzen joan
				while (rs.next()) {
					
					// Hilara objetuak sortzen joan gure datu basetik jasotako informazioarekin
					Object[] row = { rs.getInt("idlangilea"), rs.getString("izena"), rs.getString("abizena"),
							rs.getString("nan"), rs.getString("posta"), rs.getString("pasahitza"),
							rs.getString("matrikula"), rs.getInt("edukiera"), rs.getString("egoera"),
							rs.getDouble("tarifa"), };
					
					// Taulan gehitzen joan hilarak
					modelGidaria.addRow(row);
				}
			}
		} catch (SQLException ex) {
			
			// Arazorik egon baldin bada erakutsi
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Errorea bilaketa egitean: " + ex.getMessage());
			
		}
	}

	/**
	 * Langileak aldatzeko metodoa, taulan egindako aldaketak jasotzen dituena
	 * @param id Aldaketak egin behar zaizkion langilearen id-a
	 * @param zutabea Taulan editatu den zutabea, gure datu basean atributuari dagokiona
	 * @param baloreBerria Erabiltzaileak taulan sartu duen balore berria
	 */
	private void langileaAldatu(int id, String zutabea, String baloreBerria) {
		
		// Sql kontsulta eta editatu behar dugun taularen izena
		String sql = null;
		String taula = "";

		// Eguneratu behar den zutabearen arabera sql kontsulta bat sortu eta taula bat zehaztu
		if (zutabea.equals("Izena") || zutabea.equals("Abizena") || zutabea.equals("NAN")) {
			
			sql = "UPDATE langilea SET " + zutabea.toLowerCase() + " = ? WHERE idlangilea = ?";
			taula = "langilea";
			
		} else if (zutabea.equals("Posta") || zutabea.equals("Pasahitza")) {
			
			sql = "UPDATE erabiltzailea SET " + zutabea.toLowerCase()
					+ " = ? WHERE iderabiltzailea = (SELECT erabiltzailea_iderabiltzailea FROM langilea WHERE idlangilea = ?)";
			taula = "erabiltzailea";
			
		} else if (zutabea.equals("Matrikula") || zutabea.equals("Edukiera") || zutabea.equals("Egoera")
				|| zutabea.equals("Tarifa")) {
			
			sql = "UPDATE taxista SET " + zutabea.toLowerCase() + " = ? WHERE langilea_idlangilea = ?";
			taula = "taxista";
			
		}

		// Ez baldin bada zutabe koinzidentziarik aurkitzen mezua erakutsi eta ezer ez egin
		if (sql == null) {
			
			JOptionPane.showMessageDialog(null, "Ezin da eguneratu: zutabea ez da balioduna:" + zutabea);
			return;
			
		}

		// Taula egokian kontsulta exekutatzen saiatu
		try (Connection conn = Konexioa.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			// Zutabetik jasotako balorea ezarri atributuari eta langilearen id-a zehaztu
			ps.setString(1, baloreBerria);
			ps.setInt(2, id);
			
			// Exekutatu
			ps.executeUpdate();

			// Dena ondo joan baldin bada erakutsi
			JOptionPane.showMessageDialog(null, "Datuak eguneratu dira " + taula + " taulan.");

		} catch (SQLException ex) {
			
			// Arazorik egon baldin bada erakutsi
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Errorea datuak eguneratzean: " + ex.getMessage());
			
		}
	}

	public static void main(String[] args) {
		
		// Interfazea ikusgarri jarri
		SwingUtilities.invokeLater(() -> new Interfazea().setVisible(true));
		
	}
}
