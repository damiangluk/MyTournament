using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace OurTournamentAPI.Models
{
    public class Usuario
    {
        private int _IdUsuario;
        private String _NombreUsuario;
        private String _Contrasenia;
        private DateTime _FechaDeNacimiento;
        private String _Email;
        private int _GolesEnTorneo;
        private int _IDTipo;

        public int IdUsuario { get => _IdUsuario; set => _IdUsuario = value; }
        public string NombreUsuario { get => _NombreUsuario; set => _NombreUsuario = value; }
        public string Contrasenia { get => _Contrasenia; set => _Contrasenia = value; }
        public DateTime FechaDeNacimiento { get => _FechaDeNacimiento; set => _FechaDeNacimiento = value; }
        public string Email { get => _Email; set => _Email = value; }
        public int GolesEnTorneo { get => _GolesEnTorneo; set => _GolesEnTorneo = value; }
        public int IDTipo { get => _IDTipo; set => _IDTipo = value; }

        public Usuario(int IdUsuario, string NombreUsuario, string Contrasenia, DateTime FechaDeNacimiento, string Email, int GolesEnTorneo, int IDTipo)
        {
            _IdUsuario = IdUsuario;
            _NombreUsuario = NombreUsuario;
            _Contrasenia = Contrasenia;
            _FechaDeNacimiento = FechaDeNacimiento;
            _Email = Email;
            _GolesEnTorneo = GolesEnTorneo;
            _IDTipo = IDTipo;
        }

        public Usuario()
        {

        }
    }
}