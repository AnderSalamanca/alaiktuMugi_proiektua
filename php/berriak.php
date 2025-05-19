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
  $activePage = 'news';
  include 'navbar.php'; 
  ?>

  <div class="container my-5">
    <h1 class="mb-4 text-center">Alaiktumugi-ren Albisteak eta Berriak</h1>
    
    <!-- Albiste 1 -->
    <div class="card mb-4">
      <div class="card-body">
        <h5 class="card-title">Aplikazio berria aurkeztua</h5>
        <p class="card-text">
          Alaiktumugi-k bere bezeroei berritzaile asko eskaintzeko asmoz aplikazio berria aurkeztu du.
          Aplikazio honek erreserba azkarrak, ordainketa seguruak eta erabiltzaile esperientzia hobetzen lagunduko dizkio, interfaze moderno eta intuitibo baten bidez.
          Erabiltzaileek bidaia egiteko aukera errazagoa eta informazio zuzen eta denbora errealean jasotuko dute, ondorioz zerbitzu fidagarriak eta erosoak eskuratu nahian.
          Teknologia berrien erabilera agerian uzten du aplikazio honek, etengabeko eguneratzeekin eta bezeroen iritzi positiboekin, gure zerbitzuetan berrikuntza etengabea bermatzeko.
        </p>
      </div>
    </div>
    
    <!-- Albiste 2 -->
    <div class="card mb-4">
      <div class="card-body">
        <h5 class="card-title">Aurkeztu gure Flota Ekoloxikoa</h5>
        <p class="card-text">
          Alaiktumugi-k iraunkortasun apustua indartzeko bere flota ekoloxikoa aurkeztu du.
          Vehikel berri hauek, hibrido eta elektriko teknologiari esker, karbono aztarna gutxiago uzteko diseinatuta daude eta bidaia ekonomiko eta erosoagoak eskaintzen dituzte.
          Horrela, bezeroek modu egokian ingurumenarekiko errespetua izateko aukera izango dute, eta gure zerbitzuak ingurunearen jasangarritasuna babesteko apustua indartzen du.
          Honek, gainera, komunitatearen eta ingurumenaren arteko konpromiso sendoa islatzen du gure enpresaren filosofiatan.
        </p>
      </div>
    </div>
    
    <!-- Albiste 3 -->
    <div class="card mb-4">
      <div class="card-body">
        <h5 class="card-title">Fidelizazio Programa Aurkeztua</h5>
        <p class="card-text">
          Gure bezero leialentzako baliabide berria aurkeztu dugu, eta hori gure zerbitzu pertsonalizatua eta kalitate handikoa indartzeko helburua du.
          Programa honek bezeroei sari bereziak, deskontuak eta promozio esklusiboak eskaintzen dizkie, era digitaleko erlazioetan oinarrituta.
          Bezeroek esperientzia pertsonalizatua oso zabalduko dute eta gure enpresaren eta bezeroen arteko konfiantza eta konektibitatea sendotzeko aukera ematen du.
          Helburua ez da soilik bidaia errazak eskaintzea, baizik eta bezeroen arteko harreman sendoa eraikitzea eta etorkizun iraunkorra bermatzea.
        </p>
      </div>
    </div>
  
  </div>

  <footer>
    <?php include 'footer.php'; ?>
  </footer>

  <!-- Bootstrap JS Bundle (incluye Popper) -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
