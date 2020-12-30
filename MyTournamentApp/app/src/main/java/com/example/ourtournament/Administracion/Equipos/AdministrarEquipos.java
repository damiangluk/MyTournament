package com.example.ourtournament.Administracion.Equipos;

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

import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Equipo;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Torneo;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;

public class AdministrarEquipos extends Fragment {

    MainActivity Principal;
    Preferencias P;
    TextView Titulo;
    View VistaADevolver = null;
    ListView ListaEquipos;
    Button Volver;
    Torneo T;

    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        if (VistaADevolver == null) {
            VistaADevolver = inflador.inflate(R.layout.administracion_pantalla_universal, GrupoDeLaVista, false);
            Principal = (MainActivity) getActivity();
            P = Principal.CargarSharedPreferences();
            ListaEquipos = VistaADevolver.findViewById(R.id.lista);
            Titulo = VistaADevolver.findViewById(R.id.Nombre);
            Volver = VistaADevolver.findViewById(R.id.Volver);
            Volver.setOnClickListener(Atras);
            Titulo.setText("Equipos");
        }

        TraerEquiposPorTorneo Tarea = new TraerEquiposPorTorneo();
        Tarea.execute();

        return VistaADevolver;
    }

    private class TraerEquiposPorTorneo extends AsyncTask<Void,Void, ArrayList<Equipo>>
    {
        @Override
        protected ArrayList<Equipo> doInBackground(Void... voids) {
            ArrayList<Equipo> ListaEquipos= new ArrayList<>();
            try {
                String Ruta = "GetEquiposXTorneo/Torneo/"+T.IDTorneo;
                TareaAsincronica Tarea = new TareaAsincronica();
                String Respuesta = Tarea.RealizarTarea(Ruta);
                Gson g = new Gson();
                JsonArray VecEquipos = g.fromJson(Respuesta, JsonArray.class);

                for (int i = 0; i < VecEquipos.size(); i++)
                {
                    JsonElement Elemento = VecEquipos.get(i);
                    Gson gson = new Gson();
                    Equipo E = gson.fromJson(Elemento, Equipo.class);
                    ListaEquipos.add(E);
                }
            } catch (Exception ErrorOcurrido) {

                Log.d("Conexion", "Al conectar o procesar ocurrió Error: " + ErrorOcurrido.getMessage());
            }
            return ListaEquipos;
        }
        protected void onPostExecute(ArrayList<Equipo> lista)
        {
            AdaptadorAdminEquipos Adaptador = new AdaptadorAdminEquipos(Principal,R.layout.item_admin_equipos,lista);
            ListaEquipos.setAdapter(Adaptador);
        }
    }

    private View.OnClickListener Atras = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager FM = getActivity().getFragmentManager();
            FM.popBackStack();
        }
    };

    public void setTorneoElegido(Torneo torneoElegido) {
        T = torneoElegido;
    }
}