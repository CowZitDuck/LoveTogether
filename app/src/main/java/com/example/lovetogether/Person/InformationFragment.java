package com.example.lovetogether.Person;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lovetogether.MainActivity;
import com.example.lovetogether.MainApplication;
import com.example.lovetogether.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InformationFragment extends Fragment {
    public static final int MALE = 1;
    public static final int FEMALE = 2;
    private static final String MALE_NAME = "maleName";
    private static final String FEMALE_NAME = "femaleName";
    private static final String MALE_BIRTHDAY = "maleBirthday";
    private static final String FEMALE_BIRTHDAY = "femaleBirthday";
    public static final int CALL_EDIT_TEXT = 1234123;
    private Context context;
    public InformationFragment(Context context) {
        this.context = context;
    }
    private Button btn_changeMaleName;
    private Button btn_changeFemaleName;
    private Button btn_changeMaleBirthday;
    private Button btn_changeFemaleBirthday;
    private TextView tv_maleName;
    private TextView tv_femaleName;
    private TextView tv_maleBirthday;
    private TextView tv_femaleBirthday;
    private View mView;
    private Integer sex;
    private boolean isCallFragment = false;
    private boolean isCallCalendar = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_information, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init();
        SetOnClick();
    }

    @SuppressLint("SimpleDateFormat")
    public void Init() {
        btn_changeMaleName = mView.findViewById(R.id.btn_change_name_male);
        btn_changeFemaleName = mView.findViewById(R.id.btn_change_name_female);
        btn_changeMaleBirthday = mView.findViewById(R.id.btn_change_birthday_male);
        btn_changeFemaleBirthday = mView.findViewById(R.id.btn_change_birthday_female);
        tv_maleName = mView.findViewById(R.id.tv_change_name_male);
        tv_femaleName = mView.findViewById(R.id.tv_change_name_female);
        tv_maleBirthday = mView.findViewById(R.id.tv_change_birthday_male);
        tv_femaleBirthday = mView.findViewById(R.id.tv_change_birthday_female);
        Cursor cursor = MainActivity.dataBase.getData("SELECT * FROM PersonTable");
        int i = 1;
        while (cursor.moveToNext()) {
            if(i == MALE) {
                tv_maleName.setText(cursor.getString(1));
                tv_maleBirthday.setText(cursor.getString(2));
            } else {
                tv_femaleName.setText(cursor.getString(1));
                tv_femaleBirthday.setText(cursor.getString(2));
            }
            i++;
        }
        isCallFragment = false;
    }

    public void SetOnClick() {
        btn_changeMaleName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Nhấn và giữ để đổi tên", Toast.LENGTH_SHORT).show();
            }
        });
        btn_changeMaleName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                sex = MALE;
                CallDialog(sex);
                return true;
            }
        });
        btn_changeFemaleName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Nhấn và giữ để đổi tên", Toast.LENGTH_SHORT).show();
            }
        });
        btn_changeFemaleName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                sex = FEMALE;
                CallDialog(sex);
                return true;
            }
        });btn_changeMaleBirthday.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                sex = MALE;
                CallCalendar(sex);
                return true;
            }
        });
        btn_changeFemaleBirthday.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                sex = FEMALE;
                CallCalendar(sex);
                return true;
            }
        });
    }

    private void CallCalendar(Integer sex) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                String date = simpleDateFormat.format(calendar.getTime());
                if(sex == MALE) {
                    tv_maleBirthday.setText(date);
                    MainActivity.dataBase.QueryData("UPDATE " + MainActivity.PERSON_TABLE + " SET Birthday = '" + date + "' WHERE Id = '1'");
                } else {
                    tv_femaleBirthday.setText(date);
                    MainActivity.dataBase.QueryData("UPDATE " + MainActivity.PERSON_TABLE + " SET Birthday = '" + date + "' WHERE Id = '2'");
                }
            }
        }, year, month, day);
        datePickerDialog.show();
    }
    public void CallDialog(Integer sex) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_name);
        Button btn_done = (Button) dialog.findViewById(R.id.btn_done);
        Button btn_dismiss = (Button) dialog.findViewById(R.id.btn_dismiss);
        EditText et_changName = (EditText) dialog.findViewById(R.id.et_chang_name);
        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Hủy", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_changName.getText().toString().trim();
                if(name.length() == 0) Toast.makeText(context, "Hãy nhập tên", Toast.LENGTH_SHORT).show();
                else {
                    if(sex == MALE) {
                        MainActivity.dataBase.QueryData("UPDATE " + MainActivity.PERSON_TABLE + " SET Name = '" + name +"' WHERE Id = '1'");
                        tv_maleName.setText(name);
                    } else {
                        MainActivity.dataBase.QueryData("UPDATE " + MainActivity.PERSON_TABLE + " SET Name = '" + name +"' WHERE Id = '2'");
                        tv_femaleName.setText(name);
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}