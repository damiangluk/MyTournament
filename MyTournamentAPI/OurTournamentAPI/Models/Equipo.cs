using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace OurTournamentAPI.Models
{
    public class Equipo
    {
        private int _IDEquipo;
        private String _Nombre;
        private int _PartidosJugados;
        private int _Puntos;
        private int _GolesAFavor;
        private int _GolesEnContra;
        private int _IDTorneo;

        public int IDEquipo { get => _IDEquipo; set => _IDEquipo = value; }
        public String Nombre { get => _Nombre; set => _Nombre = value; }
        public int PartidosJugados { get => _PartidosJugados; set => _PartidosJugados = value; }
        public int Puntos { get => _Puntos; set => _Puntos = value; }
        public int GolesAFavor { get => _GolesAFavor; set => _GolesAFavor = value; }
        public int GolesEnContra { get => _GolesEnContra; set => _GolesEnContra = value; }
        public int IDTorneo { get => _IDTorneo; set => _IDTorneo = value; }

        public Equipo(int IDEquipo, String Nombre, int PartidosJugados, int Puntos, int GolesAFavor, int GolesEnContra, int IDTorneo)
        {
            _IDEquipo = IDEquipo;
            _Nombre = Nombre;
            _PartidosJugados = PartidosJugados;
            _Puntos = Puntos;
            _GolesAFavor = GolesAFavor;
            _GolesEnContra = GolesEnContra;
            _IDTorneo = IDTorneo;
        }
        public Equipo()
        {
        }
    }
}