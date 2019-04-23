package com.example.android.gameconsign;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.android.gameconsign.model.UserPenjual;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPenjualActivity extends AppCompatActivity {

    Button btnLoginPenjual;
    EditText etUserPenjual, etPassPenjual;
    String email, password;

    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference(MainActivity.table1);
        ref = FirebaseDatabase.getInstance().getReference().child("UserPenjual");

        btnLoginPenjual = findViewById(R.id.loginpenjual);
        etUserPenjual = findViewById(R.id.userloginpenjual);
        etPassPenjual = findViewById(R.id.passloginpenjual);

        btnLoginPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etUserPenjual.getText().toString();
                password = etPassPenjual.getText().toString();


//                    ref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            UserPenjual user = dataSnapshot.getValue(UserPenjual.class);
//                            if (password.equals(user.getPassword())) {
//                                Intent a = new Intent(LoginPenjualActivity.this, MenuPenjualActivity.class);
//                                startActivity(a);
//                            } else {
//                                Toast.makeText(LoginPenjualActivity.this, "Mohon Lengkapi", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });

                if(validateForm()){
                    signIn(email,password);
                }else{
                    Toast.makeText(LoginPenjualActivity.this, "Mohon Lengkapi", Toast.LENGTH_SHORT).show();
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
                    Intent a = new Intent(LoginPenjualActivity.this, MenuPenjualActivity.class);
                    startActivity(a);
                } else {
                    Toast.makeText(LoginPenjualActivity.this, "Akun Belum Terdaftar", Toast.LENGTH_SHORT).show();
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
        Intent main = new Intent(LoginPenjualActivity.this, MenuPenjualActivity.class);
        startActivity(main);
        finish();
    }
}
