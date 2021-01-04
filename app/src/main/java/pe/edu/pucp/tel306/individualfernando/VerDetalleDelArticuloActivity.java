package pe.edu.pucp.tel306.individualfernando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class VerDetalleDelArticuloActivity extends AppCompatActivity {

    Articulo articulo = new Articulo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalle_del_articulo);

        Intent intent =  getIntent();
        articulo = (Articulo) intent.getSerializableExtra("arti");


        Log.d("infoApp","ESTAMOS EN VER DETALLES DE ARTICULO");
        Log.d("infoApp","ESTE ES EL ARTICULO Q ME LLEGA : "+ articulo.getPk());


        TextView textView = findViewById(R.id.textView8);
        textView.setText(articulo.getTitulo());

        TextView textView1 = findViewById(R.id.textView9);
        textView1.setText(articulo.getCuerpo());
    }
    public void verComentarios(View view){
        Intent intent = new Intent(VerDetalleDelArticuloActivity.this, VerComentariosActivity.class);
        Log.d("infoApp","ESTE ES EL ARTICULO Q MANDO A LISTA COMENTARIOS : "+ articulo.getPk());
        intent.putExtra("arti", articulo);
        startActivity(intent);
        finish();
    }

}