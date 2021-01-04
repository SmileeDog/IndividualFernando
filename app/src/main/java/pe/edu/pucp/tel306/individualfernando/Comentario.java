package pe.edu.pucp.tel306.individualfernando;

import java.io.Serializable;

public class Comentario implements Serializable {
    private String cuerpo;
    private String fecha;
    private String autor;
    //----------------------------------------------------------------------------------------------
    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
