package com.example.pokedex;


public class Pokedex {

    public static void main(String[] args) {
        System.out.println("It's working !");
        if (args.length > 0) {
            System.out.println("Vous avez fourni l'argument " + args[0]);
        }
        SQLLiteExample.run();
        HTTPRequestExample.run();
    }

    public String getName() {
        return "Hello";
    }
}
