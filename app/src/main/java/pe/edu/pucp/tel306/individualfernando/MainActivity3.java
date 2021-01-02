package pe.edu.pucp.tel306.individualfernando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity3 extends AppCompatActivity {

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
            textView.setText("BIENVENIDO SENHOR CRITICO " + displayName);
        }


    }

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
}