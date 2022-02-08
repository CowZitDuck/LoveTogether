package com.example.lovetogether.FragmentPageView.Quote;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lovetogether.Calculate;
import com.example.lovetogether.MainActivity;
import com.example.lovetogether.Person.InformationFragment;
import com.example.lovetogether.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class QuoteFragment extends Fragment {
    private TextView tv_second;
    private TextView tv_minute;
    private TextView tv_hour;
    private TextView tv_day;
    private TextView tv_month;
    private TextView tv_year;
    private TextView tv_startDate;
    private TextView tv_name;
    private TextView tv_quote;
    private View mView;
    private LinearLayout linearLayout;
    private int sex = InformationFragment.MALE;
    private int day;
    private int month;
    private int year;
    private long time;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.pageview_quote, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init();
        setOnClick();
    }

    @Override
    public void onResume() {
        super.onResume();
        TimerTask timerTask = new TimerTask() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                getTime();
                time = (System.currentTimeMillis() - Calculate.TimeToMillisSecond(day, month, year));
                long var = time / (1000L * 60 * 60 * 24 * 365);
                tv_year.setText(var + "");
                time -= var * (1000L * 60 * 60 * 24 * 365);
                var = time / (1000L * 60 * 60 * 24 * 30);
                tv_month.setText(var + "");
                time -= var * (1000L * 60 * 60 * 24 * 30);
                var = time / (1000L * 60 * 60 * 24);
                tv_day.setText(var + "");
                time -= var * (1000L * 60 * 60 * 24);
                var = time / (1000L * 60 * 60);
                tv_hour.setText(var + "");
                time -= var * (1000L * 60 * 60);
                var= time / (1000L * 60);
                tv_minute.setText(var + "");
                time -= var * (1000L * 60);
                var = time / 1000;
                tv_second.setText(var + "");
            }
        };
        new Timer().scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private void getTime() {
        String date = null;
        Cursor cursor = MainActivity.dataBase.getData("SELECT * FROM " + MainActivity.QUOTE_TABLE);
        while (cursor.moveToNext()) date = cursor.getString(1);
        day = Calculate.convertTime("1", date);
        month = Calculate.convertTime("2", date);
        year = Calculate.convertTime("3", date);
    }

    private void Init() {
        linearLayout = (LinearLayout) mView.findViewById(R.id.linearLayout);
        tv_second = mView.findViewById(R.id.tv_count_second);
        tv_minute = mView.findViewById(R.id.tv_count_minute);
        tv_hour = mView.findViewById(R.id.tv_count_hour);
        tv_day = mView.findViewById(R.id.tv_count_day);
        tv_month = mView.findViewById(R.id.tv_count_month);
        tv_year = mView.findViewById(R.id.tv_count_year);
        tv_name = mView.findViewById(R.id.tv_name_quote);
        tv_quote = mView.findViewById(R.id.tv_quote);
        tv_startDate = mView.findViewById(R.id.tv_start_date);
        UpdateDataFromDataBase();
        UpdateTimeDemo();
    }

    @SuppressLint("SetTextI18n")
    private void UpdateTimeDemo() {
        tv_second.setText("59");
        tv_minute.setText("23");
        tv_hour.setText("10");
        tv_day.setText("24");
        tv_month.setText("31");
        tv_year.setText("1009");
    }

    private void UpdateDataFromDataBase() {
        Cursor cursor = MainActivity.dataBase.getData("SELECT * FROM " + MainActivity.QUOTE_TABLE);
        while (cursor.moveToNext()) {
            tv_quote.setText(cursor.getString(2));
            tv_startDate.setText(cursor.getString(1));
        }
        Cursor cursor1 = MainActivity.dataBase.getData("SELECT * FROM " + MainActivity.PERSON_TABLE);
        int i = 1;
        while (cursor1.moveToNext()) {
            if(i == sex) tv_name.setText(cursor1.getString(1));
            i++;
        }
    }

    private void setOnClick() {
        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                CallDialog();
                return true;
            }
        });
        tv_name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                CallDialog();
                return true;
            }
        });
        tv_quote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                CallDialog();
                return true;
            }
        });
        tv_startDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                CallDialog();
                return true;
            }
        });
    }

    private void CallDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_menu_change_quote);
        Button btn_replace = dialog.findViewById(R.id.btn_change_quote);
        Button btn_changeName = dialog.findViewById(R.id.btn_change_name);
        Button btn_changDate = dialog.findViewById(R.id.btn_set_day);
        btn_changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sex == InformationFragment.MALE) sex = InformationFragment.FEMALE;
                else sex = InformationFragment.MALE;
                UpdateDataFromDataBase();
                dialog.dismiss();
            }
        });
        btn_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog editQuote = new Dialog(getContext());
                editQuote.requestWindowFeature(Window.FEATURE_NO_TITLE);
                editQuote.setContentView(R.layout.dialog_change_quote);
                EditText et_changQuote = editQuote.findViewById(R.id.et_chang_quote);
                Button btn_done = editQuote.findViewById(R.id.btn_done_quote);
                Button btn_dismiss = editQuote.findViewById(R.id.btn_dismiss_quote);
                btn_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String quote = et_changQuote.getText().toString().trim();
                        MainActivity.dataBase.QueryData("UPDATE " + MainActivity.QUOTE_TABLE + " SET Quote = '" + quote + "'");
                        UpdateDataFromDataBase();
                        editQuote.dismiss();
                    }
                });
                btn_dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editQuote.dismiss();
                        Toast.makeText(getContext(), "Hủy", Toast.LENGTH_SHORT).show();
                    }
                });
                editQuote.show();
                dialog.dismiss();
            }
        });
        btn_changDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String date = simpleDateFormat.format(calendar.getTime());
                        MainActivity.dataBase.QueryData("UPDATE " + MainActivity.QUOTE_TABLE + " SET Date = '" + date + "'");
                        UpdateDataFromDataBase();
                    }
                }, year, month, day);
                datePickerDialog.show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
