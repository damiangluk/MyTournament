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
        private int IDEquipo;

        public int IdPartido { get => IDPartido; set => IDPartido = value; }
        public int IdUsuario { get => IDUsuario; set => IDUsuario = value; }
        public string NombreUsuario { get => Nombre; set => Nombre = value; }
        public int Cantgoles { get => CantGoles; set => CantGoles = value; }
        public int IDEquipo1 { get => IDEquipo; set => IDEquipo = value; }

        public GolesXUsuario(int iDPartido, int iDUsuario, String nombre, int cantGoles,int idequipo)
        {
            IDPartido = iDPartido;
            IDUsuario = iDUsuario;
            Nombre = nombre;
            CantGoles = cantGoles;
            IDEquipo = idequipo;
        }

        public GolesXUsuario()
        {
        }
    }
}