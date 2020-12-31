package com.example.ourtournament.Objetos;

public class TorneoParticipacion extends Torneo{
    public int IDParticipacion1;
    public TorneoParticipacion(int idtorneo, String nombreTorneo, String contraseniaDeAdministrador, String linkParaUnirse, int idparticipacion)
    {
        super(idtorneo, nombreTorneo,  contraseniaDeAdministrador, linkParaUnirse);
        IDParticipacion1 = idparticipacion;
    }

    public TorneoParticipacion()
    {
        this(0,"","","",0);
    }
}
