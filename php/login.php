<?php
session_start();
require 'konexioa.php'; // Archivo con la conexión a la base de datos
$error = ""; // Variable para almacenar mensajes de error

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $posta = $_POST['posta'];
    $pasahitza = $_POST['pasahitza'];

    $query = "SELECT iderabiltzailea, rola, pasahitza FROM erabiltzailea WHERE posta = ?";
    $stmt = $conn->prepare($query);
    $stmt->bind_param("s", $posta);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows == 1) {
        $erabiltzailea = $result->fetch_assoc();
        if ($pasahitza == $erabiltzailea['pasahitza']) {
            $_SESSION['erabiltzailea'] = $erabiltzailea['iderabiltzailea'];
            $_SESSION['rola'] = $erabiltzailea['rola'];

            // Redirigir según el rol
            if ($erabiltzailea['rola'] == 'bezeroa') {
                $query_bezeroa = "SELECT idbezeroa FROM bezeroa WHERE erabiltzailea_iderabiltzailea = ?";
                $stmt_bezeroa = $conn->prepare($query_bezeroa);
                $stmt_bezeroa->bind_param("i", $_SESSION['erabiltzailea']);
                $stmt_bezeroa->execute();
                $result_bezeroa = $stmt_bezeroa->get_result();

                if ($result_bezeroa->num_rows == 1) {
                    $_SESSION['idbezeroa'] = $result_bezeroa->fetch_assoc()['idbezeroa'];
                }
                header("Location: bezeroa.php");
            } else {
                $query_langilea = "SELECT idlangilea FROM langilea WHERE erabiltzailea_iderabiltzailea = ?";
                $stmt_langilea = $conn->prepare($query_langilea);
                $stmt_langilea->bind_param("i", $_SESSION['erabiltzailea']);
                $stmt_langilea->execute();
                $result_langilea = $stmt_langilea->get_result();

                if ($result_langilea->num_rows == 1) {
                    $_SESSION['idlangilea'] = $result_langilea->fetch_assoc()['idlangilea'];
                    header("Location: taxista.php");
                }
                
            }
            exit();
        } else {
            $error = "Pasahitza okerra";
        }
    } else {
        $error = "Erabiltzaile ez aurkitua";
    }
}
?>

<!DOCTYPE html>
<html lang="eu">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Saioa Hasi</title>
    <!-- Agregar Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="card p-4 shadow-lg" style="width: 400px;">
            <h2 class="text-center">Saioa Hasi</h2>
            <!-- Mostrar el mensaje de error si existe -->
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
        </div>
    </div>
    <!-- Agregar Bootstrap JS (opcional) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
