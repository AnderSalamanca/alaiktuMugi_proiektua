<?php
session_start();
require 'konexioa.php';

// Saioa egiaztatzen dugu: "erabiltzailea" edo "idlangilea" ez badira, saioa ez dagoela uste dugu
if (!isset($_SESSION['erabiltzailea']) || empty($_SESSION['idlangilea'])) {
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
    <!-- Saio ez badago, alert batekin ezin da ihes egin eta 3 segundotan login.php-ra eramango dugu -->
    <body class="bg-light d-flex align-items-center justify-content-center vh-100">
       <div class="container">
          <div class="alert alert-warning text-center" role="alert">
             Inicia sesión para ver tu historial de viajes.
          </div>
       </div>
       <script>
          setTimeout(function(){
             window.location.href = "login.php";
          }, 3000);
       </script>
    </body>
    </html>
    <?php
    exit();
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

  <!-- Nabigazio barra sartzen dugu: navbar.php fitxategia -->
  <?php include 'navbar.php'; ?>

  <div class="container mt-4">
    <?php 
      // Saioan gordeta dagoen "idbezeroa" ateratzen dugu
      $idgidaria = $_SESSION['idlangilea'];
      // SQL kontsulta: "historiala" taulatik bidai historikoak ateratzen dira.
      // LEFT JOIN erabiliz, bezeroaren (b) izen eta abizen informazioa ere eskuratzen dugu;
      // Ez badago langilearen emaitza, "Gidari gabe" edo hutsik sartzen da.
      $sql = "SELECT 
                h.idhistoriala, 
                h.data, 
                h.hasiera_ordua, 
                h.bukaera_ordua, 
                h.hasiera_kokapena, 
                h.helmuga_kokapena, 
                h.xehetasunak,
                h.prezioa,
                COALESCE(b.izena, 'Bezero gabe') AS izena,
                COALESCE(b.abizena, '') AS abizena
              FROM historiala h
              LEFT JOIN bezeroa b ON h.idbezeroa = b.idbezeroa
              WHERE h.idgidaria = '$idgidaria'
              ORDER BY h.data DESC";
      $result = $conn->query($sql);
    ?>

    <?php if ($result && $result->num_rows > 0): ?>
      <!-- Erabiltzailearen bidai historikoa taulan erakusten dugu -->
      <div class="table-responsive">
        <table class="table table-striped table-bordered">
          <thead class="table-dark">
            <tr>
              <th>ID</th>
              <th>Data</th>
              <th>Hasiera ordua</th>
              <th>Amaiera ordua</th>
              <th>Jatorria</th>
              <th>Helmuga</th>
              <th>Xehetasunak</th>
              <th>Bezeroa</th>
              <th>Prezioa</th>
            </tr>
          </thead>
          <tbody>
            <?php while ($row = $result->fetch_assoc()): ?>
              <tr>
                <!-- Bidai historikoaren datuak guztia erakusten ditugu -->
                <td><?= $row["idhistoriala"] ?></td>
                <td><?= $row["data"] ?></td>
                <td><?= $row["hasiera_ordua"] ?></td>
                <td><?= $row["bukaera_ordua"] ?></td>
                <td><?= $row["hasiera_kokapena"] ?></td>
                <td><?= $row["helmuga_kokapena"] ?></td>
                <td><?= $row["xehetasunak"] ?></td>
                <td><?= $row["izena"] . " " . $row["abizena"] ?></td>
                <td><?= $row["prezioa"] ?></td>
              </tr>
            <?php endwhile; ?>
          </tbody>
        </table>
      </div>
    <?php else: ?>
      <!-- Ez badira bidai historikorik, alerta mezu bat erakusten dugu -->
      <div class="alert alert-info" role="alert">
        Ez duzu bidaiarik zure historialean.
      </div>
    <?php endif; ?>
  </div>

  <!-- Footer sartzen dugu: footer.php fitxategia -->
  <footer>
    <?php include 'footer.php'; ?>
  </footer>

  <!-- Bootstrap JS Bundle (Popper barne) kargatzen dugu, interakzio funtzioak egiteko -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
