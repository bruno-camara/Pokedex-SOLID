# SOLID

- Principes de design de développement logiciel 
- Pour le paradigme Orienté Object

--- 

# S.O.L.I.D

- Acronyme = méthode mnémotechnique
- Pas une véritée absolue en soi (dans le monde réel, dans le cadre de ce cours oui)
- Selon les langages et les paradigmes, on peut retrouver ces principes sous des formes différentes
- Tel que formulé : très adaptés aux langages fortement orientés POO (Java, C++, C#, …)


---

# L'acronyme décortiqué

- S : Single-purpose Responsibility
- O : Open-Closed 
- L : Liskov Substition \*
- I : Interface Segregation
- D : Dependency Inversion


\* On va chercher loin pour maintenir notre acronyme sous forme de jeu de mot, bienvenu dans le monde de l'informatique

--- 

# Single-purpose Responsibility


## Définition

Une classe, une fonction ou une méthode doit avoir une et une seule responsabilité


## Objectifs

- Pour pouvoir tester efficacement chaque classe
- Pour réduire le couplage entre les classes
- Pour l'organisation du code, on aboutira naturellement à une meilleure architecture logicielle

---

# Single-purpose Responsibility

## Mauvais example

    !Java
    public class Book {
        private String name;
        private String author;
        private String text;
     
        //constructor, getters and setters
     
        public void exportToJSONFile(String filePath){
            JSONObject outputObject = new JSONObject();
            outputObject.put("name", this.getName());
            outputObject.put("author", this.getAuthor());
            outputObject.put("text", this.getText());
            Files.write(Paths.get(filename), sampleObject.toJSONString().getBytes());
        }
    }

--- 
# Single-purpose Responsibility

## Mauvais example

## Problèmes posés par ce code 

- Exporter le contenu du livre dans un fichier JSON : besoin très spécifique, pas générique, faiblement lié à la raison d'être de la classe Book
- Couplage de la classe Book avec les appels systèmes pour écrire des fichiers (`Files`, `Paths`), et la librairie JSON qui nous fournit `JONObject`

---

# Single-purpose Responsibility

## Bon example

    !Java
    public class Book {
        private String name;
        private String author;
        private String text;
     
        //constructor, getters and setters
    }

    public class BookJSONExporter {
        private Book book;
     
        public BookJSONExporter(Book book) {
          this.book = book;
        }
     
        public void exportToFile(String filePath){
            JSONObject outputObject = new JSONObject();
            outputObject.put("name", this.book.getName());
            outputObject.put("author", this.book.getAuthor());
            outputObject.put("text", this.book.getText());
            Files.write(Paths.get(filename), sampleObject.toJSONString().getBytes());
        }
    }

---

# Single-purpose Responsibility

## Un autre example, le modèle MVC

Model, View, Controller


    !Java
    public class Book {
      private String name;
      private String author;
      private String description;
      // + getters
    }
    
    public class BookController {
      public Book getBook(int bookId) {
        // Get book from database
      }
    
      public Book[] getBooks() {
        // Get all books from database
      }
    }
    
    public class BookView {
      public HTMLObject generateView(Book book) {
        // Generate HTML visualization of the book object, 
        // most of the time using a HTML template library
      }
    }

--- 

# Open-Closed 

## Définition 

Une entité applicative (class, fonction, module ...) doit être ouverte à l'extension, mais fermée à la modification

## Objectifs

- Prévoir une évolution future des besoins (évolution des besoins de notre propre code)
- Laisser de la flexibilité à l'utilisation de notre code par un tiers (important pour les librairies notamment)

## Idée clé

Une évolution des fonctionnalités ne doit pas nécessiter de réécrire du code existant, seulement de rajouter du code autour 
du code existant.

--- 

# Open-Closed 

Dans le cadre d'un langage fortement OO comme Java, cela prend la plupart du temps la forme de l'héritage d'une classe existante
pour étendre ses fonctionnalités.

Attention toutefois à comprendre qu'il s'agit d'un principe, au delà d'un pattern.

--- 

# Open-Closed 

## Mauvais example

Code initial

    !Java
    public class Book {
        private String title;
        private String author;
     
        //constructor, getters and setters
    }

Plus tard, on se rend compte que certains livres de notre catalogue sont des livres traduits, et qu'on veut aussi connaitre le titre original.
Mauvaise solution : réécrire la classe Book

    !Java
    public class Book {
        private String originalTitle;
        private String translatedTitle;
        private String author;
    }

--- 

# Open-Closed 

## Bon example

Code initial

    !Java
    public class Book {
        private String title;
        private String author;
     
        //constructor, getters and setters
    }

Plus tard, on se rend compte que certains livres de notre catalogue sont des livres traduits, et qu'on veut aussi connaitre le titre original.

    !Java
    public class TranslatedBook extends Book {
        private String originalTitle;
        private String translatedTitle;

        @Override
        public String getTitle() {
          return this.translatedTitle;
        }
    }

---

Illustration à l'aide de deux examples. Problème : un service permet de créer un livre (enregistrement en base de données). 
Besoin ultérieur, nous voulons afficher un log à chaque création d'utilisateur, sans réécrire le code existant.

Version typique OO (code Java) : 

    !Java
    /* code initial */
    class UserController {
      public void createUser(User user) {
        // make database record
        Database.createRecord(user)
      } 
    }
    
    /* nouveau code pour étendre la fonctionnalité avec du logging */
    class UserControllerWithLogging extends UserController {
      @Override
      public void createUser(User user) {
        System.out.println("User created " + user.toString());
        super.createUser(user);
      }
    }

    /* mon code client */
    UserController controller = new UserControllerWithLogging();
    controller.createUser(myUser); /* Le logging a bien lieu */

---

Une version possible en javascript (programmation fonctionnelle + observer pattern)

    !Javascript
    /* code initial, prévu avec un "hook" onCreate */
    usersController = {
      onCreateCallbacks = [];
    
      onCreate(callbackFunction) {
        this.onCreateCallbacks.push(callbackFunction);
      }
    
      createUser(user) {
        Database.createRecord(user);
        onCreateCallbacks.forEach(function(callbackFunction) {
          callbackFunction(user);
        })
      }
    }
    
    
    /* Nouveau code pour étendre la fonctionnalité avec du logging */
    usersController.onCreate(function(user) {
      console.log('User created ' + JSON.stringify(user));
    })
    
    /* Mon code qui veut faire du logging*/
    usersController.createUser(myUser);

---

# Liskov Substitution

Si le code manipule un objet de type A, et que B est un sous type de A, on doit pouvoir donner au programme un objet de type B sans 
perturber son fonctionnement.

Dans le cadre de la POO, c'est très simple : 

    !Java
    class A {
      /* lot of methods yeah */
    }
    
    class B extends A {
      /* can override A methods, or leave them as is */
    
      /* additionnal methods */
    }
    
    myProgram() {
      A myObject = new B(); /* Doit pouvoir marcher sans problème */
      
      /* happily manipulate myObject as an A, because that what we expected, even though it's a B */
    }
