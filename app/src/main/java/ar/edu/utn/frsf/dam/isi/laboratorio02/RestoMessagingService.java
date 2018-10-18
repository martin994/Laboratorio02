package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.content.BroadcastReceiver;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class RestoMessagingService extends FirebaseMessagingService {

    PedidoRepository repoPedido = null;
    Pedido actual= null;

    public RestoMessagingService() {
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        /*repoPedido=new PedidoRepository();
        Log.d(RestoFirebaseInstanceIdService.TAG.toString() ,"From: "+ remoteMessage.getFrom());
        Map<String, String> mensajeMap= remoteMessage.getData();
        if(remoteMessage.getData().containsValue("ID_PEDIDO")) {
            actual = repoPedido.buscarPorId(Integer.getInteger(remoteMessage.getData().get("ID_PEDIDO")));
            actual.setEstado(Pedido.Estado.LISTO);
            Intent broad = new Intent();
            broad.putExtra("id_pedido", actual.getId());
            broad.setAction(EstadoPedidoReciver.Evento04);
            sendBroadcast(broad);
        }*/
    }

}
