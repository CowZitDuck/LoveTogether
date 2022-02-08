package com.example.lovetogether;

import static com.example.lovetogether.R.drawable.null_img;
import static com.example.lovetogether.R.drawable.slide_up;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.BitmapCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lovetogether.FragmentPageView.Home.HomeFragment;
import com.example.lovetogether.FragmentPageView.MyViewerPageAdapter;
import com.example.lovetogether.Person.AvataFragment;
import com.example.lovetogether.Person.BackgroundFragment;
import com.example.lovetogether.Person.InformationFragment;
import com.example.lovetogether.Setting.ChooseBackgroundFragment;
import com.example.lovetogether.Setting.SettingFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainApplication extends AppCompatActivity
        implements
        SettingFragment.ICommunicate,
        ChooseBackgroundFragment.ICommunicate{

    public static final int REQUEST_CODE_FROM_GALLERY_TO_CHANGE_BACKGROUND_FOR_MALE = 19042004;
    public static final int REQUEST_CODE_FROM_GALLERY_TO_CHANGE_BACKGROUND_FOR_FEMALE = 19042005;
    public static final int PERSON_DOWN = 8798778;
    public static final int PERSON_UP = 8958589;
    public static final int SETTING_DOWN = 87987780;
    public static final int SETTING_UP = 8798781;
    public static final int VIEWPAGE_DOWN = 923482;
    public static final int VIEWPAGE_UP = 923483;
    public static final int MAX_OF_BYTES = 9000000;
    private ViewPager2 vp2_main;
    private ImageView img_background;
    private ImageButton btn_setting;
    private ImageButton btn_sileUp;
    private ImageButton btn_slideDown;
    private ImageButton btn_chooseMaleAvatar;
    private ImageButton btn_chooseFemaleAvatar;
    private MyViewerPageAdapter vpa_main;
    private Animation anim_btn_slideUp;
    private boolean isSetting = false;
    private boolean isUp = true;
    private boolean isChooseBackground = false;
    private FrameLayout avatarLayout;
    private FrameLayout backgroundLayout;
    private FrameLayout informationLayout;
    private FrameLayout settingLayout;
    private AvataFragment avataFragment;
    private InformationFragment informationFragment;
    private BackgroundFragment backgroundFragment;
    private SettingFragment settingFragment;
    private HomeFragment homeFragment;
    private ChooseBackgroundFragment chooseBackgroundFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_application);
        Init();
        setOnClick();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SimpleDateFormat"})
    public void Init() {
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.MAGENTA);
        vp2_main = (ViewPager2) findViewById(R.id.vp2_main);
        vpa_main = new MyViewerPageAdapter(this);
        vpa_main.setContext(this);
        vp2_main.setAdapter(vpa_main);
        vp2_main.setCurrentItem(1);
        btn_setting = (ImageButton) findViewById(R.id.btn_setting);
        btn_slideDown = (ImageButton) findViewById(R.id.btn_down_person);
        btn_sileUp = (ImageButton) findViewById(R.id.btn_up_person);
        btn_chooseMaleAvatar = (ImageButton) findViewById(R.id.btn_choose_avata_male);
        btn_chooseFemaleAvatar = (ImageButton) findViewById(R.id.btn_choose_avata_female);
        img_background = (ImageView) findViewById(R.id.img_background);
        if(!MainActivity.sharedPreferences.getBoolean(MainActivity.HAS_BACKGROUND, false)) {
            MainActivity.editor.putBoolean(MainActivity.HAS_BACKGROUND, true);
            MainActivity.editor.putBoolean(MainActivity.IS_BACKGROUND_FROM_DATABASE, false);
            MainActivity.editor.putInt(MainActivity.ID_BACKGROUND, 1);
            MainActivity.editor.commit();
            img_background.setImageResource(R.drawable.wp_1);
        } else {
            if(!MainActivity.sharedPreferences.getBoolean(MainActivity.IS_BACKGROUND_FROM_DATABASE, false)) {
                switch (MainActivity.sharedPreferences.getInt(MainActivity.ID_BACKGROUND, 1)) {
                    case 1: img_background.setImageResource(R.drawable.wp_1); break;
                    case 2: img_background.setImageResource(R.drawable.wp_2); break;
                    case 3: img_background.setImageResource(R.drawable.wp_3); break;
                    case 4: img_background.setImageResource(R.drawable.wp_4); break;
                    case 5: img_background.setImageResource(R.drawable.wp_5); break;
                    case 6: img_background.setImageResource(R.drawable.wp_6); break;
                    case 7: img_background.setImageResource(R.drawable.wp_7); break;
                    case 8: img_background.setImageResource(R.drawable.wp_8); break;
                    case 9: img_background.setImageResource(R.drawable.wp_9); break;
                }
            } else {
                Cursor cursor = MainActivity.dataBase.getData("SELECT * FROM ImageTable");
                while (cursor.moveToNext()) {
                    if(cursor.getInt(0) == MainActivity.sharedPreferences.getInt(MainActivity.ID_BACKGROUND, 1)) {
                        byte[] image = cursor.getBlob(1);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                        img_background.setImageBitmap(bitmap);
                        break;
                    }
                }
            }
        }
        if(isUp) btn_sileUp.setImageDrawable(getDrawable(R.drawable.null_img));
        avataFragment = new AvataFragment();
        backgroundFragment = new BackgroundFragment();
        informationFragment = new InformationFragment(this);
        settingFragment = new SettingFragment();
        homeFragment = new HomeFragment();
        chooseBackgroundFragment = new ChooseBackgroundFragment(this);
        backgroundLayout = (FrameLayout) findViewById(R.id.frg_background);
        avatarLayout = (FrameLayout) findViewById(R.id.frg_avata);
        informationLayout = (FrameLayout) findViewById(R.id.frg_information);
        settingLayout = (FrameLayout) findViewById(R.id.frg_setting);
        getSupportFragmentManager().beginTransaction().add(R.id.frg_avata, avataFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frg_background, backgroundFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frg_information, informationFragment).commit();
    }

    @SuppressLint("NonConstantResourceId")
    public void startAnimation(int view) {
        switch (view) {
            case R.id.btn_up_person:
                anim_btn_slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_slide_up_anim);
                btn_sileUp.startAnimation(anim_btn_slideUp);
                anim_btn_slideUp.start();
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void stopAnimation(int view) {
        switch (view) {
            case R.id.btn_up_person:
                anim_btn_slideUp.cancel();
                break;
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void startAnimate(int name) {
        switch (name) {
            case PERSON_DOWN:
                avatarLayout.animate().translationY(1200);
                avatarLayout.animate().start();
                backgroundLayout.animate().translationY(1200);
                backgroundLayout.animate().start();
                informationLayout.animate().translationY(1200);
                informationLayout.animate().start();

                isUp = false;
                startAnimation(R.id.btn_up_person);
                btn_sileUp.setImageDrawable(getDrawable(slide_up));
                break;
            case PERSON_UP:
                avatarLayout.animate().translationY(0);
                avatarLayout.animate().start();
                backgroundLayout.animate().translationY(0);
                backgroundLayout.animate().start();
                informationLayout.animate().translationY(0);
                informationLayout.animate().start();

                isUp = true;
                stopAnimation(R.id.btn_up_person);
                btn_sileUp.setImageDrawable(getDrawable(null_img));
                break;
            case VIEWPAGE_UP:
                vp2_main.animate().translationY(0);
                vp2_main.animate().start();
                break;
            case VIEWPAGE_DOWN:
                vp2_main.animate().translationY(4000);
                vp2_main.animate().start();
                break;
            case SETTING_UP:
                settingLayout.animate().translationY(-6000);
                settingLayout.animate().start();
                break;
            case SETTING_DOWN:
                settingLayout.animate().translationY(0);
                settingLayout.animate().start();
        }
    }

    public void setOnClick() {
        btn_slideDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimate(PERSON_DOWN);
            }
        });
        btn_sileUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimate(PERSON_UP);
            }
        });
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isSetting) {
                    startAnimate(VIEWPAGE_DOWN);
                    if(isUp) startAnimate(PERSON_DOWN);
                    getSupportFragmentManager().beginTransaction().add(R.id.frg_setting, settingFragment).commit();
                    isSetting = true;
                } else {
                    if(isChooseBackground) {
                        isChooseBackground = false;
                        getSupportFragmentManager().beginTransaction().remove(chooseBackgroundFragment).commit();
                        startAnimate(SETTING_DOWN);
                    } else {
                        getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
                        startAnimate(VIEWPAGE_UP);
                        isSetting = false;
                    }
                }
            }
        });
        btn_chooseMaleAvatar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent it_chooseImage = new Intent(Intent.ACTION_PICK);
                it_chooseImage.setType("image/*");
                startActivityForResult(it_chooseImage, REQUEST_CODE_FROM_GALLERY_TO_CHANGE_BACKGROUND_FOR_MALE);
                return true;
            }
        });
        btn_chooseFemaleAvatar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent it_chooseImage = new Intent(Intent.ACTION_PICK);
                it_chooseImage.setType("image/*");
                startActivityForResult(it_chooseImage, REQUEST_CODE_FROM_GALLERY_TO_CHANGE_BACKGROUND_FOR_FEMALE);
                return true;
            }
        });
    }

    @Override
    public void setBackground(Bitmap image) {
        img_background.setImageBitmap(image);
        getSupportFragmentManager().beginTransaction().remove(chooseBackgroundFragment).commit();
        startAnimate(SETTING_DOWN);
        isChooseBackground = false;
    }

    @Override
    public void CallFragment(int fragment) {
        switch (fragment) {
            case SettingFragment.CHOOSE_BACKGROUND:
                isChooseBackground = true;
                startAnimate(SETTING_UP);
                getSupportFragmentManager().beginTransaction().add(R.id.frg_choose_background, chooseBackgroundFragment).commit();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_FROM_GALLERY_TO_CHANGE_BACKGROUND_FOR_MALE:
                    Uri uri_bg = data.getData();
                    try {
                        InputStream inputStreambg = getContentResolver().openInputStream(uri_bg);
                        Bitmap bitmapbg = BitmapFactory.decodeStream(inputStreambg);
                        if (BitmapCompat.getAllocationByteCount(bitmapbg) <= MAX_OF_BYTES) {
                            ByteArrayOutputStream byteArrayOutputStreambg = new ByteArrayOutputStream();
                            bitmapbg.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStreambg);
                            byte[] imagebg = byteArrayOutputStreambg.toByteArray();
                            MainActivity.dataBase.UpdateImage(MainActivity.AVATAR_TABLE, imagebg, InformationFragment.MALE);
                            avataFragment.setAvatar();
                        } else {
                            Toast.makeText(this, "Kích thước tệp quá lớn", Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case REQUEST_CODE_FROM_GALLERY_TO_CHANGE_BACKGROUND_FOR_FEMALE:
                    Uri uri_bgfe = data.getData();
                    try {
                        InputStream inputStreambgfe = getContentResolver().openInputStream(uri_bgfe);
                        Bitmap bitmapbgfe = BitmapFactory.decodeStream(inputStreambgfe);
                        if (BitmapCompat.getAllocationByteCount(bitmapbgfe) <= MAX_OF_BYTES) {
                            ByteArrayOutputStream byteArrayOutputStreambgfe = new ByteArrayOutputStream();
                            bitmapbgfe.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStreambgfe);
                            byte[] imagebgfe = byteArrayOutputStreambgfe.toByteArray();
                            MainActivity.dataBase.UpdateImage(MainActivity.AVATAR_TABLE, imagebgfe, InformationFragment.FEMALE);
                            avataFragment.setAvatar();
                        } else {
                            Toast.makeText(this, "Kích thước tệp quá lớn", Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}