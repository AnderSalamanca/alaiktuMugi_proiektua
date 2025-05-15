package praktikak;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainApp {
    private static final String DB_URL = "jdbc:mysql://10.23.25.161:3307/alaiktumugi?serverTimezone=UTC";
    private static final String USER = "xiker";
    private static final String PASSWORD = "pvlbtnse";

    private static JTextArea outputArea;

    public static void main(String[] args) {
        JFrame frame = new JFrame("AlaiktoMUGI - Admin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        // === PANELES ===
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10)); // SOLO BOTONES - IZQUIERDA
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));   // SOLO FORMULARIO - DERECHA

        // === FORMULARIOA ===
        JLabel nameLabel = new JLabel("Izena:");
        JTextField nameField = new JTextField();

        JLabel surnameLabel = new JLabel("Abizena:");
        JTextField surnameField = new JTextField();

        JLabel nanLabel = new JLabel("NAN:");
        JTextField nanField = new JTextField();

        JLabel emailLabel = new JLabel("Posta Elektronikoa:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Pasahitza:");
        JTextField passwordField = new JTextField();

        JLabel licenseLabel = new JLabel("Matrikula:");
        JTextField licenseField = new JTextField();

        JLabel capacityLabel = new JLabel("Edukiera:");
        JTextField capacityField = new JTextField();

        JButton addDriverButton = new JButton("Gidaria Gehitu");

        // === FORM PANEL (ESKUBI ALDEA) ===
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(surnameLabel);
        formPanel.add(surnameField);
        formPanel.add(nanLabel);
        formPanel.add(nanField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(licenseLabel);
        formPanel.add(licenseField);
        formPanel.add(capacityLabel);
        formPanel.add(capacityField);
        formPanel.add(new JLabel()); // Spacer
        formPanel.add(addDriverButton);

        // === ACTION LISTENER gehitu gidaria ===
        addDriverButton.addActionListener(e -> {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String nan = nanField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String license = licenseField.getText();
            String capacity = capacityField.getText();

            if (!name.isEmpty() && !surname.isEmpty() && !nan.isEmpty() &&
                !email.isEmpty() && !password.isEmpty() && !license.isEmpty() && !capacity.isEmpty()) {
                addDriverToDatabase(name, surname, nan, email, password, license, capacity);
            } else {
                JOptionPane.showMessageDialog(frame, "Mesedez, bete eremu guztiak.");
            }
        });

        // === BOTONES ACCIÓN (EZKERRA) ===
        JButton showDriversButton = new JButton("Gidariak Ikusi");
        JButton deleteDriverButton = new JButton("Gidaria Ezabatu");
        JButton editDriverButton = new JButton("Gidaria Editatu");
        JButton showDriverHistoryButton = new JButton("Gidariaren historiala ikusi");
        JButton showCustomerHistoryButton = new JButton("Bezeroaren historiala ikusi");
        JButton showAllTripsButton = new JButton("Bidaien historiala ikusi");

        buttonPanel.add(showDriversButton);
        buttonPanel.add(deleteDriverButton);
        buttonPanel.add(editDriverButton);
        buttonPanel.add(showDriverHistoryButton);
        buttonPanel.add(showCustomerHistoryButton);
        buttonPanel.add(showAllTripsButton);

        // === ACTION LISTENERS ===
        showDriversButton.addActionListener(e -> showAllDrivers());
        deleteDriverButton.addActionListener(e -> {
            String driverId = JOptionPane.showInputDialog("Sartu gidariaren langilea ID-a:");
            if (driverId != null && !driverId.isEmpty()) deleteDriver(driverId);
        });
        editDriverButton.addActionListener(e -> {
            String driverId = JOptionPane.showInputDialog("Sartu gidariaren langilea ID-a:");
            if (driverId != null && !driverId.isEmpty()) editDriver(driverId);
        });
        showDriverHistoryButton.addActionListener(e -> {
            String izena = JOptionPane.showInputDialog("Sartu gidariaren izena:");
            if (izena != null && !izena.isEmpty()) {
                String abizena = JOptionPane.showInputDialog("Sartu gidariaren abizena:");
                if (abizena != null && !abizena.isEmpty()) {
                    showDriverHistoryByName(izena, abizena);
                }
            }
        });
        showCustomerHistoryButton.addActionListener(e -> {
            String izena = JOptionPane.showInputDialog("Sartu bezeroaren izena:");
            if (izena != null && !izena.isEmpty()) {
                String abizena = JOptionPane.showInputDialog("Sartu bezeroaren abizena:");
                if (abizena != null && !abizena.isEmpty()) {
                    showCustomerHistoryByName(izena, abizena);
                } else {
                    JOptionPane.showMessageDialog(null, "Abizena ezin da hutsik egon.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Izena ezin da hutsik egon.");
            }
        });

        showAllTripsButton.addActionListener(e -> showAllTrips());

        // === OUTPUT AREA (AZPIAN) ===
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // === FRAME-RA GEHITU ===
        frame.add(buttonPanel, BorderLayout.WEST);     // Botones ezkerrean
        frame.add(formPanel, BorderLayout.CENTER);     // Formulario erdian
        frame.add(scrollPane, BorderLayout.SOUTH);      // Output azpian

        frame.setVisible(true);
    }


    // DB-ra konektatzeko funtzioa
    public static Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Gidaria datu-basean gehitzeko funtzioa
    public static void addDriverToDatabase(String name, String surname, String nan, String email, String password, String license, String capacity) {
        // Datu-basearekin konexioa lortzen saiatzen gara
        Connection connection = connect();
        if (connection != null) {
            try {
                // Transakzioa hasiko dugu, auto commit desaktibatuz
                connection.setAutoCommit(false);

                // Erabiltzailea datu-basean gehitzeko SQL galdera prestatzen dugu
                // Hemen 'posta' eta 'pasahitza' sartuko ditugu, baita 'rol' bezala 'taxista' ere
                String erabiltzaileaQuery = "INSERT INTO erabiltzailea (posta, pasahitza, rola) VALUES (?, ?, 'taxista')";
                // PreparedStatement erabiliz, '?' tokiak parametroekin ordezkatuko ditugu
                PreparedStatement erabiltzaileaStmt = connection.prepareStatement(erabiltzaileaQuery, Statement.RETURN_GENERATED_KEYS);
                // 'posta' eta 'pasahitza' balioak ezartzen dira
                erabiltzaileaStmt.setString(1, email);
                erabiltzaileaStmt.setString(2, password);
                // Galdera exekutatzen dugu
                erabiltzaileaStmt.executeUpdate();
                // Galdera exekutatu ondoren, sartutako erabiltzailea lortzen dugu (berriki sartutako ID-a)
                ResultSet erabiltzaileaKeys = erabiltzaileaStmt.getGeneratedKeys();
                erabiltzaileaKeys.next(); // Lehenengo elementuari sartzen gara
                int erabiltzaileaId = erabiltzaileaKeys.getInt(1); // 'erabiltzailea' ID-a jasotzen dugu

                // Langilea datu-basean gehitzeko SQL galdera prestatzen dugu
                // Langileari 'izena', 'abizena', 'nan' eta 'erabiltzailea_id' txertatzen ari gara
                String langileaQuery = "INSERT INTO langilea (izena, abizena, nan, mota, erabiltzailea_iderabiltzailea) VALUES (?, ?, ?, 'taxista', ?)";
                // PreparedStatement-a prestatzen dugu, eta beheko parametroak ezartzen ditugu
                PreparedStatement langileaStmt = connection.prepareStatement(langileaQuery, Statement.RETURN_GENERATED_KEYS);
                langileaStmt.setString(1, name);
                langileaStmt.setString(2, surname);
                langileaStmt.setString(3, nan);
                langileaStmt.setInt(4, erabiltzaileaId);
                // Galdera exekutatzen dugu
                langileaStmt.executeUpdate();
                // Langileari dagokion ID-a lortzen dugu
                ResultSet langileaKeys = langileaStmt.getGeneratedKeys();
                langileaKeys.next();
                int langileaId = langileaKeys.getInt(1);

                // Taxista datu-basean gehitzeko SQL galdera prestatzen dugu
                // 'matrikula' eta 'edukiera' sartzen ari gara, baita 'egoera' ('aktibo') ere
                String taxistaQuery = "INSERT INTO taxista (matrikula, edukiera, egoera, langilea_idlangilea) VALUES (?, ?, 'aktibo', ?)";
                // PreparedStatement prestatzen dugu eta parametroak sartzen ditugu
                PreparedStatement taxistaStmt = connection.prepareStatement(taxistaQuery, Statement.RETURN_GENERATED_KEYS);
                taxistaStmt.setString(1, license);
                taxistaStmt.setString(2, capacity);
                taxistaStmt.setInt(3, langileaId);
                // Galdera exekutatzen dugu
                taxistaStmt.executeUpdate();

                // Datu-baseko aldaketak finkatzen ditugu (commit)
                connection.commit();
                // Erabiltzaileari jakinarazpen bat ematen diogu, gidaria ondo gehitu dela
                JOptionPane.showMessageDialog(null, "Gidaria ondo gehitu da.");
            } catch (SQLException e) {
                // SQL errore bat gertatuz gero, aldaketak atzera botatzen dira
                e.printStackTrace();
                try {
                    connection.rollback(); // Transakzioa itzuli
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                // Errorea jakinarazten dugu
                JOptionPane.showMessageDialog(null, "Errore bat egon da gidaria gehitzean.");
            } finally {
                try {
                    // Azkenik, konexioa itxi eta auto commit-ean itzultzen gara
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // Gidari guztiak erakusteko funtzioa
    public static void showAllDrivers() {
        Connection connection = connect();
        if (connection != null) {
            try {
                String query = "SELECT l.idlangilea, l.izena, l.abizena, l.nan, e.posta, t.matrikula, t.edukiera " +
                        "FROM langilea l JOIN erabiltzailea e ON l.erabiltzailea_iderabiltzailea = e.iderabiltzailea " +
                        "JOIN taxista t ON l.idlangilea = t.langilea_idlangilea";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                StringBuilder drivers = new StringBuilder("Gidariak:\n");
                while (rs.next()) {
                    drivers.append("Langilea ID: ").append(rs.getInt("idlangilea"))
                            .append(", Izena: ").append(rs.getString("izena"))
                            .append(" ").append(rs.getString("abizena"))
                            .append(", NAN: ").append(rs.getString("nan"))
                            .append(", Emaila: ").append(rs.getString("posta"))
                            .append(", Matrikula: ").append(rs.getString("matrikula"))
                            .append(", Edukiera: ").append(rs.getString("edukiera"))
                            .append("\n");
                }

                outputArea.setText(drivers.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                outputArea.setText("Errore bat egon da gidariak erakusteko.");
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Gidariaren datuak editatzeko funtzioa
    public static void editDriver(String langileaId) {
        Connection connection = connect();
        if (connection != null) {
            try {
                String newName = JOptionPane.showInputDialog("Sartu izena:");
                String newSurname = JOptionPane.showInputDialog("Sartu abizena:");
                String newNan = JOptionPane.showInputDialog("Sartu NAN:");
                String newEmail = JOptionPane.showInputDialog("Sartu posta elektronikoa:");
                String newLicense = JOptionPane.showInputDialog("Sartu matrikula:");
                String newCapacity = JOptionPane.showInputDialog("Sartu edukiera:");

                // Langilea eguneratu
                String updateLangileaQuery = "UPDATE langilea SET izena = ?, abizena = ?, nan = ? WHERE idlangilea = ?";
                PreparedStatement updateLangileaStmt = connection.prepareStatement(updateLangileaQuery);
                updateLangileaStmt.setString(1, newName);
                updateLangileaStmt.setString(2, newSurname);
                updateLangileaStmt.setString(3, newNan);
                updateLangileaStmt.setInt(4, Integer.parseInt(langileaId));
                updateLangileaStmt.executeUpdate();

                // Taxista eguneratu
                String updateTaxistaQuery = "UPDATE taxista SET matrikula = ?, edukiera = ? WHERE langilea_idlangilea = ?";
                PreparedStatement updateTaxistaStmt = connection.prepareStatement(updateTaxistaQuery);
                updateTaxistaStmt.setString(1, newLicense);
                updateTaxistaStmt.setString(2, newCapacity);
                updateTaxistaStmt.setInt(3, Integer.parseInt(langileaId));
                updateTaxistaStmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Gidaria ondo eguneratu da.");
                showAllDrivers();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore bat egon da gidaria eguneratzean.");
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Gidaria ezabatzeko funtzioa
    public static void deleteDriver(String langileaId) {
        Connection connection = connect();
        if (connection != null) {
            try {
                connection.setAutoCommit(false);

                // Erabiltzailea lortu
                String getErabiltzaileaQuery = "SELECT erabiltzailea_iderabiltzailea FROM langilea WHERE idlangilea = ?";
                PreparedStatement getStmt = connection.prepareStatement(getErabiltzaileaQuery);
                getStmt.setInt(1, Integer.parseInt(langileaId));
                ResultSet rs = getStmt.executeQuery();

                if (rs.next()) {
                    int erabiltzaileaId = rs.getInt("erabiltzailea_iderabiltzailea");

                    // Datuak ezabatu
                    PreparedStatement deleteTaxistaStmt = connection.prepareStatement("DELETE FROM taxista WHERE langilea_idlangilea = ?");
                    deleteTaxistaStmt.setInt(1, Integer.parseInt(langileaId));
                    deleteTaxistaStmt.executeUpdate();

                    PreparedStatement deleteLangileaStmt = connection.prepareStatement("DELETE FROM langilea WHERE idlangilea = ?");
                    deleteLangileaStmt.setInt(1, Integer.parseInt(langileaId));
                    deleteLangileaStmt.executeUpdate();

                    PreparedStatement deleteErabiltzaileaStmt = connection.prepareStatement("DELETE FROM erabiltzailea WHERE iderabiltzailea = ?");
                    deleteErabiltzaileaStmt.setInt(1, erabiltzaileaId);
                    deleteErabiltzaileaStmt.executeUpdate();

                    connection.commit();
                    JOptionPane.showMessageDialog(null, "Gidaria ondo ezabatu da.");
                    showAllDrivers();
                } else {
                    JOptionPane.showMessageDialog(null, "Langilea ID hori ez da aurkitu.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Errore bat egon da gidaria ezabatzean.");
            } finally {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void showDriverHistoryByName(String izena, String abizena) {
        Connection connection = connect();
        if (connection != null) {
            try {
                String query = "SELECT h.idhistoriala, h.hasiera_kokapena, h.helmuga_kokapena, h.data, h.hasiera_ordua, h.bukaera_ordua, h.xehetasunak " +
                               "FROM historiala h " +
                               "JOIN langilea l ON h.idgidaria = l.idlangilea " +
                               "WHERE l.izena = ? AND l.abizena = ?";

                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, izena);
                stmt.setString(2, abizena);

                ResultSet rs = stmt.executeQuery();

                StringBuilder history = new StringBuilder("Gidariaren historiala:\n");
                while (rs.next()) {
                    history.append("ID: ").append(rs.getInt("idhistoriala"))
                           .append(", Hasiera: ").append(rs.getString("hasiera_kokapena"))
                           .append(", Helmuga: ").append(rs.getString("helmuga_kokapena"))
                           .append(", Data: ").append(rs.getDate("data"))
                           .append(", Ordua: ").append(rs.getTime("hasiera_ordua"))
                           .append(" - ").append(rs.getTime("bukaera_ordua"))
                           .append(", Xehetasunak: ").append(rs.getString("xehetasunak"))
                           .append("\n");
                }

                outputArea.setText(history.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                outputArea.setText("Errore bat egon da gidariaren historiala erakusteko.");
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void showCustomerHistoryByName(String izena, String abizena) {
        Connection connection = connect();
        if (connection != null) {
            try {
                String queryId = "SELECT idbezeroa FROM bezeroa WHERE izena = ? AND abizena = ?";
                PreparedStatement stmtId = connection.prepareStatement(queryId);
                stmtId.setString(1, izena);
                stmtId.setString(2, abizena);
                ResultSet rsId = stmtId.executeQuery();

                if (rsId.next()) {
                    int bezeroaId = rsId.getInt("idbezeroa");

                    String queryHistory = "SELECT h.idhistoriala, h.hasiera_kokapena, h.helmuga_kokapena, h.data, h.hasiera_ordua, h.bukaera_ordua, h.xehetasunak " +
                                          "FROM historiala h WHERE h.idbezeroa = ?";

                    PreparedStatement stmtHistory = connection.prepareStatement(queryHistory);
                    stmtHistory.setInt(1, bezeroaId);
                    ResultSet rsHistory = stmtHistory.executeQuery();

                    StringBuilder history = new StringBuilder("Bezeroaren historiala:\n");
                    while (rsHistory.next()) {
                        history.append("ID: ").append(rsHistory.getInt("idhistoriala"))
                               .append(", Hasiera: ").append(rsHistory.getString("hasiera_kokapena"))
                               .append(", Helmuga: ").append(rsHistory.getString("helmuga_kokapena"))
                               .append(", Data: ").append(rsHistory.getDate("data"))
                               .append(", Ordua: ").append(rsHistory.getTime("hasiera_ordua"))
                               .append(" - ").append(rsHistory.getTime("bukaera_ordua"))
                               .append(", Xehetasunak: ").append(rsHistory.getString("xehetasunak"))
                               .append("\n");
                    }

                    outputArea.setText(history.toString());  // Aquí cambiamos System.out.println por outputArea.setText

                } else {
                    outputArea.setText("Bezeroa ez da aurkitu izen eta abizena erabiliz."); // Mensaje en JTextArea
                }

            } catch (SQLException e) {
                e.printStackTrace();
                outputArea.setText("Errore bat egon da bezeroaren historiala erakusteko.");  // Mensaje en JTextArea
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public static void showAllTrips() {
        Connection connection = connect();
        if (connection != null) {
            try {
                String query = "SELECT * FROM bidaiak";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                StringBuilder trips = new StringBuilder("Bidaien historiala:\n");
                while (rs.next()) {
                    trips.append("ID: ").append(rs.getInt("idbidaiak"))
                         .append(", Data: ").append(rs.getDate("data"))
                         .append(", Egoera: ").append(rs.getString("egoera"))
                         .append(", Hasiera: ").append(rs.getString("hasiera_kokapena"))
                         .append(", Helmuga: ").append(rs.getString("helmuga_kokapena"))
                         .append(", Ordua: ").append(rs.getTime("hasiera_ordua"))
                         .append(" - ").append(rs.getTime("bukaera_ordua"))
                         .append("\n");
                }

                outputArea.setText(trips.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                outputArea.setText("Errore bat egon da bidaien historiala erakusteko.");
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
