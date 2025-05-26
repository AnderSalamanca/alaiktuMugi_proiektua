<?php
session_start();
require 'konexioa.php';

// Erabiltzailearen saioa egiaztatzen dugu: "idbezeroa" ez dagoenean, saioa ez dagoela adierazten dugu
if (!isset($_SESSION['idbezeroa']) || empty($_SESSION['idbezeroa'])) {
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
  <!-- Orri honetatik saioa ez badago, alerta bat erakusten dugu eta 3 segundotan login.php-ra eramango dugu -->

  <body class="bg-light d-flex align-items-center justify-content-center vh-100">
    <div class="container">
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
  exit();
}
?>

<?php
// Navbar sartzen dugu
include 'navbar.php';
$message = "";

// POST eskaera jasotzen denean, datuak jaso eta balioztatzen dira.
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
  // Forma-n jasotzen dugun data bueltatzen dugu, "data" input-a
  $selected_date = $_POST['data'];
  // Gaurko data formatuan "Y-m-d" formatuan ateratzen dugu
  $today = date('Y-m-d');

  // Aukeratutako data gaurko data baino lehenago badago, errore mezua jasoko dugu
  if ($selected_date < $today) {
    $message = "Errorea: Data ezin da aurreko eguna izan.";
  } else {
    // Datu guztiak aldagaietan gordetzen dira
    $data = $selected_date;
    $hasiera_kokapena = $_POST['hasiera_kokapena'];
    $helmuga_kokapena = $_POST['helmuga_kokapena'];
    $bezero_id = $_SESSION['idbezeroa'];
    $hasiera_ordua = $_POST['ordua'];

    // Bidaiaren txertaketa: bidaiaren datuak, hasiera eta helmuga kokapenak, bezeroaren id, hasiera ordua eta egoera ezarrituta "Esleitu gabe"
    $sql = "INSERT INTO bidaiak (data, hasiera_kokapena, helmuga_kokapena, bezeroa_idbezeroa, hasiera_ordua, egoera)
                VALUES ('$data', '$hasiera_kokapena', '$helmuga_kokapena', '$bezero_id', '$hasiera_ordua', 'Esleitu gabe')";
    if ($conn->query($sql) === TRUE) {
      $message = "Bidaia eskaera egin da.";
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
  <title>Tu Página</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Bootstrap CSS kargatzen dugu -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Font Awesome CSS kargatzen dugu -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>
  <!-- Eduki nagusia: Bidaia eskaeraren orria -->
  <div class="container my-5">
    <!-- Orriaren izenburua -->
    <h1 class="mb-4">Bidaia eskatu</h1>

    <!-- Mezuaren adierazpena; mezua badago, alerta batean erakusten dugu -->
    <?php if (!empty($message)): ?>
      <div class="alert alert-info" role="alert">
        <?php echo $message; ?>
      </div>
    <?php endif; ?>

    <!-- Bidaia eskaera egiteko formularioa -->
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
        <!-- Gaurko data ezartzen dugu "min" atributuarekin, hau da, data aukeratzeko input-a gaurko datatik handiagoa izan behar dela -->
        <input type="date" class="form-control" id="data" name="data" min="<?php echo date('Y-m-d'); ?>" required>
      </div>
      <div class="mb-3">
        <label for="ordua" class="form-label">Hasiera ordua:</label>
        <input type="time" class="form-control" id="ordua" name="ordua" required>
      </div>
      <button type="submit" class="btn btn-primary">Bidaia eskatu</button>
    </form>
  </div>

  <!-- JavaScript funtzioak: Data eta orduaren eremuak eguneratzen dira -->
  <script>
    // "data" eta "ordua" input-ak lortzen ditugu
    const dateInput = document.getElementById("data");
    const timeInput = document.getElementById("ordua");

    // Funtzio honek eguneratzen du "ordua" input-aren "min" atributua, orduak gaurko data bada
    function updateTimeMin() {
      const todayStr = new Date().toISOString().split('T')[0];
      if (dateInput.value === todayStr) {
        // Gaurko ordua lortzen dugu, segundu eta milisegundu gabe
        const now = new Date();
        now.setSeconds(0, 0);
        const hh = now.getHours().toString().padStart(2, '0');
        const mm = now.getMinutes().toString().padStart(2, '0');
        const currentTime = hh + ":" + mm;
        timeInput.min = currentTime;

        // Balio jada ezarritako ordua, minimum ez bada, eguneratzen dugu
        if (timeInput.value && timeInput.value < currentTime) {
          timeInput.value = currentTime;
        }
      } else {
        // Gaurko data ez bada, "min" atributua kentzen dugu
        timeInput.removeAttribute("min");
      }
    }

    // Orri kargatzean, gaurko data ezartzen dugu "data" input-an
    if (dateInput) {
      const today = new Date().toISOString().split('T')[0];
      dateInput.value = today;
      dateInput.min = today;
    }
    // Orri kargatzean, gaurko ordua ezartzen dugu "ordua" input-an
    if (timeInput) {
      const now = new Date();
      now.setSeconds(0, 0);
      const hh = now.getHours().toString().padStart(2, '0');
      const mm = now.getMinutes().toString().padStart(2, '0');
      timeInput.value = hh + ":" + mm;
      updateTimeMin();
    }

    // Data aldatu ondoren, eta ordu input-ak fokusa jaso ondoren, "ordua" input-aren "min" eguneratzen dugu
    dateInput.addEventListener("change", updateTimeMin);
    timeInput.addEventListener("focus", updateTimeMin);
    // Minutu bakoitzean eguneratzen dugu "ordua" input-aren "min" atributoa, gaurko data bada
    setInterval(updateTimeMin, 60000);
  </script>

  <?php include 'footer.php'; ?>

  <!-- Bootstrap JS Bundle (Popper barne) kargatzen dugu -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>