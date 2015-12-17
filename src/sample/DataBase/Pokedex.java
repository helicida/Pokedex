package sample.DataBase;

import java.util.ArrayList;

/**
 * Created by sergi on 12/12/15.
 */
public class Pokedex {


    private String nombre;
    private String creado;
    private String ultimaModificacion;
    private ArrayList<Pokemon> pokemons;

    public Pokedex(String nombre, String creado, String ultimaModificacion, ArrayList<Pokemon> pokemons){
        this.nombre = nombre;
        this.creado = creado;
        this.ultimaModificacion = ultimaModificacion;
        this.pokemons = pokemons;
    }

    public Pokedex(){}

    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getCreado() {
        return creado;
    }

    public String getUltimaModificacion() {
        return ultimaModificacion;
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    // Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }

    public void setUltimaModificacion(String ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    public void setPokemons(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    // Metodo toString

    public String toString(){

        String allPokemons = "{";

        for(int x=0; x<pokemons.size(); x++){
            allPokemons=allPokemons + pokemons.get(x).toString();
        }

        allPokemons=allPokemons+"}";
        return "\nPOKEDEX DETAILS\nCreated: " + creado + "\nLast Modified: " + ultimaModificacion +"\nName: " + nombre + "\nPokemons: " + allPokemons;
    }
}


