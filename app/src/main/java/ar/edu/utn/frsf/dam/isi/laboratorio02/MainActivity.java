package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class MainActivity extends AppCompatActivity {

    private Button btnNuevoPedido;
    private Button btnHistorial;
    private Button btnListaProductos;
    private ProductoRepository repositorioProductos;
    private PedidoRepository repoPedido= null;
    private Pedido nuevo=null;

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);//sobreescritura para respues de los intents
        if(requestCode== RESULT_CANCELED){//si falla no hace nada

        }else{//si tiene exito toma los datos y crea el pedido, recordar que al date se lo debe parsear a long para enviarlo y viceversa
            nuevo=new Pedido(new Date(data.getLongExtra("Fecha",-1)), (ArrayList<PedidoDetalle>)data.getSerializableExtra("Detalle"),
                    (Pedido.Estado) data.getSerializableExtra("Estado"), data.getStringExtra("Direccion"),data.getStringExtra("Mail"),
                    data.getBooleanExtra("Retirar",false));
            if (repoPedido==null){//si el repo no esta creado, lo creo, y agrego el pedido
                repoPedido=new PedidoRepository();
                repoPedido.guardarPedido(nuevo);
            }else{
                repoPedido.guardarPedido(nuevo);
            }
        }

    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            if (repoPedido==null){//si el repo no esta creado, lo creo
                repoPedido=new PedidoRepository();

            }
        setContentView(R.layout.activity_main);//se seleccionan las 3 opciones podibles en la pantalla principal
        btnNuevoPedido = (Button) findViewById(R.id.btnMainNuevoPedido);
        btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NuevoPedido.class);
                startActivityForResult(i, 1);
            }
        });

        btnHistorial = (Button) findViewById(R.id.btnHistorialPedidos);
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                startActivity(i);
            }
        });

        btnListaProductos = (Button) findViewById(R.id.btnListaProductos);

        btnListaProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,ProductoLista.class);
                //i.putExtra("NUEVO_PEDIDO",1);
                //for(Producto p: repositorioProductos.getLista())
                //i.putExtra(p.getNombre(),p.getId().toString());
                startActivity(i);

            }
        });
    }
}
