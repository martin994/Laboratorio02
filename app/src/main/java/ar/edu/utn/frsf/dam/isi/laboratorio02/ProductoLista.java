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

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;


public class ProductoLista extends AppCompatActivity {

    private TextView tvCategoria;
    private TextView tvListProducto;
    private TextView tvCantidad;
    private EditText edtCantidad;
    private Button btnAgregar;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_lista);
        tvCategoria= (TextView) findViewById(R.id.textViewCategoria);
        tvListProducto= (TextView) findViewById(R.id.textViewListProducto);
        tvCantidad= (TextView) findViewById(R.id.textViewCantidad);
        edtCantidad = (EditText) findViewById(R.id.editTextCantidad);
        btnAgregar = (Button) findViewById(R.id.buttonAgregar);
        final ProductoRepository prodRepo= new ProductoRepository();//se inicializa con 4 categorias y 25 productos


        if((this.getIntent().getExtras().getInt("NUEVO_PEDIDO"))==1){//Si se solicita un nuevo pedido se habilitan los campos cantidad y el boton agregar punto 2.f
            edtCantidad.setEnabled(true);
            btnAgregar.setEnabled(true);
        }


        //Se crea el adaptador de Categoria y se lo settea al spinner
        final ArrayAdapter<Categoria> categoriasDeProductoSpinner = new ArrayAdapter<Categoria>(this, android.R.layout.simple_list_item_1,prodRepo.getCategorias());
        final Spinner spinnerDeCategorias = (Spinner) findViewById(R.id.spinnerCategorias);
        spinnerDeCategorias.setAdapter(categoriasDeProductoSpinner);


       //final String[] datosSpinner = new String[]{"Elem1","Elem2"};
        //Se crea el adapter de Productos y se lo setea al ListView
        final ArrayAdapter<Producto> adaptadorDeListViewProd = new ArrayAdapter<Producto>(this, android.R.layout.simple_spinner_item,prodRepo.getLista());
        final ListView listViewDeProductos = (ListView) findViewById(R.id.listViewProducto);
        listViewDeProductos.setAdapter(adaptadorDeListViewProd);
        /*
        System.out.println(prodRepo.getLista().toString());
        System.out.println(adaptadorDeListViewProd.getItem(1).toString());
        Incompleto, si secambia de categoria, se actualiza el listviewAdapter, pero no puedo sacar los viejos sin limpiar todo, sino sigue agregando sin fin
        */
        spinnerDeCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // adaptadorDeListViewProd.clear();
                adaptadorDeListViewProd.addAll(prodRepo.buscarPorCategoria(prodRepo.getCategorias().get(position)));
                listViewDeProductos.setAdapter(adaptadorDeListViewProd);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Si se selecciona un producto, se remueve de la lista, falta agregar a un nuevo pedido este producto
        listViewDeProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//
                adaptadorDeListViewProd.remove((Producto) listViewDeProductos.getSelectedItem());
                listViewDeProductos.setAdapter(adaptadorDeListViewProd);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //incompleto, se debería devolver una bandera para que el MainActivity sepa quien lo creo y un nuevo Pedido

         btnAgregar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(ProductoLista.this,MainActivity.class);
                 i.putExtra("cantidad", Integer.getInteger(edtCantidad.getText().toString()));
                 ArrayList prods=(ArrayList<Producto>) savedInstanceState.get("productos");
                 i.putExtra("p",prods);
             }
         });

    }
}