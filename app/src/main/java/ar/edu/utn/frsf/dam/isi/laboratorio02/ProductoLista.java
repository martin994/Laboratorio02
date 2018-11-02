package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;


public class ProductoLista extends AppCompatActivity {

    private TextView tvCategoria;
    private TextView tvListProducto;
    private TextView tvCantidad;
    private EditText edtCantidad;
    private Button btnAgregar;
    private PedidoRepository repoPedido;
    private ProductoRepository repoProducto;
    private PedidoDetalle nuevoDetalle;
    private Producto productoSeleccionado;
    private Pedido actual;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_lista);
        repoPedido = new PedidoRepository();
        repoProducto = new ProductoRepository();

        tvCategoria= (TextView) findViewById(R.id.textViewCategoria);
        tvListProducto= (TextView) findViewById(R.id.textViewListProducto);
        tvCantidad= (TextView) findViewById(R.id.textViewCantidad);
        edtCantidad = (EditText) findViewById(R.id.editTextCantidad);
        btnAgregar = (Button) findViewById(R.id.buttonAgregar);



        if((this.getIntent().getExtras().getInt("NUEVO_PEDIDO"))==1){//Si se solicita un nuevo pedido se habilitan los campos cantidad y el boton agregar punto 2.f
            edtCantidad.setEnabled(true);
            btnAgregar.setEnabled(true);
        }


        //Se crea el adaptador de Categoria y se lo settea al spinner
        //final ArrayAdapter<Categoria> categoriasDeProductoSpinner = new ArrayAdapter<Categoria>(this, android.R.layout.simple_list_item_1,repoProducto.getCategorias());
        //final Spinner spinnerDeCategorias = (Spinner) findViewById(R.id.spinnerCategorias);
        //spinnerDeCategorias.setAdapter(categoriasDeProductoSpinner);


        //Aca empiezo el requerimiento 3

        Runnable r = new Runnable() {
            @Override
            public void run() {
                //Creo una instancia de categoriasRest para poder traer la lista de categorias
                Categoriarest castRes= new Categoriarest();

                final List<Categoria>cats =castRes.listarTodas();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                         ArrayAdapter<Categoria> categoriasDeProductoSpinner = new ArrayAdapter<>(ProductoLista.this, android.R.layout.simple_list_item_1,cats);
                        Spinner spinnerDeCategorias = (Spinner) findViewById(R.id.spinnerCategorias);
                        spinnerDeCategorias.setAdapter(categoriasDeProductoSpinner);
                        spinnerDeCategorias.setSelection(0);
                        final ArrayAdapter<Producto> adaptadorDeListViewProd = new ArrayAdapter<Producto>(ProductoLista.this, android.R.layout.simple_spinner_item,repoProducto.buscarPorCategoria(repoProducto.getCategorias().get(0)));
                        ListView listViewDeProductos = (ListView) findViewById(R.id.listViewProducto);
                        listViewDeProductos.setAdapter(adaptadorDeListViewProd);


                        spinnerDeCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                 adaptadorDeListViewProd.addAll(repoProducto.buscarPorCategoria((Categoria)parent.getItemAtPosition(position)));
                                adaptadorDeListViewProd.notifyDataSetChanged();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }



                        });
                         final ArrayAdapter<Producto> adaptadorDeListViewProd2= new ArrayAdapter<Producto>(ProductoLista.this,android.R.layout.simple_dropdown_item_1line,repoProducto.buscarPorCategoria(repoProducto.getCategorias().get(0)));
                        listViewDeProductos=(ListView) findViewById(R.id.listViewProducto);
                        listViewDeProductos.setAdapter(adaptadorDeListViewProd2);
                    }
                });


            }



        };

        Thread hiloCargarCombo = new Thread(r);
        hiloCargarCombo.start();


        //final String[] datosSpinner = new String[]{"Elem1","Elem2"};
        //Se crea el adapter de Productos y se lo setea al ListView
        /*final ArrayAdapter<Producto> adaptadorDeListViewProd = new ArrayAdapter<Producto>(this, android.R.layout.simple_spinner_item,repoProducto.buscarPorCategoria(repoProducto.getCategorias().get(0)));
        final ListView listViewDeProductos = (ListView) findViewById(R.id.listViewProducto);
        listViewDeProductos.setAdapter(adaptadorDeListViewProd);


        spinnerDeCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Categoria catSeleccionada = (Categoria) spinnerDeCategorias.getSelectedItem();
                List<Producto> prodList= repoProducto.buscarPorCategoria(catSeleccionada);
                adaptadorDeListViewProd.clear();
                adaptadorDeListViewProd.addAll(prodList);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        })

        //Si se selecciona un producto, se remueve de la lista, falta agregar a un nuevo pedido este producto

        listViewDeProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productoSeleccionado=(Producto) listViewDeProductos.getItemAtPosition(position);
                adaptadorDeListViewProd.remove((Producto) listViewDeProductos.getItemAtPosition(position));
                listViewDeProductos.setAdapter(adaptadorDeListViewProd);
            }
        });


        listViewDeProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        //incompleto, se debería devolver una bandera para que el MainActivity sepa quien lo creo y un nuevo Pedido

         btnAgregar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {//agrgar validacion si cantidad es igual a cero, no cancelar
                 Intent i = new Intent(ProductoLista.this,NuevoPedido.class);
                nuevoDetalle=new PedidoDetalle(Integer.parseInt(edtCantidad.getText().toString()), productoSeleccionado);

                 actual = repoPedido.getLista().get(repoPedido.getLista().size()-1);
                 nuevoDetalle.setPedido(actual);
                 actual.agregarDetalle(nuevoDetalle);
                 //repoPedido.getLista().(repoPedido.getLista().size()-1)
                i.putExtra("Desde", 0);
                i.putExtra("Id", getIntent().getIntExtra("Id", 0));
                 startActivity(i);
             }
         });




    }
}