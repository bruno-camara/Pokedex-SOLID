package views;

import controllers.ControllerAPI;
import models.Pokemon;
import services.APIRequest;

public class ShowResults {
    public static void main(String[] args) {
        System.out.println("Java version " + System.getProperty("java.version"));
        if (args.length > 0) {
            //Call the classes and functions
            APIRequest apiRequest = new APIRequest();
            String jsonResponse = apiRequest.run(args[0]);
            Pokemon pokemon = ControllerAPI.createPokemon(jsonResponse);

            //Show results
            System.out.println("=============================");
            System.out.println("Pokémon # " + args[0]);
            System.out.println("Nom : " + pokemon.getName());
            System.out.println("Taille : " + pokemon.getHeight());
            System.out.println("Poids : " + pokemon.getWeight());
            System.out.println("=============================");
        }
        else {
            System.out.println("Please insert a number as argument");
        }
    }
}
