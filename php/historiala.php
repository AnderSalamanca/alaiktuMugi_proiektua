<?php
session_start(); // Se debe iniciar la sesión antes de cualquier salida
require 'konexioa.php';
?>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Historial de Viajes</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>

<?php include 'navbar.php'; ?>

<div class="container">
<?php
if (isset($_SESSION['erabiltzailea'])) {
    // Se asume que la sesión almacena el id del usuario registrado
    $erabiltzailea_id = $_SESSION['erabiltzailea'];

    // Consulta SQL para obtener el historial de viajes del cliente actual
    $sql = "SELECT 
                h.idhistoriala, 
                h.data, 
                h.hasiera_ordua, 
                h.bukaera_ordua, 
                h.hasiera_kokapena, 
                h.helmuga_kokapena, 
                h.xehetasunak,
                l.izena,
                l.abizena
            FROM historiala h
            JOIN langilea l ON h.idgidaria = l.idlangilea
            WHERE h.idbezeroa = '$erabiltzailea_id'
            ORDER BY h.data DESC";

    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        echo "<table class='table'>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Fecha</th>
                    <th>Hora de Inicio</th>
                    <th>Hora de Fin</th>
                    <th>Origen</th>
                    <th>Destino</th>
                    <th>Comentarios</th>
                    <th>Gidaria</th>
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
                  </tr>";
        }
        echo "</tbody></table>";
    } else {
        echo "<p class='message'>No tienes viajes archivados en tu historial.</p>";
    }
} else {
    echo "<p class='message'>Inicia sesión para ver tu historial de viajes.</p>";
}
?>
</div>

<footer>
  <?php include 'footer.php'; ?>
</footer>

</body>
</html>
