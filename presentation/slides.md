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

## Mauvais example

Problème : 

- On a changé le fonctionnement de `Book` (la méthode `getTitle()` n'existe plus), potentiellement tout le code qui dépend de `Book`
  se retrouve cassé.
- Ça demande de refactorer beaucoup de choses dans notre code interne, au sein même de la classe `Book`, et dans notre autre code applicatif
  qui utilise Book
- Si notre code était utilisé par un tiers, une mise à jour de sa dépendance va casser son code

--- 

# Open-Closed 

Point d'attention : l'idée n'est pas de "monkey patch" à tout prix du code existant, pour éviter d'y toucher en voulant respecter le principe.

Parfois il est indispensable de refactorer un code mal pensé, ou dont l'adaptation n'est vraiment pas possible avec l'évolution du besoin.

L'enjeu, est d'arriver à concevoir son code dès le départ pour que ce cas de figure n'arrive pas.

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


---

# Liskov Substitution

## Mauvais example

En reprenant notre extension de `Book` par `TranslatedBook`

    !Java
    public class Book {
        private String title;
        private String author;
     
        Book(String title, String author) {
          this.title = title;
          this.author = author;
        }
        
        public String getTitle() {
          return this.title;
        }
    }

---

# Liskov Substitution

## Mauvais example


Plus tard, on se rend compte que certains livres de notre catalogue sont des livres traduits, et qu'on veut aussi connaitre le titre original.

    !Java
    public class TranslatedBook extends Book {
        private String originalTitle;
        private String translatedTitle;
  
        constructor(String originalTitle, String translatedTitle) {
          super(null);
          this.originalTitle = originalTitle;
          this.translatedTitle = translatedTitle;
        }

        public String getOriginalTitle() {
          return this.originalTitle;
        }

        public String getTranslatedTitle() {
          return this.translatedTitle;
        }
    }



---

# Liskov Substitution

## Problèmes

Dans mon code client si je fais ceci : 

    !Java
    class MyBookRenderer {
    
      BookController bookController;
    
      renderBookTitleUppercase() {
        Book book = this.bookController.getBook(bookId);
    
        /* En application du principe Liskov-Substitution, bookController 
          nous renvoit une instance de TranslatedBook, il en a le droit, 
          sans que nous soyons au courant */
    
        System.out.println(book.getTitle().toUpperCase());
      }
    }

Je vais recevoir une `NullPointerException`, car `book.getTitle()` renvoit `null` si c'est une instance de `TranslatedBook`.

Note : Java est fortement typé, donc le compilateur nous assure dans tous les cas que `getTitle()` existe et renvoit un String (ou null).
Dans d'autres langages, on pourrait même avoir un cas de figure où `getTitle()` n'existe même plus. L'application du principe Liskov-Substitution
est d'autant plus important.

---

# Liskov Substitution

## Bon example

    !Java
    public class TranslatedBook extends Book {
        private String originalTitle;
        private String translatedTitle;
  
        constructor(String originalTitle, String translatedTitle) {
          super(translatedTitle);
          this.originalTitle = originalTitle;
          this.translatedTitle = translatedTitle;
        }

        public String getOriginalTitle() {
          return this.originalTitle;
        }

        public String getTranslatedTitle() {
          return this.translatedTitle;
        }

        @Override
        public String getTitle() {
          return this.translatedTitle;
        }
    }

---

# Liskov Substitution

Note : dans son sens large, le principe veut que le code fonctionne d'une bonne façon, pas seulement qu'il compile et tourne sans exceptions
(par exemple, mettre `title = ""` n'est pas satisfaisant dans le mauvais exemple).


---

# Interface Segregation 

## Définition 

Préférer plusieurs interfaces spécifiques pour chaque client plutôt qu'une seule interface générale

## Objectifs

C'est un principe très spécifique à la POO fortement typée. 

Quand on définie une interface (au sens POO, donc le mot clé Java `interface`), on oblige le client
qui veut l'utiliser à implémenter toute l'interface.

Le but est de ne pas obliger le client à implémenter une grande partie de l'interface dont il ne se sert pas, 
dans le cas où on lui donne une interface trop générique alors qu'il a un besoin spécifique



---
# Interface Segregation 

## Mauvais example

Pour gérer le contenu d'une médiathèque

    !Java
    interface MediaInterface {
        public String getTitle();
        public String getAuthor();
    
        /* applies to audio media, such as CDs */
        public Int getAudioDurationSeconds();
    
        /* applies to printed media, such as Books */
        public Int getPagesCount();
    } 
    
    class MediaRenderer {
      public String renderMediaTitleAndAuthor(MediaInterface media) {
        return media.getTitle() + " - " + media.getAuthor();
      }
    
      public String renderBook(MediaInterface book) {
        return media.getTitle() + " - " + media.getAuthor() + " - " media.getPagesCount() + " pages"; 
      }
    
      public String renderCD(MediaInterface cd) {
        return media.getTitle() + " - " + media.getAuthor() + " - " media.getAudioDurationSeconds() + " seconds"; 
      }
    }

On ne fournit pas d'implémentation de `MediaInterface (chaque client peut avoir ses besoins spécifiques sur la nature des médias, on lui demande donc
juste de respecter l'interface `MediaInterface` avec son implémentation, pour pouvoir utiliser le service `MediaRenderer`).

---

# Interface Segregation 

## Problèmes

Un client qui n'a que des livres, et ne souhaite utiliser que la méthode  `renderBook()` du service `MediaRenderer`
va être obligé de faire l'implémentation suivante.

--- 

    !Java
    class Book implements MediaInterface {
      private String title ;
      private String author;
      private int pagesCount;
    
      Book(String title, String author, int pagesCount) {
        this.title = title;
        this.author = author;
        this.pagesCount = pagesCount;
      } 
    
      public String getTitle() {
        return this.title;
      }
      
      public String getAuthor() {
        return this.author;
      }
    
      public int getPagesCount() {
        return this.pagesCount;
      }
    
      /* Implementation factice ici, car on n'a en fait pas besoin des fonctionnalités lié aux médias audio */
      public int getAudioDurationSeconds() {
        return null;
      }
    }



---

# Interface Segregation 

## Bon example

    !Java
    interface MediaInterface {
        public String getTitle();
        public String getAuthor();
    }

    interface PrintedMediaInterface extends MediaInterface {
        /* applies to printed media, such as Books */
        public Int getPagesCount();
    }

    interface AudioMediaInterface extends MediaInterface {
        /* applies to audio media, such as CDs */
        public Int getAudioDurationSeconds();
    } 
    
    class MediaRenderer {
      public String renderMediaTitleAndAuthor(MediaInterface media) {
        return media.getTitle() + " - " + media.getAuthor();
      }
    
      public String renderBook(PrintedMediaInterface book) {
        return media.getTitle() + " - " + media.getAuthor() + " - " media.getPagesCount() + " pages"; 
      }
    
      public String renderCD(AudioMediaInterface cd) {
        return media.getTitle() + " - " + media.getAuthor() + " - " media.getAudioDurationSeconds() + " seconds"; 
      }
    }


---

# Interface Segregation 

## Bon example

Le client peut implémenter seulement l'interface `PrintedMediaInterface` s'il n'a pas besoin des fonctionnalités
de `AudioMediaInterface`.

S'il ne souhaite utiliser que la méthode `renderMediaTitleAndAuthor()`, il peut même implémenter seulement l'interface 
`MediaInterface`.

S'il souhaite utiliser à la fois les fonctionalité de `PrintedMediaInterface` et `AudioMediaInterface` sur un même objet,
il peut implémenter plusieurs interfaces avec la syntaxe suivante : 

    !Java
    class MyMediaThatHasBothPagesAndAudio implements AudioMediaInterface, PrintedMediaInterface {
      /* ... */
    }


---

# Dependency Inversion principle

## Définition

Il faut dépendre des abstractions, pas des implémentations.

## Objectifs

- Un composant haut niveau (par exemple, un service pour obtenir les livres), ne doit pas dépendre d'un composant bas niveau (par exemple, 
  une librairie d'accès à une base de données MySQL)
- Les composants haut niveaux doivent dépendre d'interfaces abstraites, qui définissent seulement les besoins exacts du composant haut niveau
- Ainsi on peut fournir au composant n'importe quelle dépendance bas niveau qui remplit ses besoins

## Note

En termes de design pattern, ce principe repose souvent sur le pattern "Dependency Injection"

---

# Dependency Inversion principle

## Mauvais example

    !Java
    class BookService { 
      private MySQLConnection dbConnection;
      /* + constructor */

      public Book[] getBooks() {
        ResultSet = this.dbConnection.query("SELECT * FROM books");
        /* parse ResultSet and return an array of Book */
      }
    }

Si on veut changer notre système de base données, passer de MySQL à PostgreSQL, il faut changer la librairie, ne plus utiliser l'objet
`MySQLConnection` et à la place avec un objet `PostgreSQLConnection`. 

Ce changement bas niveau (changement d'un système de base de données), nous oblige à faire du refactor dans des composants haut niveau
(`BookService`).

---

# Dependency Inversion principle

## Bon example

    !Java
    interface GenericDbConnection {
      public ResultSet query(String queryString); 
    }

    class BookService {
      private GenericDbConnection dbConnection;
      /* + constructor() */

      public Book[] getBooks() {
        ResultSet = dbConnection.query("SELECT * FROM books");
        /* parse ResultSet and return an array of Book */
      }
    }


    main() {
      BookService service = new BookService(new PostgreSQLConnection());
      service.getBooks();
    }


---

# Dependency Inversion principle

- "Dependency injection" : c'est le code applicatif (main) qui fournit l'instance de la dépendance (le `new PostgreSQLConnection()`).
- Celà permet de donner au service ce qu'on veut, tant que ça remplit le contrat de l'interface `GenericDbConnection`.
- Très utile pour faire des tests unitaires, on remplace les dépendances par des implémentation "Mock"

---

    !Java
    class DbConnectionMock implements GenericDbConnection {
      private ArrayList<String> queries; 
    
      GenericDbConnection() {
        this.queries = new ArrayList<String>();
    
      /* The mock stores the queries done in an array */
      public ResultSet query(String queryString) {
        this.queries.add(queryString);
      }
    
      public ArrayList<String> getQueries() {
        return this.queries;
      }
    }
    
    main() {
      /* Unit testing our BookService */
      DbConnectionMock mock = new DbConnectionMock();
      BookService service = new BookService(mock);
      service.getBooks();
      /* Test is successfull if the correct query was made */
      if (mock.queries.get(mock.queries.size - 1) == "SELECT * FROM books")  {
        System.out.println("Test success");
      } else {
        System.out.println("Test fail");
      }
    }
