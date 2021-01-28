package com.example.ourtournament.Inicio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ourtournament.Objetos.Noticia;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorListaNoticias extends ArrayAdapter<Noticia>
{
    private ArrayList<Noticia> _Noticias;
    private Context _Contexto;
    private int _Resource;

    public AdaptadorListaNoticias(Context contexto, int Resource,ArrayList<Noticia> Noticias)
    {
        super(contexto,Resource,Noticias);
        this._Contexto = contexto;
        this._Resource = Resource;
        this._Noticias = Noticias;
    }
    @SuppressLint("ViewHolder")
    public View getView(int pos, View VistaActual, ViewGroup GrupoActual)
    {
        View VistaADevolver = VistaActual;
        LayoutInflater MiInflador;
        if (VistaActual == null) {
            MiInflador = LayoutInflater.from(this._Contexto);
            VistaADevolver = MiInflador.inflate(_Resource, null);
        }

        TextView Titulo,Fecha,Descripcion;
        ImageView Destacada,imageView;

        Titulo=VistaADevolver.findViewById(R.id.Titulo);
        Fecha=VistaADevolver.findViewById(R.id.Fecha);
        Descripcion=VistaADevolver.findViewById(R.id.Descripcion);
        Destacada=VistaADevolver.findViewById(R.id.Destacada);
        imageView =VistaADevolver.findViewById(R.id.imageView);

        Noticia Not = getItem(pos);
        if(Not.Destacada)
        {
            Destacada.setVisibility(View.VISIBLE);
        }else
        {
            Destacada.setVisibility(View.INVISIBLE);
        }
        Titulo.setText(Not.Titulo);
        Fecha.setText(Not.Fecha.toString());
        Descripcion.setText(Not.Descripcion);
        TareaAsincronica Tarea = new TareaAsincronica();
        Tarea.CargarFoto("Noticias/ID"+Not.IDNoticia+"_1.JPG",imageView,4);
        return  VistaADevolver;
    }
}
