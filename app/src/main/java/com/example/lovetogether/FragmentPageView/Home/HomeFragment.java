package com.example.lovetogether.FragmentPageView.Home;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lovetogether.Calculate;
import com.example.lovetogether.MainActivity;
import com.example.lovetogether.R;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private Button btn_chooseDay;
    private TextView tv_countDay;
    private View mview;
    private Timer timer;
    private Long time;
    private int day;
    private int month;
    private int year;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.pageview_main, container, false);
        return mview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init();
    }

    private void getTime() {
        String date = null;
        Cursor cursor = MainActivity.dataBase.getData("SELECT * FROM " + MainActivity.QUOTE_TABLE);
        while (cursor.moveToNext()) date = cursor.getString(1);
        day = Calculate.convertTime("1", date);
        month = Calculate.convertTime("2", date);
        year = Calculate.convertTime("3", date);
    }

    public void Init() {
        timer = new Timer();
        btn_chooseDay = (Button) mview.findViewById(R.id.btn_choose_day);
        tv_countDay = (TextView) mview.findViewById(R.id.tv_count_time);
    }

    @Override
    public void onResume() {
        super.onResume();
        getTime();
        TimerTask timerTask = new TimerTask() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                time = (System.currentTimeMillis() - Calculate.TimeToMillisSecond(day, month, year)) / (1000 * 60 * 60 * 24);
                tv_countDay.setText(time.toString());
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
