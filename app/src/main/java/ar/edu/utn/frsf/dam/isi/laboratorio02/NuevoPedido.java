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
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class NuevoPedido extends AppCompatActivity {
    private RadioButton rBtnRetirEnLocal;
    private RadioButton rBtnEntregaDomicilio;
    private EditText edtDireccion;
    private ListView lVPedido;
    private Button btnPedidoAddProducto;
    private TextView tVTotalPedido;
    private Button btnHacerPedido;
    private EditText edtCorreo;
    private EditText edtHoraEntrega;

    private  PedidoRepository repoPedido;
    private ProductoRepository repoProducto;
    private Producto nuevoProducto=null;
    private PedidoDetalle nuevoDetalle;
    private Pedido nuevoPedido;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_pedido);
        repoPedido=new PedidoRepository();
        repoProducto=new ProductoRepository();

        nuevoPedido=new Pedido();
        ArrayAdapter<PedidoDetalle> adapDetalle;
        if(repoPedido.getLista().size()!=0)
            adapDetalle = new ArrayAdapter<PedidoDetalle>(this, android.R.layout.simple_list_item_1, repoPedido.getLista().get(repoPedido.getLista().size()-1).getDetalle());
        else
            adapDetalle = new ArrayAdapter<PedidoDetalle>(this, android.R.layout.simple_list_item_1, new ArrayList<PedidoDetalle>());
        rBtnEntregaDomicilio = (RadioButton) findViewById(R.id.radioButtonEntregaDomicilio);
        rBtnRetirEnLocal = (RadioButton) findViewById(R.id.radioButtonRetirEnLocal);
        edtDireccion = (EditText) findViewById(R.id.editTextDireccion);
        lVPedido= (ListView) findViewById(R.id.listViewPedidoItems);
        btnPedidoAddProducto=(Button) findViewById(R.id.buttonAgregarProd);
        edtCorreo=(EditText) findViewById(R.id.editTextPedidoCorreo);
        edtHoraEntrega=(EditText) findViewById(R.id.editTextHoraEntrega);
        lVPedido.setAdapter(adapDetalle);
        tVTotalPedido = (TextView) findViewById(R.id.textViewTotalPedido);
        btnHacerPedido = (Button) findViewById(R.id.buttonPedidoRealizar);
        if(repoPedido.getLista().size()!=0)
        tVTotalPedido.setText( "" + tVTotalPedido.getText().toString() +repoPedido.getLista().get(repoPedido.getLista().size()-1).total().toString());

        rBtnEntregaDomicilio.setOnClickListener(new View.OnClickListener() {//cambia el campo direccion de  habilitado a deshabilitad
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
        btnPedidoAddProducto.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NuevoPedido.this, ProductoLista.class);
                repoPedido.guardarPedido(nuevoPedido);
                i.putExtra("NUEVO_PEDIDO",1);
                startActivityForResult(i, 1);

            }
        });
        btnHacerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//se validan los campos
                if(edtDireccion.getText().length()==0 || edtHoraEntrega.getText().length()==0 || edtCorreo.getText().length()==0){
                    CharSequence text = "Campos invalidos!";

                    Toast toast = Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT);
                    toast.show();
                }else{// se carga el estado al pedido y se llama a la actividad historial de pedidos
                    nuevoPedido=repoPedido.getLista().get(repoPedido.getLista().size() - 1);
                    SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm");
                    String horaString=edtHoraEntrega.getText().toString();
                    try {
                        java.util.Date hora =dateFormat.parse(horaString);
                        nuevoPedido.setFecha(hora);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    nuevoPedido.setEstado(Pedido.Estado.REALIZADO);

                    nuevoPedido.setDireccionEnvio(edtDireccion.getText().toString());
                    nuevoPedido.setMailContacto(edtCorreo.getText().toString());
                    nuevoPedido.setRetirar(rBtnRetirEnLocal.isChecked());
                    Intent i = new Intent(NuevoPedido.this, HistorialPedidos.class);


                    //Deberíamos setear los repo y todos los objetos que no esten en los repo

                    startActivity(i);

                }
            }
        });


    }
}
