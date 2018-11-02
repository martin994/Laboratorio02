package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRetrofit;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//http://192.168.0.13:5000
//C:\Users\Martin>json-server -p 5000 --host 192.168.0.13 C:\Users\Martin\Documents\Devs\resto-db.json

public class GestionProducto extends AppCompatActivity {
    private Button btnGuardar;
    private Button btnBorrar;
    private Button btnVolver;
    private Button btnBuscar;
    private EditText edtNombre;
    private EditText edtDescripcion;
    private EditText edtPrecio;
    private EditText edtBuscar;
    private Spinner spProducto;
    private ToggleButton tgCrearOBuscar;
    private ProductoRepository repoProd;
    private ArrayAdapter<Categoria> adaptadorCategorias;
    private Producto p;
    private int idProducto=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_producto);

        btnGuardar=(Button) findViewById(R.id.buttonProductoGuardar);
        btnBorrar=(Button) findViewById(R.id.buttonProductoBorrar);
        btnVolver=(Button) findViewById(R.id.buttonProductoVolver);
        btnBuscar=(Button) findViewById(R.id.buttonBuscar);
        edtNombre=(EditText) findViewById(R.id.editTextNombre);
        edtDescripcion=(EditText) findViewById(R.id.editTextDescripcion);
        edtPrecio=(EditText) findViewById(R.id.editTextPrecio);
        edtNombre=(EditText) findViewById(R.id.editTextNombre);
        edtBuscar=(EditText) findViewById(R.id.editTextBuscarProdID);
        spProducto=(Spinner) findViewById(R.id.spinnerProducto);
        tgCrearOBuscar=(ToggleButton) findViewById(R.id.toggleButtonCrearOActualizar);
        repoProd= new ProductoRepository();
        adaptadorCategorias = new ArrayAdapter<Categoria>(this, android.R.layout.simple_list_item_single_choice, repoProd.getCategorias());
        spProducto.setAdapter(adaptadorCategorias);
        edtBuscar.setEnabled(false);
        btnBorrar.setEnabled(false);
        tgCrearOBuscar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        tgCrearOBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtBuscar.setText("");
                if(tgCrearOBuscar.isChecked()){
                    btnBorrar.setEnabled(true);
                    btnBuscar.setEnabled(true);
                    edtBuscar.setEnabled(true);
                }else{
                    btnBuscar.setEnabled(false);
                    btnBorrar.setEnabled(false);
                    edtBuscar.setEnabled(false);
                }
            }
        });
        //Boton guardar, (primer condicion del IF) si se busca id actualiza los valores del producto
        //(ELSE)si se crea producto, da el alta
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tgCrearOBuscar.isChecked()){
                    p= new Producto(edtNombre.getText().toString(),
                            edtDescripcion.getText().toString(),
                            Double.valueOf(edtPrecio.getText().toString()),
                            (Categoria) spProducto.getSelectedItem());
                    ProductoRetrofit clienteRest=RestClient.getInstance()
                            .getRetrofit()
                            .create(ProductoRetrofit.class);
                    Call<Producto> actualizarCall=clienteRest
                            .actualizarProducto(idProducto,p);
                    actualizarCall.enqueue(new Callback<Producto>() {
                        @Override
                        public void
                        onResponse(Call<Producto> call, Response<Producto> resp) {// procesar la respuesta

                            switch(resp.code()) {
                                case 200:Toast.makeText(GestionProducto.this, "Producto actualizado !",
                                        Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(GestionProducto.this,"No se pudo actualizar el producto1 !",
                                            Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                        @Override
                        public void
                        onFailure(Call<Producto> call, Throwable t) {
                            Toast.makeText(GestionProducto.this,"No se pudo actualizar el producto2 !",
                                    Toast.LENGTH_LONG).show();

                        }
                    });
                }else{
                     p= new Producto(edtNombre.getText().toString(),
                            edtDescripcion.getText().toString(),
                            Double.valueOf(edtPrecio.getText().toString()),
                            (Categoria) spProducto.getSelectedItem());
                    ProductoRetrofit clienteRest=RestClient.getInstance()
                            .getRetrofit()
                            .create(ProductoRetrofit.class);
                    Call<Producto> altaCall=clienteRest
                            .crearProducto(p);
                    altaCall.enqueue(new Callback<Producto>() {
                                                @Override
                                                public void
                                                onResponse(Call<Producto> call, Response<Producto> resp) {// procesar la respuesta
                                                    switch(resp.code()) {
                                                        case 201:Toast.makeText(GestionProducto.this,"Producto creado !",
                                                            Toast.LENGTH_LONG).show();
                                                        break;
                                                        default: Toast.makeText(GestionProducto.this,"No se pudo crear el producto1 !",
                                                                Toast.LENGTH_LONG).show();
                                                        break;
                                                    }
                                                }
                                                @Override
                                                public void
                                                onFailure(Call<Producto> call, Throwable t) {
                                                    Toast.makeText(GestionProducto.this,"No se pudo crear el producto2 !",
                                                            Toast.LENGTH_LONG).show();
                                                }
                    });


                }
            }
        });
        //Boton guardar, Borrar: Manda un DELETE al id del campo buscar, se puede usar solo desde
        //(ELSE)si se crea producto, da el alta
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductoRetrofit clienteRest=RestClient.getInstance()
                        .getRetrofit()
                        .create(ProductoRetrofit.class);
                Call<Producto> borrarCall=clienteRest.borrar(Integer.parseInt(edtBuscar.getText().toString()));
                borrarCall.enqueue(new Callback<Producto>() {
                    @Override
                    public void
                    onResponse(Call<Producto> call, Response<Producto> resp) {// procesar la respuesta
                        switch(resp.code()) {
                            case 200:
                                Toast.makeText(GestionProducto.this, "Producto borrado1 !",
                                        Toast.LENGTH_LONG).show();
                                edtBuscar.setText("");
                                edtPrecio.setText("");
                                edtDescripcion.setText("");
                                edtNombre.setText("");
                                break;
                            default:
                                Toast.makeText(GestionProducto.this,"La opración ha fallado1 !",
                                        Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                    @Override
                    public void
                    onFailure(Call<Producto> call, Throwable t) {
                        Toast.makeText(GestionProducto.this,"La opración ha fallado2 !",
                                Toast.LENGTH_LONG).show();
                    }
                });

            }

        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductoRetrofit clienteRest=RestClient.getInstance()
                        .getRetrofit()
                        .create(ProductoRetrofit.class);
                Call<Producto> buscarCall=clienteRest.buscarProductoPorId(Integer.parseInt(edtBuscar.getText().toString()));
                buscarCall.enqueue(new Callback<Producto>() {
                    @Override
                    public void
                    onResponse(Call<Producto> call, Response<Producto> resp) {// procesar la respuesta
                        switch(resp.code()) {
                            case 200:

                                p = resp.body();
                                idProducto = Integer.parseInt(edtBuscar.getText().toString());
                                edtNombre.setText(p.getNombre());
                                edtDescripcion.setText(p.getDescripcion());
                                edtPrecio.setText(p.getPrecio().toString());
                                spProducto.setSelection(spProducto.getSelectedItemPosition());
                                Toast.makeText(GestionProducto.this, "Busqueda exitosa !",
                                        Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(GestionProducto.this,"Problema en la busqueda1 !",
                                        Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                    @Override
                    public void
                    onFailure(Call<Producto> call, Throwable t) {
                        Toast.makeText(GestionProducto.this,"Problema en la busqueda2 !",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    btnVolver.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(GestionProducto.this, MainActivity.class);
            startActivity(i);
        }
    });

    }
}
