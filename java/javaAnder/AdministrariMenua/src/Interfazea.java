import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Interfazea extends JFrame {

    // COMPONENTES PARA LA SECCIÓN DE GIDARIAK
    private JButton btnGidariGehitu;
    private JButton btnGidariAldatu;
    private JButton btnGidariEzabatu;
    private JButton btnGidariIkusi;

    // COMPONENTES PARA LA SECCIÓN DE HISTORIALA
    private JTable tableHistorial;
    private DefaultTableModel modelHistorial;
    
    private JTextField txtFiltroFecha, txtFiltroGidaria, txtFiltroPrezioa;
    private JButton btnFiltrar;

    public Interfazea() {
        super("Administrazio Interfazea");
        initComponents();
        addEventListeners();
        cargarHistorial("","","");
    }

    private void initComponents() {
        // Configuración de ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centrar la ventana
        setLayout(new BorderLayout());

        // Creación del TabbedPane principal
        JTabbedPane tabbedPane = new JTabbedPane();

        // SECCIÓN: Gidariak Kudeatu
        JPanel panelGidariak = new JPanel(new BorderLayout());
        JPanel panelBotoiakGidariak = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JPanel panelFiltro = new JPanel();
        
        txtFiltroFecha = new JTextField(10);
        txtFiltroGidaria = new JTextField(5);
        txtFiltroPrezioa = new JTextField(5);
        btnFiltrar = new JButton("Bilaketa");

        panelFiltro.add(new JLabel("Data:"));
        panelFiltro.add(txtFiltroFecha);
        panelFiltro.add(new JLabel("Gidaria ID:"));
        panelFiltro.add(txtFiltroGidaria);
        panelFiltro.add(new JLabel("Prezioa min:"));
        panelFiltro.add(txtFiltroPrezioa);
        panelFiltro.add(btnFiltrar);
        

        btnGidariGehitu = new JButton("Gidari Gehitu");
        btnGidariAldatu = new JButton("Gidari Aldatu");
        btnGidariEzabatu = new JButton("Gidari Ezabatu");
        btnGidariIkusi = new JButton("Gidariak Ikusi");

        panelBotoiakGidariak.add(btnGidariGehitu);
        panelBotoiakGidariak.add(btnGidariAldatu);
        panelBotoiakGidariak.add(btnGidariEzabatu);
        panelBotoiakGidariak.add(btnGidariIkusi);
        panelGidariak.add(panelBotoiakGidariak, BorderLayout.NORTH);

        tabbedPane.addTab("Gidariak Kudeatu", panelGidariak);

        // SECCIÓN: Historiala
        JPanel panelHistoria = new JPanel(new BorderLayout());
        String[] zutabeHistorial = {
            "ID", "Hasiera Kokapena", "Helmuga Kokapena", "Data",
            "Hasiera Ordua", "Bukaera Ordua", "Xehetasunak",
            "ID Gidaria", "ID Bezeroa", "Bidaiak", "Prezioa"
        };
        modelHistorial = new DefaultTableModel(zutabeHistorial, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas serán solo de lectura
            }
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:  // ID Historiala
                    case 7:  // ID Gidaria
                    case 8:  // ID Bezeroa
                    case 9:  // Bidaiak
                        return Integer.class;
                    case 10: // Prezioa
                        return Double.class;
                    default:
                        return String.class; // Todas las demás columnas son texto
                }
            }
        };
        tableHistorial = new JTable(modelHistorial);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelHistorial);
        tableHistorial.setRowSorter(sorter);
        JScrollPane scrollHistorial = new JScrollPane(tableHistorial);
        panelHistoria.add(scrollHistorial, BorderLayout.CENTER);
        panelHistoria.add(panelFiltro, BorderLayout.NORTH);

        tabbedPane.addTab("Historiala", panelHistoria);

        // Se añade el TabbedPane sólo una vez
        add(tabbedPane, BorderLayout.CENTER);
    }

    // Método encargado de registrar los eventos (action listeners)
    private void addEventListeners() {
        // Ejemplo de acción para el botón "Gidari Gehitu"
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
                    boolean ondo = Metodoak.gidariGehitu(
                            posta, pasahitza,  // erabiltzailea
                            izena, abizena, nan, // langilea
                            matrikula, edukiera, egoera, tarifa  // taxista
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


        // **Gidari Aldatu:** IDa eta datu berriak eskatuta, Metodoak.gidariAldatu deitzen da.
        btnGidariAldatu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idStr = JOptionPane.showInputDialog(Interfazea.this, "Sartu aldatzeko gidariaren ID-a:");
                if (idStr == null || idStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(Interfazea.this, "ID-a beharrezkoa da.");
                    return;
                }
                try {
                    int id = Integer.parseInt(idStr);
                    String berrizena = JOptionPane.showInputDialog(Interfazea.this, "Sartu berria den izena:");
                    String berriadinaStr = JOptionPane.showInputDialog(Interfazea.this, "Sartu berria den adina:");
                    String berrilizentzia = JOptionPane.showInputDialog(Interfazea.this, "Sartu berria den lizentzia zenbakia:");
                    int berriadina = Integer.parseInt(berriadinaStr);
                    boolean ondo = Metodoak.gidariAldatu(id, berrizena, berriadina, berrilizentzia);
                    if (ondo) {
                        JOptionPane.showMessageDialog(Interfazea.this, "Gidaria ondo eguneratu da.");
                    } else {
                        JOptionPane.showMessageDialog(Interfazea.this, "Ez da aurkitu id hori duen gidaria.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Interfazea.this, "Datuak ez dira baliozkoak.");
                }
            }
        });

        // **Gidari Ezabatu:** ID-a eskatuta, Metodoak.gidariEzabatu deitzen da.
        btnGidariEzabatu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idStr = JOptionPane.showInputDialog(Interfazea.this, "Sartu ezabatzeko gidariaren ID-a:");
                if (idStr == null || idStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(Interfazea.this, "ID-a beharrezkoa da.");
                    return;
                }
                int konfirm = JOptionPane.showConfirmDialog(Interfazea.this,
                        "Ziur zaude aukeratutako gidaria ezabatu nahi duzula?",
                        "Ezabatzeko berreskurapena", JOptionPane.YES_NO_OPTION);
                if (konfirm == JOptionPane.YES_OPTION) {
                    try {
                        int id = Integer.parseInt(idStr);
                        boolean ondo = Metodoak.gidariEzabatu(id);
                        if (ondo) {
                            JOptionPane.showMessageDialog(Interfazea.this, "Gidaria ondo ezabatu da.");
                        } else {
                            JOptionPane.showMessageDialog(Interfazea.this, "Ez da aurkitu id hori duen gidaria.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Interfazea.this, "ID-a zenbaki bat izan behar da.");
                    }
                }
            }
        });
        
        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dataFiltro = txtFiltroFecha.getText().trim();
                String gidariaFiltro = txtFiltroGidaria.getText().trim();
                String prezioaFiltro = txtFiltroPrezioa.getText().trim();
                cargarHistorial(dataFiltro, gidariaFiltro, prezioaFiltro);
            }
        });

        // **Historia ataleko ordenamendu botoiak:** (Logika pendentea, datu modeloak ordenatzeko)
        
    }

    
    private void cargarHistorial(String dataFiltro, String gidariaFiltro, String prezioaFiltro) {
        modelHistorial.setRowCount(0); // Vaciar la tabla antes de cargar los datos

        String sql = "SELECT idhistoriala, hasiera_kokapena, helmuga_kokapena, data, " +
                     "hasiera_ordua, bukaera_ordua, xehetasunak, idgidaria, idbezeroa, " +
                     "bidaiak_idbidaiak, prezioa FROM historiala WHERE 1=1";

        if (!dataFiltro.isEmpty()) sql += " AND data = ?";
        if (!gidariaFiltro.isEmpty()) sql += " AND idgidaria = ?";
        if (!prezioaFiltro.isEmpty()) sql += " AND prezioa >= ?";

        try (Connection conn = Konexioa.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int index = 1;
            if (!dataFiltro.isEmpty()) ps.setString(index++, dataFiltro);
            if (!gidariaFiltro.isEmpty()) ps.setInt(index++, Integer.parseInt(gidariaFiltro));
            if (!prezioaFiltro.isEmpty()) ps.setDouble(index++, Double.parseDouble(prezioaFiltro));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("idhistoriala"),
                        rs.getString("hasiera_kokapena"),
                        rs.getString("helmuga_kokapena"),
                        rs.getString("data"),
                        rs.getString("hasiera_ordua"),
                        rs.getString("bukaera_ordua"),
                        rs.getString("xehetasunak"),
                        rs.getInt("idgidaria"),
                        rs.getInt("idbezeroa"),
                        rs.getInt("bidaiak_idbidaiak"),
                        rs.getDouble("prezioa")
                    };
                    modelHistorial.addRow(row);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errorea bilaketa egitean: " + ex.getMessage());
        }
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Interfazea().setVisible(true));
    }
}
