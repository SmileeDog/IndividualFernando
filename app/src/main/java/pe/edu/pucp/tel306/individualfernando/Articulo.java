package pe.edu.pucp.tel306.individualfernando;

import java.io.Serializable;
import java.util.ArrayList;

public class Articulo implements Serializable {
    private String titulo;
    private String cuerpo;
    private String fecha;
    private String autor;
    private String pk;
    private String direccion;


    private ArrayList<Comentario> comentarioArrayList;
    //----------------------------------------------------------------------------------------------
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    //----------------------------------------------------------------------------------------------

    public String getPk() { return pk; }
    public void setPk(String pk) { this.pk = pk; }

    //----------------------------------------------------------------------------------------------
    public ArrayList<Comentario> getComentarioArrayList() {
        return comentarioArrayList;
    }

    public void setComentarioArrayList(ArrayList<Comentario> comentarioArrayList) {
        this.comentarioArrayList = comentarioArrayList;
    }
    //----------------------------------------------------------------------------------------------
    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

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
