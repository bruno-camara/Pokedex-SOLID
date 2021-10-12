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

On donne l'id du pokémon en premier argument de ligne de commande (attention, en lançant le programme avec l'outil gradle, 
la commande aura la forme `./gradlew run --args="<id>"`. Dans IntelliJ, pour ajouter des arguments à la commande `run` de gradle,
il faut d'abord lancer `run` une première fois depuis le panneau latéral "Gradle", afin de créer une config de run IntelliJ 
(commande Maj+F10), puis éditer la commande de run "Edit Run/Debug Configurations", pour éditer la case "Run" avec 
la commande complète : `run --args="1"`. 

Voir [StackOverflow](https://stackoverflow.com/questions/58446696/passing-arguments-to-gradle-run-in-intellij-idea).

Le programme va utiliser l'API publique de Pokeapi pour obtenir les données du pokémon demandé (voir la section Aides
de ce sujet, ainsi que le code example dans le projet PokedexProject pour voir comment utiliser l'API).

L'API nous renvoit beaucoup d'infos pour un pokémon, mais nous avons seulement besoin du nom (qui sera en anglais, ce n'est pas important),
de la taille et du poids.

- Structurez votre code de façon à respecter les principes SOLID, en particulier le principe Single Purpose Responsibility pour cette partie. 
  Indice, inspirez vous du modèle MVC.
- Il est conseillé avant de se lancer dans le code, de concevoir son architecture sur papier à l'aide de Schéma UML (pas à rendre,
  mais bien à faire pour soi).

#### Pistes pour structurer votre code en suivant les principes SOLID

##### A) Single Purpose Responsibility

On va séparer notre code en classes qui assurent chacun une responsabilité bien définie : 

- Des classes de type "model", qui servent à instancier des objets qui représentent les entités de notre modèle métier (ici les Pokémon), elles stockent
  les données associées au modèle, et peut être des méthodes intrinsèquement liées à ces données. On les regroupes dans un package `models`
- Des classes de type "service", qui servent à effectuer des actions technique bas niveau, tel que l'accès à l'API HTTP, et qui renvoient de la donnée
  brute non modélisée. On les regroupe dans un package `services`
- Des classes de type "controller", qui font l'interface entre les services et les modèles, elles utilisent les classes service pour récupérer des données
  brutes sans avoir à se soucier du fonctionnement bas niveau de l'accès à ces données, et instancient les classes modèles à partir des données récupérées. 
  On les regroupe dans un package `controllers`.
- Des classes de type "view", qui servent à génerer des représentation à destination de l'utilisateur final, à partir des instances des classe model.
  On les regroupe dans un package `views`.

##### B) Dependency Inversion (et Open-Closed)

Nos classes controllers vont dépendre des services qui font l'accès bas niveau à la source de données. Pour respecter Dependency Inversion,
on veut que le controller dépende d'une **abstraction** de ce service, c'est à dire qu'on puisse facilement lui remplacer une implémentation
bas niveau concrète par une autre. Celà passe par la création d'une Interface, de laquelle devra dépendre notre controller, et la réalisation
d'une classe concrète de service (accès à l'API HTTP) qui implémente l'interface créée.

Celà permet aussi de respecter le principe Open-Closed, car une modification ultérieure du code (avec une source de données différente)
permettre de ne pas réfactorer le code du controller, ni du service déjà existant.

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

#### Pistes pour structurer votre code en suivant les principes SOLID

##### A) Open Closed

La nouvelle fonctionnalité implique de faire évoluer notre modèle. Pour respecter le principe Open-Closed,
ne modifiez pas la classe modèle existante, mais étendez la en créant une nouvelle classe fille, qui reprend les fonctionnalité
de la classe parent, tout en ajoutant les nouvelles fonctionnalités nécessaire.

##### B) Liskov Substitution

Vous avez dorénavant deux classe modèles, dont l'une hérite de l'autre. Pour respecter le principe de Liskov Substitution,
le code qui dépend de classe parent doit pouvoir continuer à fonctionner avec des instances de la classe fille sans nécessiter
de refactoring. Notamment, vos classes "view" existante devraient continuer à fonctionner tel quel.

