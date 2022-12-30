package controllers;

import models.Pokemon;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ControllerAPI {
    public static Pokemon createPokemon(String jsonResponse) {

        Pokemon pokemon = null;
        try {
            JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(jsonResponse);
            if (resultObject instanceof JSONObject) {
                JSONObject obj = (JSONObject) resultObject;
                String name = obj.get("name").toString();
                Integer height = Integer.parseInt(obj.get("height").toString());
                Integer weight = Integer.parseInt(obj.get("weight").toString());
                pokemon = new Pokemon(name, height, weight);
            } else {
                System.err.println("Error, we expected a JSON Object from the API");
            }
        } catch (ParseException e) {
            System.err.println("Could not parse the response given by the API as JSON");
            System.err.println("Response body is :");
            System.err.println(jsonResponse);
            e.printStackTrace();
        }
        return pokemon;
    }
}
