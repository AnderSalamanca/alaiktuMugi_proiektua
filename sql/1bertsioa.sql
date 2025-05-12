-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: alaiktumugi
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administraria`
--

DROP TABLE IF EXISTS `administraria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administraria` (
  `ordutegia` varchar(45) NOT NULL,
  `langilea_idlangilea` int(11) NOT NULL,
  PRIMARY KEY (`langilea_idlangilea`),
  CONSTRAINT `fk_administraria_langilea1` FOREIGN KEY (`langilea_idlangilea`) REFERENCES `langilea` (`idlangilea`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administraria`
--

LOCK TABLES `administraria` WRITE;
/*!40000 ALTER TABLE `administraria` DISABLE KEYS */;
INSERT INTO `administraria` VALUES ('09:00-17:00',1);
/*!40000 ALTER TABLE `administraria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bezeroa`
--

DROP TABLE IF EXISTS `bezeroa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bezeroa` (
  `idbezeroa` int(11) NOT NULL,
  `izena` varchar(45) NOT NULL,
  `abizena` varchar(45) NOT NULL,
  `erabiltzailea_iderabiltzailea` int(11) NOT NULL,
  PRIMARY KEY (`idbezeroa`),
  KEY `fk_bezeroa_erabiltzailea1_idx` (`erabiltzailea_iderabiltzailea`),
  CONSTRAINT `fk_bezeroa_erabiltzailea1` FOREIGN KEY (`erabiltzailea_iderabiltzailea`) REFERENCES `erabiltzailea` (`iderabiltzailea`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bezeroa`
--

LOCK TABLES `bezeroa` WRITE;
/*!40000 ALTER TABLE `bezeroa` DISABLE KEYS */;
INSERT INTO `bezeroa` VALUES (1,'Aitor','López',3);
/*!40000 ALTER TABLE `bezeroa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bidaiak`
--

DROP TABLE IF EXISTS `bidaiak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bidaiak` (
  `idbidaiak` int(11) NOT NULL,
  `data` date NOT NULL,
  `egoera` varchar(45) NOT NULL,
  `hasiera_kokapena` varchar(45) NOT NULL,
  `helmuga_kokapena` varchar(45) NOT NULL,
  `hasiera_ordua` varchar(45) DEFAULT NULL,
  `bukaera_ordua` varchar(45) DEFAULT NULL,
  `bezeroa_idbezeroa` int(11) NOT NULL,
  `taxista_langilea_idlangilea` int(11) DEFAULT NULL,
  PRIMARY KEY (`idbidaiak`),
  KEY `fk_bidaiak_bezeroa1_idx` (`bezeroa_idbezeroa`),
  KEY `fk_bidaiak_taxista1_idx` (`taxista_langilea_idlangilea`),
  CONSTRAINT `fk_bidaiak_bezeroa1` FOREIGN KEY (`bezeroa_idbezeroa`) REFERENCES `bezeroa` (`idbezeroa`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_bidaiak_taxista1` FOREIGN KEY (`taxista_langilea_idlangilea`) REFERENCES `taxista` (`langilea_idlangilea`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bidaiak`
--

LOCK TABLES `bidaiak` WRITE;
/*!40000 ALTER TABLE `bidaiak` DISABLE KEYS */;
INSERT INTO `bidaiak` VALUES (1,'2025-05-09','Finalizado','Donostia','Bilbao','10:00','11:00',1,2);
/*!40000 ALTER TABLE `bidaiak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erabiltzailea`
--

DROP TABLE IF EXISTS `erabiltzailea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erabiltzailea` (
  `iderabiltzailea` int(11) NOT NULL,
  `posta` varchar(45) NOT NULL,
  `pasahitza` varchar(45) NOT NULL,
  `rola` varchar(45) NOT NULL,
  PRIMARY KEY (`iderabiltzailea`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erabiltzailea`
--

LOCK TABLES `erabiltzailea` WRITE;
/*!40000 ALTER TABLE `erabiltzailea` DISABLE KEYS */;
INSERT INTO `erabiltzailea` VALUES (1,'ander3223@gmail.com','pvlbtnse','administraria'),(2,'taxista@empresa.com','taxista123','taxista'),(3,'aitor@gmail.com','alejandro123','bezeroa');
/*!40000 ALTER TABLE `erabiltzailea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historiala`
--

DROP TABLE IF EXISTS `historiala`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historiala` (
  `idhistoriala` int(11) NOT NULL,
  `hasiera_kokapena` varchar(45) NOT NULL,
  `helmuga_kokapena` varchar(45) NOT NULL,
  `data` date NOT NULL,
  `hasiera_ordua` varchar(45) DEFAULT NULL,
  `bukaera_ordua` varchar(45) DEFAULT NULL,
  `xehetasunak` varchar(45) NOT NULL,
  `idgidaria` varchar(45) DEFAULT NULL,
  `idbezeroa` varchar(45) NOT NULL,
  `bidaiak_idbidaiak` int(11) DEFAULT NULL,
  PRIMARY KEY (`idhistoriala`),
  KEY `fk_historiala_bidaiak_idx` (`bidaiak_idbidaiak`),
  CONSTRAINT `fk_historiala_bidaiak` FOREIGN KEY (`bidaiak_idbidaiak`) REFERENCES `bidaiak` (`idbidaiak`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historiala`
--

LOCK TABLES `historiala` WRITE;
/*!40000 ALTER TABLE `historiala` DISABLE KEYS */;
INSERT INTO `historiala` VALUES (1,'Donostia','Bilbao','2025-05-09','10:00','11:00','Servicio puntual y seguro','2','1',1);
/*!40000 ALTER TABLE `historiala` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `langilea`
--

DROP TABLE IF EXISTS `langilea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `langilea` (
  `idlangilea` int(11) NOT NULL,
  `izena` varchar(45) NOT NULL,
  `abizena` varchar(45) NOT NULL,
  `nan` varchar(45) NOT NULL,
  `mota` varchar(45) NOT NULL,
  `erabiltzailea_iderabiltzailea` int(11) NOT NULL,
  PRIMARY KEY (`idlangilea`),
  KEY `fk_langilea_erabiltzailea1_idx` (`erabiltzailea_iderabiltzailea`),
  CONSTRAINT `fk_langilea_erabiltzailea1` FOREIGN KEY (`erabiltzailea_iderabiltzailea`) REFERENCES `erabiltzailea` (`iderabiltzailea`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `langilea`
--

LOCK TABLES `langilea` WRITE;
/*!40000 ALTER TABLE `langilea` DISABLE KEYS */;
INSERT INTO `langilea` VALUES (1,'Ander','Salamanca','00000000A','administraria',1),(2,'Xiker','Sanchez','11111111B','taxista',2);
/*!40000 ALTER TABLE `langilea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taxista`
--

DROP TABLE IF EXISTS `taxista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `taxista` (
  `matrikula` varchar(45) NOT NULL,
  `edukiera` varchar(45) NOT NULL,
  `egoera` varchar(45) NOT NULL,
  `langilea_idlangilea` int(11) NOT NULL,
  PRIMARY KEY (`langilea_idlangilea`),
  CONSTRAINT `fk_taxista_langilea1` FOREIGN KEY (`langilea_idlangilea`) REFERENCES `langilea` (`idlangilea`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taxista`
--

LOCK TABLES `taxista` WRITE;
/*!40000 ALTER TABLE `taxista` DISABLE KEYS */;
INSERT INTO `taxista` VALUES ('LIC123456','Vehículo: Seat Ibiza','Disponible',2);
/*!40000 ALTER TABLE `taxista` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-12 11:29:40
