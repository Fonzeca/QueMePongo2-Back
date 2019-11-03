package com.s21.quemepongo2front;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.s21.quemepongo2front.ui.UsuarioRq;

public class CreacionUsuario_Activity extends AppCompatActivity {
    Api retrofit ;
    Button botonNuevoUsuario;
    UsuarioRq user = new UsuarioRq();
    EditText txtNombre, txtPasw;
    String nombre, clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuariologin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        botonNuevoUsuario = findViewById(R.id.botonNuevoUsuario);
        botonNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Generar codigo para validar que el usuario no exista y esten bien los campos de usuaio y clave
                //TODO crear un string que contenga el token para enviarlo por este metodo
                //Seteamos el texto del edittext en un string
                txtNombre= (EditText)findViewById(R.id.edittext_usuario);
                txtPasw = (EditText)findViewById(R.id.edit_text_pasw);
                nombre = txtNombre.getText().toString();
                clave = txtPasw.getText().toString();
                // agregamos los string al objeto user
                user.setClave(clave);
                user.setUsuario(nombre);
                //instanciamos el RestClient y enviamos los parametros del usuario
                RestClient restClient = Api.getRetrofit().create(RestClient.class);
                Call<Void> call = restClient.crearUsuario(user);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(CreacionUsuario_Activity.this, "Usuario Creado Correctamente", Toast.LENGTH_SHORT).show();
                            Intent gohome = new Intent(CreacionUsuario_Activity.this, MainActivity.class);
                            startActivity(gohome);
                        }else {
                            Toast.makeText(CreacionUsuario_Activity.this, "Error Fatal, Intentelo de nuevo :)", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        }
}