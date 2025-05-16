<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Inicio | Alaiktumugi</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

  <?php include 'navbar.php'; ?>

  <!-- Sección 1 -->
  <div class="container my-5">
    <div class="row align-items-center">
      <!-- Columna de texto -->
      <div class="col-md-6">
        <h2>¿Por qué elegir Alaiktumugi?</h2>
        <p>Un servicio rápido, seguro y confiable que conecta pasajeros con conductores de confianza.</p>
        <!-- Botón modificado a negro -->
        <button class="btn btn-dark" onclick="window.location.href='login.php';">Saioa hasi</button>
      </div>
      <!-- Columna de imagen -->
      <div class="col-md-6">
        <img src="irudiak/argazki1.jpg" alt="Conductor 1" class="img-fluid rounded">
      </div>
    </div>
  </div>

  <!-- Sección 2 -->
  <div class="container my-5">
    <div class="row align-items-center">
      <!-- Se invierte el orden en dispositivos medianos y superiores -->
      <div class="col-md-6 order-md-2">
        <h2>Viaja con comodidad</h2>
        <p>Los mejores autos y la mejor experiencia para garantizar viajes placenteros.</p>
        <!-- Botón modificado a negro -->
        <button class="btn btn-dark" onclick="window.location.href='login.php';">Saioa hasi</button>
      </div>
      <div class="col-md-6 order-md-1">
        <img src="irudiak/argazki2.jpg" alt="Conductor 2" class="img-fluid rounded">
      </div>
    </div>
  </div>

  <footer>
    <?php include 'footer.php'; ?>
  </footer>

  <!-- Bootstrap JS Bundle with Popper -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
