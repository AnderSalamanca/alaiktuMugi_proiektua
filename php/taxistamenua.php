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
  <style>
    /* Efecto hover para las cards */
    .card-hover:hover {
      transform: scale(1.02);
      transition: transform 0.3s; 
    }
  </style>
</head>
<body>
  
  <?php 
    $activePage = 'taxista';
    include 'navbar.php'; 
  ?>

  <div class="container my-5">
    <h1 class="mb-5 text-center">Taxista Menua</h1>
    <div class="row row-cols-1 row-cols-md-3 g-4">
      
      <!-- Card 1: Bidaiak Onartu -->
      <div class="col">
        <div class="card card-hover h-100 border-primary">
          <div class="card-body text-center">
            <h5 class="card-title">Bidaiak Onartu</h5>
            <p class="card-text">
              Gure sistema zurekin esleitzen dizkizu bidaiak. Bidai eskaerak denbora errealean etengabe agertzen dira eta, zure erantzun azkar eta profesionalarekin, onartu eta hasi zure bidaiak modu eraginkorrean.
            </p>
          </div>
          <div class="card-footer text-center">
            <a href="bidaiahartu.php" class="btn btn-primary">Onartu bidaiak</a>
          </div>
        </div>
      </div>
      
      <!-- Card 2: Bidaia Amaitu -->
      <div class="col">
        <div class="card card-hover h-100 border-warning">
          <div class="card-body text-center">
            <h5 class="card-title">Bidaia Amaitu</h5>
            <p class="card-text">
              Hasitako bidaiak modu profesional eta seguruan amaitzeko aukera. Amaitzeko atalean, bidaiaren zehaztapenak eguneratu eta bezeroaren iritziak jasotzen dira, zure lana erregistratuz.
            </p>
          </div>
          <div class="card-footer text-center">
            <a href="bidaiabukatu.php" class="btn btn-warning">Amaitu bidaia</a>
          </div>
        </div>
      </div>
      
      <!-- Card 3: Historiala -->
      <div class="col">
        <div class="card card-hover h-100 border-success">
          <div class="card-body text-center">
            <h5 class="card-title">Historiala</h5>
            <p class="card-text">
              Bidai guztien xehetasunak zure erakusle gisa. Historial honek zure lana eta eskaerak zehazki erregistratzen ditu, zure lana ebaluatzeko eta etengabeko hobetzeko tresna erabilgarria izan daiteke.
            </p>
          </div>
          <div class="card-footer text-center">
            <a href="historialagidari.php" class="btn btn-success">Historiala ikusi</a>
          </div>
        </div>
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
