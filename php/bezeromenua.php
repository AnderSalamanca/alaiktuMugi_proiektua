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
    $activePage = 'bezero';
    include 'navbar.php'; 
  ?>

  <div class="container my-5">
    <h1 class="mb-5 text-center">Bezero Menua</h1>
    <div class="row row-cols-1 row-cols-md-3 g-4">
      
      <!-- Card 1: Bidaia Eskatu -->
      <div class="col">
        <div class="card card-hover h-100 border-primary">
          <div class="card-body text-center">
            <h5 class="card-title">Bidaia Eskatu</h5>
            <p class="card-text">
              Eskatu bidaia azkar, eroso eta seguru bat. Zure kokapenean eta helburuan oinarrituta, gure sistema aurreratua zure bila egongo da.
              Zure bidaia antolatzea eta operazio errazak egitea helburu duguna, zerbitzu on baten funtsezko elementua da.
            </p>
          </div>
          <div class="card-footer text-center">
            <a href="bidaiaeskatu.php" class="btn btn-primary">Eskatu orain</a>
          </div>
        </div>
      </div>
      
      <!-- Card 2: Bidaien Egoera -->
      <div class="col">
        <div class="card card-hover h-100 border-warning">
          <div class="card-body text-center">
            <h5 class="card-title">Bidaien Egoera</h5>
            <p class="card-text">
              Jarraitu zure bidaien egoera denbora errealean. Informazio zehatza eta eguneratua jasoko duzu, estekatutako txostenekin eta zehaztapenekin.
              Horrela, eskaeraren prozesua eta sentimenduak baloratzea ahalbidetzen duzu modu eraginkorrean.
            </p>
          </div>
          <div class="card-footer text-center">
            <a href="bidaiaegoera.php" class="btn btn-warning">Egoera ikusi</a>
          </div>
        </div>
      </div>
      
      <!-- Card 3: Bidaien Historiala -->
      <div class="col">
        <div class="card card-hover h-100 border-success">
          <div class="card-body text-center">
            <h5 class="card-title">Historiala</h5>
            <p class="card-text">
              Ikusi eta kudeatu zure bidaiak aurretik egindakoa. Informazio xehetasun zabala eta analisi erabilgarriak eskura ditzakezu, 
              zure erabilera historikoa eta gogoko momentuak berrizorki bizitzeko moduan.
            </p>
          </div>
          <div class="card-footer text-center">
            <a href="historiala.php" class="btn btn-success">Historiala ikusi</a>
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
