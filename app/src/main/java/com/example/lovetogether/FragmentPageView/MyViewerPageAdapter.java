package com.example.lovetogether.FragmentPageView;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.lovetogether.FragmentPageView.Home.HomeFragment;
import com.example.lovetogether.FragmentPageView.Memory.MemoryFragment;
import com.example.lovetogether.FragmentPageView.Quote.QuoteFragment;

public class MyViewerPageAdapter extends FragmentStateAdapter {

    private FragmentManager mFragmentManager;
    private Context context;

    public MyViewerPageAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new HomeFragment();
            case 0:
                return new MemoryFragment(context);
            case 2:
                return new QuoteFragment();
            default:
        }
        return new HomeFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
