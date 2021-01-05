package pe.edu.pucp.tel306.individualfernando;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
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

    EditText comentarioTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_comentario);

        Log.d("infoApp","ESTAMOS EN EL FORMULARIO DE COMENTARIO DEL COLABORADOR");

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

        comentarioTexto = findViewById(R.id.editTextTextMultiLine3);

    }

    //@RequiresApi(api = Build.VERSION_CODES.O)

    private void limpiarCajas() {
        comentarioTexto.setText("");
    }

    private void validacion() {
        String come = comentarioTexto.getText().toString().trim();
        if (come.equals("")){
            comentarioTexto.setError("EL COMENTARIO NO PUEDE QUEDAR VACIO");
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void guardarComentario(View view){

        String come = comentarioTexto.getText().toString().trim();

        if (come.equals("")) {
            validacion();
        } else {
            //guardarComentario();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//--------------------------------------------------------------------------------------------------
            Intent intent = getIntent();
            articulo = (Articulo) intent.getSerializableExtra("arti");
            Log.d("infoApp", "ESTE ES EL ARTICULO Q ME LLEGA : " + articulo.getPk());

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


            comentario.setAutor(currentUser.getUid());

            LocalDate localDate = LocalDate.now();
            comentario.setFecha(localDate.toString());
            comentario.setCuerpo(come);
//--------------------------------------------------------------------------------------------------
            Log.d("infoApp", "COMENT : " + comentario.getCuerpo());

            comeAL.add(comentario);

            articulo.setComentarioArrayList(comeAL);

            //Articulo finalArticulo = articulo;
            databaseReference.child("articulos/" + articulo.getPk()).setValue(articulo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(FormularioComentarioActivity.this, "COMENTARIO GUARDADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();

                            Log.d("infoApp", "COMENTARIO GUARDADO EXITOSAMENTE");
                            Log.d("infoApp", "TENEMOS ESTO");
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
                            Log.d("infoApp", "NO C PUDO GUARDAR");
                        }
                    });

            //
            limpiarCajas();
            //return true;
        }

    }

    public void irAComentarios(View view){
        Intent intent = new Intent(FormularioComentarioActivity.this, VerComentariosActivity.class);
        intent.putExtra("arti", artiEscuchado);
        startActivity(intent);
        finish();
    }

    public void salir(View view){
        AuthUI instance = AuthUI.getInstance();
        instance.signOut(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(FormularioComentarioActivity.this,MainActivity.class));
                finish();
            }
        });
    }


}