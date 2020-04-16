-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 15 أبريل 2020 الساعة 21:43
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `asterisklocation`
--

-- --------------------------------------------------------

--
-- بنية الجدول `client`
--

CREATE TABLE IF NOT EXISTS `client` (
  `codeClient` int(11) NOT NULL AUTO_INCREMENT,
  `nomComplet` varchar(255) NOT NULL,
  `adresse` varchar(255) NOT NULL,
  `numGsm` int(11) NOT NULL,
  `uriImage` varchar(255) NOT NULL,
  PRIMARY KEY (`codeClient`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- بنية الجدول `contrat`
--

CREATE TABLE IF NOT EXISTS `contrat` (
  `NContrat` int(11) NOT NULL AUTO_INCREMENT,
  `dateContrat` date NOT NULL,
  `dateEchéance` date NOT NULL,
  `idReservation` int(11) NOT NULL,
  PRIMARY KEY (`NContrat`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- بنية الجدول `facture`
--

CREATE TABLE IF NOT EXISTS `facture` (
  `NFacture` int(11) NOT NULL AUTO_INCREMENT,
  `dateFacture` date NOT NULL,
  `MontantAPayer` double NOT NULL,
  `idContrat` int(11) NOT NULL,
  PRIMARY KEY (`NFacture`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- بنية الجدول `parking`
--

CREATE TABLE IF NOT EXISTS `parking` (
  `NParking` int(11) NOT NULL AUTO_INCREMENT,
  `capacite` int(11) NOT NULL,
  `rue` int(11) NOT NULL,
  `arrondissement` int(11) NOT NULL,
  `nbrPlacesOccupees` int(11) NOT NULL,
  PRIMARY KEY (`NParking`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- بنية الجدول `reservation`
--

CREATE TABLE IF NOT EXISTS `reservation` (
  `codeReservation` int(11) NOT NULL AUTO_INCREMENT,
  `dateDepart` date NOT NULL,
  `dateRetour` date NOT NULL,
  `idClient` int(11) NOT NULL,
  PRIMARY KEY (`codeReservation`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- بنية الجدول `sanction`
--

CREATE TABLE IF NOT EXISTS `sanction` (
  `nbrJoursRetards` int(11) NOT NULL,
  `idContrat` int(11) NOT NULL,
  `idSanction` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idSanction`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- بنية الجدول `utilisateur`
--

CREATE TABLE IF NOT EXISTS `utilisateur` (
  `codeUtilisateur` int(11) NOT NULL AUTO_INCREMENT,
  `nomComplet` varchar(255) NOT NULL,
  `adresse` varchar(255) NOT NULL,
  `numGsm` int(11) NOT NULL,
  `uriImage` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`codeUtilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- بنية الجدول `vehicule`
--

CREATE TABLE IF NOT EXISTS `vehicule` (
  `NImmatriculation` int(11) NOT NULL AUTO_INCREMENT,
  `marque` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `carburant` varchar(255) NOT NULL,
  `compteurKm` double NOT NULL,
  `dateMiseEnCirculation` date NOT NULL,
  `idParking` int(11) NOT NULL,
  PRIMARY KEY (`NImmatriculation`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
