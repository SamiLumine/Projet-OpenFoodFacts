# Projet Yaku

## Description

Ce projet est une application web Java utilisant Spring Boot, permettant aux utilisateurs de rechercher des produits par code-barres, d'ajouter des produits à leur panier et de gérer leur panier. Chaque utilisateur est identifié par son adresse email, et son panier est stocké en base de données.

## Fonctionnalités

- **Connexion par email** : À l'ouverture de l'application, l'utilisateur doit saisir son email pour démarrer une session.
- **Recherche de produits** : L'utilisateur peut rechercher des produits en entrant un code-barres.
- **Ajout de produits au panier** : Une fois le produit trouvé, l'utilisateur peut ajouter une certaine quantité de ce produit à son panier. Si le produit est déjà présent dans le panier, la nouvelle quantité remplacera l'ancienne.
- **Affichage du panier** : La section droite de la page affiche l'état du panier de l'utilisateur, lié par l'email.
- **Gestion du panier** : L'utilisateur peut supprimer des produits de son panier.
- **Changement de session** : L'utilisateur peut retourner à la page d'accueil via le chemin `/` pour se connecter avec un autre email.
- **Réinitialisation de la base de données** : À chaque lancement de l'application, la base de données est remise à zéro.

## Exécution du projet

### Prérequis

- Java 17
- Maven
- IntelliJ IDEA

### Étapes pour exécuter le projet via IntelliJ IDEA

1. **Cloner le dépôt** : Clonez le dépôt du projet sur votre machine locale.
2. **Ouvrir le projet** : Ouvrez IntelliJ IDEA et importez le projet en tant que projet Maven.
3. **Configurer le SDK** : Assurez-vous que le SDK Java 17 est configuré pour le projet.
4. **Exécuter l'application** : Dans IntelliJ IDEA, naviguez jusqu'à la classe `YakuApplication` et exécutez-la en tant qu'application Java.
5. **Accéder à l'application** : Ouvrez votre navigateur web et accédez à `http://localhost:8080`.

### Utilisation de l'application

1. **Connexion** : Entrez votre email sur la page d'accueil pour démarrer une session.
2. **Recherche de produits** : Sur la page principale, entrez un code-barres pour rechercher un produit.
3. **Ajout au panier** : Ajoutez le produit trouvé à votre panier en spécifiant la quantité. La quantité sera mise à jour si le produit est déjà présent dans le panier.
4. **Gestion du panier** : Consultez et gérez votre panier dans la section droite de la page principale.
5. **Changer de session** : Retournez à la page d'accueil via le chemin `/` pour vous connecter avec un autre email.

## Structure du projet

- **src/main/java/com/yaku/yaku** : Contient les classes principales de l'application.
  - **controller** : Contient les contrôleurs Spring MVC.
  - **model** : Contient les entités JPA.
  - **repository** : Contient les interfaces des dépôts JPA.
  - **service** : Contient les services de l'application.
  - **utils** : Contient les classes utilitaires.
- **src/main/resources** : Contient les ressources de l'application.
  - **templates** : Contient les fichiers HTML Thymeleaf.
  - **db** : Contient les scripts SQL pour initialiser la base de données.
  - **application.properties** : Contient les configurations de l'application.
- **pom.xml** : Fichier de configuration Maven.

## Remarques

- La base de données est réinitialisée à chaque lancement de l'application.
- Les données de panier sont stockées en base de données pour chaque utilisateur identifié par son email.

## Auteur

- ABDUL-SALAM Sami
