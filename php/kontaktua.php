<!DOCTYPE html>
<html lang="eu">
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
  <?php
    $activePage = 'contact';
    include 'navbar.php';
  ?>

  <div class="container my-5">
    <div class="row align-items-center">
      <!-- Mapa a ezkerrera -->
      <div class="col-md-6">
        <div class="ratio ratio-16x9">
          <!-- Iframe-a Google Maps-ekin, zehaztutako koordenatuak erabiliz -->
          <iframe src="https://maps.google.com/maps?q=43.14958721283321, -2.0684690298050423&z=15&output=embed" 
                  allowfullscreen 
                  loading="lazy" 
                  style="border:0;">
          </iframe>
        </div>
      </div>
      <!-- Testua eskuinera -->
      <div class="col-md-6">
        <h2>Jarri gurekin harremanetan</h2>
        <p>
          <strong>Alaiktumugi</strong><br>
          Tolsaldean, Guipuzkoan<br>
          Telefonoa: +34 123 456 789<br>
          Emaila: info@alaiktumugi.eus
        </p>
      </div>
    </div>
  </div>

  <?php include 'footer.php'; ?>

  <!-- Bootstrap JS Bundle con Popper -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
