package com.example.ourtournament.Administracion.Usuario;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ourtournament.Administracion.Administracion;
import com.example.ourtournament.Administracion.Torneos.AdaptadorTorneos;
import com.example.ourtournament.Administracion.Torneos.AdministrarTorneo;
import com.example.ourtournament.Fixture.Fixture;
import com.example.ourtournament.Loguearse.IniciarSesion;
import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Torneo;
import com.example.ourtournament.Objetos.Usuario;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Perfil extends Fragment {
    FragmentManager AdminFragments;
    TextView Nombre, Edad, Email, Contrasenia, GolesEnTorneo,TXT1,TXT2;
    ImageView foto;
    Button Volver,Configuracion;
    ListView TorneosSeguidos,TorneosParticipados;
    ArrayList<Integer> IDTorneos;
    ArrayList<String> NombreTorneos;
    Spinner Torneos;
    View VistaADevolver;
    MainActivity Principal;
    Preferencias P;
    Usuario Usu;
    TareaAsincronica Tarea;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        VistaADevolver = inflador.inflate(R.layout.un_usuario, GrupoDeLaVista, false);
        AdminFragments=getFragmentManager();

        Referencias();

        Principal = (MainActivity) getActivity();
        P = Principal.CargarSharedPreferences();
        Tarea = new TareaAsincronica();

        LlenarDatos();
        SetearListeners();

        return VistaADevolver;
    }

    private void Referencias() {
        Nombre = VistaADevolver.findViewById(R.id.Nombre);
        Edad = VistaADevolver.findViewById(R.id.Edad);
        Email = VistaADevolver.findViewById(R.id.Email);
        Contrasenia = VistaADevolver.findViewById(R.id.Contrasenia);
        GolesEnTorneo = VistaADevolver.findViewById(R.id.GolesEnTorneo);
        foto = VistaADevolver.findViewById(R.id.foto);
        Volver = VistaADevolver.findViewById(R.id.Volver);
        Configuracion = VistaADevolver.findViewById(R.id.Configuracion);
        Torneos = VistaADevolver.findViewById(R.id.Torneos);

        TXT1 = VistaADevolver.findViewById(R.id.TXT1);
        TXT2 = VistaADevolver.findViewById(R.id.TXT2);
        TorneosSeguidos = VistaADevolver.findViewById(R.id.ListaTorneosSeguidos);
        TorneosParticipados = VistaADevolver.findViewById(R.id.ListaTorneosParticipados);

        IDTorneos = new ArrayList<>();
        NombreTorneos = new ArrayList<>();
    }

    private void SetearListeners(){
        Volver.setOnClickListener(Atras);
        Configuracion.setOnClickListener(Config);
        Torneos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                P.GuardarInt("IDTorneo",IDTorneos.get(i));
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private View.OnClickListener Atras = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager FM = getActivity().getFragmentManager();
            FM.popBackStack();
        }
    };
    private View.OnClickListener Config = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Configuracion conf = new Configuracion();
            Principal.IrAFragment(conf,true);
        }
    };


    private void LlenarDatos(){
        Gson gson = new Gson();
        String usuario = P.ObtenerString("InformacionUsuario", "");
        Usu = gson.fromJson(usuario, Usuario.class);

        TraerUsuario Tarea = new TraerUsuario();
        Tarea.execute();
    }

    private class TraerTorneosSeguidosPorUsuario extends AsyncTask<Integer,Void, ArrayList<Torneo>> {
        @Override
        protected ArrayList<Torneo> doInBackground(Integer... voids) {
            ArrayList<Torneo> ArrayTorneos = new ArrayList<>();
            String Ruta = "GetTorneosSeguidosPorUsuario/Usuario/"+Usu.IdUsuario;
            String Respuesta = Tarea.RealizarTarea(Ruta);
            Gson g = new Gson();
            JsonArray VecTorneos = g.fromJson(Respuesta, JsonArray.class);

            for (int i = 0; i < VecTorneos.size(); i++) {
                JsonElement Elemento = VecTorneos.get(i);
                Gson gson = new Gson();
                Torneo T = gson.fromJson(Elemento, Torneo.class);
                ArrayTorneos.add(T);
                IDTorneos.add(T.IDTorneo);
                NombreTorneos.add(T.NombreTorneo);
            }
            return ArrayTorneos;
        }
        protected void onPostExecute(final ArrayList<Torneo> ArrayTorneos)
        {
            int Cantidad=ArrayTorneos.size();
            if (Cantidad<1 || Usu.IdUsuario<4)
            {
                TorneosSeguidos.setVisibility(View.GONE);
                TXT2.setVisibility(View.GONE);
            }else
            {
                TorneosSeguidos.setVisibility(View.VISIBLE);
                TXT2.setVisibility(View.VISIBLE);
                AdaptadorTorneos Adaptador = new AdaptadorTorneos(getContext(), R.layout.item_lista_torneos_seguidos, ArrayTorneos,Usu.IdUsuario,Principal,false);
                TorneosSeguidos.getLayoutParams().height = ArrayTorneos.size()*170 + 20;
                TorneosSeguidos.setAdapter(Adaptador);
            }

            Log.d("conexion",String.valueOf(IDTorneos.size()));
            if (IDTorneos.size()<2)
            {
                Torneos.setVisibility(View.GONE);
            }else
            {
                Torneos.setVisibility(View.VISIBLE);
                ArrayAdapter<String> Adaptador = new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.spinner, NombreTorneos);
                Torneos.setAdapter(Adaptador);
                for (int i=0;i<IDTorneos.size();i++)
                {
                    if (P.ObtenerInt("IDTorneo",-1)==IDTorneos.get(i))
                    {
                        Torneos.setSelection(i);
                    }
                }
            }
        }
    }

    private class TraerTorneosParticipadosPorUsuario extends AsyncTask<Integer,Void,ArrayList<Torneo>> {
        @Override
        protected ArrayList<Torneo> doInBackground(Integer... voids) {
            ArrayList<Torneo> ArrayTorneos = new ArrayList<>();
            String Ruta = "GetTorneosParticipadosPorUsuario/Usuario/"+Usu.IdUsuario;
            String Respuesta = Tarea.RealizarTarea(Ruta);
            Gson g = new Gson();
            JsonArray VecTorneos = g.fromJson(Respuesta, JsonArray.class);

            for (int i = 0; i < VecTorneos.size(); i++) {
                JsonElement Elemento = VecTorneos.get(i);
                Gson gson = new Gson();
                Torneo T = gson.fromJson(Elemento, Torneo.class);
                ArrayTorneos.add(T);
                IDTorneos.add(T.IDTorneo);
                NombreTorneos.add(T.NombreTorneo);
            }
            return ArrayTorneos;
        }
        protected void onPostExecute(final ArrayList<Torneo> ArrayTorneos)
        {

            int Cantidad=ArrayTorneos.size();
            if (Cantidad<1 || Usu.IdUsuario<4)
            {
                TorneosParticipados.setVisibility(View.GONE);
                TXT1.setVisibility(View.GONE);
            }else
            {
                if (Usu.IDTipo == 2)
                {
                    TXT1.setText("Participando como jugador en:");
                }else if (Usu.IDTipo == 3)
                {
                    TXT1.setText("Participando como capitan en:");
                }
                AdaptadorTorneos Adaptador = new AdaptadorTorneos(getContext(), R.layout.item_lista_torneos_seguidos, ArrayTorneos,Usu.IdUsuario,Principal,false);
                TorneosParticipados.setAdapter(Adaptador);
                TorneosParticipados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private class TraerUsuario extends AsyncTask<Void, Void, Usuario> {

        @Override
        protected Usuario doInBackground(Void... voids) {
            String Ruta = "GetUsuarioPorID/Usuario/"+Usu.IdUsuario;
            String Respuesta = Tarea.RealizarTarea(Ruta);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Usuario U = gson.fromJson(Respuesta, Usuario.class);
            return U;
        }
        protected void onPostExecute(Usuario U) {
            Gson gson = new Gson();
            String json = gson.toJson(U);
            P.GuardarString("InformacionUsuario", json);
            Usu = U;

            TraerTorneosParticipadosPorUsuario Tarea1 = new TraerTorneosParticipadosPorUsuario();
            Tarea1.execute();
            TraerTorneosSeguidosPorUsuario Tarea2 = new TraerTorneosSeguidosPorUsuario();
            Tarea2.execute();
            Tarea.CargarFoto("Usuarios/ID" + Usu.IdUsuario + "_Perfil.PNG",foto,"http://181.47.112.9/MyTournament/Imagenes/Usuarios/PerfilDefault.JPG");
            if (Usu.IDTipo<4)
            {
                Volver.setVisibility(View.GONE);
            }else
            {
                Configuracion.setVisibility(View.GONE);
                TXT1.setVisibility(View.GONE);
                TXT2.setVisibility(View.GONE);
                TorneosSeguidos.setVisibility(View.GONE);
                TorneosParticipados.setVisibility(View.GONE);
            }
            Nombre.setText("Nombre: "+Usu.NombreUsuario);
            String contra = "";
            for (int i = 0; i < Usu.Contrasenia.length() ;i++)
            {
                contra += "*";
            }
            Contrasenia.setText("Contraseña: "+contra);

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(Usu.FechaDeNacimiento);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            Period edad = Period.between(LocalDate.of(year, month, day), LocalDate.now());

            Edad.setText("Edad: "+edad.getYears()+" años");
            Email.setText("Email: "+Usu.Email);
            if (Usu.IDTipo == 2 || Usu.IDTipo == 3)
            {
                GolesEnTorneo.setVisibility(View.VISIBLE);
                GolesEnTorneo.setText("Goles: "+Usu.GolesEnTorneo+ " goles en torneo");
            }else
            {
                GolesEnTorneo.setVisibility(View.GONE);
            }

        }
    }
}
