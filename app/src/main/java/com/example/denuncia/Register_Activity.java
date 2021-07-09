package com.example.denuncia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

// master
import com.example.denuncia.model.Usuarios;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Activity extends AppCompatActivity {

    EditText txt_rut,txt_nombre_apellido,txt_correo,txt_password;

    FirebaseAuth firebaseAuth;

//=======
import com.google.firebase.auth.FirebaseAuth;

public class Register_Activity extends AppCompatActivity {

    EditText txtemail,txtnombre,txtrut,txtpass;
    FirebaseAuth mAuth;
//>>>>>>> master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
// master

        txt_rut             = findViewById(R.id.registar_rut);
        txt_nombre_apellido = findViewById(R.id.register_nombre);
        txt_correo          = findViewById(R.id.register_email);
        txt_password        = findViewById(R.id.register_contraseña);

        firebaseAuth        = FirebaseAuth.getInstance();
//=======

        txtemail = findViewById(R.id.register_email);
        txtnombre = findViewById(R.id.register_nombre);
        txtrut = findViewById(R.id.registar_rut);
        txtpass = findViewById(R.id.register_contraseña);

        mAuth = FirebaseAuth.getInstance();
    }

    public void CreateAccount(View view) {
        String email,nombre,rut,pass;
        email = txtemail.getText().toString();
        nombre = txtnombre.getText().toString();
        rut = txtrut.getText().toString();
        pass = txtpass.getText().toString();

        if (email.isEmpty() || nombre.isEmpty() || rut.isEmpty() || pass.isEmpty()){
            Toast.makeText(this,"Complete la informacion",Toast.LENGTH_LONG).show();
        }else{


        }
// master
    }

    public void Launchloggin(View view) {
        Intent  intent = new Intent(this,Login_Activity.class);
        startActivity(intent);
        finish();//destroy login
    }

    public void CreateAccount(View view) {
        Usuarios usuarios            = new Usuarios();
        final String email           = txt_correo.getText().toString();
        final String nombre_apellido = txt_nombre_apellido.getText().toString();
        final String rut             = txt_rut.getText().toString();
        final String pass            = txt_password.getText().toString();

        //
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database.getReference("message");

        myRef2.setValue("Hello, World!");

        if (rut != usuarios.getRut()) {
        if (email.isEmpty() || nombre_apellido.isEmpty() || rut.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Complete la informacion", Toast.LENGTH_LONG).show();
        } else {
            //create account in firebase
            firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Usuarios");

                                usuarios.setRut(rut);
                                usuarios.setPassword(pass);
                                usuarios.setCorreo(email);
                                usuarios.setNombres_apellidos(nombre_apellido);

                                myRef.push().setValue(usuarios);

                                Toast.makeText(Register_Activity.this, "Cuenta creada", Toast.LENGTH_LONG).show();
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(Register_Activity.this, "Verifique la informacion", Toast.LENGTH_LONG).show();
                                } else {
                                    if (pass.length() < 6) {
                                        Toast.makeText(Register_Activity.this, "La contraseña es muy corta minimo 6 de largo", Toast.LENGTH_LONG).show();
                                    } else {
                                        String msg = task.getException().getMessage();
                                        Toast.makeText(Register_Activity.this, msg, Toast.LENGTH_LONG).show();
                                        //Toast.makeText(Register_Activity.this, "Hubo un error inesperado", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    });
        }
        }else {
            Toast.makeText(Register_Activity.this, "El rut ya esta siendo usado", Toast.LENGTH_LONG).show();
        }

    }


}