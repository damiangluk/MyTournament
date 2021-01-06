package com.example.ourtournament.Inicio;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Equipo;
import com.example.ourtournament.Objetos.Partido;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Torneo;
import com.example.ourtournament.Objetos.TorneoParticipacion;
import com.example.ourtournament.Objetos.Usuario;
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
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorListaTorneos extends ArrayAdapter<TorneoParticipacion> {
    private ArrayList<TorneoParticipacion> _ListaTorneos;
    private ArrayList<Boolean> _ListaAbiertos;
    private Context _Contexto;
    private int _Resource;
    private Preferencias _P;
    private Usuario U;
    private Boolean Participando = false;
    private MainActivity Principal;

    public AdaptadorListaTorneos(Context contexto, int Resource, ArrayList<TorneoParticipacion> ListaTorneos, Preferencias P,MainActivity principal) {
        super(contexto, Resource, ListaTorneos);
        this._ListaTorneos = ListaTorneos;
        this._Contexto = contexto;
        this._Resource = Resource;
        this.Principal = principal;
        String usuario =  P.ObtenerString("InformacionUsuario","");
        if (usuario.length()>1)
        {
            Gson g = new Gson();
            U = g.fromJson(usuario,Usuario.class);
        }
        _P = P;
        LLenarListaBoleans(ListaTorneos.size());
    }

    public void LLenarListaBoleans(int Cantidad)
    {
        _ListaAbiertos = new ArrayList<>();
        for (int i=0;i<Cantidad;i++)
        {
            if(_ListaTorneos.get(i).IDParticipacion1 > 1)
            {
                Participando = true;
            }
            _ListaAbiertos.add(false);
        }
    }

    @SuppressLint("ViewHolder")
    public View getView(final int pos, View VistaADevolver, ViewGroup GrupoActual) {
        LayoutInflater MiInflador;
        if (VistaADevolver == null) {
            MiInflador = LayoutInflater.from(this._Contexto);
            VistaADevolver = MiInflador.inflate(_Resource, null);
        }

        final Boolean[] Siguiendo = {false};
        final Boolean[] Participando={false};
        final ListView lista;
        final Button Seguir,Participar;
        final CircleImageView FotoPerfil;
        TextView NombreTorneo;

        FotoPerfil = VistaADevolver.findViewById(R.id.PerfilTorneo);
        NombreTorneo = VistaADevolver.findViewById(R.id.Torneo);
        Participar = VistaADevolver.findViewById(R.id.Participar);

        Seguir = VistaADevolver.findViewById(R.id.BTNSeguir);
        lista = VistaADevolver.findViewById(R.id.list);
        final TorneoParticipacion T = getItem(pos);

        if (T.IDParticipacion1 == 1) {
            Siguiendo[0] = true;
            Seguir.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(33, 36, 35)));
            Seguir.setText("Siguiendo");
            Seguir.setTextColor(Color.rgb(60, 188, 128));
        } else if(T.IDParticipacion1 > 1){
            Participando[0] = true;
            Seguir.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(33, 36, 35)));
            Seguir.setText("Participando");
            Seguir.setTextColor(Color.rgb(60, 188, 128));
            Seguir.setEnabled(false);
        }else
        {
            Seguir.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(60, 188, 128)));
            Seguir.setText("seguir");
            Seguir.setTextColor(Color.rgb(0, 0, 0));
        }

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
        NombreTorneo.setText(T.NombreTorneo);

        Seguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (T.IDParticipacion1 != 1) {
                    InsertarTorneoSeguido Tarea = new InsertarTorneoSeguido();
                    Tarea.execute(U.IdUsuario, T.IDTorneo, 0);
                    Siguiendo[0] = true;
                    Seguir.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(33, 36, 35)));
                    Seguir.setText("Siguiendo");
                    Seguir.setTextColor(Color.rgb(60, 188, 128));
                    T.IDParticipacion1 = 1;
                    int num = _P.ObtenerInt("IDTorneo",-1);
                    if (num == -1)
                    {
                        _P.GuardarInt("IDTorneo",T.IDTorneo);
                    }
                } else{
                    EliminarTorneoSeguido Tarea = new EliminarTorneoSeguido();
                    Tarea.execute(U.IdUsuario,T.IDTorneo);
                    Siguiendo[0] = false;
                    Seguir.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(60, 188, 128)));
                    Seguir.setText("seguir");
                    Seguir.setTextColor(Color.rgb(0, 0, 0));
                    T.IDParticipacion1 = 0;
                    int num = _P.ObtenerInt("IDTorneo",-1);
                    if (num != -1 || num == T.IDTorneo)
                    {
                        _P.EliminarInt("IDTorneo");
                        TraerTorneosParticipadosPorUsuario T = new TraerTorneosParticipadosPorUsuario();
                        T.execute();
                    }
                    /*
                    AlertDialog.Builder Alerta = new AlertDialog.Builder(getContext());
                    Alerta.setPositiveButton("Aceptar",Aceptar);
                    Alerta.setNegativeButton("Cancelar",null);
                    Alerta.setTitle("Dejar de seguir");
                    Alerta.setMessage("Deseas dejar de seguir al torneo "+T.NombreTorneo);
                    Alerta.create();
                    Alerta.show();
                     */
                }
            }
        });

        Participar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerPerfilTorneo VPT = new VerPerfilTorneo();
                VPT.SetIDTorneo(T,Siguiendo[0], Participando[0],U.IdUsuario,T.IDEquipo);
                Principal.IrAFragment(VPT,true);
            }
        });
        return VistaADevolver;
    }

    DialogInterface.OnClickListener Aceptar = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            /*
            EliminarTorneoSeguido Tarea = new EliminarTorneoSeguido();
            Tarea.execute(IDUsuario,T.IDTorneo);
            Seguir.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(60, 188, 128)));
            Seguir.setText("seguir");
            Seguir.setTextColor(Color.rgb(0, 0, 0));
            T.Siguiendo = false;
             */
        }
    };

    private class InsertarTorneoSeguido extends AsyncTask<Integer, Void, Boolean> {
        Boolean Resultado;
        @Override
        protected Boolean doInBackground(Integer... IDS) {
            try {
                String miURL = "http://10.0.2.2:55859/api/InsertTorneosSeguidos";
                Log.d("conexion", "estoy accediendo a la ruta " + miURL);
                URL miRuta = new URL(miURL);
                HttpURLConnection miConexion = (HttpURLConnection) miRuta.openConnection();
                miConexion.setDoInput(true);
                miConexion.setDoOutput(true);
                miConexion.setRequestProperty("Content-Type", "application/json");
                miConexion.setRequestProperty("Accept", "application/json");
                miConexion.setRequestMethod("POST");

                Log.d("conexion","Estoy insertando IDUsuario: "+IDS[0]+" y IDTorneo: "+IDS[1]);
                Gson gson = new Gson();
                String json = gson.toJson(IDS);
                OutputStream OS = miConexion.getOutputStream();
                OS.write(json.getBytes());
                OS.flush();

                int ResponseCode = miConexion.getResponseCode();
                Log.d("conexion", String.valueOf(ResponseCode));
                /*
                switch (ResponseCode)
                {
                    case 200:
                        Context C = getContext();
                        Toast T = Toast.makeText(C,"Seguimos el torneo con exito!", Toast.LENGTH_SHORT);
                        T.show();
                        break;
                    case 400:
                        Context Co = getContext();
                        Toast To = Toast.makeText(Co,"Bad request", Toast.LENGTH_SHORT);
                        To.show();
                        break;
                    case 404:
                        Context Con = getContext();
                        Toast Toa = Toast.makeText(Con,"Not Found", Toast.LENGTH_SHORT);
                        Toa.show();
                        break;
                }
                 */
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

        protected void onPostExecute(Boolean R) {
            Log.d("conexion", String.valueOf(R));
        }
    }

    private class EliminarTorneoSeguido extends AsyncTask<Integer, Void, Boolean> {
        Boolean Resultado;
        @Override
        protected Boolean doInBackground(Integer... IDS) {
            try {
                String miURL = "http://10.0.2.2:55859/api/DeleteTorneosSeguidos";
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

        protected void onPostExecute(Boolean R) {
            Log.d("conexion", String.valueOf(R));
        }
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
            if (Cantidad>0)
            {
                _P.GuardarInt("IDTorneo",ArrayTorneos.get(Cantidad-1).IDTorneo);
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
        protected void onPostExecute(final ArrayList<Torneo> ArrayTorneos)
        {
            int Cantidad=ArrayTorneos.size();
            if (Cantidad<1)
            {
                TraerTorneosSeguidosPorUsuario T = new TraerTorneosSeguidosPorUsuario();
                T.execute();
            }else
            {
                _P.GuardarInt("IDTorneo",ArrayTorneos.get(Cantidad-1).IDTorneo);
            }
        }
    }
}
