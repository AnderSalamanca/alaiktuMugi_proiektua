<?php
session_start();
require 'konexioa.php';

// Erabiltzailearen saioa egiaztatzen dugu: 
// "erabiltzailea" dela begiratzen dugu (ondoren, "idbezeroa" erabiltzen da id-a lortzeko)
if (!isset($_SESSION['erabiltzailea'])) {
?>
  <!DOCTYPE html>
  <html lang="eu">

  <head>
    <meta charset="UTF-8">
    <title>Acceso Denegado</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap CSS kargatzen dugu -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  </head>

  <body class="bg-light d-flex align-items-center justify-content-center vh-100">
    <div class="container">
      <!-- Alert mezu bat erakusten dugu, erabiltzaileak bezero bezala saioa hasi behar duelako -->
      <div class="alert alert-warning text-center" role="alert">
        Bezero bezala hasi saioa mesedez.
      </div>
    </div>
    <script>
      // 3000 milisegundotan (3 segundu) login.php-ra eramango dugu
      setTimeout(function() {
        window.location.href = "login.php";
      }, 3000);
    </script>
  </body>

  </html>
<?php
  exit(); // Exekuzioa gelditzen dugu, hurrengo orria ez da erakutsiko.
}
?>
<!DOCTYPE html>
<html lang="es">

<head>
  <meta charset="UTF-8">
  <title>Bidaien egoera</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Bootstrap CSS kargatzen dugu -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Font Awesome CSS kargatzen dugu -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>

  <?php
  // Nabigazio barra sartzen dugu
  $activePage = 'bezero';
  include 'navbar.php';
  ?>

  <div class="container mt-4">
    <?php if (isset($_SESSION['erabiltzailea'])): ?>
      <?php
      // Saioan "idbezeroa" dagoela uste dugu
      $idbezeroa = $_SESSION['idbezeroa'];

      // POST eskaera badago eta "idbidaia" jasota badago, bidaiaren egoera eguneratzen dugu
      if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["idbidaia"])) {
        $idbidaia = intval($_POST["idbidaia"]);
        // Prestatu eskaera: bidaiaren egoera "Ezeztatuta" bihurtzen dugu
        $stmt = $conn->prepare("UPDATE bidaiak SET egoera = 'Ezeztatuta' WHERE idbidaiak = ?");
        $stmt->bind_param("i", $idbidaia);
        $stmt->execute();
        if ($stmt->affected_rows > 0) {
          $message = "Bidaia ezeztatua izan da.";
        } else {
          $message = "Errorea: ezin izan da bidaia ezeztatu.";
        }
        $stmt->close();
      }

      // SQL kontsulta: erabiltzaile honen bidai aktibo edo hori ez da esleitu gabe daudenak ateratzen dira.
      // Bidai horiek "Esleitu gabe" edo "Aktibo" egoeran daude.
      $sql = "SELECT 
                b.idbidaiak,
                b.data,
                b.egoera,
                b.hasiera_kokapena,
                b.helmuga_kokapena,
                b.hasiera_ordua,
                b.bukaera_ordua,
                COALESCE(l.izena, 'Gidari gabe') AS izena,
                COALESCE(l.abizena, '') AS abizena
              FROM bidaiak b
              LEFT JOIN langilea l ON b.taxista_langilea_idlangilea = l.idlangilea
              WHERE b.bezeroa_idbezeroa = '$idbezeroa'
                AND (b.egoera = 'Esleitu gabe' OR b.egoera = 'Aktibo')
              ORDER BY b.data DESC";
      $result = $conn->query($sql);
      ?>

      <!-- Gertaeraren emaitza badago, mezuari buruzko informazioa erakusten dugu -->
      <?php if (isset($message)): ?>
        <div class="alert alert-info" role="alert">
          <?php echo $message; ?>
        </div>
      <?php endif; ?>

      <?php if ($result && $result->num_rows > 0): ?>
        <div class="table-responsive">
          <table class="table table-striped table-bordered">
            <thead class="table-dark">
              <tr>
                <th>ID</th>
                <th>Fecha</th>
                <th>Hora de Inicio</th>
                <th>Hora de Fin</th>
                <th>Origen</th>
                <th>Destino</th>
                <th>Acciones</th>
                <th>Gidaria</th>
              </tr>
            </thead>
            <tbody>
              <?php while ($row = $result->fetch_assoc()): ?>
                <tr>
                  <!-- Bidaiaren datuak erakusten dira taulan -->
                  <td><?= $row['idbidaiak'] ?></td>
                  <td><?= $row['data'] ?></td>
                  <td><?= $row['hasiera_ordua'] ?></td>
                  <td><?= $row['bukaera_ordua'] ?></td>
                  <td><?= $row['hasiera_kokapena'] ?></td>
                  <td><?= $row['helmuga_kokapena'] ?></td>
                  <td>
                    <?php if ($row['egoera'] === 'Esleitu gabe'): ?>
                      <!-- Bidaiaren ezeztatze/formularioa: Bidaiaren egoera "Esleitu gabe" bada, gertaera hori cancelatzeko -->
                      <form method="post" action="">
                        <input type="hidden" name="idbidaiak" value="<?= $row['idbidaiak'] ?>">
                        <input type="hidden" name="idbidaia" value="<?= $row['idbidaiak'] ?>">
                        <input type="submit" value="Kantzelatu" class="btn btn-danger btn-sm">
                      </form>
                    <?php else: ?>
                      <!-- Behin bidaiaren egoera aldatu bada "Aktibo", etiketatuta erakusten dugu -->
                      <span class="badge bg-success">Aktibo</span>
                    <?php endif; ?>
                  </td>
                  <td><?= $row['izena'] . ' ' . $row['abizena'] ?></td>
                </tr>
              <?php endwhile; ?>
            </tbody>
          </table>
        </div>
      <?php else: ?>
        <!-- Hitz mezu bat bidaiak ez badira aurkitzen -->
        <div class="alert alert-info" role="alert">
          Ez dituzu bidaiarik zure historialean.
        </div>
      <?php endif; ?>
    <?php else: ?>
      <!-- Sesio-ez dagoenean mezua erakusten dugu -->
      <div class="alert alert-warning" role="alert">
        Saioa hasi zure bidaien historiala ikusteko.
      </div>
    <?php endif; ?>
  </div>

  <footer>
    <?php include 'footer.php'; ?>
  </footer>

  <!-- Bootstrap JS Bundle (Popper barne): JS funtzionalitateak kargatzeko -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>