<?php
session_start();
require 'konexioa.php'; // Datu basearekin konexioa ezartzen dugu

// Erabiltzailearen saioa egiaztatzen dugu: "idlangilea" ez bada, saioa hasi behar dela esaten du
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
  <!-- Saioa ez badago, alerta bat erakusten du eta 3 segundotan login.php-ra eramango du -->

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
  exit(); // Exekuzioa gelditzen dugu
}
?>
<!DOCTYPE html>
<html lang="es">

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

  <?php include 'navbar.php'; // Nabigazio barra sartzen dugu 
  ?>

  <div class="container mt-4">
    <?php
    // Saioan gordeta dagoen "idlangilea" ateratzen dugu
    $idlangilea = $_SESSION['idlangilea'];
    // SQL kontsulta: Gidariaren bidai historikoak ateratzen ditu
    $sql = "SELECT 
                h.idhistoriala, 
                h.data, 
                h.hasiera_ordua, 
                h.bukaera_ordua, 
                h.hasiera_kokapena, 
                h.helmuga_kokapena, 
                h.xehetasunak,
                h.prezioa,
                COALESCE(l.izena, 'Gidari gabe') AS izena,
                COALESCE(l.abizena, '') AS abizena
              FROM historiala h
              LEFT JOIN langilea l ON h.idgidaria = l.idlangilea
              WHERE h.idgidaria = '$idlangilea'
              ORDER BY h.data DESC";

    // Kontsulta exekutatzen dugu
    $result = $conn->query($sql);
    ?>

    <?php if ($result && $result->num_rows > 0): ?>
      <!-- Bidai historikoak erakusten dituen taula responsiboa -->
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
              <th>Comentarios</th>
              <th>Gidaria</th>
              <th>Prezioa</th>
            </tr>
          </thead>
          <tbody>
            <?php while ($row = $result->fetch_assoc()): ?>
              <tr>
                <!-- Bidai historikoaren datu guztiak erakusten dira -->
                <td><?= $row["idhistoriala"] ?></td>
                <td><?= $row["data"] ?></td>
                <td><?= $row["hasiera_ordua"] ?></td>
                <td><?= $row["bukaera_ordua"] ?></td>
                <td><?= $row["hasiera_kokapena"] ?></td>
                <td><?= $row["helmuga_kokapena"] ?></td>
                <td><?= $row["xehetasunak"] ?></td>
                <td><?= $row["izena"] ?> <?= $row["abizena"] ?></td>
                <td><?= $row["prezioa"] ?></td>
              </tr>
            <?php endwhile; ?>
          </tbody>
        </table>
      </div>
    <?php else: ?>
      <!-- Ez bada bidai historikorik aurkitzen, alerta mezu bat erakusten da -->
      <div class="alert alert-info" role="alert">
        No tienes viajes archivados en tu historial.
      </div>
    <?php endif; ?>
  </div>

  <footer>
    <?php include 'footer.php'; // Footer-a sartzen dugu 
    ?>
  </footer>

  <!-- Bootstrap JS Bundle (Popper barne) kargatzen dugu, interakzio osagaiak ondo funtzionatzeko -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>