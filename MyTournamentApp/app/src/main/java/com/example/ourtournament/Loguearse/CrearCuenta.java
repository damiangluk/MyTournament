package com.example.ourtournament.Loguearse;

import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ourtournament.Administracion.Usuario.Perfil;
import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.DatePickerFragment;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.Objetos.Usuario;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CrearCuenta extends Fragment {
    FragmentManager AdminFragments;
    FragmentTransaction TransaccionesDeFragment;
    Date FechaFinal;
    Button ConfirmarLogueo;
    TextView Incorrecto;
    EditText Nombre,Email, Contra, ConfContra,Nacimiento;
    String nombre,email,contra,confcontra,fechanacimiento;
    Preferencias P = new Preferencias();
    boolean Finalizar;
    View VistaADevolver = null;
    MainActivity Principal;
    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {

        if (VistaADevolver == null)
        {
            VistaADevolver = inflador.inflate(R.layout.crear_cuenta, GrupoDeLaVista, false);
            AdminFragments=getFragmentManager();
            ConfirmarLogueo = VistaADevolver.findViewById(R.id.button);
            Incorrecto = VistaADevolver.findViewById(R.id.Texto);
            Email = VistaADevolver.findViewById(R.id.correo);
            Nombre = VistaADevolver.findViewById(R.id.nombre);
            Contra = VistaADevolver.findViewById(R.id.contra);
            ConfContra = VistaADevolver.findViewById(R.id.confContra);
            Nacimiento = VistaADevolver.findViewById(R.id.FechaNacimiento);
            Nacimiento.setOnClickListener(EditarFecha);
            Principal = (MainActivity) getActivity();
        }

        Finalizar = false;

        ConfirmarLogueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Incorrecto.setVisibility(View.GONE);
                nombre = Nombre.getText().toString();
                email = Email.getText().toString();
                contra = Contra.getText().toString();
                confcontra = ConfContra.getText().toString();
                fechanacimiento = Nacimiento.getText().toString();

                if (Nombre.getText().length()<3 || Nombre.getText().length()>20)
                {
                    Incorrecto.setText("El nombre de usuario debe tener entre 3 y 20 caracteres");
                    Incorrecto.setVisibility(View.VISIBLE);
                }else if (email.length()<6 || email.length()>40 || !email.contains("@") || !email.contains("."))
                {
                    Incorrecto.setText("La sintaxis del correo electronico no es correcta");
                    Incorrecto.setVisibility(View.VISIBLE);
                }else if (false)
                {
                    Incorrecto.setText("La fecha de nacimiento ingresada no es valida");
                    Incorrecto.setVisibility(View.VISIBLE);
                }else if(contra.length()<6 || contra.length()>20 || !contra.equals(confcontra))
                {
                    Incorrecto.setText("La contraseña debe tener entre 6 y 20 caracteres y coincidir con la confirmacion de la misma");
                    Incorrecto.setVisibility(View.VISIBLE);
                }else
                {
                    TraerUsuario Tarea = new TraerUsuario();
                    Tarea.execute();
                }

            }
        });
        return VistaADevolver;
    }

    private class TraerUsuario extends AsyncTask<Integer,Void, Usuario> {
        Usuario Us;
        @Override
        protected Usuario doInBackground(Integer... voids) {
            String Ruta = "GetUsuarioPorContrasenia/Usuario/"+Nombre.getText()+"/contrasenia/"+ Contra.getText();
            TareaAsincronica Tarea = new TareaAsincronica();
            String Respuesta = Tarea.RealizarTarea(Ruta);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Usuario U = gson.fromJson(Respuesta, Usuario.class);
            Us = U;
            return Us;
        }

        protected void onPostExecute(Usuario U) {
            Log.d("conexion",String.valueOf(U.IdUsuario));
            if (U.IdUsuario > 0)
            {
                Incorrecto.setText("Ya existe ese nombre de usuario, intenta nuevamente con uno distinto");
                Incorrecto.setVisibility(View.VISIBLE);
            }else
            {
                ConfirmarLogueo.setVisibility(View.GONE);
                Usuario US = new Usuario(1,nombre,contra,FechaFinal,email,0,1);

                FragmentFotoDePerfil CrearFoto = new FragmentFotoDePerfil();
                CrearFoto.LlenarUsuario(US);
                TransaccionesDeFragment=AdminFragments.beginTransaction();
                TransaccionesDeFragment.replace(R.id.inputs, CrearFoto);
                TransaccionesDeFragment.commit();
            }
        }
    }

    private View.OnClickListener EditarFecha = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDatePickerDialog();
        }
    };

    private void showDatePickerDialog() {
        final DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                FechaFinal = new Date(day+"/"+(month+1)+"/"+year);
                Nacimiento.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getFragmentManager(), "datePicker");
    }
}