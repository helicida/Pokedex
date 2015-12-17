package sample.DataBase;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import sample.Controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by sergi on 12/12/15.
 */
public class DataBaseController extends Thread{

    // Variables de nuestra DataBase:

    private Scanner teclat = new Scanner(System.in);
    private String BASE_URL = "http://pokeapi.co/";
    private String POKEDEX_URL = BASE_URL + "api/v1/pokedex/1/";
    private String URL_REFERNCIA = "api/v1/pokemon/1/";
    private String URL_POKEMON = BASE_URL + URL_REFERNCIA;
    private Pokedex pokedex;
    private Controller a;

    public String statusText = "";

    public DataBaseController(Controller a){
        this.a = a;
    }

    public void run(){
        deleteDB();
        createDatabase();
        downloadPokemons();
        insertPokemons();
    }

    public void createDatabase() {

        Connection conexion = null;
        Statement stmt = null;

        try {

            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection("jdbc:sqlite:pokemon_sbarjola.db");
            stmt = conexion.createStatement();

            a.setStatusText("Se ha accedido correctamente a la base de datos");

            a.setStatusText("Se procederá a crear las tablas.");

            // Hacemos una sola tabla para los Pokemon

            // La tabla POKEMONS tendrá la ID del pokemon, el nombre, el peso...
            String createPokemons = "CREATE TABLE POKEMONS"
                    + " (ID             TEXT,"
                    + " NOMBRE          TEXT,"
                    + " URL_REFERENCIA TEXT,"
                    + " IMAGEN          TEXT,"
                    + " PESO            INT,"
                    + " HP              TEXT)";

            // Creamos las dos tablas con nuestros script SQL de arriba
            stmt.executeUpdate(createPokemons);

            // Cerramos las conexiones a la base de datos
            stmt.close();
            conexion.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void deleteDB(){

        File file = new File("pokemon_sbarjola.db");   // Ruta del archivo de nuestra base de datos

        try{
            if(file.delete()){  // Lo eliminamos
                a.setStatusText(file.getName() + ": se ha eliminado la base de datos antigua");
            }
            else{   // Si no se ha podido eliminar
                a.setStatusText("No existe ninguna base de datos anterior o no se ha podido eliminar");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void downloadPokemons(){

        statusText = "Descargando Pokemon y generando los objetos \n----------------------------------------------";
        pokedex = new Pokedex();
        JSONObject pokedexJO = (JSONObject) JSONValue.parse(getJSON(POKEDEX_URL));
        pokedex.setNombre(pokedexJO.get("name").toString());
        pokedex.setCreado(pokedexJO.get("created").toString());
        pokedex.setUltimaModificacion(pokedexJO.get("modified").toString());

        ArrayList<Pokemon> pokemons = new ArrayList<>();
        JSONArray pokemonsJA = (JSONArray) JSONValue.parse(pokedexJO.get("pokemon").toString());

        for(int iterador = 0; iterador < pokemonsJA.size(); iterador++){

            JSONObject pokemonJO = (JSONObject) JSONValue.parse(pokemonsJA.get(iterador).toString());

            Pokemon pokemon = new Pokemon();
            pokemon.setNombre(pokemonJO.get("name").toString());
            URL_REFERNCIA = pokemonJO.get("resource_uri").toString();
            URL_POKEMON = BASE_URL + URL_REFERNCIA;
            pokemon.setUrl_referencia(URL_POKEMON);

            JSONObject pokemonDetailsJO = (JSONObject) JSONValue.parse(getJSON(URL_POKEMON));

            pokemon.setPeso(pokemonDetailsJO.get("weight").toString());
            pokemon.setHp(pokemonDetailsJO.get("hp").toString());
            pokemon.setId(pokemonDetailsJO.get("pkdx_id").toString());
            pokemon.setImagen(BASE_URL + "media/img/" + pokemon.getId() + ".png");
            a.setStatusText("\n" + pokemon.getNombre().toUpperCase() + " se ha generado -- (" + iterador + "/" + pokemonsJA.size() + ")");
            pokemons.add(pokemon);
        }
        pokedex.setPokemons(pokemons);
    }

    public void insertPokemons(){

        Class<Controller> x = Controller.class;

        for (int iterador = 0; iterador < pokedex.getPokemons().size(); iterador++){

            Connection conexion = null;

            try{
                Class.forName("org.sqlite.JDBC");
                conexion = DriverManager.getConnection("jdbc:sqlite:pokemon_sbarjola.db");
                conexion.setAutoCommit(false);

                String sql = "INSERT INTO POKEMONS" +
                        "(ID, NOMBRE, URL_REFERENCIA, IMAGEN, PESO, HP) VALUES" +
                        "(?,?,?,?,?,?)";

                PreparedStatement prepStat = conexion.prepareStatement(sql);

                prepStat.setString(1, pokedex.getPokemons().get(iterador).getId());
                prepStat.setString(2, pokedex.getPokemons().get(iterador).getNombre());
                prepStat.setString(3, pokedex.getPokemons().get(iterador).getUrl_referencia());
                prepStat.setString(4, pokedex.getPokemons().get(iterador).getImagen());
                prepStat.setString(5, pokedex.getPokemons().get(iterador).getPeso());
                prepStat.setString(6, pokedex.getPokemons().get(iterador).getHp());

                prepStat.executeUpdate();
                prepStat.close();
                conexion.commit();
                conexion.close();

                a.setStatusText("- Se ha insertado '" + pokedex.getPokemons().get(iterador).getNombre() + "' correctamente");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }
        a.refrescarLista(null);
        a.setStatusText(" · La base de datos se ha actualizado correctamente");
    }

    public String getJSON(String URLtoRead){

        try{
            StringBuilder stringJSON = new StringBuilder();
            URL url = new URL(URLtoRead);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null){
                stringJSON.append(line);
            }

            reader.close();
            return stringJSON.toString();
        }
        catch (Exception one){
            a.setStatusText(" Error al intentar acceder a la API ");
            return "Error al intentar acceder a la API";
        }
    }
}
