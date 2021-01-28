package com.example.ourtournament.Administracion.Torneos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ourtournament.Inicio.AdaptadorListaTorneos;
import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Torneo;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorTorneos extends ArrayAdapter<Torneo> {
    private ArrayList<Torneo> _ListaTorneos;
    private Context _Contexto;
    private int _Resource;
    private int _IDUsuario;
    MainActivity _Principal;
    Preferencias P;
    private Boolean _AccesoAdmin;

    public AdaptadorTorneos(Context contexto, int Resource, ArrayList<Torneo> ListaTorneos,int IDUsuario,MainActivity Principal,Boolean AccesoAdmin) {
        super(contexto, Resource, ListaTorneos);
        _ListaTorneos = ListaTorneos;
        _Contexto = contexto;
        _Resource = Resource;
        _IDUsuario = IDUsuario;
        _Principal = Principal;
        _AccesoAdmin = AccesoAdmin;
        P = Principal.CargarSharedPreferences();
    }

    @SuppressLint("ViewHolder")
    public View getView(final int pos, View VistaADevolver, ViewGroup GrupoActual) {
        LayoutInflater MiInflador;
        final ConstraintLayout Item;
        if (VistaADevolver == null) {
            MiInflador = LayoutInflater.from(this._Contexto);
            VistaADevolver = MiInflador.inflate(_Resource, null);
        }

        Button Administrar = VistaADevolver.findViewById(R.id.Administrar);
        Button Eliminar = VistaADevolver.findViewById(R.id.Eliminar);
        final CircleImageView FotoPerfil = VistaADevolver.findViewById(R.id.PerfilTorneo);
        TextView NombreTorneo = VistaADevolver.findViewById(R.id.Torneo);

        final Torneo T = getItem(pos);

        TareaAsincronica Tareas = new TareaAsincronica();
        Tareas.CargarFoto("Torneos/ID" + T.IDTorneo + "_Perfil.JPG",FotoPerfil,1);
        NombreTorneo.setText(T.NombreTorneo);

        if (_AccesoAdmin)
        {
            Administrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdministrarTorneo VT = new AdministrarTorneo();
                    VT.setTorneoElegido(T);
                    _Principal.IrAFragment(VT,true);
                }
            });
        }else
        {
            Administrar.setVisibility(View.GONE);
        }

        Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EliminarTorneoSeguido Tarea = new EliminarTorneoSeguido();
                Tarea.execute(_IDUsuario,T.IDTorneo);
                _ListaTorneos.remove(pos);
                notifyDataSetChanged();
                int num = P.ObtenerInt("IDTorneo",-1);
                if (num != -1 || num == T.IDTorneo)
                {
                    P.EliminarInt("IDTorneo");
                    TraerTorneosParticipadosPorUsuario T = new TraerTorneosParticipadosPorUsuario();
                    T.execute();
                }
            }
        });

        return VistaADevolver;
    }


    private class EliminarTorneoSeguido extends AsyncTask<Integer, Void, Boolean> {
        Boolean Resultado;
        @Override
        protected Boolean doInBackground(Integer... IDS) {
            try {
                String miURL = "http://181.47.112.9/MyTournament/api/DeleteTorneosSeguidos";
                Log.d("conexion", "estoy accediendo a la ruta " + miURL);
                URL miRuta = new URL(miURL);
                HttpURLConnection miConexion = (HttpURLConnection) miRuta.openConnection();
                miConexion.setDoInput(true);
                miConexion.setDoOutput(true);
                miConexion.setRequestProperty("Content-Type", "application/json");
                miConexion.setRequestProperty("Accept", "application/json");
                miConexion.setRequestMethod("DELETE");

                Gson gson = new Gson();
                String json = gson.toJson(IDS);
                OutputStream OS = miConexion.getOutputStream();
                OS.write(json.getBytes());
                OS.flush();

                int ResponseCode = miConexion.getResponseCode();
                Log.d("conexion", String.valueOf(ResponseCode));

                InputStream lector = miConexion.getInputStream();
                InputStreamReader lectorJSon = new InputStreamReader(lector, "utf-8");
                JsonParser parseador = new JsonParser();
                Resultado = parseador.parse(lectorJSon).getAsBoolean();
                miConexion.disconnect();
            } catch (Exception ErrorOcurrido) {
                Log.d("Conexion", "Al conectar o procesar ocurrió Error: " + ErrorOcurrido.getMessage());
            }
            return Resultado;
        }

        protected void onPostExecute(Boolean R) { }
    }

    private class TraerTorneosSeguidosPorUsuario extends AsyncTask<Integer,Void, ArrayList<Torneo>> {
        @Override
        protected ArrayList<Torneo> doInBackground(Integer... voids) {
            ArrayList<Torneo> ArrayTorneos = new ArrayList<>();
            String Ruta = "GetTorneosSeguidosPorUsuario/Usuario/"+_IDUsuario;
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
            if (Cantidad>0)
            {
                P.GuardarInt("IDTorneo",ArrayTorneos.get(Cantidad-1).IDTorneo);
            }
        }
    }

    private class TraerTorneosParticipadosPorUsuario extends AsyncTask<Integer,Void,ArrayList<Torneo>> {
        @Override
        protected ArrayList<Torneo> doInBackground(Integer... voids) {
            ArrayList<Torneo> ArrayTorneos = new ArrayList<>();
            String Ruta = "GetTorneosParticipadosPorUsuario/Usuario/"+_IDUsuario;
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
            if (Cantidad>0)
            {
                P.GuardarInt("IDTorneo",ArrayTorneos.get(Cantidad-1).IDTorneo);
            }else
            {
                TraerTorneosSeguidosPorUsuario Tarea = new TraerTorneosSeguidosPorUsuario();
                Tarea.execute();
            }
        }
    }
}
