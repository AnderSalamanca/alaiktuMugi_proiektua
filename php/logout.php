<?php
session_start(); // Saioa abiarazi

// Hainbat saio aldagaia ezabatzen dira
$_SESSION = array();

// Saioa ezabatu
session_destroy();

// main.php-ra bideratu
header("Location: main.php");
exit();
