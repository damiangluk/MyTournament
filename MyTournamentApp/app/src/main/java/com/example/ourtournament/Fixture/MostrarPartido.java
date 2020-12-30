package com.example.ourtournament.Fixture;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.example.ourtournament.MainActivity;
import com.example.ourtournament.Objetos.GolesXUsuario;
import com.example.ourtournament.Objetos.Partido;
import com.example.ourtournament.Objetos.Preferencias;
import com.example.ourtournament.Objetos.TareaAsincronica;
import com.example.ourtournament.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MostrarPartido extends Fragment {
    TextView Jorn,E1,E2,jugado,Resultado,NombreE1,NombreE2;
    Partido Par;
    Button Volver;
    ImageView Foto1,Foto2;
    ListView lista1,lista2;
    View VistaADevolver;
    ArrayList<GolesXUsuario> Goles1;
    ArrayList<GolesXUsuario> Goles2;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflador, @Nullable ViewGroup GrupoDeLaVista, Bundle savedInstanceState) {
        if(VistaADevolver == null)
        {
            VistaADevolver = inflador.inflate(R.layout.un_partido, GrupoDeLaVista, false);
            finds(VistaADevolver);
        }

        Goles1 = new ArrayList<>();
        Goles2 = new ArrayList<>();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(Par.FechaDeEncuentro);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int horas = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);

        if (Par.GolesLocal == -1)
        {
            jugado.setText("El partido se jugara el "+day+"/"+month+" a las "+horas+":"+minutos+" horas");
            Resultado.setText("-:-");

            NombreE1.setText("No hay goles");
            NombreE2.setText("No hay goles");
        }else
        {
            TraerGoles Tarea = new TraerGoles();
            Tarea.execute();
            Resultado.setText(Par.GolesLocal + " - "+ Par.GolesVisitante);
            jugado.setText("El partido se jugo el "+day+"/"+month+" a las "+horas+":"+minutos+" horas");
            NombreE1.setText(Par.NombreEquipoLocal);
            NombreE2.setText(Par.NombreEquipoVisitante);
        }

        String Ruta = "http://10.0.2.2:55859/Imagenes/Equipos/ID"+Par.IDEquipoLocal+"_Escudo.PNG";
        Picasso.get().load(Ruta).into(Foto1);
        Ruta = "http://10.0.2.2:55859/Imagenes/Equipos/ID"+Par.IDEquipoVisitante+"_Escudo.PNG";
        Picasso.get().load(Ruta).into(Foto2);

        Jorn.setText("Jornada "+Par.Jornada);
        E1.setText(Par.NombreEquipoLocal);
        E2.setText(Par.NombreEquipoVisitante);


        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager FM = getActivity().getFragmentManager();
                FM.popBackStack();
            }

        });

        return VistaADevolver;
    }

    public void SetPartidoElegido(Partido partido)
    {
        Par = partido;
    }

    public void finds(View VistaADevolver)
    {
        Foto1 = VistaADevolver.findViewById(R.id.FotoE1);
        Foto2 = VistaADevolver.findViewById(R.id.FotoE2);
        Resultado = VistaADevolver.findViewById(R.id.Resultado);
        jugado = VistaADevolver.findViewById(R.id.jugado);
        Jorn = VistaADevolver.findViewById(R.id.Jornada);
        E1 = VistaADevolver.findViewById(R.id.Equipo1);
        E2 = VistaADevolver.findViewById(R.id.Equipo2);
        Volver = VistaADevolver.findViewById(R.id.Volver);

        NombreE1 = VistaADevolver.findViewById(R.id.NombreE1);
        NombreE2 = VistaADevolver.findViewById(R.id.NombreE2);

        lista1 = VistaADevolver.findViewById(R.id.ListaGolesE1);
        lista2 = VistaADevolver.findViewById(R.id.ListaGolesE2);
    }

    private class TraerGoles extends AsyncTask<Void,Void,ArrayList<GolesXUsuario>> {
        @Override
        protected ArrayList<GolesXUsuario> doInBackground(Void... voids) {
            ArrayList<GolesXUsuario> listaGoles = new ArrayList<>();
            String Ruta = "GetGolesXPartido/Partido/"+Par.IDPartido;
            TareaAsincronica Tarea = new TareaAsincronica();
            String Respuesta = Tarea.RealizarTarea(Ruta);
            Gson g = new Gson();
            JsonArray VecGoles = g.fromJson(Respuesta, JsonArray.class);

            for (int i = 0; i < VecGoles.size(); i++) {
                JsonElement Elemento = VecGoles.get(i);
                Gson gson = new Gson();
                GolesXUsuario G = gson.fromJson(Elemento, GolesXUsuario.class);
                listaGoles.add(G);
            }
            return listaGoles;
        }
        protected void onPostExecute(ArrayList<GolesXUsuario> lista)
        {
            for(int i=0;i<lista.size();i++)
            {
                if (lista.get(i).Nombreequipo.equals(Par.NombreEquipoLocal))
                {
                    Goles1.add(lista.get(i));
                }else if (lista.get(i).Nombreequipo.equals(Par.NombreEquipoVisitante))
                {
                    Goles2.add(lista.get(i));
                }
            }

            AdaptadorGolesXPartido Adaptador = new AdaptadorGolesXPartido(Goles1, R.layout.item_goles_por_usuario, getActivity().getBaseContext());
            lista1.setAdapter(Adaptador);

            AdaptadorGolesXPartido Adaptador2 = new AdaptadorGolesXPartido(Goles2, R.layout.item_goles_por_usuario, getActivity().getApplicationContext());
            lista2.setAdapter(Adaptador2);
        }
    }
}
