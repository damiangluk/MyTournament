package com.example.ourtournament.Objetos;

public class Torneo
{
    public int IDTorneo;
    public String NombreTorneo;
    public String ContraseniaDeAdministrador;
    public String LinkParaUnirse;

    public Torneo(int idtorneo, String nombreTorneo, String contraseniaDeAdministrador, String linkParaUnirse)
    {
        IDTorneo = idtorneo;
        NombreTorneo= nombreTorneo;
        ContraseniaDeAdministrador = contraseniaDeAdministrador;
        LinkParaUnirse = linkParaUnirse;
    }

    public Torneo()
    {
        this(0,"","","");
    }
}