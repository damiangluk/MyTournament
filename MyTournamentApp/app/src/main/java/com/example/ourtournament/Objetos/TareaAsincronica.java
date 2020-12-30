package com.example.ourtournament.Objetos;

import android.app.ActivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.ourtournament.MainActivity;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import org.json.JSONStringer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TareaAsincronica {

    String Ruta1;
    public TareaAsincronica()
    {
        Ruta1 = "http://10.0.2.2:55859/api/";
    }
    public String RealizarTarea(String Ruta2)
    {
        String Respuesta = null;
        try {
            String miURL = Ruta1 + Ruta2;
            URL miRuta = new URL(miURL);
            Log.d("conexion", "estoy accediendo a la ruta: " + miURL);
            HttpURLConnection miConexion = (HttpURLConnection) miRuta.openConnection();
            miConexion.setRequestMethod("GET");
            if (miConexion.getResponseCode() == 200) {
                Log.d("conexion", "Me conecte perfectamente");
                InputStream lector = miConexion.getInputStream();
                InputStreamReader lectorJSon = new InputStreamReader(lector, "utf-8");
                JsonParser parseador = new JsonParser();

                Respuesta = parseador.parse(lectorJSon).toString();
                Log.d("conexion", "Devolvi: "+String.valueOf(Respuesta));
                miConexion.disconnect();
            } else {
                Log.d("Conexion", "Me pude conectar pero algo malo pasó");
            }
        }catch (Exception ErrorOcurrido) {
            Log.d("Conexion", "Al conectar o procesar ocurrió Error: " + ErrorOcurrido.getMessage());
        }
        return Respuesta;
    }
}
