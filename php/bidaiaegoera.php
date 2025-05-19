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
    <?php if (isset($_SESSION['erabiltzailea'])): ?>
      <?php
      // Se asume que la sesión almacena el id del usuario registrado
      $idbezeroa = $_SESSION['idbezeroa'];

      if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["idbidaia"])) {
        $idbidaia = intval($_POST["idbidaia"]);
        // Consulta preparada para actualizar el estado a 'Ezeztatuta'
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
                  <td><?= $row['idbidaiak'] ?></td>
                  <td><?= $row['data'] ?></td>
                  <td><?= $row['hasiera_ordua'] ?></td>
                  <td><?= $row['bukaera_ordua'] ?></td>
                  <td><?= $row['hasiera_kokapena'] ?></td>
                  <td><?= $row['helmuga_kokapena'] ?></td>
                  <td>
                    <?php if ($row['egoera'] === 'Esleitu gabe'): ?>
                      <!-- Formulario para cancelar el viaje -->
                      <form method="post" action="">
                        <input type="hidden" name="idbidaiak" value="<?= $row['idbidaiak'] ?>">
                        <input type="hidden" name="idbidaia" value="<?= $row['idbidaiak'] ?>">
                        <input type="submit" value="Kantzelatu" class="btn btn-danger btn-sm">
                      </form>
                    <?php else: ?>
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
        <div class="alert alert-info" role="alert">
          No tienes viajes archivados en tu historial.
        </div>
      <?php endif; ?>
    <?php else: ?>
      <div class="alert alert-warning" role="alert">
        Inicia sesión para ver tu historial de viajes.
      </div>
    <?php endif; ?>
  </div>

  <footer>
    <?php include 'footer.php'; ?>
  </footer>

  <!-- Bootstrap JS Bundle (incluye Popper) -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
