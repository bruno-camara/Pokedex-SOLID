package com.example.pokedex;


public class Pokedex {

    public static void main(String[] args) {
        System.out.println("It's aalive");
        for(int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        SQLLiteExample.run();
        HTTPRequestExample.run();
    }

    public String getName() {
        return "Hello";
    }
}
