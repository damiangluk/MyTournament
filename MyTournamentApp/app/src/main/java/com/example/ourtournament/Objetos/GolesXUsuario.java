package com.example.ourtournament.Objetos;

public class GolesXUsuario {

    public int IdPartido;
    public int IdUsuario;
    public String NombreUsuario;
    public int Cantgoles;
    public String Nombreequipo;

    public GolesXUsuario(int iDPartido, int iDUsuario, String nombre, int cantGoles,String nombreequipo)
    {
        IdPartido = iDPartido;
        IdUsuario = iDUsuario;
        NombreUsuario = nombre;
        Cantgoles = cantGoles;
        Nombreequipo = nombreequipo;
    }
    public GolesXUsuario()
    {
    }
}