##### C) Dependency Inversion

Si vous avez correctement architecturé le couplage entre vos classes controller et service, l'ajout d'une nouvelle source de données
devrait se faire sans problème. Créez deux implémentations de votre interface définie plus tot dans le package `services`, et 
selon les conditions d'appel du programmes, injectez une instance de l'une ou de l'autre à votre controller. 


### 3) Écriture d'un test unitaire



Choisissez une classe dans votre application, et écrivez un test unitaire pour tester son bon fonctionnement.
Le principe d'un test unitaire, c'est qu'il doit tester uniquement le code de la classe testée, pas de ses dépendences, qui doivent
être au maximum rendues abstraites.

Voir la section "Aide" de ce sujet pour plus d'infos sur les test unitaires.


- Choisissez une classe pertinente à tester, qui permette de mettre en évidence le principe Dependency Inversion. Indice : 
  si vous avez suivi un modèle MVC, la classe qui gère votre vue (le V de MVC) s'y prête bien


## Consignes pour le rendu

Livrables à rendre : 

- Le dossier avec l'application (l'équivalent du `PokedexProject` de départ). L'application doit être fonctionnelle, et pouvoir
  être lancée facilement en ligne de commande (laissez des instrutions claires dans un README, si vous n'avez pas repris la configuration
  Gradle fournie)
- Un document rédigé (format de votre choix, PDF, ODF, HTML, markdown pour les plus geeks d'entre vous, …), où vous expliquez pour chaque principe
  SOLID comment vous l'avez mis en œuvre dans votre application, en citant explicitement les classes et parties du code qui correspondent.
  (Exception pour le Interface Segregation Principle, où vous expliquez un scénario hypothétique de mise en œuvre).

Consignes supplémentaires : 

- Commentez votre code. C'est une pratique indispensable à tout bon développeur, et ça permet à celui qui vous relit de comprendre ce que vous 
  avez voulu faire (dans ce cas c'est moi, et dans la vraie vie ça sera le vous du futur qui devra comprendre plusieurs mois après coup ce qu'il a codé)
- Pour les commentaires, suivre le standard Javadoc permet de structurer ses commentaires (et éventuellement de génerer de la documentation).
- Prenez l'habitude de coder en anglais, que ce soit pour le nommage de vos élément ou le commentaire du code.

Date de rendu : 28 novembre 2020 à minuit (le samedi soir).

Méthode de rendu : envoyez le projet & votre rapport dans une archive par email à qrichaud.pro@gmail.com, ou mettez le sur un repo git à cloner 
(et envoyez l'adresse du repo)

## Aides

### Utilisation de la base de données SQLite

Base de données minimaliste stockée dans un seul fichier `pokemondatabase.sqlite`.

Voir <https://www.sqlite.org/index.html> pour en savoir plus sur SQLite.

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

### Utilisation de l'API pokeapi

API au format REST publique, disponible à <https://pokeapi.co/>

Dans le cadre du TP on a seulement besoin du endpoint `https://pokeapi.co/api/v2/pokemon/:id` (remplacer `:id` par le numéro du pokémon voulu),
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

### Tests unitaires


Un exemple de test unitaire est fourni dans le projet de départ. On utilise JUnit4 (attention, syntaxe différente des versions inférieures de 
JUnit, pensez y quand vous cherchez de la doc en ligne sur JUnit).

Dans IntelliJ, on peut génerer automatiquement le fichier de test unitaire pour une classe avec le raccourci Ctrl+Maj+T (ou menu contextuel "Go to" -> "Test")
lorsqu'on se trouve dans le corps de la classe en question.

Exemple de tuto sur JUnit : <https://www.vogella.com/tutorials/JUnit/article.html>

### Gradle avec Intellij

Normalement la configuration Gradle devrait marcher en l'état sans avoir à y toucher. Cependant pour ceux que ça intéresse, voici le guide 
de configuration d'un projet avec Gradle : <https://www.vogella.com/tutorials/JUnit/article.html>
