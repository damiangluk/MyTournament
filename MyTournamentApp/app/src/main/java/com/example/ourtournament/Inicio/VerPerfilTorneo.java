package com.example.ourtournament.Inicio;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.BoringLayout;
import android.text.Selection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
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
import com.example.ourtournament.Objetos.Partido;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Torneo;
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
import java.lang.invoke.VolatileCallSite;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class VerPerfilTorneo extends Fragment {
    FragmentManager AdminFragments;
    Button BTNSeguir, BTNParticipar,Volver;
    TextView Seguidores,CantNoticias,NombreTorneo;
    CircleImageView FotoPerfil;
    Boolean Seguido,Participado;
    int IdUsuario,IDEquipo;
    Torneo T;
    View VistaADevolver = null;
    ListView ListaEquipos;
    ArrayList<Equipo> ArrayEquipos;
    MainActivity Principal;
    Preferencias P;

    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        if (VistaADevolver == null) {
            VistaADevolver = inflador.inflate(R.layout.perfil_torneo, GrupoDeLaVista, false);
            AdminFragments = getFragmentManager();

            Referencias();
            SetearListeners();
            if (Participado)
            {
                BTNSeguir.setVisibility(View.GONE);
                BTNParticipar.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(10, 10, 10)));
                BTNParticipar.setText("Participando");
                BTNParticipar.setTextColor(Color.rgb(45, 104, 202));
            }else if (Seguido)
            {
                BTNSeguir.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(10, 10, 10)));
                BTNSeguir.setText("Siguiendo");
                BTNSeguir.setTextColor(Color.rgb(45, 104, 202));
            }
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

    public void SetIDTorneo(Torneo t,Boolean seguido, Boolean participado,int idusuario,int idequipo)
    {
        T = t;
        Seguido = seguido;
        Participado = participado;
        IdUsuario = idusuario;
        IDEquipo = idequipo;
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
            if (Seguido)
            {
                EliminarTorneoSeguido Tarea = new EliminarTorneoSeguido();
                Tarea.execute(IdUsuario,T.IDTorneo);
                BTNSeguir.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(45, 104, 202)));
                BTNSeguir.setText("Seguir");
                BTNSeguir.setTextColor(Color.rgb(10, 10, 10));
                Seguido=false;
                int num = P.ObtenerInt("IDTorneo",-1);
                if (num != -1 & num == T.IDTorneo)
                {
                    P.EliminarInt("IDTorneo");
                    TraerTorneosParticipadosPorUsuario T = new TraerTorneosParticipadosPorUsuario();
                    T.execute();
                }
            }else
            {
                InsertarTorneoSeguido Tarea = new InsertarTorneoSeguido();
                Tarea.execute(IdUsuario, T.IDTorneo, -1);
                BTNSeguir.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(10, 10, 10)));
                BTNSeguir.setText("Siguiendo");
                BTNSeguir.setTextColor(Color.rgb(45, 104, 202));
                Seguido=true;
                int num = P.ObtenerInt("IDTorneo",-1);
                if (num == -1)
                {
                    P.GuardarInt("IDTorneo",T.IDTorneo);
                }
            }
            AdaptadorListaEquiposPorTorneo Adaptador = new AdaptadorListaEquiposPorTorneo(Principal,R.layout.item_equipos_por_torneo,ArrayEquipos,Seguido,IDEquipo,IdUsuario);
            ListaEquipos.setAdapter(Adaptador);
        }
    };

    private View.OnClickListener clickParticipar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final Dialog dialogPersonalizado = new Dialog(Principal);
            dialogPersonalizado.setContentView(R.layout.solicitud_union_torneo);
            WindowManager.LayoutParams params = dialogPersonalizado.getWindow().getAttributes();
            params.width = 1000;
            params.height =  WindowManager.LayoutParams.WRAP_CONTENT;
            EditText Mensaje = dialogPersonalizado.findViewById(R.id.Mensaje);
            Button Enviar = dialogPersonalizado.findViewById(R.id.BTNEnviar);
            Button Cancelar = dialogPersonalizado.findViewById(R.id.BTNCancelar);
            ListView lista = dialogPersonalizado.findViewById(R.id.ListaEquipos);
            AdaptadorListaEquiposPorTorneo Adaptador = new AdaptadorListaEquiposPorTorneo(Principal,R.layout.item_equipos_por_torneo,ArrayEquipos,true,IDEquipo,IdUsuario);
            lista.setAdapter(Adaptador);
            dialogPersonalizado.show();
            Enviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogPersonalizado.cancel();
                    BTNParticipar.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(10, 10, 10)));
                    BTNParticipar.setText("En espera");
                    BTNParticipar.setTextColor(Color.rgb(45, 104, 202));
                    Participado = true;
                    BTNParticipar.setEnabled(false);
                }
            });

            Cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogPersonalizado.cancel();
                }
            });
        }
    };

    private View.OnClickListener volver = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager FM = getActivity().getFragmentManager();
            FM.popBackStack();
        }
    };

    private class TraerTorneosParticipadosPorUsuario extends AsyncTask<Integer,Void,ArrayList<Torneo>> {
        @Override
        protected ArrayList<Torneo> doInBackground(Integer... voids) {
            ArrayList<Torneo> ArrayTorneos = new ArrayList<>();
            String Ruta = "GetTorneosParticipadosPorUsuario/Usuario/"+IdUsuario;
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
                P.GuardarInt("IDTorneo",ArrayTorneos.get(Cantidad-1).IDTorneo);
            }
        }
    }

    private class TraerTorneosSeguidosPorUsuario extends AsyncTask<Integer,Void, ArrayList<Torneo>> {
        @Override
        protected ArrayList<Torneo> doInBackground(Integer... voids) {
            ArrayList<Torneo> ArrayTorneos = new ArrayList<>();
            String Ruta = "GetTorneosSeguidosPorUsuario/Usuario/"+IdUsuario;
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
            ArrayEquipos = lista;
            AdaptadorListaEquiposPorTorneo Adaptador = new AdaptadorListaEquiposPorTorneo(Principal,R.layout.item_equipos_por_torneo,lista,Seguido,IDEquipo,IdUsuario);
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

                Gson gson = new Gson();
                String json = gson.toJson(IDS);
                OutputStream OS = miConexion.getOutputStream();
                OS.write(json.getBytes());
                OS.flush();

                int ResponseCode = miConexion.getResponseCode();

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
}
