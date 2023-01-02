package controllers;

import models.Pokemon;
import models.PokemonWithDescription;
import services.RequestInterface;

import java.util.List;

public class ControllerAPI {
    public static Pokemon createPokemon(RequestInterface request, String numPokemon) {

        List<String> pokemonAttributes = request.run(numPokemon);

        Integer id = Integer.parseInt(pokemonAttributes.get(0));
        String name = pokemonAttributes.get(1);
        Integer height = Integer.parseInt(pokemonAttributes.get(2));
        Integer weight = Integer.parseInt(pokemonAttributes.get(3));

        Pokemon pokemon = new Pokemon(id, name, height, weight);

        return pokemon;
    }

    public static PokemonWithDescription createPokemonWithDescription(RequestInterface request, String numPokemon) {

        List<String> pokemonAttributes = request.run(numPokemon);

        Integer id = Integer.parseInt(pokemonAttributes.get(0));
        String name = pokemonAttributes.get(1);
        Integer height = Integer.parseInt(pokemonAttributes.get(2));
        Integer weight = Integer.parseInt(pokemonAttributes.get(3));
        String description = pokemonAttributes.get(4);

        PokemonWithDescription pokemon = new PokemonWithDescription(id, name, height, weight, description);

        return pokemon;
    }
}
