package com.example.chitieuapp.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.chitieuapp.ui.nhap.Chi;
import com.example.chitieuapp.ui.nhap.Thu;

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
