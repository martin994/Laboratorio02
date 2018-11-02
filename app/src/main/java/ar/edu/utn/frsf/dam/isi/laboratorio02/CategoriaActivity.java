package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class CategoriaActivity extends AppCompatActivity {

    private EditText textoCategoria;
    private Button btnCrearCategorias;
    private Button btnMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        textoCategoria=(EditText) findViewById(R.id.editTextNombreCat);
        btnCrearCategorias = (Button) findViewById(R.id.buttonCrearCategoria);

        btnCrearCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Categoriarest nuevaCategoriaRes= new Categoriarest();
                final Categoria nuevaCategoria= new Categoria();
                nuevaCategoria.setNombre(textoCategoria.getText().toString());

                //verifico que no este vacio
                if(textoCategoria.getText().length()==0){
                    CharSequence text = "Campos invalidos!";

                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();

                }

                else {

                    Runnable r= new Runnable(){

                        @Override
                        public void run() {
                            nuevaCategoriaRes.crearCategoria(nuevaCategoria);
                            final CharSequence text = "El la categoria "+textoCategoria.getText().toString()+"fue creada";

                            runOnUiThread(new Runnable(){

                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                                    toast.show();
                                    textoCategoria.setText("");

                                }
                            });
                        }
                    };

                    Thread nuevoHilo = new Thread(r);
                    nuevoHilo.start();

                    //json-server --host 192.168.0.100 C:\Users\Usuario\Downloads\resto-blank.json
                    //vuelvo a al MainActivity cuando toco el bonton volver
                    btnMenu= (Button) findViewById(R.id.buttonVolver);

                    btnMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i= new Intent(CategoriaActivity.this,MainActivity.class);
                            startActivity(i);
                        }
                    });

                }


            }
        });



    }
}
