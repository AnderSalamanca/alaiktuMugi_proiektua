<?php
    // Datu-basearen konexioaren xehetasunak definitzen dira
    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "alaiktumugi";
    $port = 3307;
    
    // Konexio berria sortzen da
    $conn = new mysqli($servername, $username, $password, $dbname, $port);
    
    // Konexioak errorea badu, mezu bat bistaratuko da
    if ($conn->connect_error) {
      die("Konexioa txarra izan da: " . $conn->connect_error);
    }