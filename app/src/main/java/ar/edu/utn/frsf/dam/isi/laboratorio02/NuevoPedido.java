package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;

public class NuevoPedido extends AppCompatActivity {
    private RadioButton rBtnRetirEnLocal;
    private RadioButton rBtnEntregaDomicilio;
    private EditText edtDireccion;
    private ListView lVPedido;
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

    }
}
