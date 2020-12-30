package com.example.ourtournament.TablaGoleadores;

import androidx.annotation.Nullable;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Goleadores;
import com.example.ourtournament.Objetos.Partido;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
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

public class TablaDeGoleadores extends Fragment {

    FragmentManager AdminFragments;
    ListView ListaView;
    Button SeguirTorneos;
    int IdTorneo;
    ImageView Carga;
    MainActivity Principal;
    Preferencias P;
    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        final View VistaADevolver;
        VistaADevolver = inflador.inflate(R.layout.tabla_de_goleadores, GrupoDeLaVista, false);
        AdminFragments=getFragmentManager();
        ListaView = VistaADevolver.findViewById(R.id.ListaDeGoleadores);
        Carga = VistaADevolver.findViewById(R.id.Carga);
        SeguirTorneos = VistaADevolver.findViewById(R.id.SeguirTorneos);

        ObjectAnimator Animacion = ObjectAnimator.ofFloat(Carga,"rotation",0,8000);
        Animacion.setDuration(7000);
        AnimatorSet SetDeAnimacion = new AnimatorSet();
        SetDeAnimacion.play(Animacion);
        SetDeAnimacion.start();
        Principal = (MainActivity) getActivity();
        P = Principal.CargarSharedPreferences();
        IdTorneo = P.ObtenerInt("IDTorneo",-1);
        if(IdTorneo != -1)
        {
            TraerGoleadores ASync = new TraerGoleadores();
            ASync.execute();
        }else {
            ListaView.setVisibility(View.GONE);
            Carga.setVisibility(View.GONE);
            SeguirTorneos.setVisibility(View.VISIBLE);
            SeguirTorneos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Principal.IrABuscador();
                }
            });
        }

        return VistaADevolver;
    }

    private class TraerGoleadores extends AsyncTask<Integer,Void,ArrayList<Goleadores>> {
        @Override
        protected ArrayList<Goleadores> doInBackground(Integer... voids) {
            ArrayList<Goleadores> VecGoleadores = new ArrayList<>();
            String Ruta = "GetGoleadores/Torneo/"+IdTorneo;
            TareaAsincronica Tarea = new TareaAsincronica();
            String Respuesta = Tarea.RealizarTarea(Ruta);
            Gson g = new Gson();
            JsonArray VecGol = g.fromJson(Respuesta, JsonArray.class);

            for (int i = 0; i < VecGol.size(); i++) {
                JsonElement Elemento = VecGol.get(i);
                Gson gson = new Gson();
                Goleadores G = gson.fromJson(Elemento, Goleadores.class);
                VecGoleadores.add(G);
            }
            return VecGoleadores;
        }
        protected void onPostExecute(final ArrayList<Goleadores> VecGoleadores)
        {
            AdaptadorListaGoleadores Adapter = new AdaptadorListaGoleadores(Principal,R.layout.item_tabla_goleadores,VecGoleadores);
            ListaView.setAdapter(Adapter);
            ListaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    MostrarUsuario MU = new MostrarUsuario();
                    MU.SetGoleadorElegido(VecGoleadores.get(i));
                    Principal.IrAFragment(MU,true);
                }
            });
            Carga.setVisibility(View.GONE);
        }
    }
}
