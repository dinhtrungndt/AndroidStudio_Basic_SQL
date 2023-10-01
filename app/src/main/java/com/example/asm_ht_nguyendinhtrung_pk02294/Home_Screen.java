package com.example.asm_ht_nguyendinhtrung_pk02294;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.asm_ht_nguyendinhtrung_pk02294.activities.LoginActivity;
import com.example.asm_ht_nguyendinhtrung_pk02294.activities.RegisterActivity;

public class Home_Screen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    public void onClickLogin(View view) {
        Intent login = new Intent(Home_Screen.this, LoginActivity.class);
        startActivity(login);
        finish();
    }

    public void onClickRegister(View view) {
        Intent Register = new Intent(Home_Screen.this, RegisterActivity.class);
        startActivity(Register);
        finish();
    }

}