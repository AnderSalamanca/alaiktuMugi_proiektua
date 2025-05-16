<?php
require 'konexioa.php';

if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["idbidaia"])) {
    $idbidaia = intval($_POST["idbidaia"]);
    $idtaxista = $_SESSION['idlangilea'];
    // Consulta preparada para actualizar el estado a adjudicado (1)
    $stmt = $conn->prepare("UPDATE bidaiak SET egoera = 'Aktibo', taxista_langilea_idlangilea = ? WHERE idbidaiak = ?");
    $stmt->bind_param("ii", $idtaxista, $idbidaia);
    $stmt->execute();
    if ($stmt->affected_rows > 0) {
        $message = "El viaje se adjudicó correctamente.";
    } else {
        $message = "Error: no se pudo adjudicar el viaje o ya estaba adjudicado.";
    }
    $stmt->close();
}

// Consulta para obtener todos los viajes
$sql    = "SELECT data, hasiera_kokapena, helmuga_kokapena, hasiera_ordua, egoera, idbidaiak FROM bidaiak";
$result = $conn->query($sql);
?>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Bidaia eskatu</title>
    <!-- Bootstrap CSS (versión 5.3) -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>

<body>

    <?php include 'navbar.php'; ?>

    <div class="container mt-5">
        <!-- Mensaje de feedback -->
        <?php if (isset($message)): ?>
            <div class="alert alert-info" role="alert">
                <?php echo $message; ?>
            </div>
        <?php endif; ?>

        <h1 class="mb-4">Bidaiak</h1>

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
                                    <?php if ($row['egoera'] == 'Esleitu gabe'): ?>
                                        <!-- Formulario para adjudicar el viaje -->
                                        <form method="post" action="">
                                            <input type="hidden" name="idbidaiak" value="<?php echo $row['idbidaiak']; ?>">
                                            <input type="hidden" name="idbidaia" value="<?php echo $row['idbidaiak']; ?>">
                                            <input type="submit" value="Hartu" class="btn btn-primary btn-sm">
                                        </form>
                                    <?php else: ?>
                                        <span class="badge bg-success">Aktibo</span>
                                    <?php endif; ?>
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