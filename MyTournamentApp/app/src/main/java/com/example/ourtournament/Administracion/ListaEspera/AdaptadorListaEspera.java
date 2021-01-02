package com.example.ourtournament.Administracion.ListaEspera;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import com.example.ourtournament.Objetos.ListaEspera;
import com.example.ourtournament.Objetos.Usuario;
import com.example.ourtournament.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorListaEspera extends ArrayAdapter<ListaEspera> {
    private ArrayList<ListaEspera> _ListaUsuarios;
    private int _Resource;
    private Context _Contexto;
    public AdaptadorListaEspera(Context contexto, int Resource, ArrayList<ListaEspera> ListaUsuarios) {
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
        Button Aceptar = null;
        Button Rechazar = null;
        TextView Nombre = null,NombreEquipo=null,VerMensaje = null;
        if (VistaADevolver == null) {
            MiInflador = LayoutInflater.from(this._Contexto);
            VistaADevolver = MiInflador.inflate(_Resource, null);
            Aceptar = VistaADevolver.findViewById(R.id.Aceptar);
            Rechazar = VistaADevolver.findViewById(R.id.Rechazar);
            Nombre = VistaADevolver.findViewById(R.id.Nombre);
            NombreEquipo = VistaADevolver.findViewById(R.id.NombreEquipo);
            VerMensaje = VistaADevolver.findViewById(R.id.VerMensaje);
        }
        final ImageView Foto = VistaADevolver.findViewById(R.id.Foto);

        final ListaEspera LE = getItem(pos);
        String Ruta = "http://10.0.2.2:55859/Imagenes/Usuarios/ID" + LE.IdUsuario + "_Perfil.PNG";
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
        Nombre.setText(LE.NombreUsuario);
        NombreEquipo.setText(LE.NombreEquipo);
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
        VerMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder Alerta = new AlertDialog.Builder(getContext());
                Alerta.setPositiveButton("Aceptar",null);
                Alerta.setTitle("Mensaje de solicitud");
                Alerta.setMessage(LE.Mensaje);
                Alerta.create();
                Alerta.show();
            }
        });

        return VistaADevolver;
    }
}
