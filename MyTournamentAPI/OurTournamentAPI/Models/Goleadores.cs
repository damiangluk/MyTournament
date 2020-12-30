using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace OurTournamentAPI.Models
{
    public class Goleadores
    {
        private int IDUsuario;
        private String NombreUsuario;
        private String NombreEquipo;
        private int Goles;

        public int IDUsuario1 { get => IDUsuario; set => IDUsuario = value; }
        public string NombreUsuario1 { get => NombreUsuario; set => NombreUsuario = value; }
        public string NombreEquipo1 { get => NombreEquipo; set => NombreEquipo = value; }
        public int Goles1 { get => Goles; set => Goles = value; }

        public Goleadores(int idusuario, String Nombreusuario, String Nombreequipo, int goles)
        {
            IDUsuario = idusuario;
            NombreUsuario = Nombreusuario;
            NombreEquipo = Nombreequipo;
            Goles = goles;
        }
        public Goleadores()
        {
        }
    }
}