package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;

public class HistorialPedidos extends AppCompatActivity {
    private ProductoRepository repoProducto;
    private PedidoRepository repoPedido;
    private ListView listaPedidosPersonalizada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedidos);
        repoPedido=new PedidoRepository();
        repoProducto = new ProductoRepository();
        PedidosAdapter adaptadorPedidos=new PedidosAdapter(HistorialPedidos.this, repoPedido.getLista());
        listaPedidosPersonalizada= (ListView) findViewById(R.id.lvHistorialDePedidos);
        listaPedidosPersonalizada.setAdapter(adaptadorPedidos);


    }
}
