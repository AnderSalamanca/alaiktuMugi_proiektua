<?php
require 'konexioa.php';
?>
<!DOCTYPE html>
<html lang="es">
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

<?php include 'navbar.php'; ?>

<div class="container mt-4">
  <?php
  if (isset($_SESSION['erabiltzailea'])) {
      // Se asume que la sesión almacena el id del usuario registrado
      $idgidaria = $_SESSION['idlangilea'];
      // Consulta SQL para obtener el historial de viajes del cliente actual
      $sql = "SELECT 
                h.idhistoriala, 
                h.data, 
                h.hasiera_ordua, 
                h.bukaera_ordua, 
                h.hasiera_kokapena, 
                h.helmuga_kokapena, 
                h.xehetasunak,
                b.izena,
                b.abizena,
                h.prezioa
              FROM historiala h
              LEFT JOIN bezeroa b ON h.idbezeroa = b.idbezeroa
              WHERE h.idgidaria = '$idgidaria'
              ORDER BY h.data DESC";

      $result = $conn->query($sql);

      if ($result && $result->num_rows > 0) {
          echo "<div class='table-responsive'>";
          echo "<table class='table table-striped table-bordered'>
                  <thead class='table-dark'>
                    <tr>
                      <th>ID</th>
                      <th>Data</th>
                      <th>Hasiera ordua</th>
                      <th>Bukaera ordua</th>
                      <th>Jatorria</th>
                      <th>Helmuga</th>
                      <th>Komentarioak</th>
                      <th>Bezeroa</th>
                      <th>Prezioa</th>
                    </tr>
                  </thead>
                  <tbody>";
          while ($row = $result->fetch_assoc()) {
              echo "<tr>
                      <td>" . $row["idhistoriala"] . "</td>
                      <td>" . $row["data"] . "</td>
                      <td>" . $row["hasiera_ordua"] . "</td>
                      <td>" . $row["bukaera_ordua"] . "</td>
                      <td>" . $row["hasiera_kokapena"] . "</td>
                      <td>" . $row["helmuga_kokapena"] . "</td>
                      <td>" . $row["xehetasunak"] . "</td>
                      <td>" . $row["izena"] . " " . $row["abizena"] . "</td>
                      <td>" . $row["prezioa"] . "</td>
                    </tr>";
          }
          echo "</tbody></table>";
          echo "</div>";
      } else {
          echo "<div class='alert alert-info' role='alert'>No tienes viajes archivados en tu historial.</div>";
      }
  } else {
      echo "<div class='alert alert-warning' role='alert'>Inicia sesión para ver tu historial de viajes.</div>";
  }
  ?>
</div>

<footer>
  <?php include 'footer.php'; ?>
</footer>

<!-- Bootstrap JS Bundle (incluye Popper) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
