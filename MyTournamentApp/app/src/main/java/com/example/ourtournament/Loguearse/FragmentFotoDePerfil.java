package com.example.ourtournament.Loguearse;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Usuario;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentFotoDePerfil extends Fragment{
    FragmentManager AdminFragments;
    Button Agregar,Quitar,Omitir,Confirmar;
    CircleImageView Foto;
    Bitmap Imagen = null;
    int RequestCode,CodeElegirFoto = 23;
    MainActivity Principal;
    Preferencias P;
    Usuario U;
    TareaAsincronica Tarea;
    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        View VistaADevolver;
        VistaADevolver = inflador.inflate(R.layout.armar_foto_perfil, GrupoDeLaVista, false);
        AdminFragments=getFragmentManager();
        Quitar = VistaADevolver.findViewById(R.id.Quitar);
        Agregar = VistaADevolver.findViewById(R.id.Agregar);
        Confirmar = VistaADevolver.findViewById(R.id.Confirmar);
        Omitir = VistaADevolver.findViewById(R.id.Omitir);
        Foto = VistaADevolver.findViewById(R.id.Foto);
        Principal = (MainActivity) getActivity();
        P = Principal.CargarSharedPreferences();

        Tarea = new TareaAsincronica();
        Tarea.CargarFoto("Usuarios/PerfilDefault.JPG",Foto,2);
        Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Principal,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Principal,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},RequestCode);
                }else {
                    Log.d("conexion","entre a buscar la foto");
                    Intent ObtenerFoto = new Intent(Intent.ACTION_GET_CONTENT);
                    ObtenerFoto.setType("image/*");
                    startActivityForResult(Intent.createChooser(ObtenerFoto,"Seleccione una foto"),CodeElegirFoto);
                }
            }
        });
        Quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tarea.CargarFoto("Usuarios/PerfilDefault.JPG",Foto,2);
            }
        });
        Confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertarUsuario Tarea = new InsertarUsuario();
                Tarea.execute();
            }
        });

        Omitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertarUsuario Tarea = new InsertarUsuario();
                Tarea.execute();
            }
        });

        return VistaADevolver;
    }

    public void LlenarUsuario(Usuario usuario)
    {
        U = usuario;
    }

    public void onActivityResult(int RequestCode, int ResultCode, @NonNull Intent DatosRecibidos) {
        super.onActivityResult(RequestCode, ResultCode, DatosRecibidos);

        if(RequestCode == CodeElegirFoto && ResultCode == -1)
        {
            String Ubicacion = String.valueOf(DatosRecibidos.getData());
            ContentResolver resolver = getActivity().getContentResolver();
            try {
                Imagen = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(Ubicacion));
            } catch (Exception e) {
                Log.d("conexion","Ocurrio un error: "+e);
            }
            Foto.setImageBitmap(Imagen);
        }
    }

    private class InsertarUsuario extends AsyncTask<Void, Void, Integer> {
        int NuevoID;
        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                String miURL = "http://181.47.112.9/MyTournament/api/InsertUsuario";
                Log.d("conexion", "estoy accediendo a la ruta " + miURL);
                URL miRuta = new URL(miURL);
                HttpURLConnection miConexion = (HttpURLConnection) miRuta.openConnection();
                miConexion.setDoInput(true);
                miConexion.setDoOutput(true);
                miConexion.setRequestProperty("Content-Type", "application/json");
                miConexion.setRequestProperty("Accept", "application/json");
                miConexion.setRequestMethod("POST");

                Gson gson = new Gson();
                String json = gson.toJson(U);
                OutputStream OS = miConexion.getOutputStream();
                OS.write(json.getBytes());
                OS.flush();

                int ResponseCode = miConexion.getResponseCode();
                Log.d("conexion", String.valueOf(ResponseCode));

                InputStream lector = miConexion.getInputStream();
                InputStreamReader lectorJSon = new InputStreamReader(lector, "utf-8");
                JsonParser parseador = new JsonParser();
                NuevoID = parseador.parse(lectorJSon).getAsInt();
                Log.d("conexion",String.valueOf(NuevoID));
                miConexion.disconnect();
            } catch (Exception ErrorOcurrido) {
                Log.d("Conexion", "Al conectar o procesar ocurrió Error: " + ErrorOcurrido.getMessage());
            }
            return NuevoID;
        }
        protected void onPostExecute(Integer ID) {
            Gson gson = new Gson();
            U.IdUsuario = ID;
            String json = gson.toJson(U);
            P.GuardarString("InformacionUsuario",json);
            Intent Llamada = new Intent(Principal, MainActivity.class);
            startActivity(Llamada);
        }
    }

}


