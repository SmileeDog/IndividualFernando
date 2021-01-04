package pe.edu.pucp.tel306.individualfernando;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;

public class FormularioComentarioActivity extends AppCompatActivity {

    //Articulo articulo = new Articulo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_comentario);

        //Intent intent =  getIntent();
        //articulo = (Articulo) intent.getSerializableExtra("arti");
        //Log.d("infoApp", "ESTAMOS EN EL FORMULARIO COMENTARIO");
        //Log.d("infoApp", "ARTICULO --- COMMENT : " + articulo.getTitulo() + " | AUTOR : " + articulo.getAutor() + " | FECHA : " + articulo.getFecha());

        //-----------------------------------------------------------------------------------------------------------

    }

    public void guardarComentario(View view){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Articulo articulo = new Articulo();
        //LocalDate localDate = LocalDate.now();
//--------------------------------------------------------------------------------------------------
        Intent intent =  getIntent();
        articulo = (Articulo) intent.getSerializableExtra("arti");
//--------------------------------------------------------------------------------------------------
        ArrayList<Comentario> comeAL = new ArrayList<>();
        comeAL = articulo.getComentarioArrayList();

        /*
        if(articulo.getComentarioArrayList().size()!=0){
            comeAL = articulo.getComentarioArrayList();
        }*/
//--------------------------------------------------------------------------------------------------
        Comentario comentario = new Comentario();
        EditText comentarioTexto = findViewById(R.id.editTextTextMultiLine3);
        comentario.setAutor(currentUser.getUid());
        //comentario.setFecha(localDate.toString());
        comentario.setCuerpo(comentarioTexto.getText().toString());
//--------------------------------------------------------------------------------------------------
        Log.d("infoApp","COMENT : " + comentario.getCuerpo());

        comeAL.add(comentario);

        articulo.setComentarioArrayList(comeAL);

        Articulo finalArticulo = articulo;
        databaseReference.child("articulos/"+articulo.getPk()).setValue(articulo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(FormularioComentarioActivity.this,"COMENTARIO GUARDADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();

                        Log.d("infoApp","COMENTARIO GUARDADO EXITOSAMENTE");
                        Log.d("infoApp","TENEMOS ESTO");
                        for(Comentario c : finalArticulo.getComentarioArrayList()){
                            Log.d("infoApp","COMEEE : " + c.getCuerpo());
                        }
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


}