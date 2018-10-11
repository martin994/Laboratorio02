package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;

public class HistorialPedidos extends AppCompatActivity {
    private ProductoRepository repoProducto;
    private PedidoRepository repoPedido;
    private ListView listaPedidosPersonalizada;
    private Button btnMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedidos);
        repoPedido=new PedidoRepository();
        repoProducto = new ProductoRepository();
        PedidosAdapter adaptadorPedidos=new PedidosAdapter(HistorialPedidos.this, repoPedido.getLista());
        listaPedidosPersonalizada= (ListView) findViewById(R.id.lvHistorialDePedidos);
        listaPedidosPersonalizada.setAdapter(adaptadorPedidos);
        btnMenu=(Button) findViewById(R.id.buttonHistorialAMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HistorialPedidos.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}