package com.example.lovetogether.IntroSlider;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SliderAdapter extends FragmentStateAdapter {

    FragmentManager fragmentManager;

    public SliderAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Slider1();
            case 1:
                return new Slider2();
            case 2:
                return new Slider3();
        }
        return new Slider1();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
