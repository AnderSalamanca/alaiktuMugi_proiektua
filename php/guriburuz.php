<!DOCTYPE html>
<html lang="eu">

<head>
  <meta charset="UTF-8">
  <title>Guri buruz</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Bootstrap CSS: Orriaren diseinua eta estilo orokorrak kargatzen ditu -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Font Awesome CSS: Ikonoak erakusteko modu erraza eskaintzen du -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>

  <!-- Navbar: Nabigazio barra sartzen dugu orriaren goialdean -->
  <?php
  $activePage = 'about';
  include 'navbar.php';
  ?>

  <!-- Eduki nagusia: "Guri buruz" orria -->
  <div class="container my-5">
    <!-- Orriaren izenburua -->
    <h1 class="mb-4">Guri buruz</h1>

    <!-- Aurkezpen testua: Enpresaren eta zerbitzuaren aurkezpena -->
    <p>
      Ongi etorri <strong>Alaiktumugi</strong>-ra, Guipuzkoan eragiten duen mugikortasun soluzio moderno eta fidagarri bat. Berrikuntza eta teknologia bezalako balioetan oinarrituta, bidaiariak eta gidari profesionalak modu azkar eta seguruan konektatzen ditugu.
    </p>

    <!-- "Gure Misioa" atala -->
    <h2 class="mt-4">Gure Misioa</h2>
    <p>
      Alaiktumugi-ren misioa Guipuzkoan mugikortasuna eraldatzea da, erreskuratuz eraginkortasun, atsegintasun eta segurtasun handiko garraio zerbitzu bat eskaintzea. Gure helburua egunero gure erabiltzaileen esperientzia hobetzea da, plataforma intuitibo, teknologikoa eta betiko segurtasunarekiko konprometitua eskainiz.
    </p>

    <!-- "Gure Ikuspegia" atala -->
    <h2 class="mt-4">Gure Ikuspegia</h2>
    <p>
      Gure ikuspegia ez da soilik Guipuzkoan, baizik eta Euskal Herri osoan mugikortasun teknologikoaren erreferentzia izatea. Iraunkortasunari, berrikuntzari eta zerbitzuaren kalitateari buruzko konpromisoa dugu, gure flota handituz eta ibilbideak optimizatzen inguruneko eraina murrizteko.
    </p>

    <!-- "Nor Gara Gu?" atala -->
    <h2 class="mt-4">Nor Gara Gu?</h2>
    <p>
      Alaiktumugi teknologia eta mugikortasunarekiko pasioa duten enpleguen talde baten esker sortu zen. Ikuspegi berritzaile eta konprometitua duena, teknologia eraginkortasuna eta gizarte – ingurumen konpromiso sakona uztartzen ditugu, garraio esperientzia berezi eta paregabea sortuz.
    </p>

    <!-- "Gure Balioak" atala -->
    <h2 class="mt-4">Gure Balioak</h2>
    <ul>
      <li><strong>Berrikuntza:</strong> Gure zerbitzuak etengabe hobetzeko eta optimizatzeko ideia berriak bilatzen ditugu.</li>
      <li><strong>Segurtasuna:</strong> Bidaiarien eta gidarien segurtasuna eta erosotasuna lehenesten ditugu.</li>
      <li><strong>Konpromisoa:</strong> Tokiko komunitatearekin eta ingurunearen aldeko ardura erantsita parte hartzen dugu.</li>
      <li><strong>Kalitatea:</strong> Bidai bakoitza kalitatezko esperientzia izatea bermatzen dugu.</li>
    </ul>

    <!-- "Gure Zerbitzuak" atala -->
    <h2 class="mt-4">Gure Zerbitzuak</h2>
    <p>
      Prestatu ditugu pertsonal eta enpresentzako bidai azkar eta seguruak eskaintzeko. Era berean, mugikortasun mugatu duen pertsonentzat eta korporatibo zerbitzuetarako ere aukerak ditugu. Gure plataforma bidez erreserba egin eta ibilbide bakoitza denbora errealean jarraitu daiteke, eraginkortasuna eta lasaitasuna bermatuz.
    </p>

    <p>
      Alaiktumugi-ren ustez, teknologiak gure mugimendu modua eraldatu dezake. Eskerrik asko guregan fidatzeagatik eta zure helmugarira modu seguruan, eroso eta fidagarrian eramango zaitugulako!
    </p>
  </div>

  <!-- Footer: Orriaren beheko atal -->
  <footer class="bg-dark text-light py-4">
    <div class="container text-center">
      <!-- Copyright mezua -->
      <p class="mb-0">&copy; 2025 Alaiktumugi. Eskubide guztiak erreserbatuta.</p>
    </div>
  </footer>

  <!-- Bootstrap JS Bundle with Popper: Interakzioak eta JS funtzioak kargatzen ditu -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>