<?php
session_start(); // Se debe iniciar la sesión antes de cualquier salida
require 'konexioa.php';
?>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Bidaia eskatu</title>
    <link rel="stylesheet" href="styles.css">
</head>

<body>

    <?php include 'navbar.php'; ?>

    <form action="" method="post">

        <label>Hasierako kokapena:</label>
        <input type="text" name="hasiera_kokapena" required>

        <label>Helmuga kokapena:</label>
        <input type="text" name="helmuga_kokapena" required>

        <label>Data:</label>
        <input type="date" id="data" name="data">

        <label>Hasiera ordua:</label>
        <input type="time" id="ordua" name="ordua">

        <input type="submit" value="Bidaia eskatu">
    </form>

    <script>
        document.getElementById("data").value = new Date().toISOString().split('T')[0];
        document.getElementById("ordua").value = new Date().toLocaleTimeString([], {
            hour: '2-digit',
            minute: '2-digit'
        });
    </script>
    <footer>
        <?php include 'footer.php'; ?>
    </footer>

</body>

</html>
<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $data = date('Y-m-d');
    $hasiera_kokapena = $_POST['hasiera_kokapena'];
    $helmuga_kokapena = $_POST['helmuga_kokapena'];
    $bezero_id = $_SESSION['idbezeroa'];
    $hasiera_ordua = $_POST['ordua'];

    // Insertar en la base de datos
    $sql = "INSERT INTO bidaiak (data, hasiera_kokapena, helmuga_kokapena, bezeroa_idbezeroa,hasiera_ordua, egoera) 
        VALUES ('$data', '$hasiera_kokapena', '$helmuga_kokapena', '$bezero_id', '$hasiera_ordua', 'Esleitu gabe')";

    if ($conn->query($sql) === TRUE) {

        echo "Viaje solicitado con éxito.";

    } else {

        echo "Error: " . $conexion->error;

    }
}
?>