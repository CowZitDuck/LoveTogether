package com.example.lovetogether.Person;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lovetogether.MainActivity;
import com.example.lovetogether.R;

public class AvataFragment extends Fragment {

    private View mview;
    private ImageView img_avataMale;
    private ImageView img_avataFemale;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_avata, container, false);
        return mview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init();
    }

    private void Init() {
        img_avataMale = (ImageView) mview.findViewById(R.id.img_avata_male);
        img_avataFemale = (ImageView) mview.findViewById(R.id.img_avata_female);
        setAvatar();
    }

    public void setAvatar() {
        Cursor cursor = MainActivity.dataBase.getData("SELECT * FROM "+ MainActivity.AVATAR_TABLE);
        int i = 1;
        while (cursor.moveToNext()) {
            byte[] image = cursor.getBlob(1);
            Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);
            if(i == InformationFragment.MALE) img_avataMale.setImageBitmap(bm);
            else img_avataFemale.setImageBitmap(bm);
            i++;
        }
    }
}
