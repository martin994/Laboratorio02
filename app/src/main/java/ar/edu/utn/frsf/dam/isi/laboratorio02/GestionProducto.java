package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.DAOCategoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.DAOProducto;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.MyDataBase;
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
    private Categoria cat;
    private int idProducto = 0;
    private DAOProducto prodDAO;
    private DAOCategoria catDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gestion_producto);
        prodDAO = MyDataBase.getInstance(this).getProductoDao();
        catDAO = MyDataBase.getInstance(this).getCategoriaDao();
        btnGuardar = (Button) findViewById(R.id.buttonProductoGuardar);
        btnBorrar = (Button) findViewById(R.id.buttonProductoBorrar);
        btnVolver = (Button) findViewById(R.id.buttonProductoVolver);
        btnBuscar = (Button) findViewById(R.id.buttonBuscar);
        edtNombre = (EditText) findViewById(R.id.editTextNombre);
        edtDescripcion = (EditText) findViewById(R.id.editTextDescripcion);
        edtPrecio = (EditText) findViewById(R.id.editTextPrecio);
        edtNombre = (EditText) findViewById(R.id.editTextNombre);
        edtBuscar = (EditText) findViewById(R.id.editTextBuscarProdID);
        spProducto = (Spinner) findViewById(R.id.spinnerProducto);
        tgCrearOBuscar = (ToggleButton) findViewById(R.id.toggleButtonCrearOActualizar);
        repoProd = new ProductoRepository();
        Runnable runArray = new Runnable() {
            @Override
            public void run() {
                final List<Categoria> categorias = catDAO.getAll();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        adaptadorCategorias = new ArrayAdapter<Categoria>(getApplicationContext(), android.R.layout.simple_list_item_single_choice, categorias);

                        spProducto.setAdapter(adaptadorCategorias);
                        spProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                cat= (Categoria) parent.getItemAtPosition(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                });

            }
        };
        Thread hiloArray= new Thread(runArray);
        hiloArray.start();


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
                if (tgCrearOBuscar.isChecked()) {
                    btnBorrar.setEnabled(true);
                    btnBuscar.setEnabled(true);
                    edtBuscar.setEnabled(true);
                } else {
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
                if (tgCrearOBuscar.isChecked()) {
                    p = new Producto(edtNombre.getText().toString(),
                            edtDescripcion.getText().toString(),
                            Double.valueOf(edtPrecio.getText().toString()),
                            (Categoria) spProducto.getSelectedItem());
                    p.setId(Long.parseLong(edtBuscar.getText().toString()));
                    Runnable runActualizar = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                final long idProd =prodDAO.update(p);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(GestionProducto.this,"Actualizado: \n"+ p.toString(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

                            } catch (Exception e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(GestionProducto.this, "No se pudo actualizar el producto !",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    };
                    Thread hiloActializar=new Thread(runActualizar);
                    hiloActializar.start();



                } else {
                    p = new Producto(edtNombre.getText().toString(),
                            edtDescripcion.getText().toString(),
                            Double.valueOf(edtPrecio.getText().toString()),
                            (Categoria) spProducto.getSelectedItem());

                    Runnable runCrear = new Runnable() {
                        @Override
                        public void run() {
                            try {

                                final long idProd=prodDAO.insert(p);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(GestionProducto.this,"Creado: \n"+p.toString(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

                            } catch (Exception e) {
                                Toast.makeText(GestionProducto.this, "No se pudo crear el producto !",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    };
                    Thread hiloCrear = new Thread(runCrear);
                    hiloCrear.start();

                }
            }
        });
        //Boton guardar, Borrar: Manda un DELETE al id del campo buscar, se puede usar solo desde
        //(ELSE)si se crea producto, da el alta
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p=new Producto(Long.parseLong(edtBuscar.getText().toString()),"","",0.0,null);
                Runnable runBorrar = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            prodDAO.delete(p);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GestionProducto.this, "Producto borrado!",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GestionProducto.this, "No se pudo borrar el producto!",
                                            Toast.LENGTH_LONG).show();
                                }
                            });


                        }
                    }
                };
                Thread hiloBorrar = new Thread(runBorrar);
                hiloBorrar.start();


            }

        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runBuscar = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<Producto> productos = prodDAO.buscarPorId(Long.parseLong(edtBuscar.getText().toString()));
                            final List<Categoria> categorias = catDAO.getAll();
                            if (productos.size() > 0){ productos.get(0);
                                final Producto p = productos.get(0);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        idProducto = Integer.parseInt(edtBuscar.getText().toString());
                                        edtNombre.setText(p.getNombre());
                                        edtDescripcion.setText(p.getDescripcion());
                                        edtPrecio.setText(p.getPrecio().toString());
                                        spProducto.setSelection(categorias.indexOf(p.getCategoria()));
                                        Toast.makeText(GestionProducto.this, "Busqueda exitosa !",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

                            }


                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GestionProducto.this, "Error en la busqueda !",
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                    }
                };
                Thread hiloBuscar = new Thread(runBuscar);
                hiloBuscar.start();


                btnVolver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(GestionProducto.this, MainActivity.class);
                        startActivity(i);
                    }
                });

            }
        });
    }
}