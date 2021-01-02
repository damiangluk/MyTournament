using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace OurTournamentAPI.Models
{
    public class ListaEspera
    {
        private int idUsuario;
        private String nombreUsuario;
        private int idequipo;
        private String nombreEquipo;
        private String mensaje;

        public int IdUsuario { get => idUsuario; set => idUsuario = value; }
        public string NombreUsuario { get => nombreUsuario; set => nombreUsuario = value; }
        public int Idequipo { get => idequipo; set => idequipo = value; }
        public string NombreEquipo { get => nombreEquipo; set => nombreEquipo = value; }
        public string Mensaje { get => mensaje; set => mensaje = value; }

        public ListaEspera(int IDUsuario, String nombreUsuario, int IDequipo, String nombreEquipo, String Mensaje)
        {
            idUsuario = IDUsuario;
            NombreUsuario = nombreUsuario;
            idequipo = IDequipo;
            NombreEquipo = nombreEquipo;
            mensaje = Mensaje;
        }

        public ListaEspera()
        {
        }
    }
}