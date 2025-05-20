<!DOCTYPE html>
<html lang="eu">

<head>
  <meta charset="UTF-8">
  <title>Tu Página</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Bootstrap CSS: Orriaren diseinu erantzunkorra eta estilo orokorrak kargatzen ditu -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Font Awesome CSS: Ikonoak erraz erakusteko erabiltzen da -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>
  <?php
  // Aktibatutako orria kontsulta egiten dugu: "contact" balioa ezartzen dugu
  $activePage = 'contact';
  // Nabigazio barraren edukia sartzen dugu (navbar.php)
  include 'navbar.php';
  ?>

  <!-- Eduki nagusia: Jarri gurekin harremanetan orria -->
  <div class="container my-5">
    <div class="row align-items-center">
      <!-- Ezker alde: Google Maps-ekin mapa erakusten du -->
      <div class="col-md-6">
        <!-- Ratio 16x9, mapa edo iframe baten ratio egokia ezartzeko erabiltzen da -->
        <div class="ratio ratio-16x9">
          <!-- Iframe: Google Maps-ko helbide zehaztuak erabiltzen dira koordenatuak adierazteko -->
          <iframe src="https://maps.google.com/maps?q=43.14958721283321, -2.0684690298050423&z=15&output=embed"
            allowfullscreen
            loading="lazy"
            style="border:0;">
          </iframe>
        </div>
      </div>
      <!-- Eskuineko atala: Harreman informazioa eta kontaktu datuak -->
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

  <!-- Footer-a sartzen dugu: footer.php fitxategia -->
  <?php include 'footer.php'; ?>

  <!-- Bootstrap JS Bundle with Popper: Interakzio osagaiak funtzionatzeko -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>