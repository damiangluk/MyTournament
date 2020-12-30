package com.example.ourtournament.Objetos;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Preferencias{

    SharedPreferences Datos;
    android.content.SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public Preferencias(SharedPreferences datos, android.content.SharedPreferences.Editor edit)
    {
        Datos = datos;
        editor = edit;
        editor = Datos.edit();
    }
    public Preferencias()
    {
    }
    public void EliminarTodo()
    {
        editor.clear();
        editor.apply();
    }
    public void GuardarString(String Clave,String dato)
    {
        editor.putString(Clave, dato);
        editor.apply();
    }
    public String ObtenerString(String Clave,String defaul)
    {
        return Datos.getString(Clave,defaul);
    }
    public void EliminarString(String Clave)
    {
        editor.remove(Clave);
        editor.apply();
    }

    public void GuardarInt(String Clave,int dato)
    {
        editor.putInt(Clave, dato);
        editor.apply();
    }
    public int ObtenerInt(String Clave,int defaul)
    {
        return Datos.getInt(Clave,defaul);
    }
    public void EliminarInt(String Clave)
    {
        editor.remove(Clave);
        editor.apply();
    }


}
