package sample.DataBase;

import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by sergi on 12/12/15.
 */
public class PokemonDAO {

    public String getPokemonMenu(int pokemonId){

        Connection conexion = null;
        Statement stmt = null;

        try{

            String pokemonMenu;

            Class.forName("org.sqlite.JDBC");
            conexion  = DriverManager.getConnection("jdbc:sqlite:pokemon_sbarjola.db");
            conexion.setAutoCommit(false);
            Statement statement = conexion.createStatement();
            ResultSet result;
            result = statement.executeQuery("SELECT * FROM POKEMONS WHERE ID = " + pokemonId);

            if (result.next()){
                pokemonMenu = result.getString("ID") + " | " + result.getString("NOMBRE").toUpperCase();
                result.close();
                statement.close();
                conexion.close();
                return pokemonMenu;
            }
        }
        catch (Exception one){}
        return " ";
    }

    public String getNombrePokemon(int pokemonId){
        Connection conexion = null;
        Statement stmt = null;

        try{

            String nombrePokemon;

            Class.forName("org.sqlite.JDBC");
            conexion  = DriverManager.getConnection("jdbc:sqlite:pokemon_sbarjola.db");
            conexion.setAutoCommit(false);
            Statement statement = conexion.createStatement();
            ResultSet result;
            result = statement.executeQuery("SELECT * FROM POKEMONS WHERE ID = " + pokemonId);

            if (result.next()){
                nombrePokemon = result.getString("ID") + " - " + result.getString("NOMBRE").toUpperCase();

                result.close();
                statement.close();
                conexion.close();
                return nombrePokemon;
            }
        }

        catch (Exception one){}
        return " ";
    }

    public String getDetallesPokemon(int pokemonId){

        Connection conexion = null;
        Statement stmt = null;

        try{

            String detallesPokemon;

            Class.forName("org.sqlite.JDBC");
            conexion  = DriverManager.getConnection("jdbc:sqlite:pokemon_sbarjola.db");
            conexion.setAutoCommit(false);
            Statement statement = conexion.createStatement();
            ResultSet result;
            result = statement.executeQuery("SELECT * FROM POKEMONS WHERE ID = " + pokemonId);
            if (result.next()){
                detallesPokemon = "· Puntos de vida:        " + result.getInt("HP")
                                + "\n· Peso:                  " + result.getString("PESO") + " kg"
                                + "\n· URL de la imagen:      " + result.getString("IMAGEN")
                                + "\n· URL de referencia:     " + result.getString("URL_REFERENCIA") + "\n\n";

                result.close();
                statement.close();
                conexion.close();
                return detallesPokemon;
            }
        }
        catch (Exception one){}
        return " ";
    }

    public String getURLImagen(int pokemonId){

        Connection conexion = null;
        Statement stmt = null;

        try{
            String imageURL;

            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection("jdbc:sqlite:pokemon_sbarjola.db");
            conexion.setAutoCommit(false);
            Statement statement = conexion.createStatement();
            ResultSet result;
            result = statement.executeQuery("SELECT * FROM POKEMONS WHERE ID =" + pokemonId);
            if (result.next()){
                imageURL = result.getString("IMAGEN");
                result.close();
                statement.close();
                conexion.close();
                return imageURL;
            }
        }  catch (Exception one){}
        return " ";
    }

    public String getURLImagen(String pokemonName){

        Connection conexion = null;
        Statement stmt = null;

        try{
            String imageURL;

            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection("jdbc:sqlite:pokemon_sbarjola.db");
            conexion.setAutoCommit(false);
            Statement statement = conexion.createStatement();
            ResultSet result;
            result = statement.executeQuery("SELECT * FROM POKEMONS WHERE NOMBRE = '" + pokemonName + "'");
            if (result.next()){
                imageURL = result.getString("IMAGEN");
                result.close();
                statement.close();
                conexion.close();
                return imageURL;
            }
        }  catch (Exception one){}

        return " ";
    }

    public String buscarPokemon(String nombrePokemon) throws Exception{

        nombrePokemon.toLowerCase();
        String datosPokemon;

        Connection conexion = null;
        Statement stmt = null;

        Class.forName("org.sqlite.JDBC");
        conexion = DriverManager.getConnection("jdbc:sqlite:pokemon_sbarjola.db");
        conexion.setAutoCommit(false);
        Statement statement = conexion.createStatement();
        ResultSet result;
        result = statement.executeQuery("SELECT * FROM POKEMONS WHERE NOMBRE = '" + nombrePokemon + "'");

        if (result.next()) {
            datosPokemon = "ID del Pokemon:     " + result.getString("ID")
                    + "\nNombre:        " + result.getString("NOMBRE").toUpperCase()
                    + "\nPuntos de vida:        " + result.getInt("HP")
                    + "\nPeso:      " + result.getString("PESO") + " kg"
                    + "\nURL de la imagen:      " + result.getString("IMAGEN")
                    + "\nURL de referencia:     " + result.getString("URL_REFERENCIA") + "\n\n";

            result.close();
            statement.close();
            conexion.close();
            return datosPokemon;
        }

        return " ";
    }
}
