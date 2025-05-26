<?php

if (session_status() == PHP_SESSION_NONE) {
    session_start(); // Inicia la sesión si no está activa
}

// $activePage aldagaia definitzen ez bada, balio huts bat ezartzen zaio (pertsonalizatu daiteke)
if (!isset($activePage)) {
  $activePage = '';
}
?>
<!-- Bootstrap CSS sartzen dugu (nabarra behar bezala estilatzeko) -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Nabigazio barra -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <!-- Logo ezkerrean -->
    <a class="navbar-brand" href="main.php">
      <img src="irudiak/logoa.png" alt="Logo" class="d-inline-block align-text-top" style="max-height: 40px;">
    </a>
    <!-- Mugikorrentzako togglear botoia -->
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <!-- Nabigazio menuaren edukia -->
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <!-- Menu nagusia -->
        <li class="nav-item">
          <a class="nav-link <?php echo ($activePage == 'main') ? 'active' : ''; ?>" aria-current="page" href="main.php">Hasiera</a>
        </li>
        <li class="nav-item">
          <a class="nav-link <?php echo ($activePage == 'news') ? 'active' : ''; ?>" href="berriak.php">Berriak</a>
        </li>
        <li class="nav-item">
          <a class="nav-link <?php echo ($activePage == 'contact') ? 'active' : ''; ?>" href="kontaktua.php">Kontaktua</a>
        </li>
        <li class="nav-item">
          <a class="nav-link <?php echo ($activePage == 'about') ? 'active' : ''; ?>" href="guriburuz.php">Guri buruz</a>
        </li>
        <?php if (isset($_SESSION['erabiltzailea'])): ?>
          <!-- Erabiltzailea taxista bada, taxista menu erakusten da -->
          <?php if (isset($_SESSION['rola']) && $_SESSION['rola'] === 'taxista'): ?>
            <li class="nav-item">
              <a class="nav-link <?php echo ($activePage == 'taxista') ? 'active' : ''; ?>" href="taxistamenua.php">Taxista Menua</a>
            </li>
            <!-- Bestela, bezero menu erakusten da -->
          <?php else: ?>
            <li class="nav-item">
              <a class="nav-link <?php echo ($activePage == 'bezero') ? 'active' : ''; ?>" href="bezeromenua.php">Bezero Menua</a>
            </li>
          <?php endif; ?>
        <?php endif; ?>
      </ul>
      <!-- Saioa hasi edo saioa itxi botoia, erabiltzailearen egoeraren arabera -->
      <?php if (isset($_SESSION['erabiltzailea'])): ?>
        <button class="btn btn-outline-light" onclick="window.location.href='logout.php';">Saioa Itxi</button>
      <?php else: ?>
        <button class="btn btn-outline-light" onclick="window.location.href='login.php';">Saioa hasi</button>
      <?php endif; ?>
    </div>
  </div>
</nav>

<!-- Bootstrap JS Bundle (Popper barne) kargatzen dugu, nabarra eta bestelako interakzioak ondo funtzionatzeko -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>