package com.example.ourtournament.Objetos;

public class ListaEspera {

    public int IdUsuario;
    public String NombreUsuario;
    public int Idequipo;
    public String NombreEquipo;
    public String Mensaje;

    public ListaEspera(int IDUsuario, String nombreUsuario, int IDequipo, String nombreEquipo, String mensaje)
    {
        IdUsuario = IDUsuario;
        NombreUsuario = nombreUsuario;
        Idequipo = IDequipo;
        NombreEquipo = nombreEquipo;
        Mensaje = mensaje;
    }

    public ListaEspera()
    {
    }
}
