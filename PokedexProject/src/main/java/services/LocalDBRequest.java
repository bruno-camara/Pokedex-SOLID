package services;

import java.sql.*;
import java.util.*;

public class LocalDBRequest implements RequestInterface {

    public List<String> run(String numPokemon) {

        List<String> pokemonAttributes = new ArrayList<>();

        /* Connect to the database */
        Connection conn;
        try {
            // db parameters
            String url = "jdbc:sqlite:../sujet_TP/ressources/pokemondatabase.sqlite"; //verify if I nedd to pass the url as a parameter
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

            PreparedStatement stmt  = conn.prepareStatement("SELECT name, description FROM pokemons WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(numPokemon)); // Sets the value "numPokemon" for parameter at position 1
            ResultSet rs = stmt.executeQuery();
            rs.next();

            pokemonAttributes.add(numPokemon);
            pokemonAttributes.add(rs.getString("name"));
            pokemonAttributes.add(rs.getString("height"));
            pokemonAttributes.add(rs.getString("weight"));
            pokemonAttributes.add(rs.getString("description"));


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pokemonAttributes;
    }
}
