package pe.edu.pucp.tel306.individualfernando;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaComentariosGestorActivity extends AppCompatActivity {

    Articulo articulo = new Articulo();

    Articulo artiEscuchado = new Articulo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_comentarios_gestor);

        Intent intent =  getIntent();
        articulo = (Articulo) intent.getSerializableExtra("arti");

        ArrayList<Comentario> comentarioArrayList = articulo.getComentarioArrayList();

        Comentario [] comentarios = new Comentario[comentarioArrayList.size()];

        for(int i = 1; i<= comentarioArrayList.size();i++){
            comentarios[i-1]=comentarioArrayList.get(i-1);
        }
        //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        //ACA RECYCLER VIEW
        ListaComentariosAdapter adapter = new ListaComentariosAdapter(comentarios,ListaComentariosGestorActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(ListaComentariosGestorActivity.this));
        //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("articulos").child(articulo.getPk()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                    //Articulo articulo = snapshot.getValue(Articulo.class);
                    artiEscuchado = snapshot.getValue(Articulo.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    }
    public void volverADetalleDebate(View view){

        Intent intent = new Intent(ListaComentariosGestorActivity.this, VerDetalleDelArticuloActivity.class);
        intent.putExtra("arti", artiEscuchado);
        startActivity(intent);
        finish();
    }

    public void reff(View view){
        Intent intent = new Intent(ListaComentariosGestorActivity.this, ListaComentariosGestorActivity.class);
        intent.putExtra("arti", artiEscuchado);
        startActivity(intent);
        finish();
    }


}