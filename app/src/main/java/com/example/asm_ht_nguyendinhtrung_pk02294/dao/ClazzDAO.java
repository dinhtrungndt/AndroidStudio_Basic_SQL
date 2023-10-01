package com.example.asm_ht_nguyendinhtrung_pk02294.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asm_ht_nguyendinhtrung_pk02294.home_database.AppDatabase;
import com.example.asm_ht_nguyendinhtrung_pk02294.home_list.Clazz;

import java.util.ArrayList;

public class ClazzDAO {
    private AppDatabase database;
    public ClazzDAO(Context context){
        database = AppDatabase.getInstance(context);
    }

    public Boolean insert(Clazz clazz){
        Boolean result = true;
        SQLiteDatabase db = database.getReadableDatabase();
        db.beginTransaction(); // bat dau tuong tac
        try {
            ContentValues values = new ContentValues();
            values.put("NAME", clazz.getName());
            values.put("ROOM", clazz.getRoom());
            values.put("TIME", clazz.getTime());
            Long rows = db.insertOrThrow("CLAZZS", null, values);
            result = rows >=1;
            db.setTransactionSuccessful(); // xac nha thanh cong
        }catch (Exception e){
            Log.d(">>>>>>>", "insert: " +e.getMessage());
        }finally {
            db.endTransaction(); // dong giao dien
        }

        return result;
    }
    public Boolean update(Clazz clazz){
        Boolean result = true;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); // bat dau tuong tac
        try {
            ContentValues values = new ContentValues();
            values.put("NAME", clazz.getName());
            values.put("ROOM", clazz.getRoom());
            values.put("TIME", clazz.getTime());
            Integer rows = db.update("CLAZZS", values, "ID = ?",
                    new String[]{clazz.getId().toString()});
            result = rows >=1;
            db.setTransactionSuccessful(); // xac nhan thanh cong
        }catch (Exception e){
            Log.d(">>>>>>>", "update: " +e.getMessage());
        }finally {
            db.endTransaction(); // dong giao dien
        }
        return result;
    }

    // delete from clazz where id = 1
    public Boolean delete(Integer id) {
        Boolean result = true;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); // bat dau tuong tac
        try {
            Integer rows = db.delete("CLAZZS","ID = ?",
                    new String[]{id.toString()});
            result = rows <= 1;
            db.setTransactionSuccessful(); // xac nhan thanh cong
        } catch (Exception e) {
            Log.d(">>>>>>>", "delete: " + e.getMessage());
        } finally {
            db.endTransaction(); // dong giao dien
        }
        return result;
    }

    public ArrayList<Clazz> getAll(){
        ArrayList<Clazz> list = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT ID, NAME, TIME, ROOM FROM CLAZZS ";
        Cursor cursor = db.rawQuery(sql, null);
        try {
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    Integer _id = cursor.getInt(0);
                    String _name = cursor.getString(1);
                    String _time = cursor.getString(2);
                    String _room = cursor.getString(3);
                    Clazz clazz = new Clazz(_id, _name,_time,_room);
                    list.add(clazz);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.d(">>>>>>>>>", "getAll: "+e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();{

            }
        }
        return list;
    }

}
