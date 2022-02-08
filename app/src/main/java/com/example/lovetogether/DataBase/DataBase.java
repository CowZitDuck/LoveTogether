package com.example.lovetogether.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lovetogether.MainActivity;

import java.util.Arrays;

public class DataBase extends SQLiteOpenHelper {


    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public void InsertImage(String tableName, byte[] image) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO "+tableName+" VALUES(null, ?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindBlob(1, image);
        sqLiteStatement.executeInsert();
    }

    public void UpdateImage(String tableName, byte[] image, Integer id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE " + tableName + " SET Image = ? WHERE Id = '" + id.toString() + "'";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindBlob(1, image);
        sqLiteStatement.executeInsert();
    }

    public void DeleteImage(String tableName, Integer id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM " + tableName + " WHERE Id = '" + id + "'";
        database.execSQL(sql);
        Cursor cursor = this.getData("SELECT * FROM " + tableName);
        int i = 0;
        while (cursor.moveToNext()) {
            i++;
            String qd_sql = "UPDATE " + tableName + " SET Id = " + i + " WHERE Image = ?";
            byte[] image = cursor.getBlob(1);
            SQLiteStatement sqLiteStatement = database.compileStatement(qd_sql);
            sqLiteStatement.clearBindings();
            sqLiteStatement.bindBlob(1, image);
            sqLiteStatement.executeInsert();
        }
        database.execSQL("UPDATE sqlite_sequence SET seq = " + i + " WHERE name = '" + tableName + "'");
    }

    public void InsertMemory(String name, String date, String content, byte[] image) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO "+ MainActivity.MEMORY_TABLE + " VALUES(null, ?, ?, ?, ?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1, name);
        sqLiteStatement.bindString(2, date);
        sqLiteStatement.bindString(3, content);
        sqLiteStatement.bindBlob(4, image);
        sqLiteStatement.executeInsert();
    }

    public void DeleteMemory(Integer id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM " + MainActivity.MEMORY_TABLE + " WHERE Id = " + id + "";
        database.execSQL(sql);
        Cursor cursor = this.getData("SELECT * FROM " + MainActivity.MEMORY_TABLE);
        int i = 0;
        while (cursor.moveToNext()) {
            i++;
            String name = cursor.getString(1);
            database.execSQL("UPDATE " + MainActivity.MEMORY_TABLE + " SET Id = " + i + " WHERE Name = '" + name + "'");
        }
        database.execSQL("UPDATE sqlite_sequence SET seq = " + i + " WHERE name = '" + MainActivity.MEMORY_TABLE + "'");
    }

    public void ReplaceMemory(String name, String date, String content, byte[] image, Integer id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE " + MainActivity.MEMORY_TABLE + " SET Name = ?, Date = ?, Content = ?, Image = ? WHERE Id = ?";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1, name);
        sqLiteStatement.bindString(2, date);
        sqLiteStatement.bindString(3, content);
        sqLiteStatement.bindBlob(4, image);
        sqLiteStatement.bindString(5, id.toString());
        sqLiteStatement.executeInsert();
    }
}
