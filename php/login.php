<?php
session_start();
require 'konexioa.php'; // Datu basearekiko konexioa kargatzen dugu
$error = ""; // Errore mezuek gordetzeko aldagai

// POST eskaera jasotzen bada, saioa hasteko datuak prozesatzen ditugu
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $posta = $_POST['posta'];       // Erabiltzaileak sartu duen posta
    $pasahitza = $_POST['pasahitza']; // Erabiltzaileak sartu duen pasahitza

    // SQL kontsulta prestatzen dugu, posta erabilita erabiltzailea bilatzen duena
    $query = "SELECT iderabiltzailea, rola, pasahitza FROM erabiltzailea WHERE posta = ?";
    $stmt = $conn->prepare($query);
    $stmt->bind_param("s", $posta);
    $stmt->execute();
    $result = $stmt->get_result();

    // Erabiltzaile bat aurkitzen bada
    if ($result->num_rows == 1) {
        $erabiltzailea = $result->fetch_assoc();
        // Sartutako pasahitza datu-baseko pasahitza berbera baldin bada
        if ($pasahitza == $erabiltzailea['pasahitza']) {
            // Sessioan erabiltzailearen informazioa gordetzen dugu
            $_SESSION['erabiltzailea'] = $erabiltzailea['iderabiltzailea'];
            $_SESSION['rola'] = $erabiltzailea['rola'];

            // Rolaren arabera bideratzen dugu
            if ($erabiltzailea['rola'] == 'bezeroa') {
                // Bezero gisa sartzen bada, bezeroaren id ateratzen dugu
                $query_bezeroa = "SELECT idbezeroa FROM bezeroa WHERE erabiltzailea_iderabiltzailea = ?";
                $stmt_bezeroa = $conn->prepare($query_bezeroa);
                $stmt_bezeroa->bind_param("i", $_SESSION['erabiltzailea']);
                $stmt_bezeroa->execute();
                $result_bezeroa = $stmt_bezeroa->get_result();

                if ($result_bezeroa->num_rows == 1) {
                    $_SESSION['idbezeroa'] = $result_bezeroa->fetch_assoc()['idbezeroa'];
                }
                // Main.php-ra bideratzen dugu
                header("Location: main.php");
            } else {
                // Bestela, langile gisa sartzen bada, langilearen id ateratzen dugu
                $query_langilea = "SELECT idlangilea FROM langilea WHERE erabiltzailea_iderabiltzailea = ?";
                $stmt_langilea = $conn->prepare($query_langilea);
                $stmt_langilea->bind_param("i", $_SESSION['erabiltzailea']);
                $stmt_langilea->execute();
                $result_langilea = $stmt_langilea->get_result();

                if ($result_langilea->num_rows == 1) {
                    $_SESSION['idlangilea'] = $result_langilea->fetch_assoc()['idlangilea'];
                    header("Location: main.php");
                }
            }
            exit(); // Exekuzioa gelditzen dugu
        } else {
            $error = "Pasahitza okerra"; // Pasahitza ez dator bat datu-basekoarekin
        }
    } else {
        $error = "Erabiltzaile ez aurkitua"; // Postarekin bat datorren erabiltzaileik ez bada aurkitu
    }
}
?>

<!DOCTYPE html>
<html lang="eu">
<head>
    <meta charset="UTF-8">
    <title>Saioa Hasi</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap CSS kargatzen dugu -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome CSS kargatzen dugu -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-light">
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="card p-4 shadow-lg" style="width: 400px;">
            <h2 class="text-center">Saioa Hasi</h2>
            <!-- Errore mezua erakusten da, bada -->
            <?php if (!empty($error)): ?>
                <div class="alert alert-danger" role="alert">
                    <?php echo $error; ?>
                </div>
            <?php endif; ?>
            <form action="login.php" method="POST">
                <div class="mb-3">
                    <label for="posta" class="form-label">Posta elektronikoa</label>
                    <input type="email" id="posta" name="posta" class="form-control" placeholder="Zure posta" required>
                </div>
                <div class="mb-3">
                    <label for="pasahitza" class="form-label">Pasahitza</label>
                    <input type="password" id="pasahitza" name="pasahitza" class="form-control" placeholder="Zure pasahitza" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Saioa Hasi</button>
            </form>
            <!-- Erregistro orrira bideratzeko testua -->
            <p class="mt-3 text-center">
                Ez al duzu konturik? <a href="register.php">Erregistratu hemen</a>.
            </p>
            <!-- Beste hizkuntza beharrean, testua aldatu daiteke -->
            <!-- <p class="mt-3 text-center">¿No tienes cuenta? <a href="register.php">Regístrate aquí</a>.</p> -->
        </div>
    </div>
    <!-- Bootstrap JS kargatzen dugu (erabakitako funtzio interaktiboentzako) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```