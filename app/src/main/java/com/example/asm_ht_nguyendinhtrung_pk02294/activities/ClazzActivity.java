package com.example.asm_ht_nguyendinhtrung_pk02294.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asm_ht_nguyendinhtrung_pk02294.R;
import com.example.asm_ht_nguyendinhtrung_pk02294.adapters.ClazzAdapter;
import com.example.asm_ht_nguyendinhtrung_pk02294.dao.ClazzDAO;
import com.example.asm_ht_nguyendinhtrung_pk02294.home_list.Clazz;

import java.util.ArrayList;

public class ClazzActivity extends AppCompatActivity {

    private ListView lvClazz;
    private ClazzAdapter adapter;
    private ArrayList<Clazz> list;
    private ClazzDAO dao;
    private Button btnStudent;

    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clazz);

        lvClazz = findViewById(R.id.lvClazz);
        btnStudent = findViewById(R.id.btnStudent);

        // Fill data
        dao = new ClazzDAO(ClazzActivity.this);
    }

    public void onClickSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void onAccount(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    public void onStudent(View view) {
        Toast.makeText(ClazzActivity.this, "WELLCOME STUDENT",
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, StudentActivity.class);
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
        adapter = new ClazzAdapter(list); // đưa vô adapter
        lvClazz.setAdapter(adapter);
    }


    public void onDeleteItemClick(Clazz clazz) {
        new AlertDialog.Builder(ClazzActivity.this)
                .setTitle("Xác nhận xoá")
                .setMessage("Xoá sẽ không phục hồi được!!")
                .setNegativeButton("Huỷ", null)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean result = dao.delete(clazz.getId());
                        if (result == true) {
                            Toast.makeText(ClazzActivity.this, "Đã xoá",
                                    Toast.LENGTH_LONG).show();
                            // lấy lại danh sách mới
                            fillData();
                        } else {
                            Toast.makeText(ClazzActivity.this, "Không xoá được",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .show();
    }

    public void onEditItemClick(Clazz clazz) {
        // Load layout, gán giá trị
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_clazz_info, null);
        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtTime = view.findViewById(R.id.edtTime);
        EditText edtRoom = view.findViewById(R.id.edtRoom);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnSave = view.findViewById(R.id.btnSave);

        edtName.setText(clazz.getName());
        edtTime.setText(clazz.getTime());
        edtRoom.setText(clazz.getRoom());

        // gắn sự kiện cho 2 cái button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtName.setText(null);
                edtTime.setText(null);
                edtRoom.setText(null);
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String time = edtTime.getText().toString();
                String room = edtRoom.getText().toString();
                clazz.setName(name);
                clazz.setTime(time);
                clazz.setRoom(room);
                Boolean result = dao.update(clazz);
                if (result){
                    Toast.makeText(ClazzActivity.this, "Đã cập nhập",
                            Toast.LENGTH_LONG).show();
                    // lấy lại danh sách mới
                    fillData();
                } else {
                    Toast.makeText(ClazzActivity.this, "Cập nhập thất bại",
                            Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });

        // hiển thị dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ClazzActivity.this);
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

    public void onAddClick(View _view) {
        // Load layout, gán giá trị
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_add_clazz, null);
        EditText edtTenLop = view.findViewById(R.id.edtTenLop);
        EditText edtTime = view.findViewById(R.id.edtTime);
        EditText edtSoPhong = view.findViewById(R.id.edtSoPhong);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnSave = view.findViewById(R.id.btnSave);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtTenLop.setText(null);
                edtTime.setText(null);
                edtSoPhong.setText(null);
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtTenLop.getText().toString();
                String time = edtTime.getText().toString();
                String room = edtSoPhong.getText().toString();
                Clazz clazz = new Clazz(-1, name, time, room);
                Boolean result = dao.insert(clazz);
                if (result) {
                    Toast.makeText(ClazzActivity.this, "Đã thêm mới",
                            Toast.LENGTH_LONG).show();
                    // lấy lại danh sách mới
                    fillData();
                } else {
                    Toast.makeText(ClazzActivity.this, "Thêm mới thất bại",
                            Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });

        // hiển thị dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ClazzActivity.this);
        builder.setView(view);
        builder.setCancelable(false); // Không đóng được ở ngoài
        dialog = builder.create();
        dialog.show();
    }


}