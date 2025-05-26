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
<<<<<<< HEAD
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10)); // SOLO BOTONES - IZQUIERDA
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));   // SOLO FORMULARIO - DERECHA

        // === FORMULARIOA ===
=======
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10)); //  BOTOIAK - EZKERREAN
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));   //  FORMULARIOA - ESKUBIAN

        // === FORMULARIOA ===
        
        // Izena sartzeko JTextField-a
>>>>>>> xiker
        JLabel nameLabel = new JLabel("Izena:");
        JTextField nameField = new JTextField();

        // Abizena sartzeko JTextField-a
        JLabel surnameLabel = new JLabel("Abizena:");
        JTextField surnameField = new JTextField();

        // NAN sartzeko JTextField-a
        JLabel nanLabel = new JLabel("NAN:");
        JTextField nanField = new JTextField();

        // Posta sartzeko JTextField-a
        JLabel emailLabel = new JLabel("Posta Elektronikoa:");
        JTextField emailField = new JTextField();

        // Pasahitza sartzeko JTextField-a
        JLabel passwordLabel = new JLabel("Pasahitza:");
        JTextField passwordField = new JTextField();

<<<<<<< HEAD
        JLabel licenseLabel = new JLabel("Matrikula:");
        JTextField licenseField = new JTextField();

=======
        // Matrikula sartzeko JTextField-a
        JLabel licenseLabel = new JLabel("Matrikula:");
        JTextField licenseField = new JTextField();

        // Edukiera sartzeko JTextField-a
>>>>>>> xiker
        JLabel capacityLabel = new JLabel("Edukiera:");
        JTextField capacityField = new JTextField();
        
        // Tarifa sartzeko JTextField-a
        JLabel tarifaLabel = new JLabel("Tarifa:");
        JTextField tarifafield = new JTextField();

        JButton addDriverButton = new JButton("Gidaria Gehitu");

<<<<<<< HEAD
        // === FORM PANEL (ESKUBI ALDEA) ===
=======
        // === FORM PANEL (ESKUBI ALDEA)
>>>>>>> xiker
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
<<<<<<< HEAD
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
=======
        formPanel.add(tarifaLabel);
        formPanel.add(tarifafield);

        formPanel.add(new JLabel()); // Spacer
        formPanel.add(addDriverButton);

        // === ACTION LISTENER "gehitu gidaria"
        addDriverButton.addActionListener(e -> {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String nan = nanField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String license = licenseField.getText();
            String capacity = capacityField.getText();
            String tarifa = tarifafield.getText();

            if (!name.isEmpty() && !surname.isEmpty() && !nan.isEmpty() &&
                !email.isEmpty() && !password.isEmpty() && !license.isEmpty() && !capacity.isEmpty()  && !tarifa.isEmpty()) {
            	addDriverToDatabase(name, surname, nan, email, password, license, capacity, tarifa);
>>>>>>> xiker
            } else {
                JOptionPane.showMessageDialog(frame, "Mesedez, bete eremu guztiak.");
            }
        });

<<<<<<< HEAD
        // === BOTONES ACCIÓN (EZKERRA) ===
=======
        // === BOTONES ACCIÓN (EZKERRA)
>>>>>>> xiker
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

<<<<<<< HEAD
        // === ACTION LISTENERS ===
=======
        // === ACTION LISTENERS 
