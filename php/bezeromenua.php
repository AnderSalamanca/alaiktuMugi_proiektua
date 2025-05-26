<?php
session_start();
// Saioa abiarazten dugu eta egiaztatzen dugu erabiltzaileak 'idbezeroa' balio bat duen ala ez.
if (!isset($_SESSION['idbezeroa']) || empty($_SESSION['idbezeroa'])) {
    // Erabiltzailearen saioa ez badago, mezu bat erakusten dugu eta 3 segundotan login.php-ra eredu egiten dugu.
    ?>
    <!DOCTYPE html>
    <html lang="eu">
    <head>
      <meta charset="UTF-8">
      <title>Saioa Hasi Ez</title>
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <!-- Bootstrap CSS kargatzen dugu -->
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light d-flex align-items-center justify-content-center vh-100">
      <div class="container">
        <!-- Alert mezua: erabiltzaileak bezero bezala saioa hasi behar duela adierazten du -->
        <div class="alert alert-warning text-center" role="alert">
          Bezero bezala hasi saioa mesedez.
        </div>
      </div>
      <script>
        // 3000 milisegundotan (3 segundotan) login.php-ra eramango dugu
        setTimeout(function(){
          window.location.href = "login.php";
        }, 3000);
      </script>
    </body>
    </html>
    <?php
    exit();
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
   <style>
     /* Card-ek mouse-aren gainean daudenean handitzeko efektua ezartzen dugu */
     .card-hover:hover {
       transform: scale(1.02);
       transition: transform 0.3s; 
     }
   </style>
</head>
<body>
  
  <?php 
    // Aktibatutako orriaren izena ezartzen dugu eta nabigazio barra sartzen dugu
    $activePage = 'bezero';
    include 'navbar.php'; 
  ?>

  <!-- Eduki nagusia: Bezero Menua -->
  <div class="container my-5">
    <!-- Menuaren izenburua -->
    <h1 class="mb-5 text-center">Bezero Menua</h1>
    <!-- Bezeroaren aukerak modu karten bidez banatuta -->
    <div class="row row-cols-1 row-cols-md-3 g-4">
      
      <!-- Card 1: Bidaia Eskatu -->
      <div class="col">
        <div class="card card-hover h-100 border-primary">
          <div class="card-body text-center">
            <!-- Karta titulua -->
            <h5 class="card-title">Bidaia Eskatu</h5>
            <!-- Karta testua -->
            <p class="card-text">
              Eskatu bidaia azkar, eroso eta seguru bat. Zure kokapenean eta helburuan oinarrituta, gure sistema aurreratua zure bila egongo da.
              Zure bidaia antolatzea eta operazio errazak egitea helburu duguna, zerbitzu on baten funtsezko elementua da.
            </p>
          </div>
          <div class="card-footer text-center">
            <!-- Bidaia eskatzeko esteka -->
            <a href="bidaiaeskatu.php" class="btn btn-primary">Eskatu orain</a>
          </div>
        </div>
      </div>
      
      <!-- Card 2: Bidaien Egoera -->
      <div class="col">
        <div class="card card-hover h-100 border-warning">
          <div class="card-body text-center">
            <!-- Karta titulua -->
            <h5 class="card-title">Bidaien Egoera</h5>
            <!-- Karta testua -->
            <p class="card-text">
              Jarraitu zure bidaien egoera denbora errealean. Informazio zehatza eta eguneratua jasoko duzu, estekatutako txostenekin eta zehaztapenekin.
              Horrela, eskaeraren prozesua eta sentimenduak baloratzea ahalbidetzen duzu modu eraginkorrean.
            </p>
          </div>
          <div class="card-footer text-center">
            <!-- Bidaien egoera ikusteko esteka -->
            <a href="bidaiaegoera.php" class="btn btn-warning">Egoera ikusi</a>
          </div>
        </div>
      </div>
      
      <!-- Card 3: Bidaien Historiala -->
      <div class="col">
        <div class="card card-hover h-100 border-success">
          <div class="card-body text-center">
            <!-- Karta titulua -->
            <h5 class="card-title">Historiala</h5>
            <!-- Karta testua -->
            <p class="card-text">
              Ikusi eta kudeatu zure bidaiak aurretik egindakoa. Informazio xehetasun zabala eta analisi erabilgarriak eskura ditzakezu, 
              zure erabilera historikoa eta gogoko momentuak berrizorki bizitzeko moduan.
            </p>
          </div>
          <div class="card-footer text-center">
            <!-- Historiala ikusteko esteka -->
            <a href="historiala.php" class="btn btn-success">Historiala ikusi</a>
          </div>
        </div>
      </div>
      
    </div>
  </div>

  <!-- Orriaren beheko atal: Footer -->
  <footer>
    <?php include 'footer.php'; ?>
  </footer>

  <!-- Bootstrap JS Bundle (Popper barne): Interakzio eta JS funtzioak kargatzeko -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
