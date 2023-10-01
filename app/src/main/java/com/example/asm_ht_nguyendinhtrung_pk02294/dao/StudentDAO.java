package com.example.asm_ht_nguyendinhtrung_pk02294.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asm_ht_nguyendinhtrung_pk02294.home_database.AppDatabase;
import com.example.asm_ht_nguyendinhtrung_pk02294.home_list.Student;

import java.util.ArrayList;

/**
 * Viết các hàm thao tác với DB
 * insert, update, delete, select
 */

public class StudentDAO {
    private AppDatabase database;
    public StudentDAO(Context context){
        database = AppDatabase.getInstance(context);
    }

    // register
    public  Boolean insert(Student student){
        Boolean result = true;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); // bắt đầu tương tác db
        try{
            ContentValues values = new ContentValues();
            values.put("USERNAME", student.getUsername());
            values.put("PASSWORD", student.getPassword());
            values.put("CLAZZ_ID", student.getClazz_id());
            values.put("NAME", student.getName());
            Long rows = db.insertOrThrow("STUDENTS", null, values);
            result = rows >= 1;
            db.setTransactionSuccessful(); // Xác nhận thành công
        }catch (Exception e){
            Log.d(">>>>>>>>TAG","insert: " + e.getMessage());
        }finally {
            db.endTransaction(); // Đóng giao dịch
        }
        return result;
    }
    public  Boolean update(Student student){
        Boolean result = true;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); // bắt đầu tương tác db
        try{
            ContentValues values = new ContentValues();
            values.put("CLAZZ_ID", student.getClazz_id());
            values.put("NAME", student.getName());
            Integer rows = db.update("STUDENTS", values, " ID = ? ",
                    new String[]{student.getId().toString()});
            result = rows >= 1;
            db.setTransactionSuccessful(); // Xác nhận thành công
        }catch (Exception e){
            Log.d(">>>>>>>>TAG","update: " + e.getMessage());
        }finally {
            db.endTransaction(); // Đóng giao dịch
        }
        return result;
    }
    // delete from student where id = 1
    public  Boolean delete(Integer id){
        Boolean result = true;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); // bắt đầu tương tác db
        try{
            Integer rows = db.delete("STUDENTS", " ID = ? ",
                    new String[]{id.toString()});
            result = rows >= 1;
            db.setTransactionSuccessful(); // Xác nhận thành công
        }catch (Exception e){
            Log.d(">>>>>>>>TAG","detele: " + e.getMessage());
        }finally {
            db.endTransaction(); // Đóng giao dịch
        }
        return result;
    }
    //Select.....
    public ArrayList<Student> getAll(){
        ArrayList<Student> list = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT ID, USERNAME, PASSWORD, NAME, CLAZZ_ID FROM STUDENTS";
        Cursor cursor = db.rawQuery(sql,null);
        try{
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    Integer _id = cursor.getInt(0);
                    String _username = cursor.getString(1);
                    String _password = cursor.getString(2);
                    String _name = cursor.getString(3);
                    Integer _clazz_id = cursor.getInt(4);
                    Student student = new Student(_id, _clazz_id, _username, _password, _name);
                    list.add(student);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.d(">>>>>>>>TAG","getAll: " + e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()) cursor.close(); // đóng con trỏ
        }
        return list;
    }

    // login
    public Student login(String username, String password){
        Student student = null;
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT ID, USERNAME, PASSWORD, NAME, CLAZZ_ID FROM STUDENTS WHERE USERNAME = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{username});
        try{
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    Integer _id = cursor.getInt(0);
                    String _username = cursor.getString(1);
                    String _password = cursor.getString(2);
                    String _name = cursor.getString(3);
                    Integer _clazz_id = cursor.getInt(4);
                    if (!password.equals(_password)) break;
                    student = new Student(_id, _clazz_id, _username, null, _name);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.d(">>>>>>>>TAG","Login: " + e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()) cursor.close(); // đóng con trỏ
        }
        return student;
    }

}
