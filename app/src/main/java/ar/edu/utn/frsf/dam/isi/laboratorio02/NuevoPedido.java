package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.text.DateFormat;
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
    private Button btnVolver;
    private Button btnEliminar;
    private PedidoRepository repoPedido;
    private ProductoRepository repoProducto;
    private Producto nuevoProducto = null;
    private PedidoDetalle nuevoDetalle;
    private Pedido nuevoPedido;
    private CharSequence nombre_canal;
    private SharedPreferences preferencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_pedido);
        repoPedido = new PedidoRepository();
        repoProducto = new ProductoRepository();
        final ArrayAdapter<PedidoDetalle> adapDetalle;
        preferencia= PreferenceManager.getDefaultSharedPreferences(NuevoPedido.this);
        if(getIntent().hasExtra("Id"))  // controlo si el usuario cargo datos en una instancia anterior
            nuevoPedido= repoPedido.buscarPorId(getIntent().getIntExtra("Id",0));
        else
            nuevoPedido = new Pedido();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm");

        BroadcastReceiver br = new EstadoPedidoReciver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(EstadoPedidoReciver.Evento01);
        this.registerReceiver(br,filter);


    //defino los componentes de la actividad

        rBtnEntregaDomicilio = (RadioButton) findViewById(R.id.radioButtonEntregaDomicilio);
        rBtnRetirEnLocal = (RadioButton) findViewById(R.id.radioButtonRetirEnLocal);
        edtDireccion = (EditText) findViewById(R.id.editTextDireccion);
        lVPedido = (ListView) findViewById(R.id.listViewPedidoItems);
        btnPedidoAddProducto = (Button) findViewById(R.id.buttonAgregarProd);
        edtCorreo = (EditText) findViewById(R.id.editTextPedidoCorreo);
        edtHoraEntrega = (EditText) findViewById(R.id.editTextHoraEntrega);
        btnVolver = (Button) findViewById(R.id.buttonPedidoVolver);
        tVTotalPedido = (TextView) findViewById(R.id.textViewTotalPedido);
        btnHacerPedido = (Button) findViewById(R.id.buttonPedidoRealizar);
        btnEliminar = (Button) findViewById(R.id.buttonQuitarProd);

        //si existe el pedido calcula el costo y lo carga en el lbl
        if (repoPedido.getLista().size() != 0)
            tVTotalPedido.setText("" + tVTotalPedido.getText().toString() + ": $" + String.format("%.2f",repoPedido.getLista().get(repoPedido.getLista().size() - 1).total()));

        //Si la actividad que nos instancio quiere crear un Pedido, la clave Dsde vale 0, si quiere mostrar datos, vale 1
        if (getIntent().getIntExtra("Desde", 0) == 0) {
            if (repoPedido.getLista().size() != 0)
                adapDetalle = new ArrayAdapter<PedidoDetalle>(this, android.R.layout.simple_list_item_single_choice, repoPedido.getLista().get(repoPedido.getLista().size() - 1).getDetalle());
            else
                adapDetalle = new ArrayAdapter<PedidoDetalle>(this, android.R.layout.simple_list_item_single_choice, new ArrayList<PedidoDetalle>());
            lVPedido.setAdapter(adapDetalle);

            if(preferencia.getString("edit_text_preference_1", null)!= null){
                edtCorreo.setText(preferencia.getString("edit_text_preference_1", null));
            }else if(nuevoPedido.getMailContacto()!=null){
                edtCorreo.setText(nuevoPedido.getMailContacto());
            }
            if(preferencia.getBoolean("checkbox_preference_1", false)){
                rBtnRetirEnLocal.setChecked(true);
            }else{
                rBtnEntregaDomicilio.setChecked(true);
            }

            if(nuevoPedido.getFecha()!=null)edtHoraEntrega.setText(""+hourFormat.format(nuevoPedido.getFecha()));
            if(nuevoPedido.getDireccionEnvio()!=null)edtDireccion.setText(""+nuevoPedido.getDireccionEnvio());
           // if(nuevoPedido.getMailContacto()!=null)edtCorreo.setText(""+nuevoPedido.getMailContacto());



            btnEliminar.setEnabled(true);
        } else if (getIntent().getIntExtra("Desde", 0) == 1) {

            btnEliminar.setEnabled(false);
            nuevoPedido = repoPedido.buscarPorId(getIntent().getIntExtra("Id", 0));
            edtCorreo.setText(nuevoPedido.getMailContacto());
            rBtnEntregaDomicilio.setEnabled(false);
            rBtnRetirEnLocal.setEnabled(false);
            if (!nuevoPedido.getRetirar()) {
                rBtnEntregaDomicilio.setChecked(true);
                edtDireccion.setText(nuevoPedido.getDireccionEnvio());
            } else rBtnRetirEnLocal.setChecked(true);
            edtHoraEntrega.setText(hourFormat.format(nuevoPedido.getFecha()));
            adapDetalle = new ArrayAdapter<PedidoDetalle>(this, android.R.layout.simple_list_item_1, nuevoPedido.getDetalle());
            lVPedido.setAdapter(adapDetalle);

            btnHacerPedido.setEnabled(false);
            btnPedidoAddProducto.setEnabled(false);
        }

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
        // Nos dirije a la actividad que nos permita seleccionar nuevos pedidos
        btnPedidoAddProducto.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NuevoPedido.this, ProductoLista.class);
                repoPedido.guardarPedido(nuevoPedido);
                i.putExtra("NUEVO_PEDIDO", 1);
                i.putExtra("Id", nuevoPedido.getId());
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String horaString = edtHoraEntrega.getText().toString();
                try {
                    java.util.Date hora = dateFormat.parse(horaString);
                    nuevoPedido.setFecha(hora);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                nuevoPedido.setDireccionEnvio(edtDireccion.getText().toString());
                nuevoPedido.setMailContacto(edtCorreo.getText().toString());
                nuevoPedido.setRetirar(rBtnRetirEnLocal.isChecked());
                startActivityForResult(i, 1);

            }
        });
        //controla si los campos son validos, si lo son pasa a una actividad que muestra la lista de pedidos
        btnHacerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//se validan los campos

                if ( (edtDireccion.getText().length() == 0 && rBtnEntregaDomicilio.isChecked()) ||   edtHoraEntrega.getText().length() == 0 || edtCorreo.getText().length() == 0){

                    CharSequence text = "Campos invalidos!";

                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                } else {// se carga el estado al pedido y se llama a la actividad historial de pedidos
                    nuevoPedido = repoPedido.getLista().get(repoPedido.getLista().size() - 1);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    String horaString = edtHoraEntrega.getText().toString();
                    try {
                        java.util.Date hora = dateFormat.parse(horaString);
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

                    Runnable r = new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Thread.currentThread().sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // buscar pedidos no aceptados y aceptarlos utomáticamente
                            List<Pedido> lista = repoPedido.getLista();
                            for (Pedido p : lista) {
                                if (p.getEstado().equals(Pedido.Estado.REALIZADO))
                                    createNotificationChannel();
                                    p.setEstado(Pedido.Estado.ACEPTADO);
                                    Intent broad= new Intent();
                                    broad.putExtra("id_pedido", p.getId());
                                    //broad.putExtra("Costo", p.total());
                                    //broad.putExtra("Entrega", p.getFecha());
                                    broad.setAction(EstadoPedidoReciver.Evento01);

                                    sendBroadcast(broad);
                            }

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    Toast.makeText(NuevoPedido.this,"Informacion de pedidos actualizada !",
                                    Toast.LENGTH_LONG).show();
                                }
                            });



                        }

                    };

                    Thread unHilo = new Thread(r);
                    unHilo.start();

                    startActivity(i);
                }
            }
        });
        //volvemos al menu principal, se elimina el pedido
        btnVolver.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NuevoPedido.this, MainActivity.class);
                repoPedido.getLista().remove(nuevoPedido);
                startActivity(i);
            }
        });
        //comentar despues, no me acuerdo de esta funcionalidad
        btnEliminar.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                PedidoDetalle aEliminar = ((PedidoDetalle) lVPedido.getSelectedItem());
                nuevoPedido.quitarDetalle(aEliminar);


            }
        });

        lVPedido.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lVPedido.setItemChecked(position, true);
            }
        });
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = nombre_canal;
                String description = "descripcion del canal";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("Canal1", name, importance);
                channel.setDescription(description);

                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

            }
        }
    }
}
