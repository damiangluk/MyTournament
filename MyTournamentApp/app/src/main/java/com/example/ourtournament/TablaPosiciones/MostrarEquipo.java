package com.example.ourtournament.TablaPosiciones;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Equipo;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Usuario;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MostrarEquipo extends Fragment {
    TextView Nombre,Puntos, PJugados, GolesAFavor, GolesEnContra,CantJug;
    ImageView Foto;
    Button Volver;
    ListView ListaJugadores;
    AdaptadorListaJugadores Adaptador;
    MainActivity Principal;
    Equipo E;
    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        final View VistaADevolver;
        VistaADevolver = inflador.inflate(R.layout.un_equipo, GrupoDeLaVista, false);

        Nombre  = VistaADevolver.findViewById(R.id.Nombre);
        Puntos = VistaADevolver.findViewById(R.id.Puntos);
        PJugados = VistaADevolver.findViewById(R.id.PartidosJugados);
        GolesAFavor = VistaADevolver.findViewById(R.id.GolesAFavor);
        GolesEnContra = VistaADevolver.findViewById(R.id.GolesEnContra);
        CantJug = VistaADevolver.findViewById(R.id.CantJug);
        Foto = VistaADevolver.findViewById(R.id.foto);
        Volver = VistaADevolver.findViewById(R.id.Volver);
        ListaJugadores = VistaADevolver.findViewById(R.id.ListaJugadores);
        Principal = (MainActivity) getActivity();

        TraerJugadores Tarea = new TraerJugadores();
        Tarea.execute();
        Nombre.setText(E.Nombre);
        Puntos.setText(String.valueOf(E.Puntos));
        PJugados.setText(String.valueOf(E.PartidosJugados));
        GolesAFavor.setText(String.valueOf(E.GolesAFavor));
        GolesEnContra.setText(String.valueOf(E.GolesEnContra));
        TareaAsincronica Tareas = new TareaAsincronica();
        Tareas.CargarFoto("Equipos/ID"+E.IDEquipo+"_Escudo.PNG",Foto,"https://p.kindpng.com/picc/s/154-1546024_ehr-my-team-team-png-icon-transparent-png.png");

        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager FM = getActivity().getFragmentManager();
                FM.popBackStack();
            }
        });
        return VistaADevolver;
    }

    public void SetEquipoElegido(Equipo equipo)
    {
        E = equipo;
    }

    private class TraerJugadores extends AsyncTask<Void,Void,ArrayList<Usuario>>
    {
        @Override
        protected ArrayList<Usuario> doInBackground(Void... voids) {
            ArrayList<Usuario> listajugadores= new ArrayList<>();
            String Ruta = "GetJugadoresXEquipos/Equipo/"+E.IDEquipo;
            TareaAsincronica Tarea = new TareaAsincronica();
            String Respuesta = Tarea.RealizarTarea(Ruta);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            JsonArray VecJugadores = gson.fromJson(Respuesta, JsonArray.class);
            for (int i = 0; i < VecJugadores.size(); i++)
            {
                JsonElement Elemento = VecJugadores.get(i);
                Usuario U = gson.fromJson(Elemento, Usuario.class);
                listajugadores.add(U);
                Log.d("conexion",U.NombreUsuario);
            }
            return listajugadores;
        }
        protected void onPostExecute(ArrayList<Usuario> lista)
        {
            Log.d("conexion","Traje: "+lista.size()+" usuarios");
            Adaptador = new AdaptadorListaJugadores(Principal,R.layout.item_lista_jugadores,lista);
            ListaJugadores.setAdapter(Adaptador);
            ListaJugadores.getLayoutParams().height = 100 * lista.size();
            String Texto;
            if (lista.size()>1){Texto=lista.size()+" Jugadores";}else if(lista.size()==1){Texto="1 Jugador";}else{Texto="No hay jugadores";}
            CantJug.setText(Texto);
        }
    }
}