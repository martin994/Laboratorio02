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

import java.util.ArrayList;

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
        final ArrayAdapter<Categoria> categoriasDeProductoSpinner = new ArrayAdapter<Categoria>(this, android.R.layout.simple_list_item_1,repoProducto.getCategorias());
        final Spinner spinnerDeCategorias = (Spinner) findViewById(R.id.spinnerCategorias);
        spinnerDeCategorias.setAdapter(categoriasDeProductoSpinner);


       //final String[] datosSpinner = new String[]{"Elem1","Elem2"};
        //Se crea el adapter de Productos y se lo setea al ListView
        final ArrayAdapter<Producto> adaptadorDeListViewProd = new ArrayAdapter<Producto>(this, android.R.layout.simple_spinner_item,repoProducto.buscarPorCategoria(repoProducto.getCategorias().get(0)));
        final ListView listViewDeProductos = (ListView) findViewById(R.id.listViewProducto);
        listViewDeProductos.setAdapter(adaptadorDeListViewProd);

        spinnerDeCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                adaptadorDeListViewProd.addAll(repoProducto.buscarPorCategoria(repoProducto.getCategorias().get(position)));
                listViewDeProductos.setAdapter(adaptadorDeListViewProd);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        });


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

                 startActivity(i);
             }
         });



    }
}