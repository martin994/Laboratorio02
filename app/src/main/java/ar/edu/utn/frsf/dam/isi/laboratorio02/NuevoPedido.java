package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class NuevoPedido extends AppCompatActivity {
    private RadioButton rBtnRetirEnLocal;
    private RadioButton rBtnEntregaDomicilio;
    private EditText edtDireccion;
    private ListView lVPedido;
    private Button btnPedidoAddProducto;
    private Producto nuevoProducto=null;
    private PedidoDetalle nuevoDetalle;
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);//sobreescritura para respues de los intents
        if(requestCode== RESULT_CANCELED){//si falla no hace nada

        }else{
            nuevoProducto =(Producto) data.getSerializableExtra("Producto");
            nuevoDetalle=new PedidoDetalle(data.getIntExtra("Cantidad",-1), nuevoProducto);
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_pedido);


        //ArrayAdapter<PedidoDetalle> adapDetalle = new ArrayAdapter<PedidoDetalle>(this, android.R.layout.simple_list_item_1, (ArrayList<PedidoDetalle>) this.getIntent().getSerializableExtra("Detalles"));
        ArrayAdapter<PedidoDetalle> adapDetalle = new ArrayAdapter<PedidoDetalle>(this, android.R.layout.simple_list_item_1, new ArrayList<PedidoDetalle>());

        rBtnEntregaDomicilio = (RadioButton) findViewById(R.id.radioButtonEntregaDomicilio);
        rBtnRetirEnLocal = (RadioButton) findViewById(R.id.radioButtonRetirEnLocal);
        edtDireccion = (EditText) findViewById(R.id.editTextDireccion);
        lVPedido= (ListView) findViewById(R.id.listViewPedidoItems);
        lVPedido.setAdapter(adapDetalle);
        btnPedidoAddProducto=(Button) findViewById(R.id.buttonAgregar);

        rBtnEntregaDomicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 edtDireccion.setEnabled(true);
            }
        });
        rBtnRetirEnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtDireccion.setEnabled(false);
            }
        });
        btnPedidoAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NuevoPedido.this, ProductoLista.class);
                i.putExtra("NUEVO_PEDIDO",1);

                startActivityForResult(i, 1);


            }
        });
    }
}
