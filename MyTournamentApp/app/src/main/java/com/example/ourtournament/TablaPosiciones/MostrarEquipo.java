package com.example.ourtournament.TablaPosiciones;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.audiofx.DynamicsProcessing;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Equipo;
import com.example.ourtournament.Objetos.GolesXUsuario;
import com.example.ourtournament.Objetos.Partido;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.TorneoSeguido;
import com.example.ourtournament.Objetos.Usuario;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
        String Ruta = "http://10.0.2.2:55859/Imagenes/Equipos/ID"+E.IDEquipo+"_Escudo.PNG";
        Picasso.get().load(Ruta).into(Foto);

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