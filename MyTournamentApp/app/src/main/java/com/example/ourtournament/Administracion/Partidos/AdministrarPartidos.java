package com.example.ourtournament.Administracion.Partidos;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Partido;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;

public class AdministrarPartidos extends Fragment {
    View VistaADevolver = null;
    MainActivity Principal;
    Preferencias P;
    ListView ListaPartidos;
    Button Volver;
    TextView Titulo;
    int IDTorneo;
    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        if (VistaADevolver == null) {
            VistaADevolver = inflador.inflate(R.layout.administracion_pantalla_universal, GrupoDeLaVista, false);
            Principal = (MainActivity) getActivity();
            P = Principal.CargarSharedPreferences();
            ListaPartidos = VistaADevolver.findViewById(R.id.lista);
            Titulo = VistaADevolver.findViewById(R.id.Nombre);
            Volver = VistaADevolver.findViewById(R.id.Volver);
            Volver.setOnClickListener(Atras);
            Titulo.setText("Partidos");
        }

        IDTorneo = P.ObtenerInt("IDTorneo",0);

        if (IDTorneo != 0)
        {
            TraerPartidos Tarea = new TraerPartidos();
            Tarea.execute();
        }
        return VistaADevolver;
    }

    private class TraerPartidos extends AsyncTask<Void,Void, ArrayList<Partido>> {
        @Override
        protected ArrayList<Partido> doInBackground(Void... voids) {
            ArrayList<Partido> listaPartidos = new ArrayList<>();
            String Ruta = "GetPartidos/Torneo/"+ IDTorneo;
            TareaAsincronica Tarea = new TareaAsincronica();
            String Respuesta = Tarea.RealizarTarea(Ruta);
            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            JsonArray VecPartidos = g.fromJson(Respuesta, JsonArray.class);

            for (int i = 0; i < VecPartidos.size(); i++) {
                JsonElement Elemento = VecPartidos.get(i);
                Partido Part = g.fromJson(Elemento, Partido.class);
                listaPartidos.add(Part);
            }
            return listaPartidos;
        }
        protected void onPostExecute(final ArrayList<Partido> lista)
        {
            AdaptadorAdminPartidos Adaptador = new AdaptadorAdminPartidos(Principal,R.layout.item_admin_partidos,lista);
            ListaPartidos.setAdapter(Adaptador);
        }
    }

    private View.OnClickListener Atras = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager FM = getActivity().getFragmentManager();
            FM.popBackStack();
        }
    };
}