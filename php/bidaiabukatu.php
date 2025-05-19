<?php
include 'navbar.php';
require 'konexioa.php';

if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["idbidaia"])) {
    echo $_SESSION['idlangilea'];
    $idbidaia = intval($_POST["idbidaia"]);
    // Consulta preparada para actualizar el estado a adjudicado (1)
    $stmt = $conn->prepare("UPDATE bidaiak AS b INNER JOIN taxista t ON b.taxista_langilea_idlangilea = t.langilea_idlangilea SET b.egoera = 'Amaituta', b.bukaera_ordua = CURTIME(), prezioa = t.tarifa * (TIME_TO_SEC(TIMEDIFF(CURTIME(), b.hasiera_ordua)) / 60) WHERE idbidaiak = ?");
    $stmt->bind_param("i", $idbidaia);
    $stmt->execute();
    if ($stmt->affected_rows > 0) {
        $message = "Bidaia amaitutzat eman da.";
    } else {
        $message = "Errorea: Ezin izan da bidaia amaitu.";
    }
    $stmt->close();
}

// Consulta para obtener todos los viajes
$idtaxista = $_SESSION['idlangilea'];
$sql    = "SELECT data, hasiera_kokapena, helmuga_kokapena, hasiera_ordua, egoera, idbidaiak FROM bidaiak WHERE taxista_langilea_idlangilea='$idtaxista' AND egoera = 'Aktibo'";
$result = $conn->query($sql);
?>
<!DOCTYPE html>
<html>

<head>
     <meta charset="UTF-8">
  <title>Tu Página</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Font Awesome CSS -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>

    <div class="container mt-5">
        <!-- Mensaje de feedback -->
        <?php if (isset($message)): ?>
            <div class="alert alert-info" role="alert">
                <?php echo $message; ?>
            </div>
        <?php endif; ?>

        <h1 class="mb-4">Bidaia aktiboak</h1>

        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Data</th>
                        <th>Hasiera kokapena</th>
                        <th>Helmuga</th>
                        <th>Jasotze ordua</th>
                        <th>Egoera</th>
                    </tr>
                </thead>
                <tbody>
                    <?php if ($result->num_rows > 0): ?>
                        <?php while ($row = $result->fetch_assoc()): ?>
                            <tr>
                                <td><?php echo htmlspecialchars($row['data']); ?></td>
                                <td><?php echo htmlspecialchars($row['hasiera_kokapena']); ?></td>
                                <td><?php echo htmlspecialchars($row['helmuga_kokapena']); ?></td>
                                <td><?php echo htmlspecialchars($row['hasiera_ordua']); ?></td>
                                <td>
                                    <form method="post" action="">
                                        <input type="hidden" name="idbidaia" value="<?php echo $row['idbidaiak']; ?>">
                                        <input type="submit" value="Amaitu" class="btn btn-primary btn-sm">
                                    </form>
                                </td>
                            </tr>
                        <?php endwhile; ?>
                    <?php else: ?>
                        <tr>
                            <td colspan="5" class="text-center">Ez dira bidaiak aurkitu</td>
                        </tr>
                    <?php endif; ?>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Bootstrap JS Bundle con Popper (opcional, para componentes interactivos) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
<footer>
    <?php include 'footer.php'; ?>
</footer>

</html>