-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : ven. 12 juin 2020 à 17:46
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
  `numGsm` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`codeClient`, `nomComplet`, `adresse`, `numGsm`) VALUES
(2, 'yassine', 'yassine adresse', 3111),
(3, 'oussama', 'oussama adresse', 345345);

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

--
-- Déchargement des données de la table `contrat`
--

INSERT INTO `contrat` (`NContrat`, `dateContrat`, `dateEchéance`, `idReservation`) VALUES
(8, '2020-06-05', '2020-06-05', 14),
(10, '2020-06-02', '2020-06-02', 17);

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

--
-- Déchargement des données de la table `facture`
--

INSERT INTO `facture` (`NFacture`, `dateFacture`, `MontantAPayer`, `idContrat`) VALUES
(12, '2020-06-20', 2500, 8),
(14, '2020-06-05', 500, 10);

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
(2, 23, 'Rue de la Jeunesse', 'Secteur D', 0),
(3, 4, 'Rue Yacoub El Mansour', 'Secteur B', 2),
(6, 20, 'Rue Mohammed V', 'Secteur 12', 1);

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `codeReservation` int(11) NOT NULL,
  `dateReservation` date NOT NULL,
  `dateDepart` date NOT NULL,
  `dateRetour` date NOT NULL,
  `idClient` int(11) NOT NULL,
  `idVehicule` int(11) NOT NULL,
  `etatReservation` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`codeReservation`, `dateReservation`, `dateDepart`, `dateRetour`, `idClient`, `idVehicule`, `etatReservation`) VALUES
(14, '2020-06-02', '2020-06-09', '2020-06-14', 2, 23240, 'validé'),
(15, '2020-06-01', '2020-06-04', '2020-06-12', 2, 123, 'annuler'),
(17, '2020-06-07', '2020-06-10', '2020-06-11', 3, 23240, 'validé');

-- --------------------------------------------------------

--
-- Structure de la table `sanction`
--

CREATE TABLE `sanction` (
  `nbrJoursRetard` int(11) NOT NULL,
  `idContrat` int(11) NOT NULL,
  `idSanction` int(11) NOT NULL,
  `montantAPayer` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `sanction`
--

INSERT INTO `sanction` (`nbrJoursRetard`, `idContrat`, `idSanction`, `montantAPayer`) VALUES
(2, 8, 12, 4000),
(5, 10, 16, 10000);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `codeUtilisateur` int(11) NOT NULL,
  `nomComplet` varchar(255) NOT NULL,
  `adresse` varchar(255) NOT NULL,
  `numGsm` int(11) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`codeUtilisateur`, `nomComplet`, `adresse`, `numGsm`, `password`, `email`) VALUES
(1, 'admin', 'admin adress', 5344535, 'admin', 'admin@asterisk.com'),
(2, 'adams', 'Adams adresse', 64323243, 'adams', 'adams@asterisk.com'),
(3, 'oussama', 'adresse', 534534, 'oussama', 'oussama@asterisk.com'),
(17, 'yassine', 'tikiouine', 975382, 'yassine', 'yassine@asterisk.com');

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
  `disponibilite` tinyint(1) NOT NULL,
  `prix` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `vehicule`
--

INSERT INTO `vehicule` (`NImmatriculation`, `marque`, `type`, `carburant`, `compteurKm`, `dateMiseEnCirculation`, `idParking`, `disponibilite`, `prix`) VALUES
(123, 'Alfa Romeo', 'Stelvio', 'Diesel', 23, '2020-05-16', 1, 0, 1000),
(23237, 'Renault', 'Dacia', 'Essence', 123, '2020-04-18', 3, 1, 2000),
(23240, 'Renault', 'Megane', 'Diesel', 34, '2020-05-02', 6, 1, 500),
(23241, 'Renault', 'Clio', 'Diesel', 231, '2020-05-02', 3, 1, 700);

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
  ADD PRIMARY KEY (`NContrat`),
  ADD KEY `idReservation` (`idReservation`);

--
-- Index pour la table `facture`
--
ALTER TABLE `facture`
  ADD PRIMARY KEY (`NFacture`),
  ADD KEY `idContrat` (`idContrat`);

--
-- Index pour la table `parking`
--
ALTER TABLE `parking`
  ADD PRIMARY KEY (`NParking`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`codeReservation`),
  ADD KEY `idClient` (`idClient`),
  ADD KEY `idVehicule` (`idVehicule`);

--
-- Index pour la table `sanction`
--
ALTER TABLE `sanction`
  ADD PRIMARY KEY (`idSanction`),
  ADD KEY `idContrat` (`idContrat`);

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
  MODIFY `codeClient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `contrat`
--
ALTER TABLE `contrat`
  MODIFY `NContrat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `facture`
--
ALTER TABLE `facture`
  MODIFY `NFacture` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT pour la table `parking`
--
ALTER TABLE `parking`
  MODIFY `NParking` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `codeReservation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `sanction`
--
ALTER TABLE `sanction`
  MODIFY `idSanction` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `codeUtilisateur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `contrat`
--
ALTER TABLE `contrat`
  ADD CONSTRAINT `contrat_ibfk_1` FOREIGN KEY (`idReservation`) REFERENCES `reservation` (`codeReservation`);

--
-- Contraintes pour la table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `facture_ibfk_1` FOREIGN KEY (`idContrat`) REFERENCES `contrat` (`NContrat`);

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`idClient`) REFERENCES `client` (`codeClient`),
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`idVehicule`) REFERENCES `vehicule` (`NImmatriculation`);

--
-- Contraintes pour la table `sanction`
--
ALTER TABLE `sanction`
  ADD CONSTRAINT `sanction_ibfk_1` FOREIGN KEY (`idContrat`) REFERENCES `contrat` (`NContrat`);

--
-- Contraintes pour la table `vehicule`
--
ALTER TABLE `vehicule`
  ADD CONSTRAINT `vehicule_ibfk_1` FOREIGN KEY (`idParking`) REFERENCES `parking` (`NParking`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
