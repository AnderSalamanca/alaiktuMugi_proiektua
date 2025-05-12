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
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 10, 10));

        // Formulario
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

        JLabel licenseLabel = new JLabel("Gidabaimen Zenbakia:");
        JTextField licenseField = new JTextField();

        JLabel capacityLabel = new JLabel("Ibilgailuaren Edukiera (Plazak):");
        JTextField capacityField = new JTextField();

        JButton addDriverButton = new JButton("Gidaria Gehitu");

        buttonPanel.add(nameLabel);
        buttonPanel.add(nameField);
        buttonPanel.add(surnameLabel);
        buttonPanel.add(surnameField);
        buttonPanel.add(nanLabel);
        buttonPanel.add(nanField);
        buttonPanel.add(emailLabel);
        buttonPanel.add(emailField);
        buttonPanel.add(passwordLabel);
        buttonPanel.add(passwordField);
        buttonPanel.add(licenseLabel);
        buttonPanel.add(licenseField);
        buttonPanel.add(capacityLabel);
        buttonPanel.add(capacityField);
        buttonPanel.add(addDriverButton);

        addDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String nan = nanField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                String license = licenseField.getText();
                String capacity = capacityField.getText();

                if (!name.isEmpty() && !surname.isEmpty() && !nan.isEmpty() && !email.isEmpty() && !password.isEmpty() && !license.isEmpty() && !capacity.isEmpty()) {
                    addDriverToDatabase(name, surname, nan, email, password, license, capacity);
                } else {
                    JOptionPane.showMessageDialog(frame, "Mesedez, sartu datu guztiak.");
                }
            }
        });

        JButton showDriversButton = new JButton("Gidariak Ikusi");
        buttonPanel.add(showDriversButton);
        showDriversButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllDrivers();
            }
        });

        JButton deleteDriverButton = new JButton("Gidaria Ezabatu");
        buttonPanel.add(deleteDriverButton);
        deleteDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String driverId = JOptionPane.showInputDialog("Sartu ezabatu nahi duzun gidariaren langilea ID-a:");
                if (driverId != null && !driverId.isEmpty()) {
                    deleteDriver(driverId);
                } else {
                    JOptionPane.showMessageDialog(frame, "Mesedez, sartu langilea ID-a.");
                }
            }
        });

        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addDriverToDatabase(String name, String surname, String nan, String email, String password, String license, String capacity) {
        Connection connection = connect();
        if (connection != null) {
            try {
                connection.setAutoCommit(false);

                // 1️⃣ Insert into erabiltzailea
                String erabiltzaileaQuery = "INSERT INTO erabiltzailea (posta, pasahitza, rola) VALUES (?, ?, 'taxista')";
                PreparedStatement erabiltzaileaStmt = connection.prepareStatement(erabiltzaileaQuery, Statement.RETURN_GENERATED_KEYS);
                erabiltzaileaStmt.setString(1, email);
                erabiltzaileaStmt.setString(2, password);
                erabiltzaileaStmt.executeUpdate();
                ResultSet erabiltzaileaKeys = erabiltzaileaStmt.getGeneratedKeys();
                erabiltzaileaKeys.next();
                int erabiltzaileaId = erabiltzaileaKeys.getInt(1);

                // 2️⃣ Insert into langilea
                String langileaQuery = "INSERT INTO langilea (izena, abizena, nan, mota, erabiltzailea_iderabiltzailea) VALUES (?, ?, ?, 'taxista', ?)";
                PreparedStatement langileaStmt = connection.prepareStatement(langileaQuery, Statement.RETURN_GENERATED_KEYS);
                langileaStmt.setString(1, name);
                langileaStmt.setString(2, surname);
                langileaStmt.setString(3, nan);
                langileaStmt.setInt(4, erabiltzaileaId);
                langileaStmt.executeUpdate();
                ResultSet langileaKeys = langileaStmt.getGeneratedKeys();
                langileaKeys.next();
                int langileaId = langileaKeys.getInt(1);

                // 3️⃣ Insert into taxista
                String taxistaQuery = "INSERT INTO taxista (matrikula, edukiera, langilea_idlangilea) VALUES (?, ?, ?)";
                PreparedStatement taxistaStmt = connection.prepareStatement(taxistaQuery);
                taxistaStmt.setString(1, license);
                taxistaStmt.setString(2, capacity);
                taxistaStmt.setInt(3, langileaId);
                taxistaStmt.executeUpdate();

                connection.commit();
                JOptionPane.showMessageDialog(null, "Gidaria ondo gehitu da.");
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Errore bat egon da gidaria gehitzean.");
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

    public static void deleteDriver(String langileaId) {
        Connection connection = connect();
        if (connection != null) {
            try {
                connection.setAutoCommit(false);

                // Get erabiltzailea_id first
                String getErabiltzaileaQuery = "SELECT erabiltzailea_iderabiltzailea FROM langilea WHERE idlangilea = ?";
                PreparedStatement getStmt = connection.prepareStatement(getErabiltzaileaQuery);
                getStmt.setInt(1, Integer.parseInt(langileaId));
                ResultSet rs = getStmt.executeQuery();

                if (rs.next()) {
                    int erabiltzaileaId = rs.getInt("erabiltzailea_iderabiltzailea");

                    // Delete from taxista
                    String deleteTaxistaQuery = "DELETE FROM taxista WHERE langilea_idlangilea = ?";
                    PreparedStatement deleteTaxistaStmt = connection.prepareStatement(deleteTaxistaQuery);
                    deleteTaxistaStmt.setInt(1, Integer.parseInt(langileaId));
                    deleteTaxistaStmt.executeUpdate();

                    // Delete from langilea
                    String deleteLangileaQuery = "DELETE FROM langilea WHERE idlangilea = ?";
                    PreparedStatement deleteLangileaStmt = connection.prepareStatement(deleteLangileaQuery);
                    deleteLangileaStmt.setInt(1, Integer.parseInt(langileaId));
                    deleteLangileaStmt.executeUpdate();

                    // Delete from erabiltzailea
                    String deleteErabiltzaileaQuery = "DELETE FROM erabiltzailea WHERE iderabiltzailea = ?";
                    PreparedStatement deleteErabiltzaileaStmt = connection.prepareStatement(deleteErabiltzaileaQuery);
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
}
