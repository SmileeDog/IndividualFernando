package pe.edu.pucp.tel306.individualfernando;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerDetalleDelArticuloActivity extends AppCompatActivity {

    Articulo articulo = new Articulo();

    ArrayList<Articulo> articuloArrayList = new ArrayList<>();

    Articulo artiEscuchado = new Articulo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalle_del_articulo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Log.d("infoApp","ESTAMOS EN LOS DETALLES DE DEBATE DE TODOS");

        Intent intent =  getIntent();
        articulo = (Articulo) intent.getSerializableExtra("arti");


        //Log.d("infoApp","ESTAMOS EN VER DETALLES DE ARTICULO");
        //Log.d("infoApp","ESTE ES EL ARTICULO Q ME LLEGA : "+ articulo.getPk());


        TextView textView = findViewById(R.id.textView8);
        textView.setText(articulo.getTitulo());

        TextView textView1 = findViewById(R.id.textView9);
        textView1.setText(articulo.getCuerpo());

        //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("articulos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getValue() != null ){
                    Articulo articulo = snapshot.getValue(Articulo.class);
                    //Log.d("infoApp", "TITULO : " + articulo.getTitulo() + " | AUTOR : " + articulo.getAutor() + " | FECHA : " + articulo.getFecha());
                    //Log.d("infoApp","GAA");
                    articuloArrayList.add(articulo);
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getValue() != null ){
                    Articulo articulo = snapshot.getValue(Articulo.class);
                    //Log.d("infoApp", "TITULO : " + articulo.getTitulo() + " | AUTOR : " + articulo.getAutor() + " | FECHA : " + articulo.getFecha());
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
        //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
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
        //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    }
    public void verComentarios(View view){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser.getUid().equals("URwkRvBT7RhAvnexV8yctBOPHIj1")){
            Intent intent = new Intent(VerDetalleDelArticuloActivity.this, ListaComentariosGestorActivity.class);
            //Log.d("infoApp","ESTE ES EL ARTICULO Q MANDO A LISTA COMENTARIOS : "+ articulo.getPk());
            intent.putExtra("arti", articulo);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(VerDetalleDelArticuloActivity.this, VerComentariosActivity.class);
            //Log.d("infoApp","ESTE ES EL ARTICULO Q MANDO A LISTA COMENTARIOS : "+ articulo.getPk());
            intent.putExtra("arti", articulo);
            startActivity(intent);
            finish();
        }
    }

    public void volverAListaDebates(View view){
        Intent intent = new Intent(VerDetalleDelArticuloActivity.this, ListarArticulosActivity.class);
        intent.putExtra("listaArticulos", articuloArrayList);
        startActivity(intent);
        finish();
    }

    public void fuera(View view){
        AuthUI instance = AuthUI.getInstance();
        instance.signOut(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(VerDetalleDelArticuloActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    public void refresc(View view){
        Intent intent = new Intent(VerDetalleDelArticuloActivity.this, VerDetalleDelArticuloActivity.class);
        intent.putExtra("arti", artiEscuchado);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            Intent intent = new Intent(VerDetalleDelArticuloActivity.this, VerDetalleDelArticuloActivity.class);
            intent.putExtra("arti", artiEscuchado);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}