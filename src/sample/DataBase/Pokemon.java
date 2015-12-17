package sample.DataBase;

/**
 * Created by sergi on 12/12/15.
 */
public class Pokemon {

    private String id;
    private String nombre;
    private String imagen;
    private String peso;
    private String hp;
    private String url_referencia;

    // Constructor

    public Pokemon(String id, String nombre, String imagen, String peso, String hp, String url_referencia){
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.peso = peso;
        this.hp = hp;
        this.url_referencia = url_referencia;
    }

    public Pokemon(){} // Constructor vacío para poder crear objetos Pokemon vacias


    // Getters

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public String getPeso() {
        return peso;
    }

    public String getHp() {
        return hp;
    }

    public String getUrl_referencia() {
        return url_referencia;
    }

    // Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public void setUrl_referencia(String link_referencia) {
        this.url_referencia = link_referencia;
    }

    public String toStringDetailed()
    {
        return "\nPOKEMON " + id + " DETAILS\nName: " + nombre + "\nLifepoints: " + hp + "\nWeight: " + peso + "\nImage: " + imagen + "\nResource uri: " + url_referencia;
    }
}
