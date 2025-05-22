<?php
require 'konexioa.php'; // Datu basearekiko konexioa kargatzen dugu

$message = ""; // Erabiltzaileari erakusteko mezuak gordetzeko aldagai

// POST eskaera jasotzen bada, erabiltzailea erregistratzeko datuak prozesatzen ditugu
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $posta = $_POST['email']; // Erabiltzaileak sartu duen posta elektronikoa
    $pasahitza = ($_POST['contraseña']); // Pasahitza zifratu egiten dugu segurtasun handiagoa lortzeko
    $izena = $_POST['nombre']; // Erabiltzaileak sartu duen izena
    $abizena = $_POST['apellido']; // Erabiltzaileak sartu duen abizena

    // Posta elektronikoa datu basean dagoen ala ez egiaztatzen dugu
    $sql_check = "SELECT iderabiltzailea FROM erabiltzailea WHERE posta = ?";
    $stmt = $conn->prepare($sql_check);
    $stmt->bind_param("s", $posta);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {
        // Erabiltzaileak sartu duen posta elektronikoa dagoeneko erregistratuta dago
        $message = '<div class="alert alert-danger" role="alert">Errorea: Email hau jada erregistratuta dago.</div>';
    } else {
        // `erabiltzailea` taulan datuak txertatzen ditugu
        $sql_usuario = "INSERT INTO erabiltzailea (posta, pasahitza, rola) VALUES (?, ?, 'bezeroa')";
        $stmt = $conn->prepare($sql_usuario);
        $stmt->bind_param("ss", $posta, $pasahitza);

        if ($stmt->execute()) {
            $iderabiltzailea = $stmt->insert_id; // Erabiltzailearen ID-a gordetzen dugu

            // `bezeroa` taulan datuak txertatzen ditugu
            $sql_cliente = "INSERT INTO bezeroa (izena, abizena, erabiltzailea_iderabiltzailea) VALUES (?, ?, ?)";
            $stmt = $conn->prepare($sql_cliente);
            $stmt->bind_param("ssi", $izena, $abizena, $iderabiltzailea);

            if ($stmt->execute()) {
                // Erregistro arrakastatsua izan da, erabiltzailea login orrira birbideratzen dugu
                $message = '<div class="alert alert-success" role="alert">Erregistro arrakastatsua. Login orrira birbideratzen...</div>';
                echo '<script>
                        setTimeout(function() {
                            window.location.href = "login.php";
                        }, 2000);
                      </script>';
            } else {
                // Errorea `bezeroa` taulan datuak sartzerakoan
                $message = '<div class="alert alert-danger" role="alert">Errorea bezeroa taulan: ' . $conn->error . '</div>';
            }
        } else {
            // Errorea `erabiltzailea` taulan datuak sartzerakoan
            $message = '<div class="alert alert-danger" role="alert">Errorea erabiltzailea taulan: ' . $conn->error . '</div>';
        }
    }
}
?>

<!DOCTYPE html>
<html lang="eu">

<head>
    <meta charset="UTF-8">
    <title>Erregistroa</title>
    <!-- Bootstrap CSS kargatzen dugu -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="d-flex justify-content-center align-items-center vh-100">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card p-4">
                    <h2 class="text-center">Erregistroa</h2>

                    <!-- Erabiltzaileari erakutsi behar den mezua -->
                    <?php echo $message; ?>

                    <form action="" method="POST">
                        <div class="mb-3">
                            <label for="email" class="form-label">Posta elektronikoa:</label>
                            <input type="email" class="form-control" name="email" required>
                        </div>

                        <div class="mb-3">
                            <label for="contraseña" class="form-label">Pasahitza:</label>
                            <input type="password" class="form-control" name="contraseña" required>
                        </div>

                        <div class="mb-3">
                            <label for="nombre" class="form-label">Izena:</label>
                            <input type="text" class="form-control" name="nombre" required>
                        </div>

                        <div class="mb-3">
                            <label for="apellido" class="form-label">Abizena:</label>
                            <input type="text" class="form-control" name="apellido" required>
                        </div>

                        <button type="submit" class="btn btn-primary w-100">Erregistratu</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS kargatzen dugu -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>