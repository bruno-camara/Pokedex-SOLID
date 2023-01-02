package com.pokedex.views;

import com.pokedex.controllers.ControllerAPI;
import com.pokedex.models.Pokemon;
import com.pokedex.models.PokemonWithDescription;
import com.pokedex.services.APIRequest;
import com.pokedex.services.LocalDBRequest;

public class ShowResults {
    public static void main(String[] args) {
        System.out.println("Java version " + System.getProperty("java.version"));
        if (args.length > 1) {
            //Call the classes and functions for local DB
            LocalDBRequest dbRequest = new LocalDBRequest();
            PokemonWithDescription pokemon = ControllerAPI.createPokemonWithDescription(dbRequest, args[0]);

            //Show results
            System.out.println("=============================");
            System.out.println("Pokémon # " + pokemon.getId());
            System.out.println("Nom : " + pokemon.getName());
            System.out.println("Taille : " + pokemon.getHeight());
            System.out.println("Poids : " + pokemon.getWeight());
            System.out.println("Description : " + pokemon.getDescription());
            System.out.println("=============================");
        }
        else if (args.length > 0){
            //Call the classes and functions for API
            APIRequest apiRequest = new APIRequest();
            Pokemon pokemon = ControllerAPI.createPokemon(apiRequest, args[0]);

            //Show results
            System.out.println("=============================");
            System.out.println("Pokémon # " + pokemon.getId());
            System.out.println("Nom : " + pokemon.getName());
            System.out.println("Taille : " + pokemon.getHeight());
            System.out.println("Poids : " + pokemon.getWeight());
            System.out.println("=============================");
        }
        else {System.out.println("Please insert valid arguments");}
    }
}
