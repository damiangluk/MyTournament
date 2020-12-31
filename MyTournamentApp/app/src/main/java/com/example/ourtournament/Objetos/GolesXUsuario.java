package com.example.ourtournament.Objetos;

public class GolesXUsuario {

    public int IdPartido;
    public int IdUsuario;
    public String NombreUsuario;
    public int Cantgoles;
    public int IDEquipo1;

    public GolesXUsuario(int iDPartido, int iDUsuario, String nombre, int cantGoles,int idequipo)
    {
        IdPartido = iDPartido;
        IdUsuario = iDUsuario;
        NombreUsuario = nombre;
        Cantgoles = cantGoles;
        IDEquipo1 = idequipo;
    }
    public GolesXUsuario()
    {
    }
}