>>>>>>> xiker
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
<<<<<<< HEAD
            if (izena != null && !izena.isEmpty()) {
                String abizena = JOptionPane.showInputDialog("Sartu gidariaren abizena:");
                if (abizena != null && !abizena.isEmpty()) {
                    showDriverHistoryByName(izena, abizena);
=======
            if (izena == null || izena.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Izena ezin da hutsik egon.");
                return;
            }
            
            String abizena = JOptionPane.showInputDialog("Sartu gidariaren abizena:");
            if (abizena == null || abizena.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Abizena ezin da hutsik egon.");
                return;
            }
            
            int filtratu = JOptionPane.showConfirmDialog(null, "Filtratu nahi duzu dataren arabera?", "Filtratu", JOptionPane.YES_NO_OPTION);
            String data = null;
            if (filtratu == JOptionPane.YES_OPTION) {
                data = JOptionPane.showInputDialog("Sartu data (yyyy-MM-dd formatuan):");
                if (data == null || data.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Data ezin da hutsik egon.");
                    return;
>>>>>>> xiker
                }
            }

            // Galdegin ea ID-ren arabera ordenatu nahi den
            int ordenatu = JOptionPane.showConfirmDialog(null, "Ordenatu nahi duzu ID-ren arabera?", "Ordenatu", JOptionPane.YES_NO_OPTION);

            String ordena = ""; // Ordena gordetzeko aldagai hutsa

            if (ordenatu == JOptionPane.YES_OPTION) {
                // Ordena mota aukeratzeko leihoa erakutsi
                String[] aukera = {"Goranzkoa (ASC)", "Beheranzkoa (DESC)"};
                int ordenaAukera = JOptionPane.showOptionDialog(null,
                    "Nola ordenatu nahi dituzu datuak?",
                    "Ordena aukeratu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    aukera,
                    aukera[0]);

                // Aukeraren arabera ordena zehaztu
                if (ordenaAukera == 0) {
                    ordena = "ASC";
                } else if (ordenaAukera == 1) {
                    ordena = "DESC";
                }
            }

            // Gidariaren historia erakutsi, hautatutako ordenarekin
            showDriverHistoryByName(izena, abizena, data, ordena);
        });
        
    


        showCustomerHistoryButton.addActionListener(e -> {
            String izena = JOptionPane.showInputDialog("Sartu bezeroaren izena:");
            if (izena != null && !izena.isEmpty()) {
                String abizena = JOptionPane.showInputDialog("Sartu bezeroaren abizena:");
                if (abizena != null && !abizena.isEmpty()) {
                    // Ordena aukeratu
                    String[] aukera = {"Goranzkoa (ASC)", "Beheranzkoa (DESC)"};
                    int ordenaAukera = JOptionPane.showOptionDialog(null,
                        "Nola ordenatu nahi dituzu datuak?",
                        "Ordena aukeratu",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        aukera,
                        aukera[0]);

                    if (ordenaAukera == JOptionPane.CLOSED_OPTION) {
                        JOptionPane.showMessageDialog(null, "Ez duzu ordenarik aukeratu.");
                        return;
                    }

                    String orden = (ordenaAukera == 0) ? "ASC" : "DESC";

                    // Data hasiera aukeratu nahi duen galdetu
                    int dataHasieraErabil = JOptionPane.showConfirmDialog(null,
                        "Data hasierako filtroa sartu nahi duzu?",
                        "Data filtroa",
                        JOptionPane.YES_NO_OPTION);

                    java.sql.Date dataHasiera = null;
                    if (dataHasieraErabil == JOptionPane.YES_OPTION) {
                        String dataHasieraStr = JOptionPane.showInputDialog("Sartu hasierako data (YYYY-MM-DD):");
                        try {
                            if (dataHasieraStr != null && !dataHasieraStr.trim().isEmpty()) {
                                dataHasiera = java.sql.Date.valueOf(dataHasieraStr.trim());
                            }
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(null, "Data formatua okerra da. Erabili YYYY-MM-DD formatua.");
                            return;
                        }
                    }

                    // Data amaiera aukeratu nahi duen galdetu
                    int dataAmaieraErabil = JOptionPane.showConfirmDialog(null,
                        "Data amaierako filtroa sartu nahi duzu?",
                        "Data filtroa",
                        JOptionPane.YES_NO_OPTION);

                    java.sql.Date dataAmaiera = null;
                    if (dataAmaieraErabil == JOptionPane.YES_OPTION) {
                        String dataAmaieraStr = JOptionPane.showInputDialog("Sartu amaierako data (YYYY-MM-DD):");
                        try {
                            if (dataAmaieraStr != null && !dataAmaieraStr.trim().isEmpty()) {
                                dataAmaiera = java.sql.Date.valueOf(dataAmaieraStr.trim());
                            }
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(null, "Data formatua okerra da. Erabili YYYY-MM-DD formatua.");
                            return;
                        }
                    }

                    showCustomerHistoryByName(izena, abizena, orden, dataHasiera, dataAmaiera);

                } else {
                    JOptionPane.showMessageDialog(null, "Abizena ezin da hutsik egon.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Izena ezin da hutsik egon.");
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

<<<<<<< HEAD
        showAllTripsButton.addActionListener(e -> showAllTrips());

        // === OUTPUT AREA (AZPIAN) ===
        outputArea = new JTextArea(10, 40);
=======



        showAllTripsButton.addActionListener(e -> {
            // Galdegin ea bidaien datak data tarte batekin iragazi nahi diren
            int erantzuna = JOptionPane.showConfirmDialog(null,
                    "Bidaien datak data tarte baten arabera iragazi nahi dituzu?",
                    "Data filtroa",
                    JOptionPane.YES_NO_OPTION);

            // Aukeratu nola ordenatu nahi dituen bidaien datuak: goranzkoa ala beheranzkoa
            String[] aukera = {"Goranzkoa (ASC)", "Beheranzkoa (DESC)"};
            int aukeraIndex = JOptionPane.showOptionDialog(null,
                "Nola ordenatu nahi dituzu bidaien datuak?",
                "Ordena aukeratu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                aukera,
                aukera[0]);
            
            // Ordena gordetzen da: ASC edo DESC
            String ordena = (aukeraIndex == 1) ? "DESC" : "ASC";

            // Hasierako eta amaierako datak gordetzeko aldagaia (hasieran null)
            java.sql.Date dataHasiera = null;
            java.sql.Date dataAmaiera = null;

            // Baldin eta baietz erantzuten badu, data tarteak jasoko ditu
            if (erantzuna == JOptionPane.YES_OPTION) {
                String dataHasieraStr = JOptionPane.showInputDialog("Sartu hasierako data (YYYY-MM-DD) edo utzi hutsik:");
                String dataAmaieraStr = JOptionPane.showInputDialog("Sartu amaierako data (YYYY-MM-DD) edo utzi hutsik:");

                try {
                    // Sartutako datuak Date objektu bihurtu, hutsik ez badaude
                    if (dataHasieraStr != null && !dataHasieraStr.trim().isEmpty()) {
                        dataHasiera = java.sql.Date.valueOf(dataHasieraStr.trim());
                    }
                    if (dataAmaieraStr != null && !dataAmaieraStr.trim().isEmpty()) {
                        dataAmaiera = java.sql.Date.valueOf(dataAmaieraStr.trim());
                    }
                } catch (IllegalArgumentException ex) {
                    // Data formatu okerra bada, errore-mezu bat erakutsi eta itzali
                    JOptionPane.showMessageDialog(null, "Data formatua okerra da. Erabili YYYY-MM-DD formatua.");
                    return;
                }
            }

            // Azkenik, bidaien zerrenda erakusteko funtzioa deitu aukeratutako iragazkiekin
            showAllTrips(ordena, dataHasiera, dataAmaiera);
        });





        // === OUTPUT AREA (AZPIAN) ===
        outputArea = new JTextArea(13, 40);
>>>>>>> xiker
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // === FRAME-RA GEHITU ===
        frame.add(buttonPanel, BorderLayout.WEST);     // Botones ezkerrean
        frame.add(formPanel, BorderLayout.CENTER);     // Formulario erdian
        frame.add(scrollPane, BorderLayout.SOUTH);      // Output azpian
<<<<<<< HEAD

        frame.setVisible(true);
    }


    // DB-ra konektatzeko funtzioa
=======

        frame.setVisible(true);
       }
    


    private static void addDriverToDatabase(String name, String surname, String nan, String email, String password2,
			String license, String capacity, String tarifa) {
		// TODO Auto-generated method stub
		
	}


    /**
     * Datu-basearekin konexioa ezartzen du.
     *
     * @return Connection objektua datu-basearekin konektatzea lortu bada,
     *         bestela null balioa itzultzen du errore bat gertatzen bada.
     */
>>>>>>> xiker
    public static Connection connect() {
        try {
            // Datu-basearekin konektatu eta konexioa bueltatu
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Errore bat gertatu bada, errorea inprimatu eta null itzuli
            e.printStackTrace();
            return null;
        }
    }

<<<<<<< HEAD
=======

>>>>>>> xiker
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
<<<<<<< HEAD
                String erabiltzaileaQuery = "INSERT INTO erabiltzailea (posta, pasahitza, rola) VALUES (?, ?, 'taxista')";
=======
                String erabiltzaileaQuery = "INSERT INTO erabiltzailea (posta, pasahitza, rola,) VALUES (?, ?, 'taxista')";
>>>>>>> xiker
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
<<<<<<< HEAD
                String taxistaQuery = "INSERT INTO taxista (matrikula, edukiera, egoera, langilea_idlangilea) VALUES (?, ?, 'aktibo', ?)";
=======
                String taxistaQuery = "INSERT INTO taxista (matrikula, edukiera, egoera, langilea_idlangilea, tarifa) VALUES (?, ?, 'aktibo', ?, ?)";
>>>>>>> xiker
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


<<<<<<< HEAD
    // Gidari guztiak erakusteko funtzioa
=======
    /**
     * Gidari guztiak erakusten dituen funtzioa.
     * Langilea, erabiltzailea eta taxista taulak elkartzen ditu datuak lortzeko:
     * izena, abizena, NAN, emaila, matrikula eta edukiera.
     * Datuak `outputArea` testu-eremuan bistaratzen dira.
     */
>>>>>>> xiker
    public static void showAllDrivers() {
        Connection connection = connect(); // Datu-basearekin konektatu

        if (connection != null) {
            try {
                // SQL kontsulta: gidariaren datu guztiak batu taula ezberdinetatik
                String query = "SELECT l.idlangilea, l.izena, l.abizena, l.nan, e.posta, t.matrikula, t.edukiera " +
                               "FROM langilea l " +
                               "JOIN erabiltzailea e ON l.erabiltzailea_iderabiltzailea = e.iderabiltzailea " +
                               "JOIN taxista t ON l.idlangilea = t.langilea_idlangilea";

                Statement stmt = connection.createStatement(); // Kontsulta prestatu
                ResultSet rs = stmt.executeQuery(query); // Exekutatu

                // Testu-blokea eraiki emaitzekin
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

                // Emaitzak GUI-n bistaratzen dira
                outputArea.setText(drivers.toString());

            } catch (SQLException e) {
                e.printStackTrace();
                outputArea.setText("Errore bat egon da gidariak erakusteko.");
            } finally {
                try {
                    connection.close(); // Konexioa itxi
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

<<<<<<< HEAD
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
=======

    /**
     * Gidari baten datuak editatzeko funtzioa.
     * Langilea eta bere taxista informazioa eguneratzen ditu: izena, abizena, NAN-a, matrikula eta edukiera.
     * Datuak erabiltzaileari galdetzen zaizkio `JOptionPane` bidez.
     *
     * @param langileaId Editatu nahi den gidariaren langile IDa (String moduan jasota, zenbaki bihurtzen da).
     */
    public static void editDriver(String langileaId) {
        Connection connection = connect(); // Datu-basearekin konektatu

>>>>>>> xiker
        if (connection != null) {
            try {
                // Datu berriak erabiltzaileari eskatu
                String newName = JOptionPane.showInputDialog("Sartu izena:");
                String newSurname = JOptionPane.showInputDialog("Sartu abizena:");
                String newNan = JOptionPane.showInputDialog("Sartu NAN:");
                String newEmail = JOptionPane.showInputDialog("Sartu posta elektronikoa:"); // Ez da erabiltzen
                String newLicense = JOptionPane.showInputDialog("Sartu matrikula:");
                String newCapacity = JOptionPane.showInputDialog("Sartu edukiera:");

<<<<<<< HEAD
                // Erabiltzailea lortu
=======
                // Langilea taulako datuak eguneratu
                String updateLangileaQuery = "UPDATE langilea SET izena = ?, abizena = ?, nan = ? WHERE idlangilea = ?";
                PreparedStatement updateLangileaStmt = connection.prepareStatement(updateLangileaQuery);
                updateLangileaStmt.setString(1, newName);
                updateLangileaStmt.setString(2, newSurname);
                updateLangileaStmt.setString(3, newNan);
                updateLangileaStmt.setInt(4, Integer.parseInt(langileaId));
                updateLangileaStmt.executeUpdate();

                // Taxista taulako datuak eguneratu
                String updateTaxistaQuery = "UPDATE taxista SET matrikula = ?, edukiera = ? WHERE langilea_idlangilea = ?";
                PreparedStatement updateTaxistaStmt = connection.prepareStatement(updateTaxistaQuery);
                updateTaxistaStmt.setString(1, newLicense);
                updateTaxistaStmt.setString(2, newCapacity);
                updateTaxistaStmt.setInt(3, Integer.parseInt(langileaId));
                updateTaxistaStmt.executeUpdate();

                // Mezua bistaratzen da eta taula berriz kargatzen da
                JOptionPane.showMessageDialog(null, "Gidaria ondo eguneratu da.");
                showAllDrivers();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore bat egon da gidaria eguneratzean.");
            } finally {
                try {
                    connection.close(); // Konexioa itxi
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Gidari bat ezabatzen du bere langilearen ID-aren arabera.
     * Horretarako, erlazionatutako erregistro guztiak ezabatzen dira:
     * - taxista taulako sarrera
     * - langilea taulako sarrera
     * - erabiltzailea taulako sarrera
     * 
     * Transakzio bat erabiltzen da prozesua osorik edo batere ez egiteko (rollback).
     *
     * @param langileaId Ezabatu nahi den gidariaren langilearen IDa (String moduan jasotzen da, baina zenbaki bihurtzen da).
     */
    public static void deleteDriver(String langileaId) {
        Connection connection = connect(); // Datu-basearekin konexioa sortu

        if (connection != null) {
            try {
                connection.setAutoCommit(false); // Transakzioa hasi

                // Erabiltzailearen IDa eskuratu langilea taulatik
>>>>>>> xiker
                String getErabiltzaileaQuery = "SELECT erabiltzailea_iderabiltzailea FROM langilea WHERE idlangilea = ?";
                PreparedStatement getStmt = connection.prepareStatement(getErabiltzaileaQuery);
                getStmt.setInt(1, Integer.parseInt(langileaId));
                ResultSet rs = getStmt.executeQuery();

                if (rs.next()) {
                    int erabiltzaileaId = rs.getInt("erabiltzailea_iderabiltzailea");

<<<<<<< HEAD
                    // Datuak ezabatu
                    PreparedStatement deleteTaxistaStmt = connection.prepareStatement("DELETE FROM taxista WHERE langilea_idlangilea = ?");
                    deleteTaxistaStmt.setInt(1, Integer.parseInt(langileaId));
                    deleteTaxistaStmt.executeUpdate();

                    PreparedStatement deleteLangileaStmt = connection.prepareStatement("DELETE FROM langilea WHERE idlangilea = ?");
                    deleteLangileaStmt.setInt(1, Integer.parseInt(langileaId));
                    deleteLangileaStmt.executeUpdate();

                    PreparedStatement deleteErabiltzaileaStmt = connection.prepareStatement("DELETE FROM erabiltzailea WHERE iderabiltzailea = ?");
=======
                    // 1. Taxista taulatik ezabatu
                    PreparedStatement deleteTaxistaStmt = connection.prepareStatement(
                        "DELETE FROM taxista WHERE langilea_idlangilea = ?"
                    );
                    deleteTaxistaStmt.setInt(1, Integer.parseInt(langileaId));
                    deleteTaxistaStmt.executeUpdate();

                    // 2. Langilea taulatik ezabatu
                    PreparedStatement deleteLangileaStmt = connection.prepareStatement(
                        "DELETE FROM langilea WHERE idlangilea = ?"
                    );
                    deleteLangileaStmt.setInt(1, Integer.parseInt(langileaId));
                    deleteLangileaStmt.executeUpdate();

                    // 3. Erabiltzailea taulatik ezabatu
                    PreparedStatement deleteErabiltzaileaStmt = connection.prepareStatement(
                        "DELETE FROM erabiltzailea WHERE iderabiltzailea = ?"
                    );
>>>>>>> xiker
                    deleteErabiltzaileaStmt.setInt(1, erabiltzaileaId);
                    deleteErabiltzaileaStmt.executeUpdate();

                    connection.commit(); // Guztia ondo, transakzioa konfirmatu
                    JOptionPane.showMessageDialog(null, "Gidaria ondo ezabatu da.");
                    showAllDrivers(); // Gidariak berriz bistaratu

                } else {
                    // Langilea ez da aurkitu
                    JOptionPane.showMessageDialog(null, "Langilea ID hori ez da aurkitu.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.rollback(); // Errore bat egon da, transakzioa desegin
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Errore bat egon da gidaria ezabatzean.");
            } finally {
                try {
                    connection.setAutoCommit(true); // AutoCommit berriz aktibatu
                    connection.close(); // Konexioa itxi
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    
    /**
     * Gidari baten historiala bistaratzen du izenaren eta abizenaren arabera.
     * Aukeran, egun jakin baterako eta ordenazioarekin filtratu daiteke.
     *
     * @param izena   Gidariaren izena.
     * @param abizena Gidariaren abizena.
     * @param data    Egun jakin bat (formatua: "yyyy-MM-dd"). Null edo hutsik bada, ez da filtrorik egiten.
     * @param ordena  Emaitzen ordena: "ASC" edo "DESC" (null bada edo baliogabea, ez da ordenarik aplikatzen).
     */
    public static void showDriverHistoryByName(String izena, String abizena, String data, String ordena) {
        Connection connection = connect(); // Datu-basearekin konexioa sortu

        if (connection != null) {
            try {
                // SQL kontsulta eraikitzea: gidariaren bidaiak bilatzeko
                StringBuilder query = new StringBuilder(
                    "SELECT h.idhistoriala, h.hasiera_kokapena, h.helmuga_kokapena, h.data, h.hasiera_ordua, h.bukaera_ordua, h.xehetasunak " +
                    "FROM historiala h " +
                    "JOIN langilea l ON h.idgidaria = l.idlangilea " +
                    "WHERE l.izena = ? AND l.abizena = ?"
                );

                // Data balioztatzea eta kontsultan sartzea
                if (data != null && !data.isEmpty()) {
                    query.append(" AND h.data = ?");
                }

                // Ordena balioztatzea eta kontsultari gehitzea
                if (ordena != null && (ordena.equalsIgnoreCase("ASC") || ordena.equalsIgnoreCase("DESC"))) {
                    query.append(" ORDER BY h.idhistoriala ").append(ordena);
                }

                // Kontsulta prestatzea
                PreparedStatement stmt = connection.prepareStatement(query.toString());
                stmt.setString(1, izena);
                stmt.setString(2, abizena);

                // Data baliozkoa bada, hirugarren parametro gisa gehitu
                if (data != null && !data.isEmpty()) {
                    stmt.setDate(3, java.sql.Date.valueOf(data));
                }

                // Kontsulta exekutatu eta emaitzak jaso
                ResultSet rs = stmt.executeQuery();

                // Emaitzak testu modura eraiki
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

                // Emaitza bistaratzea
                outputArea.setText(history.toString());

            } catch (SQLException e) {
                e.printStackTrace();
                outputArea.setText("Errore bat egon da gidariaren historiala erakusteko.");
            } finally {
                // Konexioa itxi
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
<<<<<<< HEAD
    
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
=======





    /**
     * Emandako izen eta abizenaren araberako bezero baten bidai historiaren informazioa bistaratzen du.
     * Aukeran, dataren araberako filtroak eta ordenazioa aplika daitezke.
     *
     * @param izena         Bezeroaren izena.
     * @param abizena       Bezeroaren abizena.
     * @param orden         Emaitzen ordena: "ASC" (goranzkoa) edo "DESC" (beheranzkoa).
     * @param dataHasiera   Aukerazko hasierako data (null bada, ez da mugarik ezartzen).
     * @param dataAmaiera   Aukerazko amaierako data (null bada, ez da mugarik ezartzen).
     */
    public static void showCustomerHistoryByName(String izena, String abizena, String orden, Date dataHasiera, Date dataAmaiera) {
        Connection connection = connect(); // Datu-basearekin konexioa

        if (connection != null) {
            try {
                // Orden balioa egiaztatu, ASC edo DESC ez bada, ASC jartzen da lehenetsita
                if (!orden.equalsIgnoreCase("ASC") && !orden.equalsIgnoreCase("DESC")) {
                    orden = "ASC";
                }

                // Lehenik, bezeroaren IDa lortu izen eta abizenaren arabera
>>>>>>> xiker
                String queryId = "SELECT idbezeroa FROM bezeroa WHERE izena = ? AND abizena = ?";
                PreparedStatement stmtId = connection.prepareStatement(queryId);
                stmtId.setString(1, izena);
                stmtId.setString(2, abizena);
                ResultSet rsId = stmtId.executeQuery();

                if (rsId.next()) {
                    int bezeroaId = rsId.getInt("idbezeroa");

<<<<<<< HEAD
                    String queryHistory = "SELECT h.idhistoriala, h.hasiera_kokapena, h.helmuga_kokapena, h.data, h.hasiera_ordua, h.bukaera_ordua, h.xehetasunak " +
                                          "FROM historiala h WHERE h.idbezeroa = ?";

                    PreparedStatement stmtHistory = connection.prepareStatement(queryHistory);
                    stmtHistory.setInt(1, bezeroaId);
                    ResultSet rsHistory = stmtHistory.executeQuery();

                    StringBuilder history = new StringBuilder("Bezeroaren historiala:\n");
=======
                    // Bezeroaren historiaren kontsulta eraiki
                    String queryHistory = "SELECT h.idhistoriala, h.hasiera_kokapena, h.helmuga_kokapena, h.data, h.hasiera_ordua, h.bukaera_ordua, h.xehetasunak " +
                                          "FROM historiala h WHERE h.idbezeroa = ?";

                    // Data bidezko filtroak gehitu, badaude
                    if (dataHasiera != null) {
                        queryHistory += " AND h.data >= ?";
                    }
                    if (dataAmaiera != null) {
                        queryHistory += " AND h.data <= ?";
                    }

                    // Ordena aplikatu
                    queryHistory += " ORDER BY h.idhistoriala " + orden;

                    // Kontsulta prestatu eta parametroak ezarri
                    PreparedStatement stmtHistory = connection.prepareStatement(queryHistory);
                    stmtHistory.setInt(1, bezeroaId);

                    int paramIndex = 2;
                    if (dataHasiera != null) {
                        stmtHistory.setDate(paramIndex++, dataHasiera);
                    }
                    if (dataAmaiera != null) {
                        stmtHistory.setDate(paramIndex++, dataAmaiera);
                    }

                    // Emaitzak lortu
                    ResultSet rsHistory = stmtHistory.executeQuery();

                    // Emaitzak testu formatuan eraiki
                    StringBuilder history = new StringBuilder("Bezeroaren historiala (" + orden + "):\n");
>>>>>>> xiker
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

<<<<<<< HEAD
                    outputArea.setText(history.toString());  // Aquí cambiamos System.out.println por outputArea.setText

                } else {
                    outputArea.setText("Bezeroa ez da aurkitu izen eta abizena erabiliz."); // Mensaje en JTextArea
=======
                    // Testu-area eguneratu
                    outputArea.setText(history.toString());

                } else {
                    // Bezeroa ez da aurkitu
                    outputArea.setText("Bezeroa ez da aurkitu izen eta abizena erabiliz.");
>>>>>>> xiker
                }

            } catch (SQLException e) {
                e.printStackTrace();
<<<<<<< HEAD
                outputArea.setText("Errore bat egon da bezeroaren historiala erakusteko.");  // Mensaje en JTextArea
            } finally {
=======
                outputArea.setText("Errore bat egon da bezeroaren historiala erakusteko.");
            } finally {
                // Konexioa itxi
>>>>>>> xiker
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



<<<<<<< HEAD
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
=======


    /**
     * Bidai guztiak erakusten ditu datu-basean, IDaren arabera ordenatuta (ASC edo DESC),
     * eta aukeran data bitarte baten arabera filtratuta.
     *
     * @param orden        Emaitzen ordena: "ASC" (goranzkoa) edo "DESC" (beheranzkoa).
     * @param dataHasiera  Hasierako data (null bada, ez da hasierako mugarik jartzen).
     * @param dataAmaiera  Amaierako data (null bada, ez da amaierako mugarik jartzen).
     */
    public static void showAllTrips(String orden, Date dataHasiera, Date dataAmaiera) {
        Connection connection = connect(); // Datu-basearekin konexioa

        if (connection != null) {
            try {
                // 'orden' parametroa balioztatzea. ASC edo DESC ez bada, ASC jartzen da lehenetsita
                if (!orden.equalsIgnoreCase("ASC") && !orden.equalsIgnoreCase("DESC")) {
                    orden = "ASC";
                }

                // SQL kontsulta eraikitzea, aukerako iragazkiekin
                String query = "SELECT * FROM historiala WHERE 1=1";

                if (dataHasiera != null) {
                    query += " AND data >= ?";
                }
                if (dataAmaiera != null) {
                    query += " AND data <= ?";
                }

                // Ordena gehitzen da kontsultari
                query += " ORDER BY idhistoriala " + orden;

                PreparedStatement stmt = connection.prepareStatement(query);

                // Parametroen posizioak kontrolatzeko indizea
                int paramIndex = 1;
                if (dataHasiera != null) {
                    stmt.setDate(paramIndex++, dataHasiera);
                }
                if (dataAmaiera != null) {
                    stmt.setDate(paramIndex++, dataAmaiera);
                }

                // Kontsulta exekutatu eta emaitzak lortu
                ResultSet rs = stmt.executeQuery();

                // Emaitzak testu formatuan eraikitzea
                StringBuilder trips = new StringBuilder("Historiala (" + orden + "):\n");
                while (rs.next()) {
                    trips.append("ID: ").append(rs.getInt("idhistoriala"))
                         .append(", Data: ").append(rs.getDate("data"))
>>>>>>> xiker
                         .append(", Hasiera: ").append(rs.getString("hasiera_kokapena"))
                         .append(", Helmuga: ").append(rs.getString("helmuga_kokapena"))
                         .append(", Ordua: ").append(rs.getTime("hasiera_ordua"))
                         .append(" - ").append(rs.getTime("bukaera_ordua"))
<<<<<<< HEAD
                         .append("\n");
                }

                outputArea.setText(trips.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                outputArea.setText("Errore bat egon da bidaien historiala erakusteko.");
            } finally {
=======
                         .append(", Bezeroa ID: ").append(rs.getInt("idbezeroa"))
                         .append(", Taxista ID: ").append(rs.getInt("idgidaria"))
                         .append(", Bidaia ID: ").append(rs.getInt("bidaiak_idbidaiak"))
                         .append(", Prezioa: ").append(rs.getDouble("prezioa"))
                         .append(" €\n");
                }

                // Testu-area eguneratu
                outputArea.setText(trips.toString());

            } catch (SQLException e) {
                e.printStackTrace();
                outputArea.setText("Errore bat egon da historiala erakusteko.");
            } finally {
                // Konexioa itxi
>>>>>>> xiker
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

<<<<<<< HEAD
=======





>>>>>>> xiker
}
