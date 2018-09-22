package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class ProductoLista extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ProductoLista);
        final String[] datosSpinner = new String[]{"Elem1","Elem2"};
        ArrayAdapter<String> categoriasDeProductoSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,datosSpinner);
        Spinner spinnerDeProductos = (Spinner) findViewById(R.id.spinner);
        spinnerDeProductos.setAdapter(categoriasDeProductoSpinner);

        final String[] datosListView = new String[]{"Elem1","Elem2"};
        ArrayAdapter<String> categoriasDeProductoLista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datosListView);
        ListView listaDeProductos = (ListView) findViewById(R.id.listViewProducto);
        listaDeProductos.setAdapter(categoriasDeProductoLista);
    }
}
