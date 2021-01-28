package com.example.ourtournament.Administracion.Noticias;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ourtournament.Objetos.Equipo;
import com.example.ourtournament.Objetos.Noticia;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorAdminNoticias extends ArrayAdapter<Noticia> {
    private ArrayList<Noticia> _ListaNoticias;
    private int _Resource;
    private Context _Contexto;
    public AdaptadorAdminNoticias(Context contexto, int Resource, ArrayList<Noticia> ListaNoticias) {
        super(contexto, Resource, ListaNoticias);
        this._ListaNoticias = ListaNoticias;
        this._Contexto = contexto;
        this._Resource = Resource;
    }

    @SuppressLint("ViewHolder")
    public View getView(int pos, View VistaADevolver, ViewGroup GrupoActual) {
        Log.d("conexion","Entre en pos: "+pos);
        LayoutInflater MiInflador;
        if (VistaADevolver == null) {
            MiInflador = LayoutInflater.from(this._Contexto);
            VistaADevolver = MiInflador.inflate(_Resource, null);
        }

        Noticia N = getItem(pos);
        final ImageView Foto = VistaADevolver.findViewById(R.id.Foto);
        TextView Nombre = VistaADevolver.findViewById(R.id.Nombre);
        TareaAsincronica Tareas = new TareaAsincronica();
        Tareas.CargarFoto("Noticias/ID" + N.IDNoticia + "_1.JPG",Foto,4);
        Nombre.setText(N.Titulo);

        return VistaADevolver;
    }

}

