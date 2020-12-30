package com.example.ourtournament.Objetos;

import java.util.Date;

public class Partido
{

    public int IDPartido;
    public Date FechaDeEncuentro;
    public String NombreEquipoLocal;
    public String NombreEquipoVisitante;
    public int IDEquipoLocal;
    public int IDEquipoVisitante;
    public int GolesLocal;
    public int GolesVisitante;
    public int Jornada;


    public Partido(int idpartido, Date fechadeencuentro, String nombrelocal, String nombrevisitante, int goleslocal, int golesvisitante,int jornada,int idequipoLocal, int idequipoVisitante)
    {
        IDPartido = idpartido;
        NombreEquipoLocal = nombrelocal;
        NombreEquipoVisitante = nombrevisitante;
        GolesLocal = goleslocal;
        GolesVisitante = golesvisitante;
        FechaDeEncuentro = fechadeencuentro;
        Jornada = jornada;
        IDEquipoLocal = idequipoLocal;
        IDEquipoVisitante = idequipoVisitante;

    }

    public Partido()
    {

    }



}