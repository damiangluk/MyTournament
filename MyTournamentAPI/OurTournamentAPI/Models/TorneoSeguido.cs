using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace OurTournamentAPI.Models
{
    public class TorneoSeguido:Torneo
    {
        private Boolean siguiendo;

        public TorneoSeguido(int idtorneo, string nombreTorneo, string contraseniaDeAdministrador, string linkParaUnirse, bool siguiendo) :
            base( idtorneo,  nombreTorneo,  contraseniaDeAdministrador,  linkParaUnirse)
        {
            this.siguiendo = siguiendo;
        }
        public TorneoSeguido() : this(0, "", "", "", false)
        {
        }
        public bool Siguiendo { get => siguiendo; set => siguiendo = value; }
    }
}