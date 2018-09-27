package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;

public class PedidosAdapter extends ArrayAdapter {

    int cantidadUnPedido = 0;

    private Context ctx;
    private List<Pedido> datos;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public PedidosAdapter(Context context, List<Pedido> listaDePedidos) {
        super(context, 0, listaDePedidos);
        this.ctx = context;
        this.datos = listaDePedidos;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View fila = convertView;
        if (fila == null) {
            fila = inflater.inflate(R.layout.fila_historial, parent, false);
        }
        PedidoHolder holder = (PedidoHolder) fila.getTag();
        if (holder == null) {
            holder = new PedidoHolder(fila);
            fila.setTag(holder);
        }




        Pedido unPedido = (Pedido) super.getItem(position);

        holder.tvMailPedido.setText(unPedido.getMailContacto());
        holder.tvHoraDeEntrega.setText("Fecha:" + sdf.format(unPedido.getFecha()));
        holder.btnCancelar.setTag(position);
        for (PedidoDetalle i : unPedido.getDetalle()) {
            cantidadUnPedido += i.getCantidad();
        }
        holder.tvCantidadDeItems.setText(""+cantidadUnPedido);

        holder.tvPrecio.setText(unPedido.total().toString());

        switch (unPedido.getEstado()) {
            case LISTO:
                holder.estado.setTextColor(Color.DKGRAY);
                break;
            case ENTREGADO:
                holder.estado.setTextColor(Color.BLUE);
                break;
            case CANCELADO:
            case RECHAZADO:
                holder.estado.setTextColor(Color.RED);
                break;
            case ACEPTADO:
                holder.estado.setTextColor(Color.GREEN);
                break;
            case EN_PREPARACION:
                holder.estado.setTextColor(Color.MAGENTA);
                break;
            case REALIZADO:
                holder.estado.setTextColor(Color.BLUE);
                break;


        }

        if (unPedido.getRetirar()) {
            holder.tipoEntrega.setImageResource(R.drawable.domicilio);
        } else holder.tipoEntrega.setImageResource(R.drawable.sucursal);

        holder.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indice = (Integer) v.getTag();
                Pedido pedidoSeleccionado = datos.get(indice);
                if (pedidoSeleccionado.getEstado().equals(Pedido.Estado.REALIZADO) || pedidoSeleccionado.getEstado().equals(Pedido.Estado.ACEPTADO) || pedidoSeleccionado.getEstado().equals(Pedido.Estado.EN_PREPARACION)) {
                    pedidoSeleccionado.setEstado(Pedido.Estado.CANCELADO);
                    PedidosAdapter.this.notifyDataSetChanged();
                    return;
                }

            }
        });

        holder.btnVerdetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indice = (Integer) v.getTag();
                Pedido pedidoSeleccionado = datos.get(indice);
            }
        });






        return fila;
    }
}
