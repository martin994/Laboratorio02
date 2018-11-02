package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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

    private Button btnMainNuevoPedido;
    private Button btnHistorial;
    private Button btnListaProductos;
    private Button btnPrepararPedido;
    private Button btnCrearActualizarProd;
    private Button  btnCrearCategoria;

    private Button btnConfiguracion;

    private ProductoRepository repositorioProductos;
    private PedidoRepository repoPedido = null;
    private Pedido nuevo = null;
    private ProductoRepository repoProducto;
    private CharSequence nombre_canal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);//se seleccionan las 3 opciones podibles en la pantalla principal

        repoPedido = new PedidoRepository();
        repoProducto = new ProductoRepository();

        btnMainNuevoPedido = (Button) findViewById(R.id.btnMainNuevoPedido);
        btnMainNuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NuevoPedido.class);
                i.putExtra("Desde", 0);
                startActivityForResult(i, 1);
            }
        });

        btnHistorial = (Button) findViewById(R.id.btnHistorialPedidos);
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HistorialPedidos.class);
                startActivity(i);
            }
        });

        btnListaProductos = (Button) findViewById(R.id.btnListaProductos);

        btnListaProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, ProductoLista.class);
                i.putExtra("NUEVO_PEDIDO", 0);
                //for(Producto p: repositorioProductos.getLista())
                //i.putExtra(p.getNombre(),p.getId().toString());
                startActivity(i);

            }
        });
        btnPrepararPedido = (Button) findViewById(R.id.btnPrepararPedido);
        btnPrepararPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PrepararPedidoService.class);
                startService(intent);
            }
        });


        btnCrearCategoria= (Button) findViewById(R.id.buttonCategorias);
        btnCrearCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CategoriaActivity.class);
                startActivity(i);
            }
        });


        btnConfiguracion = (Button) findViewById(R.id.btnConfiguracion);
        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ConfiguracionActivity.class);
                startActivity(i);
            }
        });
        btnCrearActualizarProd=(Button) findViewById(R.id.btnCrearActualizar);
        btnCrearActualizarProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GestionProducto.class);
                startActivity(i);
            }
        });

    }


}