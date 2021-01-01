package com.example.ourtournament.Inicio;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
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

import com.example.ourtournament.Administracion.Equipos.AdaptadorAdminEquipos;
import com.example.ourtournament.Administracion.Torneos.AdaptadorTorneos;
import com.example.ourtournament.Administracion.Torneos.AdministrarTorneo;
import com.example.ourtournament.Administracion.Usuario.Configuracion;
import com.example.ourtournament.Administracion.Usuario.Perfil;
import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Equipo;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Torneo;
import com.example.ourtournament.Objetos.Usuario;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import java.lang.invoke.VolatileCallSite;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class VerPerfilTorneo extends Fragment {
    FragmentManager AdminFragments;
    Button BTNSeguir, BTNParticipar,Volver;
    TextView Seguidores,CantNoticias,NombreTorneo;
    CircleImageView FotoPerfil;
    Torneo T;
    View VistaADevolver = null;
    ListView ListaEquipos;
    MainActivity Principal;
    Preferencias P;

    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        if (VistaADevolver == null) {
            VistaADevolver = inflador.inflate(R.layout.perfil_torneo, GrupoDeLaVista, false);
            AdminFragments = getFragmentManager();

            Referencias();
            SetearListeners();
        }
        AsyncTasks();
        return VistaADevolver;
    }

    private void SetearListeners() {
        BTNSeguir.setOnClickListener(clickSeguir);
        BTNParticipar.setOnClickListener(clickParticipar);
        Volver.setOnClickListener(volver);
    }

    private void Referencias() {
        Principal = (MainActivity) getActivity();
        P = Principal.CargarSharedPreferences();

        Seguidores = VistaADevolver.findViewById(R.id.Seguidores);
        CantNoticias = VistaADevolver.findViewById(R.id.CantNoticias);
        BTNSeguir = VistaADevolver.findViewById(R.id.BTNSeguir);
        BTNParticipar = VistaADevolver.findViewById(R.id.BTNParticipar);
        ListaEquipos = VistaADevolver.findViewById(R.id.ListaEquipos);
        NombreTorneo = VistaADevolver.findViewById(R.id.NombreTorneo);
        Volver = VistaADevolver.findViewById(R.id.Volver);
        FotoPerfil = VistaADevolver.findViewById(R.id.foto);
    }

    public void SetIDTorneo(Torneo t)
    {
        T = t;
    }

    private void AsyncTasks(){
        TraerInfoDeTorneo Tarea1 = new TraerInfoDeTorneo();
        Tarea1.execute();
        TraerEquiposPorTorneo Tarea2 = new TraerEquiposPorTorneo();
        Tarea2.execute();
    }

    private View.OnClickListener clickSeguir = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BTNSeguir.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(10, 10, 10)));
            BTNSeguir.setText("Siguiendo");
            BTNSeguir.setTextColor(Color.rgb(45, 104, 202));
        }
    };

    private View.OnClickListener clickParticipar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BTNParticipar.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(10, 10, 10)));
            BTNParticipar.setText("Participando");
            BTNParticipar.setTextColor(Color.rgb(45, 104, 202));
        }
    };

    private View.OnClickListener volver = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager FM = getActivity().getFragmentManager();
            FM.popBackStack();
        }
    };

    private class TraerEquiposPorTorneo extends AsyncTask<Integer,Void,ArrayList<Equipo>> {
        @Override
        protected ArrayList<Equipo> doInBackground(Integer... voids) {
            ArrayList<Equipo> VecPosiciones = new ArrayList<>();
            String Ruta = "GetPosiciones/Torneo/" + T.IDTorneo;
            TareaAsincronica Tarea = new TareaAsincronica();
            String Respuesta = Tarea.RealizarTarea(Ruta);
            Gson g = new Gson();
            JsonArray VecPos = g.fromJson(Respuesta, JsonArray.class);
            for (int i = 0; i < VecPos.size(); i++) {
                JsonElement Elemento = VecPos.get(i);
                Gson gson = new Gson();
                Equipo E = gson.fromJson(Elemento, Equipo.class);
                VecPosiciones.add(E);
            }
            return VecPosiciones;
        }
        @SuppressLint("SetTextI18n")
        protected void onPostExecute(ArrayList<Equipo> lista)
        {
            AdaptadorListaEquiposPorTorneo Adaptador = new AdaptadorListaEquiposPorTorneo(Principal,R.layout.item_equipos_por_torneo,lista);
            ListaEquipos.setAdapter(Adaptador);
        }
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
            NombreTorneo.setText(T.NombreTorneo);
            String Ruta = "http://10.0.2.2:55859/Imagenes/Torneos/ID" + T.IDTorneo + "_Perfil.JPG";
            Picasso.get().load(Ruta)
                    .into(FotoPerfil, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            FotoPerfil.setImageResource(R.drawable.icono_torneo);
                        }

                    });
        }
    }
}
