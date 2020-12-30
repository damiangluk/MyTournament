package com.example.ourtournament.Objetos;

import android.widget.ImageView;

import java.sql.Date;

public class Noticia {
    public int IDNoticia;
    public String Torneo;
    public String Titulo;
    public String Descripcion;
    public Boolean Destacada;
    public int Foto;
    public Date Fecha;
    public Noticia(int idNoticia, String torneo, String titulo, String descripcion, Boolean destacada, int foto, Date fecha)
    {
        IDNoticia = idNoticia;
        Torneo = torneo;
        Titulo = titulo;
        Descripcion = descripcion;
        Destacada = destacada;
        Foto = foto;
        Fecha = fecha;
    }
    public Noticia() {
    }

}
