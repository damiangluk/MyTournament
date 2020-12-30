using OurTournamentAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace OurTournamentAPI.Controllers
{
    public class NoticiaController : ApiController
    {

        [HttpGet]
        [Route("api/GetNoticiasPorTorneo/Torneo/{IDTorneo}")]
        public IHttpActionResult TraerNoticiasPorTorneo(int IDTorneo)
        {
            List<Models.Noticia> Lista = new List<Models.Noticia>();
            QQSM Conexion = new QQSM();
            Lista = Conexion.TraerNoticiasPorTorneo(IDTorneo);
            if (Lista != null)
            {
                return Ok(Lista);
            }
            else
            {
                return NotFound();
            }
        }

        [System.Web.Http.Route("api/InsertNoticia")]
        [System.Web.Http.HttpPost]
        public IHttpActionResult InsertarNoticia(Models.Noticia N)
        {
            int Devolver;
            QQSM Conexion = new QQSM();
            Models.Noticia Not = new Models.Noticia(N.IDTorneo, N.IDNoticia, N.Titulo, N.Descripcion, N.Destacada, N.Foto, N.Fecha);
            Devolver = Conexion.InstertarNoticias(Not);
            return Ok(Devolver);
        }
    }
}
