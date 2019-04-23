package com.example.android.gameconsign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    Button regPenjual, regPembeli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regPenjual = findViewById(R.id.regpenjual);
        regPembeli = findViewById(R.id.regpembeli);

        regPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (RegisterActivity.this, RegistPenjualActivity.class);
                startActivity(intent);
            }
        });

        regPembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, RegistPembeliActivity.class);
                startActivity(intent);
            }
        });
    }
}
