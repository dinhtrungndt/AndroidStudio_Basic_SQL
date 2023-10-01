package com.example.asm_ht_nguyendinhtrung_pk02294.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asm_ht_nguyendinhtrung_pk02294.R;
import com.example.asm_ht_nguyendinhtrung_pk02294.adapters.StudentAdapter;
import com.example.asm_ht_nguyendinhtrung_pk02294.dao.ClazzDAO;
import com.example.asm_ht_nguyendinhtrung_pk02294.dao.StudentDAO;
import com.example.asm_ht_nguyendinhtrung_pk02294.home_list.Clazz;
import com.example.asm_ht_nguyendinhtrung_pk02294.home_list.Student;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity  {

    private ListView lvStudents;
    private StudentAdapter adapter;
    private ArrayList<Student> list;
    private StudentDAO dao;
    private Button btnClass;

    private ArrayList<Clazz> clazzes = new ArrayList<>();
    private ClazzDAO clazzDAO;

    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        lvStudents = findViewById(R.id.lvStudent);
        btnClass = findViewById(R.id.btnClass);

        // Fill data
        dao = new StudentDAO(StudentActivity.this);

        clazzDAO = new ClazzDAO(StudentActivity.this);
        clazzes = clazzDAO.getAll();
    }

    public void onClickSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void onAccount(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    public void onClass(View view){
        Toast.makeText(StudentActivity.this, "WELLCOME CLASS",
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ClazzActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLogoutClick(View view) {
        SharedPreferences preferences = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("isLoggedIn");
        editor.remove("id");
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void fillData() {
        list = dao.getAll(); // Lấy data
        adapter = new StudentAdapter(list); // đưa vô adapter
        lvStudents.setAdapter(adapter);
    }

    public void onDeleteClick(Student student) {
        new AlertDialog.Builder(StudentActivity.this)
                .setTitle("Xác nhận xoá")
                .setMessage("Xoá sẽ không phục hồi được!!")
                .setNegativeButton("Huỷ", null)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean result = dao.delete(student.getId());
                        if (result == true) {
                            Toast.makeText(StudentActivity.this, "Đã xoá",
                                    Toast.LENGTH_LONG).show();
                            // lấy lại danh sách mới
                            fillData();
                        } else {
                            Toast.makeText(StudentActivity.this, "Không xoá được",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .show();
    }

    public void onEditClick(Student student){
        // Load layout, gán giá trị
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_student_info,null);
        EditText editStudentName = view.findViewById(R.id.edtStudentName);
        EditText edtStudentClazz = view.findViewById(R.id.edtStudentClazz);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnSave = view.findViewById(R.id.btnSave);

        editStudentName.setText(student.getName());
        edtStudentClazz.setText(student.getClazz_id().toString());

        // gắn sự kiện cho 2 cái button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editStudentName.setText(null);
                edtStudentClazz.setText(null);
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editStudentName.getText().toString();
                String clazz = edtStudentClazz.getText().toString();
                student.setName(name);
                student.setClazz_id(Integer.parseInt(clazz));
                Boolean result = dao.update(student);
                if (result){
                    Toast.makeText(StudentActivity.this, "Đã cập nhập",
                            Toast.LENGTH_LONG).show();
                    // lấy lại danh sách mới
                    fillData();
                } else {
                    Toast.makeText(StudentActivity.this, "Cập nhập thất bại",
                            Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });

        // hiển thị dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
        builder.setTitle("Cập nhập thông tin sinh viên");
        builder.setView(view);
        builder.setCancelable(false); // Không đóng được ở ngoài
        dialog = builder.create();
        dialog.show();
    }



    @Override
    protected void onResume() {
        super.onResume();
        fillData();
    }

    public void onAddClick(View _view){
        final Integer[] clazzId = {-1};
        // Load layout, gán giá trị
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_add_student,null);
        EditText edtUsername = view.findViewById(R.id.edtUsername);
        EditText edtPassword = view.findViewById(R.id.edtPassword);
        EditText editStudentName = view.findViewById(R.id.edtStudentName);
        Spinner edtStudentClazz = view.findViewById(R.id.edtStudentClazz);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnSave = view.findViewById(R.id.btnSave);

        ArrayAdapter<Clazz> arrayAdapter = new ArrayAdapter<>(StudentActivity.this,
                android.R.layout.simple_spinner_item, clazzes.toArray(new Clazz[0]));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtStudentClazz.setAdapter(arrayAdapter);
        edtStudentClazz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter = parent.getAdapter();
                Clazz clazz = (Clazz) adapter.getItem(position);
                clazzId[0] = clazz.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtUsername.setText(null);
                edtPassword.setText(null);
                editStudentName.setText(null);
//                edtStudentClazz.setText(null);
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
//                String clazz = edtStudentClazz.getText().toString();
                String name = editStudentName.getText().toString();
                Student student = new Student(-1, clazzId[0], username, password, name);
                Boolean result = dao.insert(student);
                if (result){
                    Toast.makeText(StudentActivity.this, "Đã thêm mới",
                            Toast.LENGTH_LONG).show();
                    // lấy lại danh sách mới
                    fillData();
                } else {
                    Toast.makeText(StudentActivity.this, "Thêm mới thất bại",
                            Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });

        // hiển thị dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
        builder.setTitle("Thêm thông tin sinh viên");
        builder.setView(view);
        builder.setCancelable(false); // Không đóng được ở ngoài
        dialog = builder.create();
        dialog.show();
    }

}