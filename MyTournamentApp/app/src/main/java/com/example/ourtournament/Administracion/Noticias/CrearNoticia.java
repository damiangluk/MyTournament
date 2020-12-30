package com.example.ourtournament.Administracion.Noticias;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ourtournament.Administracion.Partidos.AdministrarPartidos;
import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.R;

import java.time.LocalDate;
import java.util.Date;

public class CrearNoticia extends Fragment {
    View VistaADevolver = null;
    int RequestCode,CodeElegirFoto = 23;
    EditText Titulo, Descripcion;
    Button EditarFoto,Publicar,Cancelar,Borrar;
    Switch Destacar;
    Bitmap Imagen = null;
    ImageView Foto,Destacada;
    MainActivity Principal;
    Preferencias P;
    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        if (VistaADevolver == null) {
            VistaADevolver = inflador.inflate(R.layout.crear_noticia, GrupoDeLaVista, false);
            Principal = (MainActivity) getActivity();
            P = Principal.CargarSharedPreferences();
            Titulo = VistaADevolver.findViewById(R.id.Titulo);
            Descripcion = VistaADevolver.findViewById(R.id.Descripcion);
            EditarFoto = VistaADevolver.findViewById(R.id.CambiarFoto);
            Publicar = VistaADevolver.findViewById(R.id.Publicar);
            Cancelar = VistaADevolver.findViewById(R.id.Cancelar);
            Destacar = VistaADevolver.findViewById(R.id.Destacar);
            Foto = VistaADevolver.findViewById(R.id.Foto);
            Destacada = VistaADevolver.findViewById(R.id.Destacada);
            Borrar = VistaADevolver.findViewById(R.id.Borrar);
            if (Destacar.isChecked())
            {
                Destacada.setVisibility(View.VISIBLE);
            }else
            {
                Destacada.setVisibility(View.GONE);
            }
        }

        Destacar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    Destacada.setVisibility(View.VISIBLE);
                }else
                {
                    Destacada.setVisibility(View.GONE);
                }
            }
        });

        EditarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Principal, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Principal,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},RequestCode);
                }else {
                    Log.d("conexion","entre a buscar la foto");
                    Intent ObtenerFoto = new Intent(Intent.ACTION_GET_CONTENT);
                    ObtenerFoto.setType("image/*");
                    startActivityForResult(Intent.createChooser(ObtenerFoto,"Seleccione una foto"),CodeElegirFoto);
                }
            }
        });
        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager FM = getActivity().getFragmentManager();
                FM.popBackStack();
            }
        });
        Borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Foto.setImageResource(R.drawable.noticia_default);
            }
        });
        return VistaADevolver;
    }

    public void onActivityResult(int RequestCode, int ResultCode, @NonNull Intent DatosRecibidos) {
        super.onActivityResult(RequestCode, ResultCode, DatosRecibidos);

        if(RequestCode == CodeElegirFoto && ResultCode == -1)
        {
            String Ubicacion = String.valueOf(DatosRecibidos.getData());
            ContentResolver resolver = getActivity().getContentResolver();
            try {
                Imagen = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(Ubicacion));
            } catch (Exception e) {
                Log.d("conexion","Ocurrio un error: "+e);
            }
            Foto.setImageBitmap(Imagen);

        }
    }
}
