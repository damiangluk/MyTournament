package com.example.ourtournament.Fixture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ourtournament.Objetos.Equipo;
import com.example.ourtournament.Objetos.Partido;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorPartidos extends ArrayAdapter<Partido>
{
    private ArrayList<Partido> _ListaPartidos;
    private Context _Contexto;
    private int _Resource;

    public AdaptadorPartidos(ArrayList<Partido> ListaPartidos,int Resource, Context contexto)
    {

        super(contexto,Resource,ListaPartidos);
        this._ListaPartidos = ListaPartidos;
        this._Contexto = contexto;
        this._Resource = Resource;
    }

    public Partido getItem(int pos)
    {
        return _ListaPartidos.get(pos);
    }

    @SuppressLint("ViewHolder")
    public View getView(int pos, View VistaADevolver, ViewGroup GrupoActual)
    {
        LayoutInflater MiInflador;
        if(VistaADevolver == null)
        {
            MiInflador = LayoutInflater.from(this._Contexto);
            VistaADevolver = MiInflador.inflate(R.layout.item_lista_partidos,null);
        }

        ImageView Foto1,Foto2;
        TextView Equipo1,Equipo2,renglon,jugado;

        Equipo1=VistaADevolver.findViewById(R.id.Equipo1);
        Equipo2=VistaADevolver.findViewById(R.id.Equipo2);
        renglon=VistaADevolver.findViewById(R.id.renglonArriba);
        jugado =VistaADevolver.findViewById(R.id.jugado);
        Foto2 =VistaADevolver.findViewById(R.id.LogoEquipo2);
        Foto1 =VistaADevolver.findViewById(R.id.LogoEquipo1);

        Partido P = getItem(pos);
        TareaAsincronica Tarea = new TareaAsincronica();
        Tarea.CargarFoto("Equipos/ID"+P.IDEquipoLocal+"_Escudo.PNG",Foto1,3);
        Tarea.CargarFoto("Equipos/ID"+P.IDEquipoVisitante+"_Escudo.PNG",Foto2,3);
        if(pos == 0)
        {
            renglon.setVisibility(View.GONE);
        }
        if (P.GolesLocal == -1)
        {
            jugado.setText("No jugado");
        }else
        {
            jugado.setText("Jugado");
        }
        Equipo1.setText(P.NombreEquipoLocal);
        Equipo2.setText(P.NombreEquipoVisitante);

        return VistaADevolver;
    }
}
