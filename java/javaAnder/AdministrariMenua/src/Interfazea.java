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
	private DefaultTableModel modelGidaria;
	
	// Filtroen textfield-ak hasieratu
	private JTextField txtFiltroIzena, txtFiltroAbizena;
	

	// Historialaren ataleko osagaiak:
	
	// Taula eta bere modelua sortu
	private JTable tableHistorial;
	private DefaultTableModel modelHistorial;
	
	// Filtroeen textfield-ak hasieratu
	private JTextField txtFiltroData, txtFiltroGidaria, txtFiltroPrezioa;
	
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
		
		

		// SECCIÓN: Historiala
		
		
		// Panelak
		JPanel panelFiltroHistoriala = new JPanel();
		JPanel panelHistoria = new JPanel(new BorderLayout());
		
		// Filtroen textfield-ak hasieratu
		txtFiltroData = new JTextField(10);
		txtFiltroGidaria = new JTextField(5);
		txtFiltroPrezioa = new JTextField(5);
		
		// Buttonari balorea eman
		btnFiltratuHistoriala = new JButton("Bilaketa");
		
		// Historialaren filtroen panelean labelak eta textfield-ak gehitu
		panelFiltroHistoriala.add(new JLabel("Data:"));
		panelFiltroHistoriala.add(txtFiltroData);
		panelFiltroHistoriala.add(new JLabel("Gidaria ID:"));
		panelFiltroHistoriala.add(txtFiltroGidaria);
		panelFiltroHistoriala.add(new JLabel("Prezioa min:"));
		panelFiltroHistoriala.add(txtFiltroPrezioa);
		panelFiltroHistoriala.add(btnFiltratuHistoriala);
		
		
		// Historialaren taularen zutabeak definitu
		String[] zutabeHistorial = { "ID", "Hasiera Kokapena", "Helmuga Kokapena", "Data", "Hasiera Ordua",
				"Bukaera Ordua", "Xehetasunak", "ID Gidaria", "ID Bezeroa", "Bidaiak", "Prezioa" };
		
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

		

	}

	// Eventuak erregistratzeko metodoa
	private void eventListenerrakGehitu() {
		
		// Gidariak gehitzeko botoiaren listenerra
		btnGidariGehitu.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				// Erabiltzailearen datuak (taula: erabiltzailea)
				String posta = JOptionPane.showInputDialog(Interfazea.this, "Sartu erabiltzailearen posta:");
				if (posta == null || posta.trim().isEmpty()) {
					
					JOptionPane.showMessageDialog(Interfazea.this, "Posta ezin da hutsik egon.");
					return;
					
				}
				String pasahitza = JOptionPane.showInputDialog(Interfazea.this, "Sartu erabiltzailearen pasahitza:");
				if (pasahitza == null || pasahitza.trim().isEmpty()) {
					JOptionPane.showMessageDialog(Interfazea.this, "Pasahitza ezin da hutsik egon.");
					return;
				}

				// Langilearen datuak (taula: langilea)
				String izena = JOptionPane.showInputDialog(Interfazea.this, "Sartu langilearen izena:");
				if (izena == null || izena.trim().isEmpty()) {
					JOptionPane.showMessageDialog(Interfazea.this, "Izena ezin da hutsik egon.");
					return;
				}
				String abizena = JOptionPane.showInputDialog(Interfazea.this, "Sartu langilearen abizena:");
				if (abizena == null || abizena.trim().isEmpty()) {
					JOptionPane.showMessageDialog(Interfazea.this, "Abizena ezin da hutsik egon.");
					return;
				}
				String nan = JOptionPane.showInputDialog(Interfazea.this, "Sartu langilearen NAN:");
				if (nan == null || nan.trim().isEmpty()) {
					JOptionPane.showMessageDialog(Interfazea.this, "NAN ezin da hutsik egon.");
					return;
				}

				// Taxistaren datuak (taula: taxista)
				String matrikula = JOptionPane.showInputDialog(Interfazea.this, "Sartu taxistaren matrikula:");
				if (matrikula == null || matrikula.trim().isEmpty()) {
					JOptionPane.showMessageDialog(Interfazea.this, "Matrikula ezin da hutsik egon.");
					return;
				}
				String edukieraStr = JOptionPane.showInputDialog(Interfazea.this, "Sartu taxistaren edukiera:");
				if (edukieraStr == null || edukieraStr.trim().isEmpty()) {
					JOptionPane.showMessageDialog(Interfazea.this, "Edukiera ezin da hutsik egon.");
					return;
				}
				String egoera = JOptionPane.showInputDialog(Interfazea.this, "Sartu taxistaren egoera:");
				if (egoera == null || egoera.trim().isEmpty()) {
					JOptionPane.showMessageDialog(Interfazea.this, "Egoera ezin da hutsik egon.");
					return;
				}
				String tarifaStr = JOptionPane.showInputDialog(Interfazea.this, "Sartu taxistaren tarifa:");
				if (tarifaStr == null || tarifaStr.trim().isEmpty()) {
					JOptionPane.showMessageDialog(Interfazea.this, "Tarifa ezin da hutsik egon.");
					return;
				}

				try {
					double tarifa = Double.parseDouble(tarifaStr);
					int edukiera = Integer.parseInt(edukieraStr);
					// Llamada al método para crear el taxista con toda la información
					boolean ondo = Metodoak.gidariGehitu(posta, pasahitza, // erabiltzailea
							izena, abizena, nan, // langilea
							matrikula, edukiera, egoera, tarifa // taxista
					);
					if (ondo) {
						JOptionPane.showMessageDialog(Interfazea.this, "Taxista ondo sortu da.");
					} else {
						JOptionPane.showMessageDialog(Interfazea.this, "Taxista sortzean arazo bat egon da.");
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(Interfazea.this, "Tarifa ez da baliozko zenbaki bat.");
				}
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
				String dataFiltro = txtFiltroData.getText().trim();
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

	private void historialaKargatu(String dataFiltro, String gidariaFiltro, String prezioaFiltro) {
		
		// Taula hustu
		modelHistorial.setRowCount(0);

		// Historialaren select-a 
		String sql = "SELECT idhistoriala, hasiera_kokapena, helmuga_kokapena, data, "
				+ "hasiera_ordua, bukaera_ordua, xehetasunak, idgidaria, idbezeroa, "
				+ "bidaiak_idbidaiak, prezioa FROM historiala WHERE 1=1";

		// Jasotako baloreen arabera WHERE-eri gauzak gehitu
		if (!dataFiltro.isEmpty())
			sql += " AND data = ?";
		
		if (!gidariaFiltro.isEmpty())
			sql += " AND idgidaria = ?";
		
		if (!prezioaFiltro.isEmpty())
			sql += " AND prezioa >= ?";

		try (Connection conn = Konexioa.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			// Indexa hasieratu
			int index = 1;
			
			// Jasotako baloreen arabera galdera ikurrei baloreak eman
			if (!dataFiltro.isEmpty())
				ps.setString(index++, dataFiltro);
			
			if (!gidariaFiltro.isEmpty())
				ps.setInt(index++, Integer.parseInt(gidariaFiltro));
			
			if (!prezioaFiltro.isEmpty())
				ps.setDouble(index++, Double.parseDouble(prezioaFiltro));

			// Kontsulta exekutatu eta resultset batean gordetzen saiatu
			try (ResultSet rs = ps.executeQuery()) {
				
				// ResultSet-a igarotu
				while (rs.next()) {
					
					// Hilarak sortu datu basetik lortutako baloreekin
					Object[] row = { rs.getInt("idhistoriala"), rs.getString("hasiera_kokapena"),
							rs.getString("helmuga_kokapena"), rs.getString("data"), rs.getString("hasiera_ordua"),
							rs.getString("bukaera_ordua"), rs.getString("xehetasunak"), rs.getInt("idgidaria"),
							rs.getInt("idbezeroa"), rs.getInt("bidaiak_idbidaiak"), rs.getDouble("prezioa") };
					
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

	private void gidariakKargatu(String izena, String abizena) {
		modelGidaria.setRowCount(0); // Vaciar la tabla antes de cargar los datos

		String sql = "SELECT * FROM langilea JOIN erabiltzailea ON langilea.erabiltzailea_iderabiltzailea = erabiltzailea.iderabiltzailea JOIN taxista ON langilea.idlangilea= taxista.langilea_idlangilea WHERE langilea.mota = 'taxista'";

		if (!izena.isEmpty())
			sql += " AND izena = ?";
		if (!abizena.isEmpty())
			sql += " AND abizena = ?";

		try (Connection conn = Konexioa.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			int index = 1;
			if (!izena.isEmpty())
				ps.setString(index++, izena);
			if (!abizena.isEmpty())
				ps.setString(index++, abizena);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Object[] row = { rs.getInt("idlangilea"), rs.getString("izena"), rs.getString("abizena"),
							rs.getString("nan"), rs.getString("posta"), rs.getString("pasahitza"),
							rs.getString("matrikula"), rs.getInt("edukiera"), rs.getString("egoera"),
							rs.getDouble("tarifa"), };
					modelGidaria.addRow(row);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Errorea bilaketa egitean: " + ex.getMessage());
		}
	}

	private void langileaAldatu(int id, String zutabea, String baloreBerria) {
		String sql = null; // Inicializamos la consulta SQL
		String tabla = ""; // Definimos la tabla a modificar

		// Determinar en qué tabla se encuentra el campo a actualizar
		if (zutabea.equals("Izena") || zutabea.equals("Abizena") || zutabea.equals("NAN")) {
			sql = "UPDATE langilea SET " + zutabea.toLowerCase() + " = ? WHERE idlangilea = ?";
			tabla = "langilea";
		} else if (zutabea.equals("Posta") || zutabea.equals("Pasahitza")) {
			sql = "UPDATE erabiltzailea SET " + zutabea.toLowerCase()
					+ " = ? WHERE iderabiltzailea = (SELECT erabiltzailea_iderabiltzailea FROM langilea WHERE idlangilea = ?)";
			tabla = "erabiltzailea";
		} else if (zutabea.equals("Matrikula") || zutabea.equals("Edukiera") || zutabea.equals("Egoera")
				|| zutabea.equals("Tarifa")) {
			sql = "UPDATE taxista SET " + zutabea.toLowerCase() + " = ? WHERE langilea_idlangilea = ?";
			tabla = "taxista";
		}

		// Si el campo no pertenece a ninguna tabla conocida, no se hace nada
		if (sql == null) {
			JOptionPane.showMessageDialog(null, "Ezin da eguneratu: zutabea ez da balioduna." + zutabea);
			return;
		}

		// Ejecutar la actualización en la tabla correcta
		try (Connection conn = Konexioa.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, baloreBerria);
			ps.setInt(2, id);
			ps.executeUpdate();

			JOptionPane.showMessageDialog(null, "Datuak eguneratu dira " + tabla + " taulan.");

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Errorea datuak eguneratzean: " + ex.getMessage());
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Interfazea().setVisible(true));
	}
}
