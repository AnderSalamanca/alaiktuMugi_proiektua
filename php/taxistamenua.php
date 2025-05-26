<?php
session_start();
// Saioa abiarazten dugu eta egiaztatzen dugu erabiltzaileak 'idlangilea' balio bat duen ala ez.
if (!isset($_SESSION['idlangilea']) || empty($_SESSION['idlangilea'])) {
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
        <!-- Alert mezua: erabiltzaileak langile bezala saioa hasi behar duela adierazten du -->
        <div class="alert alert-warning text-center" role="alert">
          Langile bezala hasi saioa mesedez.
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
   <title>Langile Menua</title>
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
    $activePage = 'taxista';
    include 'navbar.php'; 
  ?>

  <!-- Eduki nagusia: Langile Menua -->
  <div class="container my-5">
    <!-- Menuaren izenburua -->
    <h1 class="mb-5 text-center">Langile Menua</h1>
    <!-- Langilearen aukerak modu karten bidez banatuta -->
    <div class="row row-cols-1 row-cols-md-3 g-4">
      
      <!-- Card 1: Bidaia Hartu -->
      <div class="col">
        <div class="card card-hover h-100 border-primary">
          <div class="card-body text-center">
            <!-- Karta titulua -->
            <h5 class="card-title">Bidaia Hartu</h5>
            <!-- Karta testua -->
            <p class="card-text">
              Aukeratu eskuragarri dauden bidaiak eta hartu zure hurrengo zerbitzua. Hemen ikusiko dituzu esleitu gabe dauden eskaerak.
            </p>
          </div>
          <div class="card-footer text-center">
            <!-- Bidaia hartzeko esteka -->
            <a href="bidaiahartu.php" class="btn btn-primary">Hartu bidaia</a>
          </div>
        </div>
      </div>
      
      <!-- Card 2: Bidaia Amaitu -->
      <div class="col">
        <div class="card card-hover h-100 border-warning">
          <div class="card-body text-center">
            <!-- Karta titulua -->
            <h5 class="card-title">Bidaia Amaitu</h5>
            <!-- Karta testua -->
            <p class="card-text">
              Zure bidaia amaitu ondoren, hemen erregistratu ahal izango duzu zerbitzuaren bukaera. Bidaiaren xehetasunak eguneratu eta ordaintza kudeatu.
            </p>
          </div>
          <div class="card-footer text-center">
            <!-- Bidaia bukatzeko esteka -->
            <a href="bidaiabukatu.php" class="btn btn-warning">Amaitu bidaia</a>
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
              Zure aurreko bidaiak kontsultatu eta zure zerbitzuei buruzko erregistroak ikusi. Informazio xehea zure historiako zerbitzu guztiei buruz.
            </p>
          </div>
          <div class="card-footer text-center">
            <!-- Historiala ikusteko esteka -->
            <a href="historialagidari.php" class="btn btn-success">Ikusi historial</a>
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
