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
import android.widget.TextView;

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

public class MainActivity3 extends AppCompatActivity {

    ArrayList<Articulo> articuloArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null){
            //SI EL USUARIO ES DIFERENTE DE NULLL, SIGNIFICA QUE SE LOGEO CORRECTAMENTE
            String uid = currentUser.getUid();
            String displayName = currentUser.getDisplayName();
            String email = currentUser.getEmail();

            Log.d("infoApp","UID : " + uid + " |  DISPLAY : " + displayName + " | EMAIL : " + email );

            TextView textView = findViewById(R.id.textView2);
            textView.setText("CRITICO " + displayName);

        }
        //------------------------------------------------------------------------------------------
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        //listener = new ListenerFB();
        //databaseReference.child("GAAA").addValueEventListener(listener);
        //------------------------------------------------------------------------------------------

        databaseReference.child("articulos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getValue() != null ){
                    Articulo articulo = snapshot.getValue(Articulo.class);
                    //Log.d("infoApp", "TITULO : " + articulo.getTitulo() + " | AUTOR : " + articulo.getAutor() + " | FECHA : " + articulo.getFecha());
                    Log.d("infoApp","GAA");

                    articuloArrayList.add(articulo);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getValue() != null ){
                    Articulo articulo = snapshot.getValue(Articulo.class);
                    Log.d("infoApp", "TITULO : " + articulo.getTitulo() + " | AUTOR : " + articulo.getAutor() + " | FECHA : " + articulo.getFecha());
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //------------------------------------------------------------------------------------------

    }

    //private ListenerFB listener;

    /*
    class ListenerFB implements ValueEventListener{
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.getValue() != null){
                Articulo articulo = snapshot.getValue(Articulo.class);
                Log.d("infoApp","GAAAAAAAAAAAAAA");
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.removeEventListener(listener);
    }
    */
    //----------------------------------------------------------------------------------------------
    public void logout(View view){
        AuthUI instance = AuthUI.getInstance();
        instance.signOut(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(MainActivity3.this,MainActivity.class));
                finish();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void guardarArticulo(View view){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Articulo articulo = new Articulo();


        EditText titulo = findViewById(R.id.editTextTextMultiLine2);
        EditText body = findViewById(R.id.editTextTextMultiLine);

        articulo.setAutor(currentUser.getUid());

        articulo.setTitulo(titulo.getText().toString());

        articulo.setCuerpo(body.getText().toString());
        LocalDate localDate = LocalDate.now();
        articulo.setFecha(localDate.toString());

        //LocalDate localDate = LocalDate.;
        //articulo.setFecha(.toString());

        databaseReference.child("articulos").push().setValue(articulo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("infoApp","ARTICULO GUARDADO EXITOSAMENTE");
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

    /*
    public void mostrarArticulos(View view){
        for (Articulo articulo : articuloArrayList){
            Log.d("infoApp", "TITULO : " + articulo.getTitulo() + " | AUTOR : " + articulo.getAutor() + " | FECHA : " + articulo.getFecha());
        }
    }*/

    public void mirarArticulos(View view){

        Intent intent = new Intent(MainActivity3.this, ListarArticulosActivity.class);
        intent.putExtra("listaArticulos", articuloArrayList);
        startActivity(intent);
        finish();

    }



}