<?php
session_start();
require 'konexioa.php';

// Saioa egiaztatzen dugu: "erabiltzailea" ez badago, testa ez da erakutsiko.
if (!isset($_SESSION['erabiltzailea']) || empty($_SESSION['erabiltzailea'])) {
?>
  <!DOCTYPE html>
  <html lang="es">

  <head>
    <meta charset="UTF-8">
    <title>Sarrera denegatua</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap CSS kargatzen dugu -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  </head>

  <!-- Orri hau agertzen da saioa ez badago; alerta bat erakusten da eta 3 segundotan login.php-ra eramango da -->

  <body class="bg-light d-flex align-items-center justify-content-center vh-100">
    <div class="container">
      <div class="alert alert-warning text-center" role="alert">
        Bezero bezala hasi saioa mesedez.
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
  exit(); // Exekuzioa gelditzen dugu, eta ez da hurrengo kodea kargatuko.
}
?>
<!DOCTYPE html>
<html lang="es">

<head>
  <meta charset="UTF-8">
  <title>Historiala</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Bootstrap CSS kargatzen dugu -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Font Awesome CSS kargatzen dugu -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>

  <!-- Navbar: Nabigazio barra sartzen dugu (navbar.php fitxategia sartzen da) -->
  <?php include 'navbar.php'; ?>

  <div class="container mt-4">
    <?php
    // Saioan "idbezeroa" gordeta dagoela uste dugu.
    $idbezeroa = $_SESSION['idbezeroa'];
    // SQL kontsulta: "historiala" taulatik ateratzen dira bezero honen bidai historikoak.
    // LEFT JOIN bidez, langilearen izena eta abizena ere eskuratzen dira; ez badago, "Gidari gabe" eta hutsik jarriko dira.
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
                WHERE h.idbezeroa = '$idbezeroa'
                ORDER BY h.data DESC";

    // Kontsulta exekutatzen dugu eta emaitzak gordetzen ditugu
    $result = $conn->query($sql);
    ?>

    <?php if ($result && $result->num_rows > 0): ?>
      <!-- Taula responsiboa: Bidai historikoak erakusten ditu -->
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
                <!-- Eskuz erakusten dira bidaiaren datuak -->
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
      <!-- Ez badira bidai historikorik, alerta bat erakusten da -->
      <div class="alert alert-info" role="alert">
        Ez duzu bidaiarik zure historialean.
      </div>
    <?php endif; ?>
  </div>

  <!-- Footer sartzen dugu: footer.php fitxategia -->
  <footer>
    <?php include 'footer.php'; ?>
  </footer>

  <!-- Bootstrap JS Bundle (Popper barne): Interakzio osagaiak funtzionatzeko -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>