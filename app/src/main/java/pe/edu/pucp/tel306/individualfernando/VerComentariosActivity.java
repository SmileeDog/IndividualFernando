package pe.edu.pucp.tel306.individualfernando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VerComentariosActivity extends AppCompatActivity {

    Articulo articulo = new Articulo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_comentarios);

        Intent intent =  getIntent();
        articulo = (Articulo) intent.getSerializableExtra("arti");


    }


    public void irAFormularioComentario(View view){
        //startActivity(new Intent(VerComentariosActivity.this,FormularioComentarioActivity.class));
        //finish();

        Intent intent = new Intent(VerComentariosActivity.this, FormularioComentarioActivity.class);
        intent.putExtra("arti", articulo);
        startActivity(intent);
        finish();

    }
}