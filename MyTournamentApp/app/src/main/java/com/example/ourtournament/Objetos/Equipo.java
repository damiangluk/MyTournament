package com.example.ourtournament.Objetos;

public class Equipo
{
    public int IDEquipo;
    public String Nombre;
    public int PartidosJugados;
    public int Puntos;
    public int GolesAFavor;
    public int GolesEnContra;
    public int IDTorneo;

    public Equipo(int idequipo, String nombre, int partidosJugados, int puntos, int golesAFavor, int golesEnContra, int idtorneo)
    {
        IDEquipo = idequipo;
        Nombre = nombre;
        PartidosJugados = partidosJugados;
        Puntos = puntos;
        GolesAFavor = golesAFavor;
        GolesEnContra = golesEnContra;
        IDTorneo = idtorneo;
    }
    public Equipo()
    {
    }
}