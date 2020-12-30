package com.example.ourtournament.Loguearse;

import androidx.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ourtournament.Administracion.Torneos.AdaptadorTorneos;
import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Goleadores;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Torneo;
import com.example.ourtournament.Objetos.Usuario;
import com.example.ourtournament.R;
import com.example.ourtournament.TablaGoleadores.AdaptadorListaGoleadores;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class IniciarSesion extends Fragment {
    FragmentManager AdminFragments;
    FragmentTransaction TransaccionesDeFragment;
    Button ConfirmarLogueo;
    EditText Nombre, Contrasenia;
    TextView Incorrecto;
    MainActivity Principal;
    Preferencias P;
    Usuario Us;
    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        View VistaADevolver;
        VistaADevolver = inflador.inflate(R.layout.iniciar_sesion, GrupoDeLaVista, false);
        AdminFragments = getFragmentManager();
        ConfirmarLogueo = VistaADevolver.findViewById(R.id.button);
        Nombre = VistaADevolver.findViewById(R.id.Nombre);
        Contrasenia = VistaADevolver.findViewById(R.id.Contrasenia);
        Incorrecto = VistaADevolver.findViewById(R.id.Texto);
        Principal = (MainActivity) getActivity();
        P = Principal.CargarSharedPreferences();

        ConfirmarLogueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Nombre.getText().length() > 2 & Contrasenia.getText().length() > 2) {
                    TraerUsuario Tarea = new TraerUsuario();
                    Tarea.execute();
                } else {
                    Incorrecto.setText("Debes ingresar minimo 3 caracteres en cada espacio");
                    Incorrecto.setVisibility(View.VISIBLE);
                }
            }
        });

        return VistaADevolver;
    }
    private class TraerUsuario extends AsyncTask<Void, Void, Usuario> {

        @Override
        protected Usuario doInBackground(Void... voids) {
            String Ruta = "GetUsuarioPorContrasenia/Usuario/" + Nombre.getText() + "/contrasenia/" + Contrasenia.getText();
            TareaAsincronica Tarea = new TareaAsincronica();
            String Respuesta = Tarea.RealizarTarea(Ruta);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Us = gson.fromJson(Respuesta, Usuario.class);
            return Us;
        }

        protected void onPostExecute(Usuario U) {
            if (U.IdUsuario > 0) {
                Log.d("conexion","GuardoShared");
                Gson gson = new Gson();
                String json = gson.toJson(U);
                P.GuardarString("InformacionUsuario", json);
                TraerTorneosParticipadosPorUsuario Tarea = new TraerTorneosParticipadosPorUsuario();
                Tarea.execute();
            } else {
                Incorrecto.setText("El nombre de usuario y/o la contraseña son incorrectos");
                Incorrecto.setVisibility(View.VISIBLE);
                Contrasenia.setText("");
            }
        }
    }

    private class TraerTorneosParticipadosPorUsuario extends AsyncTask<Void, Void, ArrayList<Torneo>> {
        @Override
        protected ArrayList<Torneo> doInBackground(Void... voids) {
            ArrayList<Torneo> ArrayTorneos = new ArrayList<>();
            String Ruta = "GetTorneosParticipadosPorUsuario/Usuario/" + Us.IdUsuario;
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

        protected void onPostExecute(final ArrayList<Torneo> ArrayTorneos) {
            if (ArrayTorneos.size()<1)
            {
                TraerTorneosSeguidosPorUsuario Tarea = new TraerTorneosSeguidosPorUsuario();
                Tarea.execute();
            }else
            {
                P.GuardarInt("IDTorneo",ArrayTorneos.get(0).IDTorneo);
                Principal.CargarGeneral();
            }
        }
    }

    private class TraerTorneosSeguidosPorUsuario extends AsyncTask<Void,Void, ArrayList<Torneo>> {
        @Override
        protected ArrayList<Torneo> doInBackground(Void... voids) {
            ArrayList<Torneo> ArrayTorneos = new ArrayList<>();
            String Ruta = "GetTorneosSeguidosPorUsuario/Usuario/"+Us.IdUsuario;
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
            if (ArrayTorneos.size()>0)
            {
                P.GuardarInt("IDTorneo",ArrayTorneos.get(0).IDTorneo);
            }
            Principal.CargarGeneral();
        }
    }
}