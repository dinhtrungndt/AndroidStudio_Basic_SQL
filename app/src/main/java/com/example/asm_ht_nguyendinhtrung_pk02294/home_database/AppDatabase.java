package com.example.asm_ht_nguyendinhtrung_pk02294.home_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabase extends SQLiteOpenHelper {
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) instance = new AppDatabase(context);
        return instance;
    }

    private AppDatabase(Context context) {
        super(context, "MY_DATABASE", null, 4);
    }

    /**
     * chỉ chạy 1 lần duy nhất, lần đầu tiên
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlClazz = "CREATE TABLE IF NOT EXISTS CLAZZS(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT , TIME TEXT, ROOM TEXT )";
        db.execSQL(sqlClazz);
        String sqlStudents = "CREATE TABLE IF NOT EXISTS STUDENTS(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, NAME TEXT, CLAZZ_ID INTEGER, FOREIGN KEY (CLAZZ_ID) REFERENCES CLAZZS(ID) )";
        db.execSQL(sqlStudents);

        db.execSQL("insert into CLAZZS (name, time, room) " +
                "values  ('Phoenicopterus ruber', '8:54 PM', 65) ," +
                "('Terrapene carolina', '4:18 PM', 33) ," +
                "('Nesomimus trifasciatus', '11:59 AM', 4) ," +
                "('Zonotrichia capensis', '9:20 PM', 53) ");

        db.execSQL("insert into STUDENTS (username, password, name, clazz_id) " +
                "values ('ADmin', 'ADmin123', 'Terri Coulthart', 2),"+
                "('tcoulthart0', 'DuNVBlg', 'Terri Coulthart', 2)," +
                "('ibarnardo1', 'tiHIwkd', 'Ives Barnardo', 2)," +
                "('dgierke2', 'UtaviVO3', 'Dennison Gierke', 1)," +
                "('bcarbonell3', 'nkxX3n', 'Beryle Carbonell', 2);");

    }

    /**
     * chạy khi version tăng lên
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS STUDENTS");
            db.execSQL("DROP TABLE IF EXISTS CLAZZS");
            onCreate(db);
        }
    }

    /**
     * Ràng buộc khóa chính khóa ngoại
     */
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
