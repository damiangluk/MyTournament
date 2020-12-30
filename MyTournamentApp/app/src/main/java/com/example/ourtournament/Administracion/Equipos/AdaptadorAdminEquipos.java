package com.example.ourtournament.Administracion.Equipos;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Equipo;
import com.example.ourtournament.Objetos.Torneo;
import com.example.ourtournament.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdaptadorAdminEquipos extends ArrayAdapter<Equipo> {
    private ArrayList<Equipo> _ListaEquipos;
    private int _Resource;
    private Context _Contexto;
    public AdaptadorAdminEquipos(Context contexto, int Resource, ArrayList<Equipo> ListaEquipos) {
        super(contexto, Resource, ListaEquipos);
        this._ListaEquipos = ListaEquipos;
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

        Equipo E = getItem(pos);
        final ImageView Foto = VistaADevolver.findViewById(R.id.Foto);
        TextView Nombre = VistaADevolver.findViewById(R.id.Nombre);
        String Ruta = "http://10.0.2.2:55859/Imagenes/Equipos/ID" + E.IDEquipo + "_Escudo.PNG";

        Picasso.get().load(Ruta)
                .into(Foto, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Foto.setImageResource(R.drawable.icono_equipo);
                    }

                });
        Nombre.setText(E.Nombre);

        return VistaADevolver;
    }

}

