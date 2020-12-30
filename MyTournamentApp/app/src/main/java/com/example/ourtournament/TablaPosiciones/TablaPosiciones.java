package com.example.ourtournament.TablaPosiciones;

import androidx.annotation.Nullable;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ourtournament.Fixture.Fixture;
import com.example.ourtournament.Fixture.MostrarPartido;
import com.example.ourtournament.Loguearse.Loguear;
import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Equipo;
import com.example.ourtournament.Objetos.Partido;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TablaPosiciones extends Fragment {

    View VistaADevolver = null;
    FragmentManager AdminFragments;
    ListView listaposiciones;
    ImageView Carga;
    Button SeguirTorneos;
    int ID;
    MainActivity Principal;
    Preferencias P;
    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        if (VistaADevolver == null)
        {
            VistaADevolver = inflador.inflate(R.layout.tabla_de_posiciones, GrupoDeLaVista, false);
            AdminFragments=getFragmentManager();
            listaposiciones = VistaADevolver.findViewById(R.id.ListaDePosiciones);
            Carga = VistaADevolver.findViewById(R.id.Carga);
            SeguirTorneos = VistaADevolver.findViewById(R.id.SeguirTorneos);
            Principal = (MainActivity) getActivity();
            P = Principal.CargarSharedPreferences();
        }
        ID = P.ObtenerInt("IDTorneo",-1);

        if(ID!=-1)
        {
            Carga.setVisibility(View.VISIBLE);
            ObjectAnimator Animacion = ObjectAnimator.ofFloat(Carga,"rotation",0,8000);
            Animacion.setDuration(7000);
            AnimatorSet SetDeAnimacion = new AnimatorSet();
            SetDeAnimacion.play(Animacion);
            SetDeAnimacion.start();
            SeguirTorneos.setVisibility(View.GONE);
            listaposiciones.setVisibility(View.VISIBLE);
            TraerPosiciones Tarea = new TraerPosiciones();
            Tarea.execute(ID);
        }else
        {
            SeguirTorneos.setVisibility(View.VISIBLE);
            Carga.setVisibility(View.GONE);
            listaposiciones.setVisibility(View.GONE);
            SeguirTorneos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Principal.IrABuscador();
                }
            });
        }
        return VistaADevolver;
    }

    private class TraerPosiciones extends AsyncTask<Integer,Void,ArrayList<Equipo>> {
        @Override
        protected ArrayList<Equipo> doInBackground(Integer... voids) {
            ArrayList<Equipo> VecPosiciones = new ArrayList<>();
            String Ruta = "GetPosiciones/Torneo/" + ID;
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
        protected void onPostExecute(final ArrayList<Equipo> VecPosiciones)
        {
            AdaptadorListaPosiciones Adaptador = new AdaptadorListaPosiciones(Principal,R.layout.item_lista_posiciones,VecPosiciones);
            listaposiciones.setAdapter(Adaptador);
            listaposiciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    MostrarEquipo MP = new MostrarEquipo();
                    MP.SetEquipoElegido(VecPosiciones.get(i));
                    Principal.IrAFragment(MP,true);
                }
            });
            Carga.setVisibility(View.GONE);
        }
    }

}
