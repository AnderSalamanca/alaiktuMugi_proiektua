<?php
require 'konexioa.php';

$message = "";

// Procesamos el formulario antes de emitir cualquier salida HTML
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Recogemos la fecha enviada por el formulario
    $selected_date = $_POST['data'];
    $today = date('Y-m-d');

    // Verificamos que la fecha seleccionada no sea anterior a hoy
    if ($selected_date < $today) {
        $message = "Errorea: Data ezin da aurreko eguna izan.";
    } else {
        $data = $selected_date;
        $hasiera_kokapena = $_POST['hasiera_kokapena'];
        $helmuga_kokapena = $_POST['helmuga_kokapena'];
        $bezero_id = $_SESSION['idbezeroa'];
        $hasiera_ordua = $_POST['ordua'];

        // Insertar en la base de datos
        $sql = "INSERT INTO bidaiak (data, hasiera_kokapena, helmuga_kokapena, bezeroa_idbezeroa, hasiera_ordua, egoera)
                VALUES ('$data', '$hasiera_kokapena', '$helmuga_kokapena', '$bezero_id', '$hasiera_ordua', 'Esleitu gabe')";
        if ($conn->query($sql) === TRUE) {
            $message = "Viaje solicitado con éxito.";
        } else {
            $message = "Error: " . $conn->error;
        }
    }
}
?>
<!DOCTYPE html>
<html lang="eu">
<head>
  <meta charset="UTF-8">
  <title>Bidaia eskatu</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

  <?php include 'navbar.php'; ?>

  <div class="container my-5">
    <h1 class="mb-4">Bidaia eskatu</h1>
    
    <?php if (!empty($message)): ?>
      <div class="alert alert-info" role="alert">
        <?php echo $message; ?>
      </div>
    <?php endif; ?>
    
    <form action="" method="post">
      <div class="mb-3">
        <label for="hasiera_kokapena" class="form-label">Hasierako kokapena:</label>
        <input type="text" class="form-control" id="hasiera_kokapena" name="hasiera_kokapena" required>
      </div>
      <div class="mb-3">
        <label for="helmuga_kokapena" class="form-label">Helmuga kokapena:</label>
        <input type="text" class="form-control" id="helmuga_kokapena" name="helmuga_kokapena" required>
      </div>
      <div class="mb-3">
        <label for="data" class="form-label">Data:</label>
        <!-- Se establece min con la fecha actual -->
        <input type="date" class="form-control" id="data" name="data" min="<?php echo date('Y-m-d'); ?>" required>
      </div>
      <div class="mb-3">
        <label for="ordua" class="form-label">Hasiera ordua:</label>
        <input type="time" class="form-control" id="ordua" name="ordua" required>
      </div>
      <button type="submit" class="btn btn-primary">Bidaia eskatu</button>
    </form>
  </div>

  <script>
    // Referencias a los inputs
    const dateInput = document.getElementById("data");
    const timeInput = document.getElementById("ordua");

    // Función para actualizar el atributo min del input "ordua" si la fecha es hoy
    function updateTimeMin() {
      const todayStr = new Date().toISOString().split('T')[0];
      if (dateInput.value === todayStr) {
        // Obtenemos la hora actual sin segundos ni milisegundos
        const now = new Date();
        now.setSeconds(0, 0);
        const hh = now.getHours().toString().padStart(2, '0');
        const mm = now.getMinutes().toString().padStart(2, '0');
        const currentTime = hh + ":" + mm;
        timeInput.min = currentTime;

        // Si el usuario ya tenía un valor menor al mínimo, se actualiza
        if (timeInput.value && timeInput.value < currentTime) {
          timeInput.value = currentTime;
        }
      } else {
        // Para fechas distintas a hoy, se elimina la restricción
        timeInput.removeAttribute("min");
      }
    }

    // Asignamos la fecha actual al cargar la página
    if (dateInput) {
      const today = new Date().toISOString().split('T')[0];
      dateInput.value = today;
      dateInput.min = today;
    }
    // Asignamos la hora actual al cargar la página
    if (timeInput) {
      const now = new Date();
      now.setSeconds(0, 0);
      const hh = now.getHours().toString().padStart(2, '0');
      const mm = now.getMinutes().toString().padStart(2, '0');
      timeInput.value = hh + ":" + mm;
      updateTimeMin();
    }

    // Actualizar el min de la hora cuando cambia la fecha
    dateInput.addEventListener("change", updateTimeMin);
    // También se actualiza al enfocar el campo de hora
    timeInput.addEventListener("focus", updateTimeMin);
    // Actualizamos cada minuto para reflejar la hora actual en el caso de que la fecha sea hoy
    setInterval(updateTimeMin, 60000);
  </script>
  
  <?php include 'footer.php'; ?>
  
  <!-- Bootstrap JS Bundle con Popper -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
