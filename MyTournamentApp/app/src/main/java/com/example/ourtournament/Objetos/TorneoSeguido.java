package com.example.ourtournament.Objetos;

public class TorneoSeguido extends Torneo{
    public Boolean Siguiendo;
    public TorneoSeguido(int idtorneo, String nombreTorneo, String contraseniaDeAdministrador, String linkParaUnirse, boolean siguiendo)
    {
        super(idtorneo, nombreTorneo,  contraseniaDeAdministrador, linkParaUnirse);
        Siguiendo = siguiendo;
    }

    public TorneoSeguido()
    {
        this(0,"","","",false);
    }
}
