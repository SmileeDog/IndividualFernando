package pe.edu.pucp.tel306.individualfernando;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity3 extends AppCompatActivity {

    ArrayList<Articulo> articuloArrayList = new ArrayList<>();

    EditText descripcion;
    EditText titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        TextView textViewGps = findViewById(R.id.textViewGps);
        Log.d("infoApp","direccion GAAAAAAAAAAAAAA : " + textViewGps.toString());

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

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null){
            //SI EL USUARIO ES DIFERENTE DE NULLL, SIGNIFICA QUE SE LOGEO CORRECTAMENTE
            String uid = currentUser.getUid();
            String displayName = currentUser.getDisplayName();
            String email = currentUser.getEmail();

            Log.d("infoApp","UID : " + uid + " |  DISPLAY : " + displayName + " | EMAIL : " + email );

            TextView textView = findViewById(R.id.textView2);
            textView.setText("INICIAR DEBATE");
        }

        //----------------------------------
        descripcion = findViewById(R.id.editTextTextMultiLine);
        titulo = findViewById(R.id.editTextTextMultiLine2);
        //-----------------------------------
    }

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

    public void salir(){
        AuthUI instance = AuthUI.getInstance();
        instance.signOut(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(MainActivity3.this,MainActivity.class));
                finish();
            }
        });

    }

    private void limpiarCajas() {
        descripcion.setText("");
        titulo.setText("");
    }

    private void validacion() {
        String des = descripcion.getText().toString().trim();
        String tit = titulo.getText().toString().trim();
        if (tit.equals("")){
            descripcion.setError("EL TITULO NO PUEDE QUEDAR VACIO");
        }else if (des.equals("")){
            descripcion.setError("LA DESCRIPCION NO PUEDE QUEDAR VACIA");
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void guardarArticulo(View view){

        String tit = titulo.getText().toString().trim();
        String des = descripcion.getText().toString().trim();

        if(des.equals("") || tit.equals("")){
            validacion();
        }else{

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            Articulo articulo = new Articulo();


            //EditText titulo = findViewById(R.id.editTextTextMultiLine2);
            //EditText body = findViewById(R.id.editTextTextMultiLine);

            articulo.setAutor(currentUser.getUid());

            articulo.setTitulo(tit);

            articulo.setCuerpo(des);

            LocalDate localDate = LocalDate.now();
            articulo.setFecha(localDate.toString());

            String mypk = databaseReference.push().getKey();
            articulo.setPk(mypk);

            ArrayList<Comentario> alcom = new ArrayList<>();
            //----------------------------------------
            Comentario comentario = new Comentario();
            comentario.setAutor(currentUser.getUid());
            comentario.setFecha(localDate.toString());

            comentario.setCuerpo("POR FAVOR, LOS COMENTARIOS DEBEN SER RESPETUOSOS");
//--------------------------------------------------------------------------------------------------
            alcom.add(comentario);
            //----------------------------------------

            articulo.setComentarioArrayList(alcom);

            databaseReference.child("articulos/"+articulo.getPk()).setValue(articulo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("infoApp","ARTICULO GUARDADO EXITOSAMENTE");
                            Toast.makeText(MainActivity3.this,"ARTICULO GUARDADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            Log.d("infoApp","NO C PUDO GUARDAR");
                        }
                    });

            limpiarCajas();
        }
    }

    /*
    public void mostrarArticulos(View view){
        for (Articulo articulo : articuloArrayList){
            Log.d("infoApp", "TITULO : " + articulo.getTitulo() + " | AUTOR : " + articulo.getAutor() + " | FECHA : " + articulo.getFecha());
        }
    }*/

    public void mirarArticulos(View view){
        //startActivity(new Intent(MainActivity3.this,ListarArticulosActivity.class));
        //finish();
        Intent intent = new Intent(MainActivity3.this, ListarArticulosActivity.class);
        intent.putExtra("listaArticulos", articuloArrayList);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity3.this);
            builder.setMessage("EN SERIO DESEAS IRTE?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /*
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            */
                            salir();
                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //gaaaaaaa
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("infoApp", "Permisos concedidos");
            } else {
                Log.d("infoApp", "Persmisos denegados");
            }

        }
    }

    private boolean gpsActivo() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return providerEnabled;
    }

    public void mostrarInfoDeUbicacion(View view) {
        if (gpsActivo()) {
            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                FusedLocationProviderClient location = LocationServices.getFusedLocationProviderClient(this);
                location.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        //if(location.getAltitude() || location)
                        Log.d("infoApp", "ALt" + location.getAltitude());
                        Log.d("infoApp", "Lat" + location.getLatitude());
                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        try {
                            List<Address> direccion = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            Log.d("infoApp", "la direccion es:" + direccion.get(0).getAddressLine(0));

                            TextView textViewGps = findViewById(R.id.textViewGps);
                            textViewGps.setText(direccion.get(0).getAddressLine(0));
                            textViewGps.setVisibility(View.VISIBLE);
                            //deviceUser.setLatitud(location.getLatitude());
                            //deviceUser.setLongitud(location.getLongitude());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
                location.getLastLocation().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("infoApp", "Fallo aquí GA");
                    }
                });
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } else {
            Toast.makeText(MainActivity3.this, "Por favor active su GPS", Toast.LENGTH_SHORT).show(); //FORMATO DE UN TOAST QUE ES COMO UN POP UP
        }


    }


}