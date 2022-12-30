package services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LocalDBRequest implements RequestInterface {

    public String run(String numPokemon) {
        String jsonResponse = "";

        /* Connect to the database */
        Connection conn;
        try {
            // db parameters
            String url = "jdbc:sqlite:../sujet_TP/ressources/pokemondatabase.sqlite";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

            PreparedStatement stmt  = conn.prepareStatement("SELECT name, description FROM pokemons WHERE id = ?");
            stmt.setInt(1, 3); // Sets the value "3" for parameter at position 1
            ResultSet rs = stmt.executeQuery();
            rs.next();
            System.out.println("Pokémon name : " + rs.getString("name"));
            System.out.println("Pokémon description : " + rs.getString("description"));

            //Transforming to json format
            ResultSetMetaData md = rs.getMetaData();
            int numCols = md.getColumnCount();
            List<String> colNames = IntStream.range(0, numCols)
                    .mapToObj(i -> {
                        try {
                            return md.getColumnName(i + 1);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return "?";
                        }
                    })
                    .collect(Collectors.toList());

            JSONArray result = new JSONArray();
            while (rs.next()) {
                JSONObject row = new JSONObject();
                colNames.forEach(cn -> {
                    try {
                        row.put(cn, rs.getObject(cn));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                result.add(row);
            }

            jsonResponse = result.toString();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonResponse;
    }
}
