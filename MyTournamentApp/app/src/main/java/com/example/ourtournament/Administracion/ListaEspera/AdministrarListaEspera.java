package com.example.ourtournament.Administracion.ListaEspera;

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
import com.example.ourtournament.Objetos.ListaEspera;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Usuario;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;

public class AdministrarListaEspera extends Fragment {

    View VistaADevolver = null;
    MainActivity Principal;
    Preferencias P;
    ListView ListaUsuarios;
    Button Volver;
    TextView Titulo;
    int IDTorneo;
    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        if (VistaADevolver == null) {
            VistaADevolver = inflador.inflate(R.layout.administracion_pantalla_universal, GrupoDeLaVista, false);
            Principal = (MainActivity) getActivity();
            P = Principal.CargarSharedPreferences();
            ListaUsuarios = VistaADevolver.findViewById(R.id.lista);
            Titulo = VistaADevolver.findViewById(R.id.Nombre);
            Volver = VistaADevolver.findViewById(R.id.Volver);
            Volver.setOnClickListener(Atras);
            Titulo.setText("Lista de espera");
        }

        IDTorneo = P.ObtenerInt("IDTorneo",0);

        if (IDTorneo != 0)
        {
            TraerListaEspera Tarea = new TraerListaEspera();
            Tarea.execute();
        }
        return VistaADevolver;
    }

    private class TraerListaEspera extends AsyncTask<Void,Void, ArrayList<ListaEspera>>
    {
        @Override
        protected ArrayList<ListaEspera> doInBackground(Void... voids) {
            ArrayList<ListaEspera> Lista= new ArrayList<>();
            try {
                String Ruta = "GetUsuariosEnEspera/Torneo/"+IDTorneo;
                TareaAsincronica Tarea = new TareaAsincronica();
                String Respuesta = Tarea.RealizarTarea(Ruta);
                Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                JsonArray VecUsuarios = g.fromJson(Respuesta, JsonArray.class);

                for (int i = 0; i < VecUsuarios.size(); i++)
                {
                    JsonElement Elemento = VecUsuarios.get(i);
                    ListaEspera LE = g.fromJson(Elemento, ListaEspera.class);
                    Lista.add(LE);
                }
            } catch (Exception ErrorOcurrido) {

                Log.d("Conexion", "Al conectar o procesar ocurrió Error: " + ErrorOcurrido.getMessage());
            }
            return Lista;
        }
        protected void onPostExecute(ArrayList<ListaEspera> lista)
        {
            AdaptadorListaEspera Adaptador = new AdaptadorListaEspera(Principal,R.layout.item_admin_lista_espera,lista);
            ListaUsuarios.setAdapter(Adaptador);
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
