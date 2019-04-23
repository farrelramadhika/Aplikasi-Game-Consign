package com.example.android.gameconsign;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.android.gameconsign.model.UserPenjual;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistPenjualActivity extends AppCompatActivity {

    Button btnRegPenjual;
    EditText etUserPenjual,etPassPenjual;
    String email,password;

    private FirebaseAuth mAuth;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_penjual);

        mAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference(MainActivity.table1);

        btnRegPenjual = findViewById(R.id.registpenjual);
        etUserPenjual = findViewById(R.id.userregistpenjual);
        etPassPenjual = findViewById(R.id.passregistpenjual);

        btnRegPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etUserPenjual.getText().toString();
                password = etPassPenjual.getText().toString();

                if(validateForm()){
                    createAccount(email, password);
                } else {
                    Toast.makeText(RegistPenjualActivity.this, "Mohon lengkapi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    protected void onStart(){
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            sendToMain();
//        }
//    }

    private boolean validateForm(){
        boolean valid = false;

        if (email.isEmpty() || password.isEmpty()){
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    private void sendToMain(){
        Intent main = new Intent(RegistPenjualActivity.this, MainActivity.class);
        startActivity(main);
        finish();
    }

    private void createAccount (final String email, final String password){

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String id = mAuth.getUid();
                            UserPenjual user = new UserPenjual(id, password, email);
                            databaseUser.child(id).setValue(user);
                            Intent a = new Intent(RegistPenjualActivity.this, MainActivity.class);
                            startActivity(a);
                        } else {
                            Toast.makeText(RegistPenjualActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
