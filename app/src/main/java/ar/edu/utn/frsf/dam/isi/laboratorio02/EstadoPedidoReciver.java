package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;

public class EstadoPedidoReciver extends BroadcastReceiver {
    public static final String Evento01="ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_ACEPTADO";
    public static final String Evento02="ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_CANCELADO";
    public static final String Evento03="ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_EN_PREPARACION";
    public static final String Evento04="ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_LISTO";

    @Override
    public void onReceive(Context context, Intent intent) {
        //if(intent.getAction().toString()==Evento01)
        //    Toast.makeText(context, "=>"+"Estado aceptado.", Toast.LENGTH_LONG).show();
        if(intent.hasExtra("id_pedido")) {
            PedidoRepository repoPedido= new PedidoRepository();
            Pedido p = repoPedido.buscarPorId(intent.getExtras().getInt("id_producto"));
            DateFormat hourFormat = new SimpleDateFormat("HH:mm");
            Intent destino =new Intent(context, NuevoPedido.class);
            destino.putExtra("Desde", 1);
            destino.putExtra("Id" , p.getId());
            destino.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent= PendingIntent.getActivity(context,0, destino,0);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "Canal1")
                    .setContentTitle("Pedido Realizado")
                    .setSmallIcon(R.drawable.domicilio)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("El costo sera: $" + String.format("%.2f",p.total()) +"\nSe entrega a las: "+hourFormat.format(p.getFecha())))
                    .setPriority((NotificationCompat.PRIORITY_DEFAULT))
                    .setContentIntent(pendingIntent);


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(99, mBuilder.build());
        }
    }
}
