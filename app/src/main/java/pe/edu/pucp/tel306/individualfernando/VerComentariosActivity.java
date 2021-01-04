package pe.edu.pucp.tel306.individualfernando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class VerComentariosActivity extends AppCompatActivity {

    Articulo articulo = new Articulo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_comentarios);

        Intent intent =  getIntent();
        articulo = (Articulo) intent.getSerializableExtra("arti");

        Log.d("infoApp","ESTAMOS EN LISTA COMENTARIOS");
        Log.d("infoApp","ESTE ES EL ARTICULO Q ME LLEGA : "+ articulo.getPk());

        ArrayList<Comentario> comentarioArrayList = articulo.getComentarioArrayList();

        //Log.d("infoApp","SIZE : " + articuloArrayList.size());

        //Articulo [] articulos = new Articulo[articuloArrayList.size()];
        Comentario [] comentarios = new Comentario[comentarioArrayList.size()];

        //Log.d("infoApp","LENGTH : "+ articulos.length);
        for(int i = 1; i<= comentarioArrayList.size();i++){
            comentarios[i-1]=comentarioArrayList.get(i-1);
        }

        ListaComentariosAdapter adapter = new ListaComentariosAdapter(comentarios,VerComentariosActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewComentarios);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(VerComentariosActivity.this));



    }


    public void irAFormularioComentario(View view){
        //startActivity(new Intent(VerComentariosActivity.this,FormularioComentarioActivity.class));
        //finish();

        Intent intent = new Intent(VerComentariosActivity.this, FormularioComentarioActivity.class);
        Log.d("infoApp","ESTE ES EL ARTICULO Q MANDO A FORMULARIO COMENTARIO : "+ articulo.getPk());
        intent.putExtra("arti", articulo);
        startActivity(intent);
        finish();

    }
}