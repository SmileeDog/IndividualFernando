package pe.edu.pucp.tel306.individualfernando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class ListarArticulosActivity extends AppCompatActivity {

    ArrayList<Articulo> articuloArrayList = new ArrayList<>();

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
    }

    public void regresarPrincipalColaborador(View view){
        startActivity(new Intent(ListarArticulosActivity.this,ColaboradorIndicacionesActivity.class));
        finish();
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