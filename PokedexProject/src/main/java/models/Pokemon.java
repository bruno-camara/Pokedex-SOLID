package models;

public class Pokemon {

    private Integer id;
    private String name;
    private Integer height;
    private Integer weight;

    public Pokemon(Integer id, String name, Integer height, Integer weight){
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
