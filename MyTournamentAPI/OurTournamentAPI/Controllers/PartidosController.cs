using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace OurTournamentAPI.Controllers
{
    public class PartidosController : ApiController
    {
        [HttpGet]
        [Route("api/GetJornadas/Torneo/{IDTorneo}")]
        public IHttpActionResult TraerJornadasPorTorneo(int IDTorneo)
        {
            List<int> Lista = new List<int>();
            QQSM Conexion = new QQSM();
            Lista = Conexion.TraerJornadasPorTorneo(IDTorneo);
            if (Lista != null)
            {
                return Ok(Lista);
            }
            else
            {
                return NotFound();
            }

        }

        [HttpGet]
        [Route("api/GetPartidosPorJornada/Jornada/{IDJornada}/Torneo/{IDTorneo}")]
        public IHttpActionResult ObtenerPartidosPorJornadas(int IDJornada, int IDTorneo)
        {
            List<Models.Partido> Lista = new List<Models.Partido>();
            QQSM Conexion = new QQSM();
            Lista = Conexion.TraerPartidosPorJornada(IDJornada, IDTorneo);
            if (Lista != null)
            {
                return Ok(Lista);
            }
            else
            {
                return NotFound();
            }
        }

        [HttpGet]
        [Route("api/GetPartidos/Torneo/{IDTorneo}")]
        public IHttpActionResult ObtenerPartidosPorJornadas(int IDTorneo)
        {
            List<Models.Partido> Lista = new List<Models.Partido>();
            QQSM Conexion = new QQSM();
            Lista = Conexion.TraerPartidos(IDTorneo);
            if (Lista != null)
            {
                return Ok(Lista);
            }
            else
            {
                return NotFound();
            }
        }

        [HttpGet]
        [Route("api/GetGolesXPartido/Partido/{IDPartido}")]
        public IHttpActionResult ObtenerGolesXPartido(int IDPartido)
        {
            List<Models.GolesXUsuario> Lista = new List<Models.GolesXUsuario>();
            QQSM Conexion = new QQSM();
            Lista = Conexion.TraerGolesXusuario(IDPartido);
            if (Lista != null)
            {
                return Ok(Lista);
            }
            else
            {
                return NotFound();
            }
        }

        [System.Web.Http.Route("api/InsertPartidos")]
        [System.Web.Http.HttpPost]
        public IHttpActionResult InsertarPartidos(Models.Partido P)
        {
            int Devolver;
            QQSM Conexion = new QQSM();
            Models.Partido partido = new Models.Partido(P.IDPartido, P.FechaDeEncuentro, P.NombreEquipoLocal, P.NombreEquipoVisitante, P.GolesLocal, P.GolesVisitante, P.IDTorneo, P.Jornada,P.IDEquipoLocal,P.IDEquipoVisitante);
            Devolver = Conexion.InsertarPartidos(partido);
            return Ok(Devolver);
        }

    }
}
