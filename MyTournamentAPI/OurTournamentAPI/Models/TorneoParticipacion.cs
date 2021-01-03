using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace OurTournamentAPI.Models
{
    public class TorneoParticipacion:Torneo
    {
        private int IDParticipacion;
        private int iDEquipo;

        public int IDParticipacion1 { get => IDParticipacion; set => IDParticipacion = value; }
        public int IDEquipo { get => iDEquipo; set => iDEquipo = value; }

        public TorneoParticipacion(int idtorneo, string nombreTorneo, string contraseniaDeAdministrador, string linkParaUnirse, int idparticipacion, int idequipo) :
            base( idtorneo,  nombreTorneo,  contraseniaDeAdministrador,  linkParaUnirse)
        {
            this.IDParticipacion = idparticipacion;
            this.IDEquipo = idequipo;
        }
        public TorneoParticipacion() : this(0, "", "", "", 0,0)
        {
        }

      
    }
}