-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : sam. 02 mai 2020 à 03:36
-- Version du serveur :  10.4.11-MariaDB
-- Version de PHP : 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `asterisklocation`
--

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `codeClient` int(11) NOT NULL,
  `nomComplet` varchar(255) NOT NULL,
  `adresse` varchar(255) NOT NULL,
  `numGsm` int(11) NOT NULL,
  `uriImage` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`codeClient`, `nomComplet`, `adresse`, `numGsm`, `uriImage`) VALUES
(2, 'yassine', 'yassine adresse', 3111, ''),
(3, 'oussama', 'oussama adresse', 345345, '');

-- --------------------------------------------------------

--
-- Structure de la table `contrat`
--

CREATE TABLE `contrat` (
  `NContrat` int(11) NOT NULL,
  `dateContrat` date NOT NULL,
  `dateEchéance` date NOT NULL,
  `idReservation` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `facture`
--

CREATE TABLE `facture` (
  `NFacture` int(11) NOT NULL,
  `dateFacture` date NOT NULL,
  `MontantAPayer` double NOT NULL,
  `idContrat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `parking`
--

CREATE TABLE `parking` (
  `NParking` int(11) NOT NULL,
  `capacite` int(11) NOT NULL,
  `rue` varchar(255) NOT NULL,
  `arrondissement` varchar(255) NOT NULL,
  `nbrPlacesOccupees` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `parking`
--

INSERT INTO `parking` (`NParking`, `capacite`, `rue`, `arrondissement`, `nbrPlacesOccupees`) VALUES
(1, 34, 'Rue Ibn Batouta', 'Secteur Z', 1),
(2, 23, 'Rue de la Jeunesse', 'Secteur D', 2),
(3, 2, 'Rue Yacoub El Mansour', 'Secteur B', 1),
(4, 30, 'Rue Hassane 2', 'Secteur A', 1);

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `codeReservation` int(11) NOT NULL,
  `dateDepart` date NOT NULL,
  `dateRetour` date NOT NULL,
  `idClient` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `sanction`
--

CREATE TABLE `sanction` (
  `nbrJoursRetards` int(11) NOT NULL,
  `idContrat` int(11) NOT NULL,
  `idSanction` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `codeUtilisateur` int(11) NOT NULL,
  `nomComplet` varchar(255) NOT NULL,
  `adresse` varchar(255) NOT NULL,
  `numGsm` int(11) NOT NULL,
  `uriImage` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`codeUtilisateur`, `nomComplet`, `adresse`, `numGsm`, `uriImage`, `password`, `email`) VALUES
(1, 'admin', 'admin adress', 5344535, 'admin pic', 'admin', 'admin@asterisk.com'),
(2, 'adams', 'Adams adresse', 64323243, 'Adams image', 'adams', 'adams@asterisk.com'),
(3, 'oussama', 'adresse', 534534, '', 'oussama', 'oussama@asterisk.com'),
(17, 'yassine', 'tikiouine', 975382, '', 'yassine', 'yassine@asterisk.com');

-- --------------------------------------------------------

--
-- Structure de la table `vehicule`
--

CREATE TABLE `vehicule` (
  `NImmatriculation` int(11) NOT NULL,
  `marque` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `carburant` varchar(255) NOT NULL,
  `compteurKm` double NOT NULL,
  `dateMiseEnCirculation` date NOT NULL,
  `idParking` int(11) NOT NULL,
  `disponibilite` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `vehicule`
--

INSERT INTO `vehicule` (`NImmatriculation`, `marque`, `type`, `carburant`, `compteurKm`, `dateMiseEnCirculation`, `idParking`, `disponibilite`) VALUES
(123, 'Alfa Romeo', 'Stelvio', 'Diesel', 23, '2020-05-16', 1, 1),
(23232, 'ford', 'focus', 'diesel', 232, '2020-04-08', 4, 1),
(23237, 'dacia', 'dacia', 'Essence', 123, '2020-04-18', 3, 0),
(23240, 'Renault', 'Megane', 'Diesel', 34, '2020-05-02', 3, 0),
(23241, 'Renault', 'Clio', 'Diesel', 231, '2020-05-02', 2, 0);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`codeClient`);

--
-- Index pour la table `contrat`
--
ALTER TABLE `contrat`
  ADD PRIMARY KEY (`NContrat`);

--
-- Index pour la table `facture`
--
ALTER TABLE `facture`
  ADD PRIMARY KEY (`NFacture`);

--
-- Index pour la table `parking`
--
ALTER TABLE `parking`
  ADD PRIMARY KEY (`NParking`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`codeReservation`);

--
-- Index pour la table `sanction`
--
ALTER TABLE `sanction`
  ADD PRIMARY KEY (`idSanction`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`codeUtilisateur`);

--
-- Index pour la table `vehicule`
--
ALTER TABLE `vehicule`
  ADD PRIMARY KEY (`NImmatriculation`),
  ADD KEY `idParking` (`idParking`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `codeClient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `contrat`
--
ALTER TABLE `contrat`
  MODIFY `NContrat` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `facture`
--
ALTER TABLE `facture`
  MODIFY `NFacture` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `parking`
--
ALTER TABLE `parking`
  MODIFY `NParking` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `codeReservation` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `sanction`
--
ALTER TABLE `sanction`
  MODIFY `idSanction` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `codeUtilisateur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `vehicule`
--
ALTER TABLE `vehicule`
  ADD CONSTRAINT `vehicule_ibfk_1` FOREIGN KEY (`idParking`) REFERENCES `parking` (`NParking`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
