package pe.edu.pucp.tel306.individualfernando;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListarArticulosActivity extends AppCompatActivity {

    ArrayList<Articulo> articuloArrayList = new ArrayList<>();

    ArrayList<Articulo> artiArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_articulos);

        Intent intent =  getIntent();
        articuloArrayList = (ArrayList<Articulo>) intent.getSerializableExtra("listaArticulos");

        Log.d("infoApp","SIZE : " + articuloArrayList.size());
        Articulo [] articulos = new Articulo[articuloArrayList.size()];
        Log.d("infoApp","LENGTH : "+ articulos.length);
        for(int i = 1; i<= articuloArrayList.size();i++){
            articulos[i-1]=articuloArrayList.get(i-1);
        }

        ListaArticulosAdapter adapter = new ListaArticulosAdapter(articulos,ListarArticulosActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(ListarArticulosActivity.this));

        //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("articulos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getValue() != null ){
                    Articulo articulo = snapshot.getValue(Articulo.class);
                    //Log.d("infoApp", "TITULO : " + articulo.getTitulo() + " | AUTOR : " + articulo.getAutor() + " | FECHA : " + articulo.getFecha());
                    Log.d("infoApp","GAA");
                    artiArrayList.add(articulo);
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
        //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    }

    public void refrescar(View view){
        Intent intent = new Intent(ListarArticulosActivity.this, ListarArticulosActivity.class);
        intent.putExtra("listaArticulos", artiArrayList);
        startActivity(intent);
        finish();
    }

    public void regresaaaaar(View view){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser.getUid().equals("URwkRvBT7RhAvnexV8yctBOPHIj1")){
            startActivity(new Intent(ListarArticulosActivity.this,MainActivity3.class));
            finish();
        }else{
            startActivity(new Intent(ListarArticulosActivity.this,ColaboradorIndicacionesActivity.class));
            finish();
        }
    }

    public void out(View view){
        AuthUI instance = AuthUI.getInstance();
        instance.signOut(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(ListarArticulosActivity.this,MainActivity.class));
                finish();
            }
        });

    }


}