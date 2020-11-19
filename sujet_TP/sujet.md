# TP Mise en pratique des principes SOLID

## Mise en place

Récupérez le dossier `PokedexProject`. Il s'agit d'un projet configuré pour l'IDE IntelliJ, et le gestionnaire de build Gradle. 

Normalement le projet est correctement configuré pour pouvoir tout de suite compiler, lancer l'application et les tests unitaires sur votre machine
tant que l'environnement Java est correctement installé (systèmes Unix-like, c'est à dire Linux ou MacOS). 

Dans le dossier du projet, un fichier `README.md` donne les instructions pour compiler, lancer le projet, lancer les tests unitaires.

Si vous maitrisez bien l'environnement et les outils Java, vous pouvez utiliser d'autres outils de votre choix (autre IDE, système de build…),
mais il faudra que vous fournissiez un dossier de rendu avec un `README.md` similaire qui permette en quelques commandes simples de lancer
l'application et les tests unitaires.

## Sujet du TP

### 1) Pokédex 

On souhaite réaliser un pokédex basique en ligne de commande. Il fonctionne de la façon suivante :

```
$ PokedexApplication <pokemonId>
=============================
Pokémon # 1
Nom : Bulbizarre
Taille : 7
Poids : 69
=============================
```

On donne l'id du pokémon en premier argument de ligne de command (attention, en lançant le programme avec l'outil gradle, 
la commande aura la forme `./gradlew run --args="<id>"`. Dans Intellij, c'est un peu bugué, il faut utiliser le workaround
décrit ici : https://stackoverflow.com/questions/58446696/passing-arguments-to-gradle-run-in-intellij-idea).

Le programme va utiliser l'API publique de Pokeapi pour obtenir les données du pokémon demandé (voir la section Aides
de ce sujet, ainsi que le code example dans le projet PokedexProject pour voir comment utiliser l'API).

L'API nous renvoit beaucoup d'infos pour un pokémon, mais nous avons seulement besoin du nom (en anglais, ce n'est pas grave),
de la taille et du poids.

- Structurez votre code de façon à respecter les principes SOLID, en particulier le principe S pour cette partie. Indice, inspirez
  vous du modèle MVC.
- Il est conseillé avant de se lancer dans le code, de concevoir son architecture sur papier à l'aide de Schéma UML (pas à rendre,
  mais bien à faire pour soi).

### 2) Ajout de fonctionnalité !

On veut maintenant pouvoir obtenir les données des pokémons depuis une autre source de données. On a une petite base de données locale
(fichier `pokemondatabase.sqlite`, fournit dans le dossier `ressources`). Il présente l'avantage d'être disponible en local 
(pas de risque d'indisponibilité du réseau ou du serveur distant), et d'avoir une information supplémentaire dans son jeu de donnée,
la description du pokémon.

On laisse toutefois la possibilité à l'utilisateur de continuer à utiliser les données de l'API Pokeapi si le fichier de base de 
donnée n'est pas disponible.

Fonctionnement du programme dorénavant : 
 
- Si on spécifie le fichier de base de données après l'ID du pokémon voulu

```
$ PokedexApplication <pokemonId> <databaseFile>
=============================
Pokémon # 1
Nom : Bulbizarre
Taille : 7
Poids : 69
Description : Il a une étrange graine plantée sur son dos. Elle grandit avec lui depuis la naissance.
=============================
```

- Sinon le fonctionnement reste le même que précédemment 

```
$ PokedexApplication <pokemonId> 
=============================
Pokémon # 1
Nom : Bulbizarre
Taille : 7
Poids : 69
=============================
```


- Est ce que 

## Aides

## Utilisation de la base de données SQLite

Base de données minimaliste stockée dans un seul fichier `pokemondatabase.sqlite`.

Voir https://www.sqlite.org/index.html pour en savoir plus sur SQLite.

Client graphique pour explorer et manipuler facilement la base de donnée : SQLiteBrowser https://sqlitebrowser.org/

Schéma de la base de donnée :

```
CREATE TABLE IF NOT EXISTS "pokemons" (
	`id`	INTEGER NOT NULL,
	`name`	TEXT,
	`description`	TEXT,
	`height`	INTEGER,
	`weight`	INTEGER,
	PRIMARY KEY(`id`)
);
```

Il y a des entrées pour les 5 premiers pokémons (ID de 1 à 5).

## Utilisation de l'API pokeapi

API au format REST publique, disponible à https://pokeapi.co/

Dans le cadre du TP on a seulement besoin du endpoint https://pokeapi.co/api/v2/pokemon/:id  (remplace :id par le numéro du pokémon voulu),
avec la requête HTTP suivante : 

```
GET https://pokeapi.co/api/v2/pokemon/1
```

qui retourne un objet JSON, assez gros, mais dont seulement le sous ensemble suivant nous intéresse : 

```
{
  "id": 1,
  "name": "bulbasaur",
  "height": 7,
  "weight": 69
}
```
