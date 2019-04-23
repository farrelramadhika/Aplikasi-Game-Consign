package com.example.android.gameconsign;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPembeliActivity extends AppCompatActivity {

    Button btnLoginPembeli;
    EditText etUserPembeli, etPassPembeli;

    String email, password;

    private FirebaseAuth mAuth;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pembeli);

        mAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference(MainActivity.table2);

        btnLoginPembeli = findViewById(R.id.loginpembeli);
        etUserPembeli = findViewById(R.id.userloginpembeli);
        etPassPembeli = findViewById(R.id.passloginpembeli);

        btnLoginPembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etUserPembeli.getText().toString();
                password = etPassPembeli.getText().toString();

                if(validateForm()){
                    signIn(email,password);
                }else{
                    Toast.makeText(LoginPembeliActivity.this, "Mohon Lengkapi", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent a = new Intent(LoginPembeliActivity.this, MenuPembeliActivity.class);
                    startActivity(a);
                } else {
                    Toast.makeText(LoginPembeliActivity.this, "Akun Belum Terdaftar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    protected  void onStart(){
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null){
//            sendToMain();
//        }
//    }

    private boolean validateForm(){
        boolean valid = false;

        if(email.isEmpty() || password.isEmpty()){
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    private void sendToMain(){
        Intent main = new Intent(LoginPembeliActivity.this, MenuPembeliActivity.class);
        startActivity(main);
        finish();
    }


}
