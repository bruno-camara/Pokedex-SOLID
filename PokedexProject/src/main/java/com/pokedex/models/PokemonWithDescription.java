package com.pokedex.models;

public class PokemonWithDescription extends Pokemon {
    private String description;

    public PokemonWithDescription(Integer id, String name, Integer height, Integer weight, String description){
        super(id, name, height, weight);
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
