package com.example.chitieuapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPageAdapter extends FragmentStateAdapter {
    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity){
        super (fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position){
        switch (position){
            case 0:
                return new FragmentNhap();
            case 1:
                return new FragmentBaocao();
            case 2:
                return new FragmentKhac();
            default:
                return new FragmentNhap();
        }
    }

    @Override
    public int getItemCount(){
        return 3;
    }
}
