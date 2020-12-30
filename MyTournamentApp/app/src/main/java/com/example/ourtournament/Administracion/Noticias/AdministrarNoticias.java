package com.example.ourtournament.Administracion.Noticias;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.ourtournament.Administracion.Equipos.AdaptadorAdminEquipos;
import com.example.ourtournament.Administracion.Equipos.AdministrarEquipos;
import com.example.ourtournament.Administracion.ListaEspera.AdaptadorListaEspera;
import com.example.ourtournament.Administracion.ListaEspera.AdministrarListaEspera;
import com.example.ourtournament.Inicio.AdaptadorListaNoticias;
import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Noticia;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Usuario;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;

public class AdministrarNoticias extends Fragment {
    View VistaADevolver = null;
    MainActivity Principal;
    Preferencias P;
    ListView ListaNoticias;
    Button Volver,Agregar;
    TextView Titulo;
    int IDTorneo;
    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        if (VistaADevolver == null) {
            VistaADevolver = inflador.inflate(R.layout.administracion_pantalla_universal, GrupoDeLaVista, false);
            Principal = (MainActivity) getActivity();
            P = Principal.CargarSharedPreferences();
            ListaNoticias = VistaADevolver.findViewById(R.id.lista);
            Titulo = VistaADevolver.findViewById(R.id.Nombre);
            Volver = VistaADevolver.findViewById(R.id.Volver);
            Volver.setOnClickListener(Atras);
            Agregar = VistaADevolver.findViewById(R.id.Agregar);
            Agregar.setOnClickListener(IrAAgregar);
            Titulo.setText("Noticias");
        }

        IDTorneo = P.ObtenerInt("IDTorneo",0);

        if (IDTorneo != 0)
        {
            TraerNoticias Tarea = new TraerNoticias();
            Tarea.execute();
        }
        return VistaADevolver;
    }

    private class TraerNoticias extends AsyncTask<Void, Void, ArrayList<Noticia>> {
        @Override
        protected ArrayList<Noticia> doInBackground(Void... voids) {
            ArrayList<Noticia> ListaNoticias = new ArrayList<>();
            String Ruta = "GetNoticiasPorTorneo/Torneo/"+IDTorneo;
            TareaAsincronica Tarea = new TareaAsincronica();
            String Respuesta = Tarea.RealizarTarea(Ruta);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            JsonArray VecNoticias = gson.fromJson(Respuesta, JsonArray.class);

            for (int i = 0; i < VecNoticias.size(); i++) {
                JsonElement Elemento = VecNoticias.get(i);
                Noticia N = gson.fromJson(Elemento, Noticia.class);
                ListaNoticias.add(N);
            }
            return ListaNoticias;
        }

        protected void onPostExecute(ArrayList<Noticia> listaN) {
            AdaptadorAdminNoticias Adaptador = new AdaptadorAdminNoticias(Principal,R.layout.item_admin_equipos,listaN);
            ListaNoticias.setAdapter(Adaptador);
        }
    }

    private View.OnClickListener Atras = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager FM = getActivity().getFragmentManager();
            FM.popBackStack();
        }
    };
    private View.OnClickListener IrAAgregar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CrearNoticia CN = new CrearNoticia();
            Principal.IrAFragment(CN,true);
        }
    };
}
