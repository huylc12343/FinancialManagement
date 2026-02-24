package com.example.chitieuapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewNhapAdapter extends FragmentStateAdapter {
    public ViewNhapAdapter(@NonNull Fragment fragment) {super(fragment);}
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Chi();
            case 1:
                return new Thu();
            default:
                return new Chi();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
