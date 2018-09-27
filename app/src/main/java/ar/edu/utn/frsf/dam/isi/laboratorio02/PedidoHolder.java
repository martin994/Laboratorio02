package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PedidoHolder extends AppCompatActivity {

    public TextView tvMailPedido;
    public TextView tvHoraDeEntrega;
    public TextView tvCantidadDeItems;
    public TextView tvPrecio;
    public TextView estado;
    public ImageView tipoEntrega;
    public Button btnCancelar;



    PedidoHolder(View base){
        this.tvMailPedido=(TextView)base.findViewById(R.id.textViewEmailPedido);
        this.tvHoraDeEntrega=(TextView)base.findViewById(R.id.textViewHoraDenetrega);
        this.tvCantidadDeItems=(TextView)base.findViewById(R.id.textViewCantidadItems);
        this.tvPrecio=(TextView)base.findViewById(R.id.textViewPrecio);
        this.estado=(TextView)base.findViewById(R.id.textViewEestado);
        this.tipoEntrega=(ImageView)base.findViewById(R.id.imageViewTipoEntrega);
        this.btnCancelar=(Button)base.findViewById(R.id.buttonCancelarPedido);
    }



}
