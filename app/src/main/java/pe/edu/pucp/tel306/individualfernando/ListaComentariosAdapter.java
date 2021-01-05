package pe.edu.pucp.tel306.individualfernando;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListaComentariosAdapter extends RecyclerView.Adapter<ListaComentariosAdapter.ComentarioViewHolder>{

    private Comentario[] listaComentarios;
    private Context context;

    public ListaComentariosAdapter(Comentario[] listaComentarios, Context context) {
        this.listaComentarios = listaComentarios;
        this.context = context;
    }


    @NonNull
    @Override
    public ComentarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.itemrecyclerviewcomentario,parent,false);
        ComentarioViewHolder comentarioViewHolder = new ComentarioViewHolder(itemview);
        return comentarioViewHolder;
    }

    @Override
    public void onBindViewHolder(ComentarioViewHolder holder, int position) {
        Comentario comentario = listaComentarios[position];
        String autor = "    COMENTADO POR : " + comentario.getAutor() + "\n DESDE " + comentario.getDireccion();
        holder.textView7.setText(autor);
        String fecha = "    FECHA : " + comentario.getFecha();
        holder.textView10.setText(fecha);
        String texto = "    OPINION : " + comentario.getCuerpo();
        holder.textView11.setText(texto);

    }

    @Override
    public int getItemCount() {
        return listaComentarios.length;
    }

    public static class ComentarioViewHolder extends RecyclerView.ViewHolder{

        public TextView textView7;
        public TextView textView10;
        public TextView textView11;
        public ComentarioViewHolder(View itemview){
            super(itemview);

            this.textView7 = itemview.findViewById(R.id.textView7);
            this.textView10 = itemview.findViewById(R.id.textView10);
            this.textView11 = itemview.findViewById(R.id.textView11);


        }
    }

}
