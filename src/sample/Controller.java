package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import sample.DataBase.PokemonDAO;

public class Controller {

    // Nuevo objeto de nuestro DataAccesObject. Para hacer consultas a nuestra base de datos
    PokemonDAO DAO = new PokemonDAO();

    // Todos los elementos de JAVAFX
    public TextArea textoBuscar;            // TextArea en el que se introduce el nombre del Pokemon a buscar
    public Button botonBuscar;              // Boton que hay que pulsar para hacer la busqueda
    public Button botonVolver;              // Boton para volver de los details al menú
    public ListView<String> listView;       // List View que contiene todos los pokemons
    public ImageView imagenPokemon;         // Imagen del Pokemon
    public Text pokemonNameText;            // Texto con el titulo e ID del pokemon
    public Text datosPokemon;               // Detalles del Pokemon
    public Text statusText;                 // Texto en la parte inferior de la ventana que muestra el estado de la base de datos
    public ObservableList<String> items = FXCollections.observableArrayList();  // ObservableList para nuestro listView

    // Métodos

    public void initialize(){

        refrescarLista(null);   // Refrescamos la lista

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {  // Y hacemos el listener que controla los clicks

            public void handle(MouseEvent event) {

                int idPokemonSeleccionado = listView.getSelectionModel().getSelectedIndex() + 1;    // La id de nuestro pokemon sera igual al indice de seleccion de nuestro listView

                listView.setVisible(false); // Ocultamos el list view

                // Y mostramos todos los elementos de detalles
                mostrarDetalles(true);

                // Aquí establecemos que textos e imagen mostrar
                imagenPokemon.setImage(new Image(DAO.getURLImagen(idPokemonSeleccionado)));    // Establece la imagen del Pokemon bajandola de la API
                pokemonNameText.setText(DAO.getNombrePokemon(idPokemonSeleccionado));   // Carga el nombre del Pokemon
                datosPokemon.setText(DAO.getDetallesPokemon(idPokemonSeleccionado));    // Carga los detalles del Pokemon
            }
        });

    }

    public void buscar(ActionEvent actionEvent){

        mostrarMenu(false); // Ocultamos el menu

        // Mostramos los elementos de los detalles del pokemon
        mostrarDetalles(true);  // Mostramos los detalles

        try{
            // pokemonNameText.setText(DAO.getNombrePokemon(idPokemonSeleccionado)); FUNCIÓN BUSCAR por terminar
            datosPokemon.setText(DAO.buscarPokemon(textoBuscar.getText().toLowerCase()));
            imagenPokemon.setImage(new Image(DAO.getURLImagen(textoBuscar.getText().toLowerCase())));
        }
        catch (Exception one){
            refrescarLista(null);   // Refrescamos la lista
            items.clear();          // Limpiamos el observableList
            items.add("\n No se ha podido encontrar al Pokemon");   // Añadimos al observableList que no se ha podido encontrar ningun pokemon
            listView.setItems(items);   // Y fijamos el observableList a la lista
        }
    }

    public void about(ActionEvent actionEvent) {
        // Dialogo de informacion sobre la APP
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pokedex v0.1");
        alert.setHeaderText("Pokedex PokeApi Client v0.1");
        alert.setContentText("Developed by Sergi Barjola." + "\n" + "Project aviable on gitHub:" + "\n" + "https://github.com/helicida/Pokedex");
        alert.showAndWait();
    }

    public void refrescarLista(ActionEvent actionEvent) {

        mostrarDetalles(false); // Mostramos los detalles

        items.clear();  // Limpiamos el arrayList

        for (int iterador = 1; iterador <= 718; iterador++) {
            items.add(DAO.getPokemonMenu(iterador));    // Le añadimos todos los pokemons descargados al observableList
        }

        listView.setItems(items);   // Y se lo acoplamos al array list
    }

    public void actualizarBBDD(ActionEvent actionEvent) {

    }

    public void cerrar(ActionEvent actionEvent){
        Platform.exit();    // Cerramos la aplicación
    }

    public void mostrarMenu (boolean mostrar){
        if(mostrar == true){
            statusText.setVisible(true);    // Mostramos el texto de estado de descarga de la BBDD
            listView.setVisible(true);      // Mostramos el list view
            textoBuscar.setVisible(true);   // Mostramos el TextArea para buscar
            botonBuscar.setVisible(true);   // Mostramos el boton de búsqueda
        }
        else{
            statusText.setVisible(false);    // Ocultamos el texto de estado de descarga de la BBDD
            listView.setVisible(false);      // Ocultamos el list view
            textoBuscar.setVisible(false);   // Ocultamos el TextArea para buscar
            botonBuscar.setVisible(false);   // Ocultamos el boton de búsqueda
        }
    }

    public void mostrarDetalles (boolean mostrar){
        if(mostrar == true){
            pokemonNameText.setVisible(true);   // Mostramos el nombre del pokemon con su ID
            datosPokemon.setVisible(true);      // Mostramos los detalles del pokemon
            imagenPokemon.setVisible(true);     // Mostramos la imagen del pokemon
            botonVolver.setVisible(true);       // Mostramos el boton volver para retroceder al menu
        }
        else{
            pokemonNameText.setVisible(false);   // Ocultamos el nombre del pokemon con su ID
            datosPokemon.setVisible(false);      // Ocultamos los detalles del pokemon
            imagenPokemon.setVisible(false);     // Ocultamos la imagen del pokemon
            botonVolver.setVisible(false);       // Ocultamos el boton volver para retroceder al menu
        }
    }

    public void volverMenu(ActionEvent actionEvent) {

        // Ocultamos los elementos de los detalles
        mostrarDetalles(false);

        // Mostramos los elementos de nuestro menu
        mostrarMenu(true);
    }
}
