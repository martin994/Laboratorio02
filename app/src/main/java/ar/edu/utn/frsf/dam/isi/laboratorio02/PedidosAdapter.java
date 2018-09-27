package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class PedidosAdapter extends ArrayAdapter {

    private Context ctx;
    private List<Pedido> datos;

    public PedidosAdapter(Context context, List<Pedido> objects) {
        super (context, 0,objects);
        this.ctx = context;
        this.datos = objects;
    }
}
