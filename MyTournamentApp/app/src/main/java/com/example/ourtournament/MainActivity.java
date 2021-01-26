package com.example.ourtournament;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ourtournament.Administracion.Administracion;
import com.example.ourtournament.Administracion.Usuario.Perfil;
import com.example.ourtournament.Fixture.Fixture;
import com.example.ourtournament.Inicio.Inicio;
import com.example.ourtournament.Loguearse.Loguear;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.Usuario;
import com.example.ourtournament.TablaGoleadores.TablaDeGoleadores;
import com.example.ourtournament.TablaPosiciones.TablaPosiciones;
import com.google.gson.Gson;

public class MainActivity<task> extends AppCompatActivity {
    FragmentManager AdminFragments=getFragmentManager();
    FragmentTransaction TransaccionesDeFragment;
    Preferencias P;
    Usuario U;
    Button BTNFixture,BTNTablaDePosiciones,BTNInicio,BTNTablaDeGoleadores,BTNAdministracion;

    Fixture fixture = new Fixture();
    TablaDeGoleadores tablaDeGoleadores = new TablaDeGoleadores();
    Inicio inicio = new Inicio();
    TablaPosiciones tabladeposiciones = new TablaPosiciones();
    Administracion admin = new Administracion();
    Perfil perfil = new Perfil();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        P = CargarSharedPreferences();
        //P.EliminarString("InformacionUsuario");
        //P.EliminarTodo();
        Gson gson = new Gson();
        String usuario = P.ObtenerString("InformacionUsuario", "");

        if(usuario.length()<1)
        {
            Loguear logueo = new Loguear();
            IrAFragmentDePantallaVacia(logueo);
        }else
        {
            U = gson.fromJson(usuario, Usuario.class);
            CargarGeneral();
        }
    }
    public Preferencias CargarSharedPreferences()
    {
        SharedPreferences aux = getSharedPreferences("DatosGenerales",MODE_PRIVATE);
        SharedPreferences.Editor editor = null;
        Preferencias P = new Preferencias(aux,editor);
        return P;
    }
    //  GENERAL
    public void CargarGeneral()
    {
        setContentView(R.layout.activity_main);
        Gson gson = new Gson();
        String usuario = P.ObtenerString("InformacionUsuario", "");

        if (usuario.length()>1)
        {
            U = gson.fromJson(usuario, Usuario.class);
            BTNFixture = findViewById(R.id.Fixture);
            BTNTablaDePosiciones = findViewById(R.id.TablaDePosiciones);
            BTNInicio = findViewById(R.id.Inicio);
            BTNTablaDeGoleadores = findViewById(R.id.TablaDeGoleadores);
            BTNAdministracion = findViewById(R.id.Administracion);
            if (P.ObtenerInt("IDTorneo",-1)!=-1)
            {
                IrANoticias(null);
            }else
            {
                IrABuscador();
            }
        }

    }

    //Navegacion
    public void IrAFixture(View vista)
    {
        CambiarColor();
        BTNFixture.setBackgroundResource(R.drawable.icono_fixture_verde);

        IrAFragment(fixture,false);
    }

    /*
    public void IrATablaGoleadores(View vista)
    {
        CambiarColor();
        BTNTablaDeGoleadores.setBackgroundResource(R.drawable.icono_tabla_goleadores_verde);

        IrAFragment(tablaDeGoleadores,false);
    }

     */

    public void IrANoticias(View vista) {
        CambiarColor();
        BTNInicio.setBackgroundResource(R.drawable.icono_inicio_verde);
        inicio.LLenarBoolean(true);
        IrAFragment(inicio,false);
    }

    public void IrABuscador() {
        CambiarColor();
        BTNInicio.setBackgroundResource(R.drawable.icono_inicio_verde);
        inicio.LLenarBoolean(false);
        IrAFragment(inicio,false);
    }

    public void IrATablaPosiciones(View vista) {
        CambiarColor();
        BTNTablaDePosiciones.setBackgroundResource(R.drawable.icono_tabla_posiciones_verde);

        IrAFragment(tabladeposiciones,false);
    }

    public void IrAAdministracion(View vista) {
        CambiarColor();
        if (U.IDTipo <4)
        {
            BTNAdministracion.setBackgroundResource(R.drawable.icono_perfil_verde);
            IrAFragment(perfil,false);
        }else
        {
            BTNAdministracion.setBackgroundResource(R.drawable.icono_admin_verde);
            IrAFragment(admin,false);
        }
    }

    //Inicio

    public void CambiarColor()
    {
        BTNFixture.setBackgroundResource(R.drawable.icono_fixture);
        BTNTablaDeGoleadores.setBackgroundResource(R.drawable.icono_tabla_goleadores);
        BTNInicio.setBackgroundResource(R.drawable.icono_inicio);
        BTNTablaDePosiciones.setBackgroundResource(R.drawable.icono_tabla_posiciones);
        if (U.IDTipo <4)
        {
            BTNAdministracion.setBackgroundResource(R.drawable.icono_perfil);
        }else
        {
            BTNAdministracion.setBackgroundResource(R.drawable.icono_admin);
        }
    }

    public void IrAFragment(Fragment fragment,Boolean B){
        TransaccionesDeFragment=AdminFragments.beginTransaction();
        TransaccionesDeFragment.replace(R.id.Frame,fragment);
        TransaccionesDeFragment.commit();
        if (B)
        {
            TransaccionesDeFragment.addToBackStack(null);
        }
    }
    public void IrAFragmentDePantallaVacia(Fragment fragment){
        setContentView(R.layout.pantalla_vacia_con_fragment);
        TransaccionesDeFragment=AdminFragments.beginTransaction();
        TransaccionesDeFragment.replace(R.id.fragmentodepantallacompleta,fragment);
        TransaccionesDeFragment.commit();
        TransaccionesDeFragment.addToBackStack(null);
    }

}
