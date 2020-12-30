package com.example.ourtournament.Administracion.ListaEspera;

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

import com.example.ourtournament.Objetos.Equipo;
import com.example.ourtournament.Objetos.Usuario;
import com.example.ourtournament.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorListaEspera extends ArrayAdapter<Usuario> {
    private ArrayList<Usuario> _ListaUsuarios;
    private int _Resource;
    private Context _Contexto;
    public AdaptadorListaEspera(Context contexto, int Resource, ArrayList<Usuario> ListaUsuarios) {
        super(contexto, Resource, ListaUsuarios);
        _ListaUsuarios = ListaUsuarios;
        _Contexto = contexto;
        _Resource = Resource;
        Log.d("conexion",String.valueOf(_ListaUsuarios.size()));
    }

    @SuppressLint("ViewHolder")
    public View getView(int pos, View VistaADevolver, ViewGroup GrupoActual) {
        Log.d("conexion","Entre al GetView");
        LayoutInflater MiInflador;
        if (VistaADevolver == null) {
            MiInflador = LayoutInflater.from(this._Contexto);
            VistaADevolver = MiInflador.inflate(_Resource, null);
        }


        Usuario U = getItem(pos);
        final ImageView Foto = VistaADevolver.findViewById(R.id.Foto);
        Button Aceptar = VistaADevolver.findViewById(R.id.Aceptar);
        Button Rechazar = VistaADevolver.findViewById(R.id.Rechazar);
        TextView Nombre = VistaADevolver.findViewById(R.id.Nombre);
        String Ruta = "http://10.0.2.2:55859/Imagenes/Usuarios/ID" + U.IdUsuario + "_Perfil.PNG";
        Log.d("conexion","Inicialize las variables");

        Picasso.get().load(Ruta)
                .into(Foto, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load("http://10.0.2.2:55859/Imagenes/Usuarios/PerfilDefault.JPG").into(Foto);
                    }

                });
        Nombre.setText(U.NombreUsuario);
        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return VistaADevolver;
    }
}
