<?php
session_start();
// Si no se ha definido $activePage, se le asigna un valor vacío (puedes cambiarlo si lo prefieres)
if(!isset($activePage)) {
    $activePage = '';
}
?>
<!-- Incluye Bootstrap CSS en el head (puede ir en tu archivo de encabezado común) -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <!-- Logo a la izquierda -->
    <a class="navbar-brand" href="main.php">
      <img src="irudiak/logoa.png" alt="Logo" class="d-inline-block align-text-top" style="max-height: 40px;">
    </a>
    <!-- Botón para togglear en dispositivos pequeños -->
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" 
      aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <!-- Menú de enlaces -->
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link <?php echo ($activePage == 'main') ? 'active' : ''; ?>" aria-current="page" href="main.php">Hasiera</a>
        </li>
        <li class="nav-item">
          <a class="nav-link <?php echo ($activePage == 'news') ? 'active' : ''; ?>" href="#news">Berriak</a>
        </li>
        <li class="nav-item">
          <a class="nav-link <?php echo ($activePage == 'contact') ? 'active' : ''; ?>" href="kontaktua.php">Kontaktua</a>
        </li>
        <li class="nav-item">
          <a class="nav-link <?php echo ($activePage == 'about') ? 'active' : ''; ?>" href="guriburuz.php">Guri buruz</a>
        </li>
      </ul>
      <!-- Botón de login o logout a la derecha -->
      <?php if(isset($_SESSION['erabiltzailea'])): ?>
        <button class="btn btn-outline-light" onclick="window.location.href='logout.php';">Saioa Itxi</button>
      <?php else: ?>
        <button class="btn btn-outline-light" onclick="window.location.href='login.php';">Saioa hasi</button>
      <?php endif; ?>
    </div>
  </div>
</nav>

<!-- Incluye Bootstrap JS Bundle (incluye Popper) antes del cierre de body -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
