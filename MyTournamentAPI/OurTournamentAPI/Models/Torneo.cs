using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace OurTournamentAPI.Models
{
    public class Torneo
    {
        private int _IDTorneo;
        private String _nombreTorneo;
        private String _contraseniaDeAdministrador;
        private String _linkParaUnirse;

        public int IDTorneo { get => _IDTorneo; set => _IDTorneo = value; }
        public String NombreTorneo { get => _nombreTorneo; set => _nombreTorneo = value; }
        public String ContraseniaDeAdministrador { get => _contraseniaDeAdministrador; set => _contraseniaDeAdministrador = value; }
        public String LinkParaUnirse { get => _linkParaUnirse; set => _linkParaUnirse = value; }

        public Torneo(int idtorneo, string nombreTorneo, string contraseniaDeAdministrador, string linkParaUnirse)
        {
            _IDTorneo = idtorneo;
            _nombreTorneo= nombreTorneo;
            _contraseniaDeAdministrador = contraseniaDeAdministrador;
           _linkParaUnirse = linkParaUnirse;
        }

        public Torneo() : this(0, "", "", "")
        {
           
        }



    }
}