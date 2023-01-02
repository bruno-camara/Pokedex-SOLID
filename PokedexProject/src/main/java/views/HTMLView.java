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
    public String generateHtmlWithDescription(){
        String output =
                "<h1>" + this.pokemonWithDescription.getName() + "</h1>\n" +
                "<ul>\n" +
                "<li>Id : " + this.pokemonWithDescription.getId() + "</li>\n" +
                "<li>Taille : " + this.pokemonWithDescription.getHeight() + "</li>\n" +
                "<li>Poids : " + this.pokemonWithDescription.getWeight() + "</li>\n" +
                "<li>Description : " + this.pokemonWithDescription.getDescription() + "</li>\n" +
                "</ul>";

        return output;
    }

    public String generateHtml(){
        String output =
                "<h1>" + this.pokemon.getName() + "</h1>\n" +
                        "<ul>\n" +
                        "<li>Id : " + this.pokemon.getId() + "</li>\n" +
                        "<li>Taille : " + this.pokemon.getHeight() + "</li>\n" +
                        "<li>Poids : " + this.pokemon.getWeight() + "</li>\n" +
                        "</ul>";

        return output;
    }
}
