package pe.edu.pucp.tel306.individualfernando;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///asdsaad

        validacionUsuario();
    }

    public void logeoEmail(View view){
        //List<AuthUI.IdpConfig> proveedores = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(), new AuthUI.IdpConfig.GoogleBuilder().build());
        List<AuthUI.IdpConfig> proveedores = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());

        //AuthMethodPickerLayout authMethodPickerLayout = new AuthMethodPickerLayout.Builder(R.layout.loginlayout).setEmailButtonId(R.id.button5).setGoogleButtonId(R.id.button4).build();
        AuthMethodPickerLayout authMethodPickerLayout = new AuthMethodPickerLayout.Builder(R.layout.loginlayout).setEmailButtonId(R.id.button5).build();

        AuthUI instance = AuthUI.getInstance();

        //Intent intent = instance.createSignInIntentBuilder().setAvailableProviders(proveedores).build();

        //Intent intent = instance.createSignInIntentBuilder().setLogo(R.drawable.ic_launcher_foreground).setAvailableProviders(proveedores).build();

        Intent intent = instance.createSignInIntentBuilder().setLogo(R.drawable.ic_launcher_foreground).setAuthMethodPickerLayout(authMethodPickerLayout).setAvailableProviders(proveedores).build();

        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            validacionUsuario();
        }
    }

    public void validacionUsuario(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null){
            currentUser.reload();
            //SI EL USUARIO ES DIFERENTE DE NULLL, SIGNIFICA QUE SE LOGEO CORRECTAMENTE
            //LO MANDAMOS A LA ACTIVIDAD 2
            if(currentUser.isEmailVerified()){
                if(currentUser.getUid().equals("URwkRvBT7RhAvnexV8yctBOPHIj1")){
                    startActivity(new Intent(this,MainActivity3.class));
                    finish();
                }else{
                    startActivity(new Intent(this,MainActivity2.class));
                    finish();
                }
                //startActivity(new Intent(this,MainActivity2.class));
                //finish();
            }else{
                Toast.makeText(MainActivity.this,"SE LE HA ENVIADO UN  CORREO PARA VERIFICAR SU CUENTA", Toast.LENGTH_SHORT).show();
                currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //EL ADDONCOMPLETE ES PARA SABER QUE EL APLICATIVO HA MANDADO EL CORREO
                        Log.d("infoApp","CORREO ENVIADO");
                    }
                });
            }
        }
    }




}