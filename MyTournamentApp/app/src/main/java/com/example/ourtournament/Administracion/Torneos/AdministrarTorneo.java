package com.example.ourtournament.Administracion.Torneos;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.ourtournament.Administracion.Equipos.AdministrarEquipos;
import com.example.ourtournament.Administracion.ListaEspera.AdministrarListaEspera;
import com.example.ourtournament.Administracion.Noticias.AdministrarNoticias;
import com.example.ourtournament.Administracion.Partidos.AdministrarPartidos;
import com.example.ourtournament.Fixture.AdaptadorPartidos;
import com.example.ourtournament.Fixture.MostrarPartido;
import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Partido;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Torneo;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;

public class AdministrarTorneo extends Fragment{

    Torneo T = null;
    MainActivity Principal;
    Preferencias P;
    ImageView Perfil;
    Button Volver, ListaEspera,Equipos,Partidos,Noticias;
    TextView NombreTorneo,Seguidores,CantNoticias;
    View VistaADevolver = null;

    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        if (VistaADevolver == null)
        {
            VistaADevolver = inflador.inflate(R.layout.administrar_torneo, GrupoDeLaVista, false);
            Perfil = VistaADevolver.findViewById(R.id.foto);
            Volver = VistaADevolver.findViewById(R.id.Volver);
            NombreTorneo = VistaADevolver.findViewById(R.id.NombreTorneo);
            Equipos = VistaADevolver.findViewById(R.id.Equipos);
            ListaEspera = VistaADevolver.findViewById(R.id.ListaDeEspera);
            Partidos = VistaADevolver.findViewById(R.id.Partidos);
            Noticias = VistaADevolver.findViewById(R.id.Noticias);
            Seguidores = VistaADevolver.findViewById(R.id.Seguidores);
            CantNoticias = VistaADevolver.findViewById(R.id.CantNoticias);
            Principal = (MainActivity) getActivity();
            P = Principal.CargarSharedPreferences();
        }

        String Ruta = "http://10.0.2.2:55859/Imagenes/Torneos/ID"+T.IDTorneo+"_Perfil.JPG";
        Picasso.get().load(Ruta)
                .into(Perfil, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Perfil.setImageResource(R.drawable.icono_torneo);
                    }

                });
        NombreTorneo.setText(T.NombreTorneo);
        Equipos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdministrarEquipos AE = new AdministrarEquipos();
                AE.setTorneoElegido(T);
                Principal.IrAFragment(AE,true);
            }
        });

        ListaEspera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdministrarListaEspera ALE = new AdministrarListaEspera();
                Principal.IrAFragment(ALE,true);
            }
        });

        Noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdministrarNoticias AN = new AdministrarNoticias();
                Principal.IrAFragment(AN,true);
            }
        });

        Partidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdministrarPartidos AP = new AdministrarPartidos();
                Principal.IrAFragment(AP,true);
            }
        });

        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager FM = getActivity().getFragmentManager();
                FM.popBackStack();
            }

        });

        TraerInfoDeTorneo Tarea = new TraerInfoDeTorneo();
        Tarea.execute();
        return VistaADevolver;
    }

    public void setTorneoElegido(Torneo torneoElegido) {
        T = torneoElegido;
    }

    private class TraerInfoDeTorneo extends AsyncTask<Void,Void, JsonArray> {
        @Override
        protected JsonArray doInBackground(Void... voids) {
            String Ruta = "GetSeguidoresPorTorneo/Torneo/"+T.IDTorneo;
            TareaAsincronica Tarea = new TareaAsincronica();
            String Respuesta = Tarea.RealizarTarea(Ruta);
            Gson g = new Gson();
            JsonArray VecInfo = g.fromJson(Respuesta, JsonArray.class);
            return VecInfo;
        }
        protected void onPostExecute(JsonArray Info)
        {
            Seguidores.setText(String.valueOf(Info.get(0)));
            CantNoticias.setText(String.valueOf(Info.get(1)));
        }
    }
}