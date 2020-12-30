package com.example.ourtournament.Administracion.Usuario;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.ourtournament.Loguearse.Loguear;
import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.R;

public class Configuracion extends Fragment {
    FragmentManager AdminFragments;
    Button Volver,CerrarSesion;
    MainActivity Principal;
    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        final View VistaADevolver;
        VistaADevolver = inflador.inflate(R.layout.configuracion, GrupoDeLaVista, false);
        Volver = VistaADevolver.findViewById(R.id.Volver);
        CerrarSesion = VistaADevolver.findViewById(R.id.CerrarSesion);
        AdminFragments=getFragmentManager();
        Principal = (MainActivity) getActivity();
        final Preferencias P = Principal.CargarSharedPreferences();

        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager FM = getActivity().getFragmentManager();
                FM.popBackStack();
            }

        });
        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                P.EliminarTodo();
                Loguear logueo = new Loguear();
                Principal.IrAFragmentDePantallaVacia(logueo);
            }

        });
        return VistaADevolver;
    }

}
