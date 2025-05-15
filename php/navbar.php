<ul>
  <!-- Imagen de logo a la izquierda -->
  <li><img src="irudiak/logoa.png" alt="Logo" class="logo"></li>
  
  <!-- Enlaces del menú -->
  <li><a class="active" href="#home">Hasiera</a></li>
  <li><a href="#news">Berriak</a></li>
  <li><a href="#contact">Kontaktua</a></li>
  <li><a href="#about">Guri buruz</a></li>
  
  <!-- Botón a la derecha -->
   <?php
   if(isset($_SESSION['erabiltzailea'])){
    ?>

    <li class="login-button"><button class="button-right" onclick="window.location.href='login.php';">Saioa Itxi</button></li>
  
  <?php
   } else{
    ?>

    <li class="login-button"><button class="button-right" onclick="window.location.href='login.php';">Saioa hasi</button></li>
  
  <?php
  }
  ?>
</ul>