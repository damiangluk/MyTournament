using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace OurTournamentAPI.Models
{
    public class TorneoParticipacion:Torneo
    {
        private int IDParticipacion;

        public int IDParticipacion1 { get => IDParticipacion; set => IDParticipacion = value; }

        public TorneoParticipacion(int idtorneo, string nombreTorneo, string contraseniaDeAdministrador, string linkParaUnirse, int idparticipacion) :
            base( idtorneo,  nombreTorneo,  contraseniaDeAdministrador,  linkParaUnirse)
        {
            this.IDParticipacion = idparticipacion;
        }
        public TorneoParticipacion() : this(0, "", "", "", 0)
        {
        }

      
    }
}