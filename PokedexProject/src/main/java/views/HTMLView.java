package views;

import models.Pokemon;
import models.PokemonWithDescription;
import utilities.OutputHTMLGeneratorInterface;

public class HTMLView implements OutputHTMLGeneratorInterface {

    private Pokemon pokemon;
    private PokemonWithDescription pokemonWithDescription;

    public HTMLView(Pokemon pokemon){
        this.pokemon = pokemon;
    }

    public HTMLView(PokemonWithDescription pokemonWithDescription){
        this.pokemonWithDescription = pokemonWithDescription;
    }
    public String generateHtml(){
        String output =
                "<h1>" + this.pokemonWithDescription.getName() + "</h1>\n" +
                "<ul>\n" +
                "<li>" + this.pokemonWithDescription.getId() + "</li>\n" +
                "<li>" + this.pokemonWithDescription.getHeight() + "</li>\n" +
                "<li>" + this.pokemonWithDescription.getWeight() + "</li>\n" +
                "<li>" + this.pokemonWithDescription.getDescription() + "</li>\n" +
                "</ul>";

        return output;
    }
}
