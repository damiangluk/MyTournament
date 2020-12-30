using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace OurTournamentAPI.Models
{
    public class GolesXUsuario
    {
        private int IDPartido;
        private int IDUsuario;
        private String Nombre;
        private int CantGoles;
        private String NombreEquipo;

        public int IdPartido { get => IDPartido; set => IDPartido = value; }
        public int IdUsuario { get => IDUsuario; set => IDUsuario = value; }
        public string NombreUsuario { get => Nombre; set => Nombre = value; }
        public int Cantgoles { get => CantGoles; set => CantGoles = value; }
        public string Nombreequipo { get => NombreEquipo; set => NombreEquipo = value; }

        public GolesXUsuario(int iDPartido, int iDUsuario, String nombre, int cantGoles,String nombreequipo)
        {
            IDPartido = iDPartido;
            IDUsuario = iDUsuario;
            Nombre = nombre;
            CantGoles = cantGoles;
            NombreEquipo = nombreequipo;
        }

        public GolesXUsuario()
        {
        }
    }
}