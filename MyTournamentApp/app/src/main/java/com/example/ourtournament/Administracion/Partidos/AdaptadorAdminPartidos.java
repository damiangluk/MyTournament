package com.example.ourtournament.Administracion.Partidos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.icu.util.EthiopicCalendar;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.ourtournament.Objetos.Partido;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorAdminPartidos extends ArrayAdapter<Partido> {
    private ArrayList<Partido> _ListaPartidos;
    private int _Resource;
    private Context _Contexto;
    public AdaptadorAdminPartidos(Context contexto, int Resource, ArrayList<Partido> ListaPartidos) {
        super(contexto, Resource, ListaPartidos);
        this._ListaPartidos = ListaPartidos;
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

        Partido P = getItem(pos);
        ConstraintLayout Fondo = VistaADevolver.findViewById(R.id.Fondo);
        final ImageView Foto1 = VistaADevolver.findViewById(R.id.FotoE1);
        final ImageView Foto2 = VistaADevolver.findViewById(R.id.FotoE2);
        TextView Estado = VistaADevolver.findViewById(R.id.Estado);
        TextView Jornada = VistaADevolver.findViewById(R.id.Jornada);
        Button Eliminar = VistaADevolver.findViewById(R.id.Eliminar);

        TareaAsincronica Tareas = new TareaAsincronica();
        Tareas.CargarFoto("Equipos/ID" + P.IDEquipoLocal + "_Escudo.PNG",Foto1,3);
        Tareas.CargarFoto("Equipos/ID" + P.IDEquipoVisitante + "_Escudo.PNG",Foto2,3);

        if (P.GolesLocal == -1)
        {
            Estado.setText("No jugado");
            Fondo.setBackgroundColor(Color.rgb(108,232,108));
        }else
        {
            Estado.setText("Jugado");
            Fondo.setBackgroundColor(Color.rgb(232,108,108));
            Eliminar.setVisibility(View.GONE);
        }
        Jornada.setText("Jornada "+P.Jornada);

        return VistaADevolver;
    }

}
