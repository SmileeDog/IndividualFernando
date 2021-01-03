package pe.edu.pucp.tel306.individualfernando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ListarArticulosActivity extends AppCompatActivity {

    ArrayList<Articulo> articuloArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_articulos);

        Intent intent =  getIntent();
        articuloArrayList = (ArrayList<Articulo>) intent.getSerializableExtra("listaArticulos");

        Log.d("infoApp","SIZE : "+ articuloArrayList.size());

        Articulo [] articulos = new Articulo[articuloArrayList.size()];

        Log.d("infoApp","LENGTH : "+ articulos.length);

        /*
        articulos[1-1]=articuloArrayList.get(0);
        articulos[2-1]=articuloArrayList.get(1);
        articulos[3-1]=articuloArrayList.get(2);
        */

        for(int i = 1; i<= articuloArrayList.size();i++){
            articulos[i-1]=articuloArrayList.get(i-1);
            //Log.d("infoApp", "ART => TITULO : " + articulos[i-1].getTitulo() + " | AUTOR : " + articulos[i-1].getAutor() + " | FECHA : " + articulos[i-1].getFecha());
            //Log.d("infoApp", "HAB => TITULO : " + articuloArrayList.get(i-1).getTitulo() + " | AUTOR : " + articuloArrayList.get(i-1).getAutor() + " | FECHA : " + articuloArrayList.get(i-1).getFecha());
        }
        //Log.d("infoApp","ALGO : "+ articulos[2].getTitulo());
        /*
        for (Articulo articulo : articuloArrayList){
            Log.d("infoApp", "ART => TITULO : " + articulo.getTitulo() + " | AUTOR : " + articulo.getAutor() + " | FECHA : " + articulo.getFecha());
        }
        Log.d("infoApp", "------------------------------------------------------------------------------------------------------");
        */

        ListaArticulosAdapter adapter = new ListaArticulosAdapter(articulos,ListarArticulosActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(ListarArticulosActivity.this));
    }

}