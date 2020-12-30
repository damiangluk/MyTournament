package com.example.ourtournament.Administracion;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.ourtournament.Administracion.Equipos.AdministrarEquipos;
import com.example.ourtournament.Administracion.Torneos.AdaptadorTorneos;
import com.example.ourtournament.Administracion.Torneos.AdministrarTorneo;
import com.example.ourtournament.Administracion.Usuario.Configuracion;
import com.example.ourtournament.Administracion.Usuario.Perfil;
import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Torneo;
import com.example.ourtournament.Objetos.Usuario;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Administracion extends Fragment {
    FragmentManager AdminFragments;
    Button btn_Perfil, btn_Config;
    TextView TXT1,TXT2;
    View VistaADevolver = null;
    AdaptadorTorneos Adaptador;
    ListView ListaSeguidos,ListaParticipados;
    MainActivity Principal;
    Preferencias P;
    Usuario U;

    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        if (VistaADevolver == null) {
            VistaADevolver = inflador.inflate(R.layout.administracion, GrupoDeLaVista, false);
            AdminFragments = getFragmentManager();

            Referencias();
            SetearListeners();
        }

        Gson gson = new Gson();
        String usuario = P.ObtenerString("InformacionUsuario", "");
        U = gson.fromJson(usuario, Usuario.class);
        AsyncTasks();
        return VistaADevolver;
    }

    private class TraerTorneosSeguidosPorUsuario extends AsyncTask<Integer,Void, ArrayList<Torneo>> {
        @Override
        protected ArrayList<Torneo> doInBackground(Integer... voids) {
            ArrayList<Torneo> ArrayTorneos = new ArrayList<>();
            String Ruta = "GetTorneosSeguidosPorUsuario/Usuario/"+U.IdUsuario;
            TareaAsincronica Tarea = new TareaAsincronica();
            String Respuesta = Tarea.RealizarTarea(Ruta);
            Gson g = new Gson();
            JsonArray VecTorneos = g.fromJson(Respuesta, JsonArray.class);

            for (int i = 0; i < VecTorneos.size(); i++) {
                JsonElement Elemento = VecTorneos.get(i);
                Gson gson = new Gson();
                Torneo T = gson.fromJson(Elemento, Torneo.class);
                ArrayTorneos.add(T);
            }
            return ArrayTorneos;
        }
        protected void onPostExecute(final ArrayList<Torneo> ArrayTorneos)
        {
            int Cantidad=ArrayTorneos.size();
            if (Cantidad<1)
            {
                ListaSeguidos.setVisibility(View.GONE);
                TXT2.setVisibility(View.GONE);
            }else
            {
                Adaptador = new AdaptadorTorneos(getContext(), R.layout.item_lista_torneos_seguidos, ArrayTorneos,U.IdUsuario,Principal,false);
                ListaSeguidos.getLayoutParams().height = 140 * 6;
                ListaSeguidos.setAdapter(Adaptador);
            }
        }
    }

    private class TraerTorneosParticipadosPorUsuario extends AsyncTask<Integer,Void,ArrayList<Torneo>> {
        @Override
        protected ArrayList<Torneo> doInBackground(Integer... voids) {
            ArrayList<Torneo> ArrayTorneos = new ArrayList<>();
            String Ruta = "GetTorneosParticipadosPorUsuario/Usuario/"+U.IdUsuario;
            TareaAsincronica Tarea = new TareaAsincronica();
            String Respuesta = Tarea.RealizarTarea(Ruta);
            Gson g = new Gson();
            JsonArray VecTorneos = g.fromJson(Respuesta, JsonArray.class);

            for (int i = 0; i < VecTorneos.size(); i++) {
                JsonElement Elemento = VecTorneos.get(i);
                Gson gson = new Gson();
                Torneo T = gson.fromJson(Elemento, Torneo.class);
                ArrayTorneos.add(T);
            }
            return ArrayTorneos;
        }
        @SuppressLint("SetTextI18n")
        protected void onPostExecute(final ArrayList<Torneo> ArrayTorneos)
        {
            int Cantidad=ArrayTorneos.size();
            if (Cantidad<1)
            {
                ListaParticipados.setVisibility(View.GONE);
                TXT1.setVisibility(View.GONE);
            }else
            {
                if (U.IDTipo == 4)
                {
                    TXT1.setText("Participando como creador en:");
                }else
                {
                    TXT1.setText("Participando como administrador en:");
                }
                Adaptador = new AdaptadorTorneos(getContext(), R.layout.item_lista_torneos_seguidos, ArrayTorneos,U.IdUsuario,Principal,true);
                //ListaParticipados.getLayoutParams().height = 165 * Cantidad;
                ListaParticipados.setAdapter(Adaptador);
                ListaParticipados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        AdministrarTorneo VT = new AdministrarTorneo();
                        VT.setTorneoElegido(ArrayTorneos.get(i));
                        Principal.IrAFragment(VT,true);
                    }
                });
            }

        }
    }
    private void SetearListeners() {
        btn_Perfil.setOnClickListener(clickP);
        btn_Config.setOnClickListener(clickF);
    }

    private void Referencias() {
        ListaSeguidos = VistaADevolver.findViewById(R.id.ListaTorneosSeguidos);
        ListaParticipados = VistaADevolver.findViewById(R.id.ListaTorneosParticipados);
        btn_Perfil = VistaADevolver.findViewById(R.id.btn_Perfil);
        btn_Config = VistaADevolver.findViewById(R.id.btn_Config);
        Principal = (MainActivity) getActivity();
        P = Principal.CargarSharedPreferences();

        TXT1 = VistaADevolver.findViewById(R.id.TXT1);
        TXT2 = VistaADevolver.findViewById(R.id.TXT2);
    }

    private void AsyncTasks(){
        TraerTorneosSeguidosPorUsuario TorneoXusuario = new TraerTorneosSeguidosPorUsuario();
        TorneoXusuario.execute();
        TraerTorneosParticipadosPorUsuario Tarea = new TraerTorneosParticipadosPorUsuario();
        Tarea.execute();
    }

    private View.OnClickListener clickP = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Perfil perf = new Perfil();
            Principal.IrAFragment(perf,true);
        }
    };

    private View.OnClickListener clickF = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Configuracion conf = new Configuracion();
            Principal.IrAFragment(conf,true);
        }
    };


}
