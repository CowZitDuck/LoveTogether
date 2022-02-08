package com.example.lovetogether.IntroSlider;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.lovetogether.MainApplication;
import com.example.lovetogether.R;

public class IntroSliderActivity extends AppCompatActivity {

    private ViewPager2 vp2_intro;
    private SliderAdapter sliderAdapter;
    private Button btn_next;
    private Button btn_back;
    private Button btn_skip;
    TextView[] tv_dot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);
        Init();
        setOnClick();
    }

    private void setOnClick() {
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroSliderActivity.this, MainApplication.class));
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vp2_intro.getCurrentItem() < 2) {
                    vp2_intro.setCurrentItem(vp2_intro.getCurrentItem() + 1);
                    setDot(vp2_intro.getCurrentItem());
                } else startActivity(new Intent(IntroSliderActivity.this, MainApplication.class));
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vp2_intro.getCurrentItem() > 0) {
                    vp2_intro.setCurrentItem(vp2_intro.getCurrentItem() - 1);
                    setDot(vp2_intro.getCurrentItem());
                } else Toast.makeText(IntroSliderActivity.this, "Đã là trang đầu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setDot(int position) {
        for(int i = 0; i < 3; i++) {
            if(i == position) tv_dot[i].setTextColor(getResources().getColor(R.color.white));
            else tv_dot[i].setTextColor(getResources().getColor(R.color.dot));
        }
    }

    private void Init() {
        vp2_intro = (ViewPager2) findViewById(R.id.vp2_intro);
        sliderAdapter = new SliderAdapter(this);
        vp2_intro.setAdapter(sliderAdapter);
        tv_dot = new TextView[3];
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_skip = (Button) findViewById(R.id.btn_skip);
        tv_dot[0] = (TextView) findViewById(R.id.tv_sc1_dot1);
        tv_dot[1] = (TextView) findViewById(R.id.tv_sc1_dot3);
        tv_dot[2] = (TextView) findViewById(R.id.tv_sc1_dot2);
    }
}
