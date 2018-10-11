package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class PrepararPedidoService extends IntentService {
    PedidoRepository repoPedido;

    public PrepararPedidoService() {
        super("PrepararPedidoService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Thread.currentThread().sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PedidoRepository repoPedido= new PedidoRepository();
        for(Pedido p:repoPedido.getLista()){
            if(p.getEstado()== Pedido.Estado.ACEPTADO){
                p.setEstado(Pedido.Estado.EN_PREPARACION);
                Intent broad= new Intent();
                broad.putExtra("id_pedido", p.getId());
                broad.setAction(EstadoPedidoReciver.Evento03);
                sendBroadcast(broad);
            }
        }
    }
}
