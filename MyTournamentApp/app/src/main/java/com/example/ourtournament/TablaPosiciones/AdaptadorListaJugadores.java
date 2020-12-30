package com.example.ourtournament.TablaPosiciones;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ourtournament.Objetos.Equipo;
import com.example.ourtournament.Objetos.Usuario;
import com.example.ourtournament.R;

import java.util.ArrayList;

public class AdaptadorListaJugadores extends ArrayAdapter<Usuario>
{
    private ArrayList<Usuario> _ListaJugadores;
    private Context _Contexto;
    private int _Resource;

    public AdaptadorListaJugadores(Context contexto,int Resource,ArrayList<Usuario> ListaJugadores)
    {
        super(contexto,Resource,ListaJugadores);
        this._ListaJugadores = ListaJugadores;
        this._Contexto = contexto;
        this._Resource = Resource;
        Log.d("conexion",String.valueOf(ListaJugadores.size()));
    }

    @SuppressLint("ViewHolder")
    public View getView(int pos, View VistaADevolver, ViewGroup GrupoActual)
    {
        Log.d("conexion","La lista tiene "+_ListaJugadores.size());
        LayoutInflater MiInflador;
        ConstraintLayout CL;
        if(VistaADevolver == null)
        {
            MiInflador = LayoutInflater.from(this._Contexto);
            VistaADevolver = MiInflador.inflate(_Resource,null);
        }

        TextView Nombre,Goles;
        Nombre = VistaADevolver.findViewById(R.id.Nombre);
        Goles = VistaADevolver.findViewById(R.id.Goles);
        CL = VistaADevolver.findViewById(R.id.Fondo);

        if (pos%2==0)
        {
            CL.setBackgroundColor(Color.rgb(166,188,250));//170 170 170
        }else
        {
            CL.setBackgroundColor(Color.rgb(117,151,249));//108 108 108
        }
        Usuario U = getItem(pos);
        Goles.setText("Goles: "+U.GolesEnTorneo);
        Nombre.setText(U.NombreUsuario);

        return  VistaADevolver;
    }
}
