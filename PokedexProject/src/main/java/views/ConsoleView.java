package views;

import models.Pokemon;
import models.PokemonWithDescription;
import utilities.OutputTextGeneratorInterface;

public class ConsoleView implements OutputTextGeneratorInterface {

    private Pokemon pokemon;
    private PokemonWithDescription pokemonWithDescription;

    public ConsoleView(Pokemon pokemon){
        this.pokemon = pokemon;
    }

    public ConsoleView(PokemonWithDescription pokemonWithDescription){
        this.pokemonWithDescription = pokemonWithDescription;
    }

    @Override
    public String generateText() {
        String output =
                "=============================\n" +
                "Pokémon # " + this.pokemon.getId() + "\n" +
                "Nom : " + this.pokemon.getName() + "\n" +
                "Taille : " + this.pokemon.getHeight() + "\n" +
                "Poids : " + this.pokemon.getWeight() + "\n" +
                "=============================";

        return output;
    }

    @Override
    public String generateTextWithDescription() {
        String output =
                "=============================\n" +
                        "Pokémon # " + this.pokemonWithDescription.getId() + "\n" +
                        "Nom : " + this.pokemonWithDescription.getName() + "\n" +
                        "Taille : " + this.pokemonWithDescription.getHeight() + "\n" +
                        "Poids : " + this.pokemonWithDescription.getWeight() + "\n" +
                        "Description : " + this.pokemonWithDescription.getDescription() + "\n" +
                        "=============================";

        return output;
    }
}
