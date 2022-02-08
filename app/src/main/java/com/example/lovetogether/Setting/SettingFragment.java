package com.example.lovetogether.Setting;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lovetogether.R;

public class SettingFragment extends Fragment {

    public static final int CHOOSE_BACKGROUND = 9857868;

    private Button btn_chooseImage;
    private ICommunicate iCommunicate;

    private View mView;

    public interface ICommunicate {
        void CallFragment(int fragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_setting, container, false);
        Init();
        setOnclick();
        return mView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iCommunicate = (ICommunicate) getActivity();
    }

    private void setOnclick() {
        btn_chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iCommunicate.CallFragment(CHOOSE_BACKGROUND);
            }
        });
    }

    private void Init() {
        btn_chooseImage = mView.findViewById(R.id.btn_choose_background);
    }
}
