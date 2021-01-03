package com.example.ourtournament.Objetos;

public class TorneoParticipacion extends Torneo{
    public int IDParticipacion1;
    public int IDEquipo;
    public TorneoParticipacion(int idtorneo, String nombreTorneo, String contraseniaDeAdministrador, String linkParaUnirse, int idparticipacion,int idequipo)
    {
        super(idtorneo, nombreTorneo,  contraseniaDeAdministrador, linkParaUnirse);
        IDParticipacion1 = idparticipacion;
        IDEquipo = idequipo;
    }

    public TorneoParticipacion()
    {
        this(0,"","","",0,0);
    }
}
