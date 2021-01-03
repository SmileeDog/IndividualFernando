package pe.edu.pucp.tel306.individualfernando;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListaArticulosAdapter extends RecyclerView.Adapter<ListaArticulosAdapter.ArticuloViewHolder> {

    private Articulo[] listaArticulos;
    private Context context;

    public ListaArticulosAdapter(Articulo[] listaArticulos, Context context) {
        this.listaArticulos = listaArticulos;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticuloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.itemrecyclerview, parent, false);
        ArticuloViewHolder articuloViewHolder = new ArticuloViewHolder(itemview);
        return articuloViewHolder;
    }

    @Override
    public void onBindViewHolder(ArticuloViewHolder holder, int position) {
        Articulo articulo = listaArticulos[position];
        String data = " / AUTOR : " + articulo.getAutor() + " / TITUTLO : " + articulo.getTitulo() + " / FECHA : " + articulo.getFecha() + " / ";
        holder.textView.setText(data);

        holder.verDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VerDetalleDelArticuloActivity.class);
                intent.putExtra("arti", articulo);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaArticulos.length;
    }

    public static class ArticuloViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public Button verDetalle;

        public ArticuloViewHolder(View itemview){
            super(itemview);
            this.textView = itemview.findViewById(R.id.textView3);

            verDetalle =itemview.findViewById(R.id.button8);



        }
    }

}
