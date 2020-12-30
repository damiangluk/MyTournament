using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace OurTournamentAPI.Controllers
{
    public class TorneosController : ApiController
    {
        [HttpGet]
        [Route("api/GetTorneoPorID/Torneo/{id}")]
        public IHttpActionResult ObtenerTorneoPorID(int id)
        {
            Models.Torneo T = new Models.Torneo();
            QQSM Conexion = new QQSM();
            T = Conexion.TraerTorneoPorID(id);
            if(T!= null)
            {
                return Ok(T);
            }

            else
            {
                return NotFound();
            }
            
        }

        [HttpGet]
        [Route("api/GetTorneosPorNombre/Nombre/{Nombre}/Usuario/{IDUsuario}")]
        public IHttpActionResult ObtenerTorneosPorNombre(String Nombre,int IDUsuario)
        {
            List<Models.TorneoSeguido> T = new List<Models.TorneoSeguido>();
            QQSM Conexion = new QQSM();
            T = Conexion.TraerTorneosPorNombre(Nombre,IDUsuario);
            if (T != null)
            {
                return Ok(T);
            }
            else
            {
                return NotFound();
            }

        }

        [HttpGet]
        [Route("api/GetSeguidoresPorTorneo/Torneo/{IDTorneo}")]
        public IHttpActionResult ObtenerTorneosPorNombre(int IDTorneo)
        {
            QQSM Conexion = new QQSM();
            return Ok(Conexion.TraerSeguidoresPorTorneo(IDTorneo));
        }
        /*
        [System.Web.Http.Route("api/InsertTorneo")]
        [System.Web.Http.HttpPost]
        public IHttpActionResult InsertarTorneos(List<int> ListaTorneos)
        {
            bool Devolver;
            QQSM Conexion = new QQSM();
            Devolver = Conexion.InsertarTorneos(ListaTorneos); //IDUsuario, IDTorneo,IDEquipo
            return Ok(Devolver);
        }
        */
    }
}
