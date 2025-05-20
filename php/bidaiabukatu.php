<?php
session_start();
require 'konexioa.php';

// Taxistaren saioa egiaztatzen dugu: "idlangilea" aldagaiaren existintza eta balioa begiratzen dugu.
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
    <!-- Erabiltzaileak "Langile bezala" saioa hasi behar du -->
    <body class="bg-light d-flex align-items-center justify-content-center vh-100">
       <div class="container">
          <div class="alert alert-warning text-center" role="alert">
             Langile bezala hasi saioa mesedez.
          </div>
       </div>
       <script>
          // 3 segundotan login.php-ra eramango dugu
          setTimeout(function(){
             window.location.href = "login.php";
          }, 3000);
       </script>
    </body>
    </html>
    <?php
    exit(); // Aurrera ez jarraitu, exekuzioa gelditzen dugu
}
?>

<?php
// Nabigazio barraren edukia sartzen dugu
include 'navbar.php';

// POST eskaera jasotzen bada eta idbidaia balioa badago, bidaia amaitzeko eskaera kudeatzen dugu
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["idbidaia"])) {
    // Taxistaren id bilatzen dugu saioan gordeta dagoena
    // echo $_SESSION['idlangilea']; // Depurazio helburuetarako (komentatu edo ez erabili nahi bada)
    $idbidaia = intval($_POST["idbidaia"]);
    
    // Prestatu eta exekutatu eskaera: bidaiaren egoera 'Amaituta' izendatzen dugu, bukaera ordua orain bezala ezartzen da,
    // prezioa taxistaren tarifa eta bidaia denboraren arabera kalkulatzen da.
    $stmt = $conn->prepare("UPDATE bidaiak AS b INNER JOIN taxista t ON b.taxista_langilea_idlangilea = t.langilea_idlangilea SET b.egoera = 'Amaituta', b.bukaera_ordua = CURTIME(), prezioa = t.tarifa * (TIME_TO_SEC(TIMEDIFF(CURTIME(), b.hasiera_ordua)) / 60) WHERE idbidaiak = ?");
    $stmt->bind_param("i", $idbidaia);
    $stmt->execute();
    
    // Emaitzaren arabera mezu egokia erakusten dugu
    if ($stmt->affected_rows > 0) {
        $message = "Bidaia amaitutzat eman da.";
    } else {
        $message = "Errorea: Ezin izan da bidaia amaitu.";
    }
    $stmt->close();
}

// Taxistaren id hartu saioan gordeta dagoena
$idtaxista = $_SESSION['idlangilea'];
// Aktibo egoeran dauden bidaiak ateratzeko SQL kontsulta
$sql = "SELECT data, hasiera_kokapena, helmuga_kokapena, hasiera_ordua, egoera, idbidaiak 
        FROM bidaiak 
        WHERE taxista_langilea_idlangilea='$idtaxista' AND egoera = 'Aktibo'";
$result = $conn->query($sql);
?>
<!DOCTYPE html>
<html>
<head>
     <meta charset="UTF-8">
  <title>Tu Página</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Bootstrap CSS kargatzen dugu -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Font Awesome CSS kargatzen dugu -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <div class="container mt-5">
        <!-- Feedback mesaja jaso bada, mezu hori erakusten dugu -->
        <?php if (isset($message)): ?>
            <div class="alert alert-info" role="alert">
                <?php echo $message; ?>
            </div>
        <?php endif; ?>

        <!-- Orriaren izenburua -->
        <h1 class="mb-4">Bidaia bukatu</h1>

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
                                <!-- Bidaiaren datuak taulan erakusten ditugu -->
                                <td><?php echo htmlspecialchars($row['data']); ?></td>
                                <td><?php echo htmlspecialchars($row['hasiera_kokapena']); ?></td>
                                <td><?php echo htmlspecialchars($row['helmuga_kokapena']); ?></td>
                                <td><?php echo htmlspecialchars($row['hasiera_ordua']); ?></td>
                                <td>
                                    <!-- Bidaiaren egoera ez badago "Esleitu gabe", adjudikatzeko formularioa erakusten dugu -->
                                    <form method="post" action="">
                                        <input type="hidden" name="idbidaia" value="<?php echo $row['idbidaiak']; ?>">
                                        <input type="submit" value="Amaitu" class="btn btn-primary btn-sm">
                                    </form>
                                </td>
                            </tr>
                        <?php endwhile; ?>
                    <?php else: ?>
                        <!-- Bidai aktiborik ez badira, alerta mezu bat erakusten dugu -->
                        <tr>
                            <td colspan="5" class="text-center">Ez dira bidaiak aurkitu</td>
                        </tr>
                    <?php endif; ?>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Bootstrap JS Bundle (Popper barne): Komponente interaktiboak kargatzeko -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
<footer>
    <?php include 'footer.php'; ?>
</footer>
</html>
