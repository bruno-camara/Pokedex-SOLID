package views;

import controllers.ControllerAPI;
import models.Pokemon;
import models.PokemonWithDescription;
import services.APIRequest;
import services.LocalDBRequest;
import utilities.ConsoleLogUtility;
import utilities.FileLogUtility;

import java.io.File;
import java.io.IOException;

public class ShowResults {
    public static void main(String[] args) throws IOException {
        System.out.println("Java version " + System.getProperty("java.version"));
        if (args.length > 1) {
            //Call the classes and functions for local DB
            LocalDBRequest dbRequest = new LocalDBRequest();
            PokemonWithDescription pokemon = ControllerAPI.createPokemonWithDescription(dbRequest, args[0]);

            //Show results
            ConsoleView consoleView = new ConsoleView(pokemon);
            System.out.println(consoleView.generateTextWithDescription());

            HTMLView htmlView = new HTMLView(pokemon);
            FileLogUtility fileLogUtility = new FileLogUtility();
            fileLogUtility.logHtmlToFile("./output.html", htmlView);
        }
        else if (args.length > 0){
            //Call the classes and functions for API
            APIRequest apiRequest = new APIRequest();
            Pokemon pokemon = ControllerAPI.createPokemon(apiRequest, args[0]);

            //Show results
            ConsoleView consoleView = new ConsoleView(pokemon);
            System.out.println(consoleView.generateText());
        }
        else {System.out.println("Please insert valid arguments");}
    }
}
