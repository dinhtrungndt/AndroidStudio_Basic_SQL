package com.example.asm_ht_nguyendinhtrung_pk02294.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.asm_ht_nguyendinhtrung_pk02294.R;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_actyvity);
    }

    public void onHomeSearch(View view){
        Intent intent = new Intent(this, ClazzActivity.class);
        startActivity(intent);
        finish();
    }
    public void onAcc(View view){
        Intent intent = new Intent(this, ClazzActivity.class);
        startActivity(intent);
        finish();
    }
}