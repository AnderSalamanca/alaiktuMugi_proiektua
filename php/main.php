<!DOCTYPE html>
<html lang="eu">

<head>
  <meta charset="UTF-8">
  <title>Tu Página</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Bootstrap CSS: Diseinu erantzunkorra eta estilo orokorrak kargatzen ditu -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Font Awesome CSS: Ikonoak erakusteko erabiltzen da -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>

  <?php
  // Nabigazio barra sartzen dugu eta orri aktiboa definitzen dugu
  $activePage = 'main';
  include 'navbar.php';
  ?>

  <!-- Lehenengo atala -->
  <div class="container my-5">
    <div class="row align-items-center">
      <!-- Testuaren zutabea -->
      <div class="col-md-6">
        <h2>Zergatik Alaiktumugi aukeratu?</h2>
        <p>Zerbitzu azkarra, segurua eta fidagarria, bidaiariak konfiantzazko gidariekin konektatzen dituena.</p>
        <!-- Saioa hasteko botoia, beltzez definituta -->
        <button class="btn btn-dark" onclick="window.location.href='login.php';">Saioa hasi</button>
      </div>
      <!-- Irudiaren zutabea -->
      <div class="col-md-6">
        <img src="irudiak/argazki1.jpg" alt="Gidaria 1" class="img-fluid rounded">
      </div>
    </div>
  </div>

  <!-- Bigarren atala -->
  <div class="container my-5">
    <div class="row align-items-center">
      <!-- Ordena aldatzen da pantaila handiagoetan -->
      <div class="col-md-6 order-md-2">
        <h2>Eroso bidaiatu</h2>
        <p>Auto onenak eta esperientzia onena bidaia atseginak bermatzeko.</p>
        <!-- Saioa hasteko botoia, beltzez definituta -->
        <button class="btn btn-dark" onclick="window.location.href='login.php';">Saioa hasi</button>
      </div>
      <div class="col-md-6 order-md-1">
        <img src="irudiak/argazki2.jpg" alt="Gidaria 2" class="img-fluid rounded">
      </div>
    </div>
  </div>

  <!-- Footer -->
  <footer>
    <?php include 'footer.php'; ?>
  </footer>

  <!-- Bootstrap JS Bundle with Popper: Funtzionalitate interaktiboak kargatzeko -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>