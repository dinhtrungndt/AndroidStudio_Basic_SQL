package com.example.asm_ht_nguyendinhtrung_pk02294.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_ht_nguyendinhtrung_pk02294.R;
import com.example.asm_ht_nguyendinhtrung_pk02294.dao.StudentDAO;
import com.example.asm_ht_nguyendinhtrung_pk02294.home_list.Student;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private StudentDAO studentDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        studentDAO = new StudentDAO(RegisterActivity.this);

    }

    //  id,  clazz_id,  username,  password,  name
    public void onRegisterClick(View view) {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String pattenUsername = "[a-zA-Z0-9]{6,}" // ít nhất 6 ký tự đặc biệt, không trắng
                ,pattenPassword = "\\w{6,}"; // ít nhất 6 ký tự, có số, ký tự đặc biệt
        if (!username.matches(pattenUsername)|| !password.matches(pattenPassword)){
            Toast.makeText(this, "Register failed", Toast.LENGTH_LONG).show();
            return;
        }
        Student student = new Student(-1, 1, username, password, null);
        Boolean result = studentDAO.insert(student);
        if (result == null) {
            Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Register success", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onLoginClick(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

}