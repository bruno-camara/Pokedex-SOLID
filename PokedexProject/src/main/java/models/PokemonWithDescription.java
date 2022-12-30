package models;

public class PokemonWithDescription extends Pokemon {
    private String descriptions;

    public PokemonWithDescription(String name, Integer height, Integer weight, String descriptions){
        super(name, height, weight);
        this.descriptions = descriptions;
    }

    public String getDescriptions(){
        return this.descriptions;
    }

    public void setDescriptions(String descriptions){
        this.descriptions = descriptions;
    }
}
