package com.s21.quemepongo2front.creadoresDeFragments.ubicaciones;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.s21.quemepongo2front.Api;
import com.s21.quemepongo2front.MainActivity;
import com.s21.quemepongo2front.R;
import com.s21.quemepongo2front.RestClient;
import com.s21.quemepongo2front.adaptadores.AdapterListaCiudad;
import com.s21.quemepongo2front.objetosDeLaApi.ObjetosRS.CiudadRs;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ubicacionesFragment extends Fragment {
    ArrayList<CiudadRs> listaCiudadesRecibe, ciudadesUsuario;
    AdapterListaCiudad adapterListaCiudad;
    Button agregarUbicacion;
    CiudadRs ubicacionSeleccionada,ubicacion1, ubicacion2, ubicacion3, ubicacion4;
    String buscador, token, ciudadSeleccionada;
    int idciudad;
    EditText editBuscador;
    RecyclerView recycler;
    ListView listViewMisCiudades;
    TextView txtCiudadSeleccion,ubicaciontxt1,ubicaciontxt2,ubicaciontxt3,ubicaciontxt4;

    private UbicacionesViewModel ubicacionesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ubicacionesViewModel =
                ViewModelProviders.of(this).get(UbicacionesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ubicaciones, container, false);
        ubicacionesViewModel.getText().observe(this, new Observer<String>() {

        public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mostrarCiudadesDeUsuario();
        editBuscador = getActivity().findViewById(R.id.editTextBuscador);
        editBuscador.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            public void afterTextChanged(Editable s) {
                buscarCiudadOnTextChange();
            }

        });
    }

    //busca la ciudad cuando se escribe en el editText del buscador
    private void buscarCiudadOnTextChange(){
        //le seteamos a la variable buscador el texto que se escribe, para enviarlo a la api
        buscador = editBuscador.getText().toString();

        RestClient restClient = Api.getRetrofit().create(RestClient.class);

        Call<ArrayList<CiudadRs>> listar = restClient.obtenerCiudad(buscador,token);

        listar.enqueue(new Callback<ArrayList<CiudadRs>>() {

            public void onResponse(Call<ArrayList<CiudadRs>> call, Response<ArrayList<CiudadRs>> response) {

                if(response.isSuccessful()){
                    listaCiudadesRecibe = response.body();
                    agregaradaptador(listaCiudadesRecibe);
                }else{
                    Toast.makeText(getContext(), "Error intentelo denuevo", Toast.LENGTH_SHORT).show();
                }
            }
            public void onFailure(Call<ArrayList<CiudadRs>> call, Throwable t) {

            }
        });
    }
    public void agregaradaptador(ArrayList <CiudadRs>lista){
        recycler = getActivity().findViewById(R.id.recyclerViewCiudades);
        txtCiudadSeleccion = getActivity().findViewById(R.id.textViewCiudadSeleccionada);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
         adapterListaCiudad = new AdapterListaCiudad(lista);
        //on click de cuando se toca una ciudad en el adapter
        adapterListaCiudad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ciudadSeleccionada = listaCiudadesRecibe.get(recycler.getChildAdapterPosition(v)).getNombre()+", "+listaCiudadesRecibe.get(recycler.getChildAdapterPosition(v)).getPais();
                Toast.makeText(getContext(), "Selecciono la ciudad:  "+ ciudadSeleccionada, Toast.LENGTH_SHORT).show();
                txtCiudadSeleccion.setText(ciudadSeleccionada);
                ubicacionSeleccionada = listaCiudadesRecibe.get(recycler.getChildAdapterPosition(v));
                agregarubicacion(ubicacionSeleccionada,v);

            }
        });
        recycler.setAdapter(adapterListaCiudad);
    }

    public void agregarubicacion(final CiudadRs ubicacion, View v){
        agregarUbicacion = getActivity().findViewById(R.id.botonAgregarCiudadSeleccionada);
        ubicacion1 = listaCiudadesRecibe.get(recycler.getChildAdapterPosition(v));
        agregarUbicacion.setOnClickListener(new View.OnClickListener() {
        //crear metodo para la seleccion de ciudades revisar que se esten mostrando antes las ciudades del usuario
            public void onClick(View v) {
                mostrarCiudadesDeUsuario();
                agregarCiudadaMisCiudades(ubicacion);
            }
        });
    }
    public void mostrarCiudadesDeUsuario(){
        RestClient restClient = Api.getRetrofit().create(RestClient.class);
        listViewMisCiudades= getActivity().findViewById(R.id.listViewCiudadesUsuario);
        token= MainActivity.token ;
        Call<ArrayList<CiudadRs>> ubicaciones = restClient.misCiudades(token);
        ubicaciones.enqueue(new Callback<ArrayList<CiudadRs>>() {

            public void onResponse(Call<ArrayList<CiudadRs>> call, Response<ArrayList<CiudadRs>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ciudadesUsuario = response.body();
                        cargarListaCiudadesUsuario(ciudadesUsuario);
                    } else {
                        Toast.makeText(getContext(), "Ocurrio un error: No hay ciudades cargadas", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ocurrio un error " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            public void onFailure(Call<ArrayList<CiudadRs>> call, Throwable t) {
            }
        });
    }

    public void cargarListaCiudadesUsuario(ArrayList lista){

        ArrayList <String> nombreDeCiudad = new ArrayList<String>();
        for (int i = 0; i <lista.size() ; i++) {
            nombreDeCiudad.add(((CiudadRs)lista.get(i)).getNombre());
        }
        ArrayAdapter adaptadorCiudadUsuario = new ArrayAdapter(getActivity(), R.layout.item_list_ciudades,R.id.itemListaCiudades, nombreDeCiudad);
        listViewMisCiudades.setAdapter(adaptadorCiudadUsuario);
    }

    public void agregarCiudadaMisCiudades(final CiudadRs ciudad){
        RestClient restClient = Api.getRetrofit().create(RestClient.class);
        idciudad= ciudad.getId();
        Call <Void> agregarciudad= restClient.agregarCiudad(idciudad,token);
        agregarciudad.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Se agrego la ciudad: " + ciudad.getNombre(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Se produjo un error en agregar la ciudad", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}