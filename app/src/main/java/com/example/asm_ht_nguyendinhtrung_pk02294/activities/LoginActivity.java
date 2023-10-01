package com.example.asm_ht_nguyendinhtrung_pk02294.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_ht_nguyendinhtrung_pk02294.R;
import com.example.asm_ht_nguyendinhtrung_pk02294.dao.StudentDAO;
import com.example.asm_ht_nguyendinhtrung_pk02294.home_list.Student;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private StudentDAO studentDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // read login
        readLogin();

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        studentDAO = new StudentDAO(LoginActivity.this);

    }

    public void onLoginClick(View view){
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        Student result = studentDAO.login(username,password);
        if (result == null) {
            Toast.makeText(this,"Login failed", Toast.LENGTH_SHORT).show();
        } else{
            // Lưu trạng thái đăng nhập
            saveLogin(result);

            Toast.makeText(this,"Login success", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,ClazzActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onRegisterClick(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private void saveLogin(Student student){
        SharedPreferences preferences = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putInt("id", student.getId());
        editor.commit();

    }

    private void readLogin(){
        SharedPreferences preferences = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE);
        Boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn){
            Toast.makeText(this, "WELLCOME CLASS",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,ClazzActivity.class);
            startActivity(intent);
            finish();
        }
    }

}