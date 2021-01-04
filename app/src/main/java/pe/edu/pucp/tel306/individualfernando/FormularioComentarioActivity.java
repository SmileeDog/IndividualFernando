package pe.edu.pucp.tel306.individualfernando;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;

public class FormularioComentarioActivity extends AppCompatActivity {

    Articulo articulo = new Articulo();
    Articulo artiEscuchado = new Articulo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_comentario);

        Intent intent =  getIntent();
        articulo = (Articulo) intent.getSerializableExtra("arti");

        /*
        Articulo articuloQllego = new Articulo();
        Intent intent =  getIntent();
        articuloQllego = (Articulo) intent.getSerializableExtra("arti");*/

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

        //Intent intent =  getIntent();
        //articulo = (Articulo) intent.getSerializableExtra("arti");
        //Log.d("infoApp", "ESTAMOS EN EL FORMULARIO COMENTARIO");
        //Log.d("infoApp", "ARTICULO --- COMMENT : " + articulo.getTitulo() + " | AUTOR : " + articulo.getAutor() + " | FECHA : " + articulo.getFecha());

        //-----------------------------------------------------------------------------------------------------------

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void guardarComentario(View view){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//--------------------------------------------------------------------------------------------------
        Intent intent =  getIntent();
        articulo = (Articulo) intent.getSerializableExtra("arti");
        Log.d("infoApp","ESTE ES EL ARTICULO Q ME LLEGA : "+ articulo.getPk());

//--------------------------------------------------------------------------------------------------
        ArrayList<Comentario> comeAL = new ArrayList<>();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Log.d("infoApp","CUANTO T MIDE : " + articulo.getComentarioArrayList().size());
        comeAL = articulo.getComentarioArrayList();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /*
        if(articulo.getComentarioArrayList().size()!=0){
            comeAL = articulo.getComentarioArrayList();
        }*/
//--------------------------------------------------------------------------------------------------
        Comentario comentario = new Comentario();
        EditText comentarioTexto = findViewById(R.id.editTextTextMultiLine3);
        comentario.setAutor(currentUser.getUid());

        LocalDate localDate = LocalDate.now();
        comentario.setFecha(localDate.toString());
        comentario.setCuerpo(comentarioTexto.getText().toString());
//--------------------------------------------------------------------------------------------------
        Log.d("infoApp","COMENT : " + comentario.getCuerpo());

        comeAL.add(comentario);

        articulo.setComentarioArrayList(comeAL);

        //Articulo finalArticulo = articulo;
        databaseReference.child("articulos/"+articulo.getPk()).setValue(articulo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(FormularioComentarioActivity.this,"COMENTARIO GUARDADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();

                        Log.d("infoApp","COMENTARIO GUARDADO EXITOSAMENTE");
                        Log.d("infoApp","TENEMOS ESTO");
                        /*
                        for(Comentario c : finalArticulo.getComentarioArrayList()){
                            Log.d("infoApp","COMEEE : " + c.getCuerpo());
                        }
                        */
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Log.d("infoApp","NO C PUDO GUARDAR");
                    }
                });
    }

    public void irAComentarios(View view){

        Intent intent = new Intent(FormularioComentarioActivity.this, VerComentariosActivity.class);
        intent.putExtra("arti", artiEscuchado);
        startActivity(intent);
        finish();

    }


}