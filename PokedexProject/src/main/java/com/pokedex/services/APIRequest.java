package com.pokedex.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class APIRequest implements RequestInterface{

    @Override
    public List<String> run(String numPokemon) {
        List<String> pokemonAttributes = new ArrayList<>();
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://pokeapi.co/api/v2/pokemon/" + numPokemon);
            request.addHeader("content-type", "application/json");
            HttpResponse result = httpClient.execute(request);
            String jsonResponse = EntityUtils.toString(result.getEntity(), "UTF-8");

            try {
                JSONParser parser = new JSONParser();
                Object resultObject = parser.parse(jsonResponse);
                if (resultObject instanceof JSONObject) {
                    JSONObject obj = (JSONObject) resultObject;
                    String name = obj.get("name").toString();
                    String height = obj.get("height").toString();
                    String weight = obj.get("weight").toString();

                    pokemonAttributes.add(numPokemon);
                    pokemonAttributes.add(name);
                    pokemonAttributes.add(height);
                    pokemonAttributes.add(weight);
                } else {
                    System.err.println("Error, we expected a JSON Object from the API");
                }
            } catch (ParseException e) {
                System.err.println("Could not parse the response given by the API as JSON");
                System.err.println("Response body is :");
                System.err.println(jsonResponse);
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return pokemonAttributes;
    }
}
