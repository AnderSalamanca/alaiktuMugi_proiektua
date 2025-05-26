<?php
session_start();
require 'konexioa.php';

// Taxistaren saioa egiaztatzen dugu: "idlangilea" kontsulta egiten dugu
if (!isset($_SESSION['idlangilea']) || empty($_SESSION['idlangilea'])) {
?>
    <!DOCTYPE html>
    <html lang="es">

    <head>
        <meta charset="UTF-8">
        <title>Acceso Denegado</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Bootstrap CSS kargatzen dugu -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <!-- Saioaren ez egiaztatzean alerta bat erakusten dugu eta 3 segundotan login.php-ra eramango dugu -->

    <body class="bg-light d-flex align-items-center justify-content-center vh-100">
        <div class="container">
            <div class="alert alert-warning text-center" role="alert">
                Langile bezala hasi saioa mesedez.
            </div>
        </div>
        <script>
            setTimeout(function() {
                window.location.href = "login.php";
            }, 3000);
        </script>
    </body>

    </html>
<?php
    exit();
}
?>

<?php
// Nabigazio barra sartzen dugu
include 'navbar.php';

if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["idbidaia"])) {
    // POST bidez gizatzen den bidaia id-a ateratzen dugu
    $idbidaia = intval($_POST["idbidaia"]);
    // Saioan gordeta dagoen taxistaren ida hartu behar dugu
    $idtaxista = $_SESSION['idlangilea'];
    // Prestatu SQL eskaera: bidaiaren egoera "Aktibo" bihurtu eta taxista erregistratuta ezarri (adjudikatu)
    $stmt = $conn->prepare("UPDATE bidaiak SET egoera = 'Aktibo', taxista_langilea_idlangilea = ? WHERE idbidaiak = ?");
    $stmt->bind_param("ii", $idtaxista, $idbidaia);
    $stmt->execute();
    // Emaitzaren arabera mezua ezartzen dugu
    if ($stmt->affected_rows > 0) {
        $message = "El viaje se adjudicó correctamente.";
    } else {
        $message = "Error: no se pudo adjudicar el viaje o ya estaba adjudicado.";
    }
    $stmt->close();
}

// Bidai guztiak ateratzen dituen SQL kontsulta, taxista zehatz batek esleituta baita
$sql = "SELECT data, hasiera_kokapena, helmuga_kokapena, hasiera_ordua, egoera, idbidaiak FROM bidaiak";
$result = $conn->query($sql);
?>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Bidaia hartu</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap CSS kargatzen dugu -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome CSS kargatzen dugu -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>
    <div class="container mt-5">
        <!-- Feedback mesaja, mezu bat badago erakusten dugu -->
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
                                <!-- Bidaiaren datuak eskuz erakusten ditugu -->
                                <td><?php echo htmlspecialchars($row['data']); ?></td>
                                <td><?php echo htmlspecialchars($row['hasiera_kokapena']); ?></td>
                                <td><?php echo htmlspecialchars($row['helmuga_kokapena']); ?></td>
                                <td><?php echo htmlspecialchars($row['hasiera_ordua']); ?></td>
                                <td>
                                    <?php if ($row['egoera'] == 'Esleitu gabe'): ?>
                                        <!-- Bidaiaren adjudikazioa egiteko formularioa -->
                                        <form method="post" action="">
                                            <input type="hidden" name="idbidaiak" value="<?php echo $row['idbidaiak']; ?>">
                                            <input type="hidden" name="idbidaia" value="<?php echo $row['idbidaiak']; ?>">
                                            <input type="submit" value="Hartu" class="btn btn-primary btn-sm">
                                        </form>
                                    <?php else: ?>
                                        <!-- Egoera aldatu bada "Aktibo" etiketa erakusten dugu -->
                                        <span class="badge bg-success">Aktibo</span>
                                    <?php endif; ?>
                                </td>
                            </tr>
                        <?php endwhile; ?>
                    <?php else: ?>
                        <!-- Bidaiak ez badira, alerta bat erakusten dugu -->
                        <tr>
                            <td colspan="5" class="text-center">Ez dira bidaiak aurkitu</td>
                        </tr>
                    <?php endif; ?>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Bootstrap JS Bundle (Popper barne): Interakzio funtzioak kargatzeko -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
<footer>
    <?php include 'footer.php'; ?>
</footer>

</html>