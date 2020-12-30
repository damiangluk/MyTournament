using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace OurTournamentAPI.Models
{
    public class Noticia
    {
        private int IDnoticia;
        private int IDtorneo;
        private string titulo;
        private string descripcion;
        private bool destacada;
        private int foto;
        private DateTime fecha;

        public int IDNoticia { get => IDnoticia; set => IDnoticia = value; }
        public int IDTorneo { get => IDtorneo; set => IDtorneo = value; }
        public string Titulo { get => titulo; set => titulo = value; }
        public string Descripcion { get => descripcion; set => descripcion = value; }
        public bool Destacada { get => destacada; set => destacada = value; }
        public int Foto { get => foto; set => foto = value; }
        public DateTime Fecha { get => fecha; set => fecha = value; }

        public Noticia(int idNoticia, int Idtorneo, string Titulo, string Descripcion, bool Destacada, int Foto, DateTime Fecha)
        {
            IDnoticia = idNoticia;
            IDtorneo = Idtorneo;
            titulo = Titulo;
            descripcion = Descripcion;
            destacada = Destacada;
            foto = Foto;
            fecha = Fecha;
        }
        public Noticia()
        {
        }
    }
}