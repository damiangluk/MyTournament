using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace OurTournamentAPI.Models
{
    public class Partido
    {

        private int _IDPartido;
        private DateTime _FechaDeEncuentro;
        private int _IDEquipoLocal;
        private int _IDEquipoVisitante;
        private String _NombreEquipoLocal;
        private String _NombreEquipoVisitante;
        private int _GolesLocal;
        private int _GolesVisitante;
        private int _IDTorneo;
        private int _Jornada;

        public int IDTorneo { get => this._IDTorneo; set => this._IDTorneo = value; }
        public int IDPartido { get => _IDPartido; set => _IDPartido = value; }
        public DateTime FechaDeEncuentro { get => _FechaDeEncuentro; set => _FechaDeEncuentro = value; }
        public String NombreEquipoLocal { get => _NombreEquipoLocal; set => _NombreEquipoLocal = value; }
        public String NombreEquipoVisitante { get => _NombreEquipoVisitante; set => _NombreEquipoVisitante = value; }
        public int GolesLocal { get => _GolesLocal; set => _GolesLocal = value; }
        public int GolesVisitante { get => _GolesVisitante; set => _GolesVisitante = value; }
        public int Jornada { get => _Jornada; set => _Jornada = value; }
        public int IDEquipoLocal { get => _IDEquipoLocal; set => _IDEquipoLocal = value; }
        public int IDEquipoVisitante { get => _IDEquipoVisitante; set => _IDEquipoVisitante = value; }

        public Partido(int IDPartido, DateTime FechaDeEncuentro, String NombreEquipoLocal, String NombreEquipoVisitante, int GolesLocal, int GolesVisitante, int IDTorneo, int Jornada, int IDEquipoLocal, int IDEquipoVisitante)
        {
            _IDPartido = IDPartido;
            _NombreEquipoLocal = NombreEquipoLocal;
            _NombreEquipoVisitante = NombreEquipoVisitante;
            _IDEquipoLocal = IDEquipoLocal;
            _IDEquipoVisitante = IDEquipoVisitante;
            _GolesLocal = GolesLocal;
            _GolesVisitante = GolesVisitante;
            _FechaDeEncuentro = FechaDeEncuentro;
            _Jornada = Jornada;
            _IDTorneo = IDTorneo;
        }

        public Partido()
        {

        }



    }
}