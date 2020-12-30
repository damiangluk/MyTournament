package com.example.ourtournament.Objetos;

import android.widget.ImageView;

import java.sql.Date;

public class Goleadores {
    public int IDUsuario1;
    public String NombreUsuario1;
    public String NombreEquipo1;
    public int Goles1;

    public Goleadores(int IDUsuario, String NombreUsuario, String NombreEquipo, int goles)
    {
        IDUsuario1 = IDUsuario;
        NombreUsuario1 = NombreUsuario;
        NombreEquipo1 = NombreEquipo;
        Goles1 = goles;
    }
    public Goleadores()
    {
    }
}
