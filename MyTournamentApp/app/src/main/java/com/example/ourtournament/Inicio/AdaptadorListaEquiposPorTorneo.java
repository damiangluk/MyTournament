package com.example.ourtournament.Inicio;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.text.BoringLayout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Equipo;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Torneo;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AdaptadorListaEquiposPorTorneo extends ArrayAdapter<Equipo> {
    private ArrayList<Equipo> _ListaEquipos;
    private Context _Contexto;
    private int _Resource;
    private ArrayList<Boolean> ListaDestacado;
    private Boolean Destacable;
    public int IDEquipo,IDUsuario;

    public AdaptadorListaEquiposPorTorneo(Context contexto,int Resource,ArrayList<Equipo> lista,Boolean destacable,int idequipo,int idusuario)
    {
        super(contexto,Resource,lista);
        this._Contexto = contexto;
        this._Resource = Resource;
        this.Destacable = destacable;
        this.IDEquipo = idequipo;
        this.IDUsuario = idusuario;
        if (lista.size()<1)
        {
            Equipo E = new Equipo(-1,"No hay equipos aún",0,0,0,0,0);
            lista.add(E);
        }
        this._ListaEquipos = lista;
        LLenarListaBoleans(lista.size());
    }

    public void LLenarListaBoleans(int Cantidad)
    {
        ListaDestacado = new ArrayList<>();
        for (int i=0;i<Cantidad;i++)
        {
            if (_ListaEquipos.get(i).IDEquipo == IDEquipo)
            {
                ListaDestacado.add(true);
            }else
            {
                ListaDestacado.add(false);
            }
        }
    }

    @SuppressLint("ViewHolder")
    public View getView(final int pos, View VistaADevolver, ViewGroup GrupoActual)
    {
        ImageView Foto;
        final Button Destacada;
        TextView NombreEquipo;
        LayoutInflater MiInflador;
        if(VistaADevolver == null)
        {
            MiInflador = LayoutInflater.from(this._Contexto);
            VistaADevolver = MiInflador.inflate(_Resource,null);
        }

        NombreEquipo = VistaADevolver.findViewById(R.id.Nombre);
        Foto = VistaADevolver.findViewById(R.id.foto);
        Destacada = VistaADevolver.findViewById(R.id.Destacada);

        final Equipo E = getItem(pos);
        NombreEquipo.setText(E.Nombre);

        TareaAsincronica Tarea = new TareaAsincronica();
        Tarea.CargarFoto("Equipos/ID"+E.IDEquipo+"_Escudo.PNG",Foto,3);

        if (!Destacable)
        {
            Destacada.setVisibility(View.GONE);
        }else if (!ListaDestacado.get(pos))
        {
            Destacada.setBackgroundResource(R.drawable.estrella_equipos);
        }else
        {
            Destacada.setBackgroundResource(R.drawable.estrella_equipo_favorito);
        }
        Destacada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ListaDestacado.get(pos))
                {
                    IDEquipo = E.IDEquipo;
                    CambiarEquipoFavorito Tarea = new CambiarEquipoFavorito();
                    Tarea.execute(IDUsuario,E.IDTorneo,E.IDEquipo);
                    Log.d("conexion","Usuario: "+IDUsuario+" Torneo: "+E.IDTorneo+" Equipo: "+E.IDEquipo);
                    for (int i=0;i<ListaDestacado.size();i++)
                    {
                        ListaDestacado.set(i,false);
                    }
                    ListaDestacado.set(pos,true);
                    notifyDataSetChanged();
                }else
                {
                    CambiarEquipoFavorito Tarea = new CambiarEquipoFavorito();
                    Tarea.execute(IDUsuario,E.IDTorneo,0);
                    ListaDestacado.set(pos,false);
                    notifyDataSetChanged();
                }
            }
        });
        if (E.IDEquipo==-1)
        {
            Foto.setVisibility(View.GONE);
            Destacada.setVisibility(View.GONE);
            Destacada.setEnabled(false);
        }

        return  VistaADevolver;
    }


    private class CambiarEquipoFavorito extends AsyncTask<Integer, Void, Void> {
        int Resultado;

        @Override
        protected Void doInBackground(Integer... IDS) {
            try {
                String miURL = "http://181.47.112.9/MyTournament/api/UpdateEquipoFavorito";
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
                Log.d("conexion","La respuesta fue: "+ResponseCode);
                miConexion.disconnect();
            } catch (Exception ErrorOcurrido) {
                Log.d("Conexion", "Al conectar o procesar ocurrió Error: " + ErrorOcurrido.getMessage());
            }
            return null;
        }
    }
}
