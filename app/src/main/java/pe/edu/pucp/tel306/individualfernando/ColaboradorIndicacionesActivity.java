package pe.edu.pucp.tel306.individualfernando;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ColaboradorIndicacionesActivity extends AppCompatActivity {

    ArrayList<Articulo> articuloArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colaborador_indicaciones);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
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


    }

    public void mirarArticulos(View view){

        //startActivity(new Intent(MainActivity3.this,ListarArticulosActivity.class));
        //finish();
        Intent intent = new Intent(ColaboradorIndicacionesActivity.this, ListarArticulosActivity.class);
        intent.putExtra("listaArticulos", articuloArrayList);
        startActivity(intent);
        finish();

    }

    public void logout(View view){
        AuthUI instance = AuthUI.getInstance();
        instance.signOut(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(ColaboradorIndicacionesActivity.this,MainActivity.class));
                finish();
            }
        });

    }
}