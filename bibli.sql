-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Lun 16 Décembre 2013 à 17:17
-- Version du serveur: 5.6.12-log
-- Version de PHP: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `bibli`
--
CREATE DATABASE IF NOT EXISTS `bibli` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `bibli`;

-- --------------------------------------------------------

--
-- Structure de la table `bi_articles`
--

CREATE TABLE IF NOT EXISTS `bi_articles` (
  `ISBN` char(17) NOT NULL,
  `TypeArticle` varchar(20) NOT NULL,
  `Titre` varchar(100) NOT NULL,
  `Resume` varchar(500) NOT NULL,
  `PrixUnitaire` decimal(5,2) NOT NULL,
  `IndicateurEnCommande` char(1) NOT NULL,
  `QuantiteEnCommande` decimal(4,0) NOT NULL,
  `DateParution` datetime NOT NULL,
  `MaisonEditionID` decimal(7,0) NOT NULL,
  `Langue` varchar(2) NOT NULL DEFAULT 'FR',
  `ageMin` tinyint(3) unsigned NOT NULL COMMENT 'Indique l''âge minimum pour louer un article.',
  PRIMARY KEY (`ISBN`),
  KEY `FK_BI_Articles_TypeArticle` (`TypeArticle`),
  KEY `FK_BI_Articles_MaisonEditionID` (`MaisonEditionID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_articles`
--

INSERT INTO `bi_articles` (`ISBN`, `TypeArticle`, `Titre`, `Resume`, `PrixUnitaire`, `IndicateurEnCommande`, `QuantiteEnCommande`, `DateParution`, `MaisonEditionID`, `Langue`, `ageMin`) VALUES
('008888528111', 'JEU', 'Assassin''s Creed IV: Black Flag', 'Assassin''s Creed 4 pour XBox 360', '59.99', '0', '0', '2013-10-05 00:00:00', '2', 'FR', 0),
('978-2-12345-012-1', 'DVD', 'Harry Potter et les reliques de la mort - 2e partie', 'Dans ce dernier opus spectaculaire, le combat entre les forces du bien et du mal dans le monde de la magie s''intensifie et se transforme en guerre totale. Les enjeux n''ont jamais été aussi importants et personne n''est à l''abri. Mais c''est Harry Potter qui risque de devoir faire l''ultime sacrifice au moment de la confrontation cruciale imminente avec Lord Voldemort.', '18.99', '1', '3', '2011-11-11 00:00:00', '1', 'FR', 0),
('978-2-70964-192-0', 'LI', 'Cinquante nuances de Grey', 'Romantique, libérateur et totalement addictif, ce roman vous obsédera, vous possédera et vous marquera à jamais.   Lorsqu''Anastasia Steele, étudiante en littérature, interviewe le richissime jeune chef d''entreprise Christian Grey, elle le trouve très séduisant mais profondément intimidant. Convaincue que leur rencontre a été désastreuse, elle tente de l''oublier ? ', '16.99', '1', '5', '2012-08-02 00:00:00', '2', 'FR', 0);

-- --------------------------------------------------------

--
-- Structure de la table `bi_articlesauteurs`
--

CREATE TABLE IF NOT EXISTS `bi_articlesauteurs` (
  `AuteurID` decimal(7,0) NOT NULL,
  `ISBN` char(17) NOT NULL,
  PRIMARY KEY (`AuteurID`,`ISBN`),
  KEY `FK_ArticlesAuteurs_ISBN` (`ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_articlesauteurs`
--

INSERT INTO `bi_articlesauteurs` (`AuteurID`, `ISBN`) VALUES
('1', '008888528111'),
('2', '008888528111'),
('2', '978-2-12345-012-1'),
('1', '978-2-70964-192-0');

-- --------------------------------------------------------

--
-- Structure de la table `bi_auteurs`
--

CREATE TABLE IF NOT EXISTS `bi_auteurs` (
  `AuteurID` decimal(7,0) NOT NULL,
  `Nom` varchar(50) NOT NULL,
  `Prenom` varchar(50) NOT NULL,
  `Pays` varchar(50) NOT NULL DEFAULT 'Canada',
  `SiteInternet` varchar(100) DEFAULT NULL,
  `AnneeNaissance` char(4) NOT NULL,
  `AnneeDeces` char(4) DEFAULT NULL,
  PRIMARY KEY (`AuteurID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_auteurs`
--

INSERT INTO `bi_auteurs` (`AuteurID`, `Nom`, `Prenom`, `Pays`, `SiteInternet`, `AnneeNaissance`, `AnneeDeces`) VALUES
('1', 'James', 'Erika Leonard', 'Canada', '', '1963', ''),
('2', 'Rowling', 'J.K.', 'Angleterre', '', '1965', '');

-- --------------------------------------------------------

--
-- Structure de la table `bi_commentaires`
--

CREATE TABLE IF NOT EXISTS `bi_commentaires` (
  `CommentaireID` decimal(7,0) NOT NULL,
  `EmpruntID` decimal(7,0) NOT NULL,
  `Commentaire` varchar(250) NOT NULL,
  `datetimeCommentaire` datetime NOT NULL,
  PRIMARY KEY (`CommentaireID`),
  KEY `FK_Commentaires_EmpruntID` (`EmpruntID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_commentaires`
--

INSERT INTO `bi_commentaires` (`CommentaireID`, `EmpruntID`, `Commentaire`, `datetimeCommentaire`) VALUES
('1', '1', 'A perdu un livre.', '2012-09-15 00:00:00'),
('2', '1', 'A brisé un livre.', '2012-09-14 00:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `bi_copiesarticles`
--

CREATE TABLE IF NOT EXISTS `bi_copiesarticles` (
  `NoArticle` decimal(7,0) NOT NULL,
  `ISBN` char(17) NOT NULL,
  `IndicateurDisponible` int(11) DEFAULT NULL,
  PRIMARY KEY (`NoArticle`,`ISBN`),
  KEY `FK_CopiesArticles_ISBN` (`ISBN`),
  KEY `IndicateurDisponible` (`IndicateurDisponible`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_copiesarticles`
--

INSERT INTO `bi_copiesarticles` (`NoArticle`, `ISBN`, `IndicateurDisponible`) VALUES
('1', '978-2-70964-192-0', 1),
('2', '978-2-70964-192-0', 1),
('3', '978-2-12345-012-1', 1),
('4', '978-2-12345-012-1', 1),
('5', '008888528111', 1);

-- --------------------------------------------------------

--
-- Structure de la table `bi_emprunts`
--

CREATE TABLE IF NOT EXISTS `bi_emprunts` (
  `EmpruntID` decimal(7,0) NOT NULL,
  `NoMembre` decimal(7,0) NOT NULL,
  `NoArticle` decimal(7,0) NOT NULL,
  `dateEmprunt` datetime NOT NULL,
  `dateRetourPrevue` datetime NOT NULL,
  `dateRetour` datetime DEFAULT NULL,
  `NbJoursDeRetard` int(11) DEFAULT NULL,
  `AmendeParJour` decimal(3,2) NOT NULL,
  `IndicateurPerte` char(1) NOT NULL,
  `PrixUnitaire` decimal(5,2) NOT NULL,
  `TotalAmende` decimal(6,2) DEFAULT NULL,
  `ModePaiementCd` varchar(20) DEFAULT NULL,
  `ISBN` char(17) NOT NULL,
  PRIMARY KEY (`EmpruntID`),
  KEY `FK_Emprunts_NoMembre` (`NoMembre`),
  KEY `FK_Emprunts_NoArticle_ISBN` (`NoArticle`,`ISBN`),
  KEY `FK_Emprunts_ModePaiementCd` (`ModePaiementCd`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_emprunts`
--

INSERT INTO `bi_emprunts` (`EmpruntID`, `NoMembre`, `NoArticle`, `dateEmprunt`, `dateRetourPrevue`, `dateRetour`, `NbJoursDeRetard`, `AmendeParJour`, `IndicateurPerte`, `PrixUnitaire`, `TotalAmende`, `ModePaiementCd`, `ISBN`) VALUES
('1', '1', '2', '2012-01-01 00:00:00', '2012-01-07 00:00:00', '2012-01-10 00:00:00', NULL, '2.00', '0', '0.00', NULL, NULL, '978-2-70964-192-0'),
('2', '2', '2', '2012-01-03 00:00:00', '2012-01-04 00:00:00', '2012-01-03 00:00:00', NULL, '3.00', '1', '0.00', NULL, NULL, '978-2-70964-192-0'),
('3', '2', '1', '2012-01-03 00:00:00', '2012-01-04 00:00:00', '2012-01-03 00:00:00', NULL, '3.00', '1', '0.00', NULL, NULL, '978-2-70964-192-0'),
('4', '2', '1', '2012-09-05 00:00:00', '2012-09-09 00:00:00', '2012-09-12 00:00:00', NULL, '3.00', '1', '0.00', NULL, NULL, '978-2-70964-192-0'),
('5', '3', '4', '2012-09-05 00:00:00', '2012-09-09 00:00:00', '2012-09-12 00:00:00', NULL, '3.00', '0', '0.00', NULL, NULL, '978-2-12345-012-1'),
('6', '3', '4', '2012-09-05 00:00:00', '2012-09-09 00:00:00', '2012-09-12 00:00:00', NULL, '3.00', '0', '0.00', NULL, NULL, '978-2-12345-012-1'),
('7', '3', '3', '2012-09-05 00:00:00', '2012-09-09 00:00:00', '2012-09-12 00:00:00', NULL, '3.00', '0', '0.00', NULL, NULL, '978-2-12345-012-1'),
('8', '1', '4', '2012-09-05 00:00:00', '2012-09-09 00:00:00', '2012-09-12 00:00:00', NULL, '1.00', '1', '0.00', NULL, NULL, '978-2-12345-012-1'),
('9', '2', '3', '2012-09-05 00:00:00', '2012-09-09 00:00:00', '2012-09-12 00:00:00', NULL, '1.00', '0', '0.00', NULL, NULL, '978-2-12345-012-1'),
('10', '2', '5', '2014-09-05 00:00:00', '2014-09-09 00:00:00', '2014-09-12 00:00:00', NULL, '1.25', '0', '0.00', NULL, NULL, '008888528111');

-- --------------------------------------------------------

--
-- Structure de la table `bi_etatarticle`
--

CREATE TABLE IF NOT EXISTS `bi_etatarticle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `valeur` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Contenu de la table `bi_etatarticle`
--

INSERT INTO `bi_etatarticle` (`id`, `valeur`) VALUES
(1, 'DISPONIBLE'),
(2, 'PRÊTÉ'),
(3, 'RÉSERVÉ'),
(4, 'PERDU'),
(5, 'EN RÉPARATION');

-- --------------------------------------------------------

--
-- Structure de la table `bi_maisonseditions`
--

CREATE TABLE IF NOT EXISTS `bi_maisonseditions` (
  `MaisonEditionID` decimal(10,0) NOT NULL,
  `Nom` varchar(50) NOT NULL,
  `Adresse` varchar(200) NOT NULL,
  `Ville` varchar(50) NOT NULL DEFAULT 'Québec',
  `CdPostal` char(7) NOT NULL,
  `ProvCode` char(2) NOT NULL,
  `Pays` varchar(50) NOT NULL DEFAULT 'Canada',
  `NoTel` char(14) NOT NULL,
  `NoFax` char(14) DEFAULT NULL,
  `Email` varchar(100) NOT NULL,
  `SiteInternet` varchar(100) DEFAULT NULL,
  `Contact` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MaisonEditionID`),
  KEY `FK_MaisonsEditions_ProvCode` (`ProvCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_maisonseditions`
--

INSERT INTO `bi_maisonseditions` (`MaisonEditionID`, `Nom`, `Adresse`, `Ville`, `CdPostal`, `ProvCode`, `Pays`, `NoTel`, `NoFax`, `Email`, `SiteInternet`, `Contact`) VALUES
('1', 'Belle-oeuvre', '192 rue de la chapelle', 'Québec', 'G0S 1N0', 'QC', 'Canada', '(418) 333-4434', '(418) 454-1212', 'belleoeuvre@hotmail.com', 'www.belle-oeuvre.com', 'Gérard'),
('2', 'Belle-horizon', '99 rue des zombies', 'Québec', 'G4S 3N0', 'QC', 'Canada', '(418) 222-4344', '(418) 555-1122', 'bellehorizon@hotmail.com', 'www.belle-horizon.com', 'Roger');

-- --------------------------------------------------------

--
-- Structure de la table `bi_membres`
--

CREATE TABLE IF NOT EXISTS `bi_membres` (
  `NoMembre` decimal(7,0) NOT NULL,
  `Nom` varchar(50) NOT NULL,
  `Prenom` varchar(50) NOT NULL,
  `TypeMembre` char(1) NOT NULL,
  `Salutation` varchar(20) NOT NULL,
  `Addresse` varchar(100) NOT NULL,
  `Ville` varchar(50) NOT NULL DEFAULT 'Québec',
  `CodePostal` char(7) NOT NULL,
  `ProvCode` char(2) NOT NULL,
  `Pays` varchar(50) NOT NULL DEFAULT 'Canada',
  `NoTel` char(14) NOT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Login` varchar(255) NOT NULL,
  `MotPasse` varchar(255) NOT NULL,
  `DernierLogin` datetime DEFAULT NULL,
  `DateActivation` date NOT NULL,
  `QuestionSecrete` varchar(255) NOT NULL,
  `ReponseSecrete` varchar(255) NOT NULL,
  `EstActif` tinyint(1) NOT NULL DEFAULT '0',
  `DateNaissance` date NOT NULL,
  PRIMARY KEY (`NoMembre`),
  KEY `FK_Membres_TypeMembre` (`TypeMembre`),
  KEY `FK_Membres_ProvCode` (`ProvCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_membres`
--

INSERT INTO `bi_membres` (`NoMembre`, `Nom`, `Prenom`, `TypeMembre`, `Salutation`, `Addresse`, `Ville`, `CodePostal`, `ProvCode`, `Pays`, `NoTel`, `Email`, `Login`, `MotPasse`, `DernierLogin`, `DateActivation`, `QuestionSecrete`, `ReponseSecrete`, `EstActif`, `DateNaissance`) VALUES
('1', 'Filion', 'Jean', '1', 'M.', '123 rue des Sapins', 'Québec', 'G4S 4H8', 'QC', 'Canada', '(418) 222-6666', 'jean_filion@hotmail.com', 'jeafil', 'efe6398127928f1b2e9ef3207fb82663', '0000-00-00 00:00:00', '0000-00-00', '', '', 0, '0000-00-00'),
('2', 'Lemay', 'Nicole', '1', 'Mme.', '1 rue des Peupliers', 'Québec', 'G2D 4H6', 'QC', 'Canada', '(418) 332-4344', 'nicole_lemay@hotmail.com', '', '', '0000-00-00 00:00:00', '0000-00-00', '', '', 0, '0000-00-00'),
('3', 'Nadeau', 'Olivier', '1', 'M.', '76 rue des Pins', 'Québec', 'G1D 7J8', 'QC', 'Canada', '(418) 123-4567', 'onadeau@cegepgarneau.ca', '', '', '0000-00-00 00:00:00', '0000-00-00', '', '', 0, '0000-00-00'),
('4', 'Cégep', 'Garneau', '4', 'M.', 'Rue du Cégep', 'Québec', 'H0H0H0', 'QC', 'Canada', '555555555', 'email@email.com', 'garneau', '3fc0a7acf087f549ac2b266baf94b8b1', NULL, '2013-12-15', 'Nom du cégep', 'Garneau', 1, '2013-12-01');

-- --------------------------------------------------------

--
-- Structure de la table `bi_modespaiements`
--

CREATE TABLE IF NOT EXISTS `bi_modespaiements` (
  `ModePaiementCd` varchar(20) NOT NULL,
  `CdDescFr` varchar(50) NOT NULL,
  `CdDescEn` varchar(50) NOT NULL,
  PRIMARY KEY (`ModePaiementCd`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_modespaiements`
--

INSERT INTO `bi_modespaiements` (`ModePaiementCd`, `CdDescFr`, `CdDescEn`) VALUES
('C', 'Comptant', 'Cash'),
('V', 'Visa', 'Visa');

-- --------------------------------------------------------

--
-- Structure de la table `bi_produits`
--

CREATE TABLE IF NOT EXISTS `bi_produits` (
  `CodeProduit` decimal(7,0) NOT NULL,
  `Nom` varchar(50) NOT NULL,
  `Description` varchar(200) NOT NULL,
  `PrixUnitaire` decimal(5,2) NOT NULL,
  `IndicateurTaxable` char(1) NOT NULL,
  `QteEnInventaire` decimal(5,0) NOT NULL,
  `NiveauRuptureStock` decimal(5,0) NOT NULL,
  `QteACommander` decimal(5,0) NOT NULL,
  PRIMARY KEY (`CodeProduit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_produits`
--

INSERT INTO `bi_produits` (`CodeProduit`, `Nom`, `Description`, `PrixUnitaire`, `IndicateurTaxable`, `QteEnInventaire`, `NiveauRuptureStock`, `QteACommander`) VALUES
('1', 'Skor', 'Barre de chocolat Skor', '3.00', '1', '25', '5', '0'),
('2', 'Ruffles Crème sure et oignon', 'Chips Ruffles à saveur de crème sure et oignon', '2.00', '1', '5', '5', '15'),
('3', 'Dentyne aux fraises', 'Gomme DEntyne à saveur de fraise', '2.50', '1', '5', '5', '15');

-- --------------------------------------------------------

--
-- Structure de la table `bi_provinces`
--

CREATE TABLE IF NOT EXISTS `bi_provinces` (
  `ProvCode` char(2) NOT NULL,
  `ProvDescFr` varchar(50) NOT NULL,
  `ProvDescEn` varchar(50) NOT NULL,
  `PcTaxeProv` decimal(3,3) NOT NULL,
  `PcTaxeFed` decimal(3,3) NOT NULL,
  PRIMARY KEY (`ProvCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_provinces`
--

INSERT INTO `bi_provinces` (`ProvCode`, `ProvDescFr`, `ProvDescEn`, `PcTaxeProv`, `PcTaxeFed`) VALUES
('ON', 'Ontario', 'Ontario', '0.080', '0.070'),
('QC', 'Québec', 'Quebec', '0.095', '0.050');

-- --------------------------------------------------------

--
-- Structure de la table `bi_reservations`
--

CREATE TABLE IF NOT EXISTS `bi_reservations` (
  `IdMembre` decimal(7,0) NOT NULL,
  `IdArticle` varchar(17) NOT NULL,
  `DateReservation` date NOT NULL,
  `EstActif` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`IdMembre`,`IdArticle`,`DateReservation`),
  KEY `IdArticle` (`IdArticle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_reservations`
--

INSERT INTO `bi_reservations` (`IdMembre`, `IdArticle`, `DateReservation`, `EstActif`) VALUES
('1', '008888528111', '2013-12-16', 1),
('1', '978-2-12345-012-1', '2013-12-16', 1);

-- --------------------------------------------------------

--
-- Structure de la table `bi_typearticles`
--

CREATE TABLE IF NOT EXISTS `bi_typearticles` (
  `TypeArticle` varchar(20) NOT NULL,
  `TypeArticleDescFr` varchar(80) NOT NULL,
  `TypeArticleDescEn` varchar(80) NOT NULL,
  `AmendeParJour` decimal(3,2) NOT NULL,
  PRIMARY KEY (`TypeArticle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_typearticles`
--

INSERT INTO `bi_typearticles` (`TypeArticle`, `TypeArticleDescFr`, `TypeArticleDescEn`, `AmendeParJour`) VALUES
('BLU', 'Film Blu-ray', 'Blu-ray Movie', '1.00'),
('DVD', 'Film DVD', 'DVD Movie', '1.00'),
('JEU', 'Jeu vidéo', 'Video game', '1.25'),
('LI', 'Livre', 'Book', '0.20');

-- --------------------------------------------------------

--
-- Structure de la table `bi_typesmembres`
--

CREATE TABLE IF NOT EXISTS `bi_typesmembres` (
  `TypeMembre` char(1) NOT NULL,
  `TypeDescFr` varchar(50) NOT NULL,
  `TypeDescEn` varchar(50) NOT NULL,
  `NbJoursSurEmprunt` decimal(2,0) NOT NULL,
  PRIMARY KEY (`TypeMembre`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_typesmembres`
--

INSERT INTO `bi_typesmembres` (`TypeMembre`, `TypeDescFr`, `TypeDescEn`, `NbJoursSurEmprunt`) VALUES
('1', 'Résident', 'Resident', '7'),
('2', 'Entreprise', 'Entreprise', '15'),
('3', 'Étudiant', 'Student', '15'),
('4', 'Employé', 'Employee', '7');

-- --------------------------------------------------------

--
-- Structure de la table `bi_ventes`
--

CREATE TABLE IF NOT EXISTS `bi_ventes` (
  `VenteID` decimal(7,0) NOT NULL,
  `NoMembre` decimal(7,0) NOT NULL,
  `ModePaiementCd` varchar(20) NOT NULL,
  `dateVente` datetime NOT NULL,
  `TotalVente` decimal(5,2) NOT NULL,
  `TaxeProvCourante` decimal(8,2) DEFAULT NULL,
  `TaxeFedCourante` decimal(8,2) DEFAULT NULL,
  `TotalTaxes` decimal(8,2) DEFAULT NULL,
  `GrandTotalVente` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`VenteID`),
  KEY `FK_Ventes_NoMembre` (`NoMembre`),
  KEY `FK_Ventes_ModePaiementCd` (`ModePaiementCd`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_ventes`
--

INSERT INTO `bi_ventes` (`VenteID`, `NoMembre`, `ModePaiementCd`, `dateVente`, `TotalVente`, `TaxeProvCourante`, `TaxeFedCourante`, `TotalTaxes`, `GrandTotalVente`) VALUES
('1', '1', 'V', '2012-01-02 00:00:00', '6.00', NULL, NULL, NULL, NULL),
('2', '1', 'C', '2012-01-02 00:00:00', '2.00', NULL, NULL, NULL, NULL),
('3', '1', 'C', '2012-09-03 00:00:00', '3.00', NULL, NULL, NULL, NULL),
('4', '1', 'C', '2012-08-20 00:00:00', '11.00', NULL, NULL, NULL, NULL),
('5', '1', 'C', '2012-08-20 00:00:00', '2.50', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `bi_ventesproduits`
--

CREATE TABLE IF NOT EXISTS `bi_ventesproduits` (
  `VenteID` decimal(7,0) NOT NULL,
  `CodeProduit` decimal(7,0) NOT NULL,
  `IndicateurTaxable` char(1) NOT NULL,
  `QteAchetee` decimal(5,0) NOT NULL,
  `PrixUnitaire` decimal(6,2) NOT NULL,
  `TotalAchatProduit` decimal(6,2) NOT NULL,
  PRIMARY KEY (`VenteID`,`CodeProduit`),
  KEY `FK_VentesProduits_CodProd` (`CodeProduit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `bi_ventesproduits`
--

INSERT INTO `bi_ventesproduits` (`VenteID`, `CodeProduit`, `IndicateurTaxable`, `QteAchetee`, `PrixUnitaire`, `TotalAchatProduit`) VALUES
('1', '1', '1', '2', '3.00', '6.00'),
('2', '2', '1', '1', '2.00', '2.00'),
('3', '1', '1', '1', '3.00', '3.00'),
('4', '2', '1', '2', '2.00', '6.00'),
('4', '3', '1', '2', '2.50', '5.00'),
('5', '3', '1', '1', '2.50', '2.50');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `bi_articles`
--
ALTER TABLE `bi_articles`
  ADD CONSTRAINT `FK_BI_Articles_TypeArticle` FOREIGN KEY (`TypeArticle`) REFERENCES `bi_typearticles` (`TypeArticle`);

--
-- Contraintes pour la table `bi_articlesauteurs`
--
ALTER TABLE `bi_articlesauteurs`
  ADD CONSTRAINT `FK_ArticlesAuteurs_AuteurID` FOREIGN KEY (`AuteurID`) REFERENCES `bi_auteurs` (`AuteurID`),
  ADD CONSTRAINT `FK_ArticlesAuteurs_ISBN` FOREIGN KEY (`ISBN`) REFERENCES `bi_articles` (`ISBN`);

--
-- Contraintes pour la table `bi_commentaires`
--
ALTER TABLE `bi_commentaires`
  ADD CONSTRAINT `FK_Commentaires_EmpruntID` FOREIGN KEY (`EmpruntID`) REFERENCES `bi_emprunts` (`EmpruntID`);

--
-- Contraintes pour la table `bi_copiesarticles`
--
ALTER TABLE `bi_copiesarticles`
  ADD CONSTRAINT `bi_copiesarticles_ibfk_1` FOREIGN KEY (`IndicateurDisponible`) REFERENCES `bi_etatarticle` (`id`),
  ADD CONSTRAINT `FK_CopiesArticles_ISBN` FOREIGN KEY (`ISBN`) REFERENCES `bi_articles` (`ISBN`);

--
-- Contraintes pour la table `bi_emprunts`
--
ALTER TABLE `bi_emprunts`
  ADD CONSTRAINT `FK_Emprunts_ModePaiementCd` FOREIGN KEY (`ModePaiementCd`) REFERENCES `bi_modespaiements` (`ModePaiementCd`),
  ADD CONSTRAINT `FK_Emprunts_NoArticle_ISBN` FOREIGN KEY (`NoArticle`, `ISBN`) REFERENCES `bi_copiesarticles` (`NoArticle`, `ISBN`),
  ADD CONSTRAINT `FK_Emprunts_NoMembre` FOREIGN KEY (`NoMembre`) REFERENCES `bi_membres` (`NoMembre`);

--
-- Contraintes pour la table `bi_maisonseditions`
--
ALTER TABLE `bi_maisonseditions`
  ADD CONSTRAINT `FK_MaisonsEditions_ProvCode` FOREIGN KEY (`ProvCode`) REFERENCES `bi_provinces` (`ProvCode`);

--
-- Contraintes pour la table `bi_membres`
--
ALTER TABLE `bi_membres`
  ADD CONSTRAINT `FK_Membres_ProvCode` FOREIGN KEY (`ProvCode`) REFERENCES `bi_provinces` (`ProvCode`),
  ADD CONSTRAINT `FK_Membres_TypeMembre` FOREIGN KEY (`TypeMembre`) REFERENCES `bi_typesmembres` (`TypeMembre`);

--
-- Contraintes pour la table `bi_reservations`
--
ALTER TABLE `bi_reservations`
  ADD CONSTRAINT `bi_reservations_ibfk_1` FOREIGN KEY (`IdMembre`) REFERENCES `bi_membres` (`NoMembre`),
  ADD CONSTRAINT `bi_reservations_ibfk_2` FOREIGN KEY (`IdArticle`) REFERENCES `bi_articles` (`ISBN`);

--
-- Contraintes pour la table `bi_ventes`
--
ALTER TABLE `bi_ventes`
  ADD CONSTRAINT `FK_Ventes_ModePaiementCd` FOREIGN KEY (`ModePaiementCd`) REFERENCES `bi_modespaiements` (`ModePaiementCd`),
  ADD CONSTRAINT `FK_Ventes_NoMembre` FOREIGN KEY (`NoMembre`) REFERENCES `bi_membres` (`NoMembre`);

--
-- Contraintes pour la table `bi_ventesproduits`
--
ALTER TABLE `bi_ventesproduits`
  ADD CONSTRAINT `FK_VentesProduits_CodProd` FOREIGN KEY (`CodeProduit`) REFERENCES `bi_produits` (`CodeProduit`),
  ADD CONSTRAINT `FK_VentesProduits_VenteID` FOREIGN KEY (`VenteID`) REFERENCES `bi_ventes` (`VenteID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
