
package com.example.lovetogether;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;

import com.example.lovetogether.DataBase.DataBase;
import com.example.lovetogether.IntroSlider.IntroSliderActivity;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    final static private int TIMEOUT = 3000;
    final static public String HAS_BACKGROUND = "hasBackground";
    final static public String IS_BACKGROUND_FROM_DATABASE = "isBackgroundFromDataBase";
    final static public String ID_BACKGROUND = "idBackground";
    final static public String HAS_CREATE = "hasCreate";
    final static public String IMAGE_TABLE = "ImageTable";
    final static public String AVATAR_TABLE = "AvatarTable";
    final static public String PERSON_TABLE = "PersonTable";
    final static public String MEMORY_TABLE = "MemoryTable";
    final static public String QUOTE_TABLE = "QuoteTable";
    final static public String DATABASE = "Database";
    final static public String START_FIRST_TIME = "StartFirstTime";
    public static DataBase dataBase;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        CreateDataBase();
        nextState();
    }

    private void CreateDataBase() {
        dataBase = new DataBase(this, DATABASE, null, 1);
        dataBase.QueryData("CREATE TABLE IF NOT EXISTS ImageTable(Id INTEGER PRIMARY KEY AUTOINCREMENT, Image BLOB)");
        dataBase.QueryData("CREATE TABLE IF NOT EXISTS PersonTable(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(50), Birthday VARCHAR(20))");
        dataBase.QueryData("CREATE TABLE IF NOT EXISTS AvatarTable(Id INTEGER PRIMARY KEY AUTOINCREMENT, Image BLOB)");
        dataBase.QueryData("CREATE TABLE IF NOT EXISTS MemoryTable(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(50), Date VARCHAR(20), Content VARCHAR(500), Image BLOB)");
        dataBase.QueryData("CREATE TABLE IF NOT EXISTS QuoteTable(Id INTEGER PRIMARY KEY AUTOINCREMENT, Date VARCHAR(20), Quote VARCHAR(1000))");
        if(!sharedPreferences.getBoolean(HAS_CREATE, false)) {
            CreateDataBaseForPerson();
            CreateDataBaseForAvatar();
            CreateDataBaseForMemory();
            CreateDataBaseForQuote();
            editor.putBoolean(HAS_CREATE, true);
            editor.commit();
        }
    }

    private void CreateDataBaseForQuote() {
        dataBase.QueryData("INSERT INTO " + QUOTE_TABLE + " VALUES(null, '01/01/2000', 'Viết những lời yêu thương của bạn gửi cho người ấy.')");
    }

    private void CreateDataBaseForMemory() {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.memory_image);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] image = byteArrayOutputStream.toByteArray();
        dataBase.InsertMemory("Bắt đầu yêu nhau", "01/01/2000", "Chúng ta đã có một ngày hạnh phúc", image);
    }

    private void CreateDataBaseForAvatar() {
        Bitmap bm_avatarMale = BitmapFactory.decodeResource(this.getResources(), R.drawable.avata_male);
        ByteArrayOutputStream ba_avatarMale = new ByteArrayOutputStream();
        bm_avatarMale.compress(Bitmap.CompressFormat.PNG, 100, ba_avatarMale);
        byte[] b_avatarMale = ba_avatarMale.toByteArray();
        dataBase.InsertImage(AVATAR_TABLE, b_avatarMale);
        Bitmap bm_avatarFemale = BitmapFactory.decodeResource(this.getResources(), R.drawable.avata_female);
        ByteArrayOutputStream ba_avatarFemale = new ByteArrayOutputStream();
        bm_avatarFemale.compress(Bitmap.CompressFormat.PNG, 100, ba_avatarFemale);
        byte[] b_avatarFemale = ba_avatarFemale.toByteArray();
        dataBase.InsertImage(AVATAR_TABLE, b_avatarFemale);
    }

    private void CreateDataBaseForPerson() {
        dataBase.QueryData("INSERT INTO PersonTable VALUES(null, 'Male Name', '1/1/2000')");
        dataBase.QueryData("INSERT INTO PersonTable VALUES(null, 'Female Name', '1/1/2000')");
    }


    private void Init() {
        sharedPreferences = this.getSharedPreferences("checkHasCreate", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void nextState() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!sharedPreferences.getBoolean(START_FIRST_TIME, false)) startActivity(new Intent(MainActivity.this, IntroSliderActivity.class));
                else MainActivity.this.startActivity(new Intent(MainActivity.this, MainApplication.class));
                editor.putBoolean(START_FIRST_TIME, true);
                editor.commit();
                finish();
            }
        }, TIMEOUT);
    }
}