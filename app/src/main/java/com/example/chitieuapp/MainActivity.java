package com.example.chitieuapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    TabLayout tabChiTieu;
    ViewPager2 viewChiTieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tabChiTieu = findViewById(R.id.TabChiTieu);
        viewChiTieu = findViewById(R.id.ViewChiTieu);

        ViewPageAdapter adapter = new ViewPageAdapter(this);
        viewChiTieu.setAdapter(adapter);

        new TabLayoutMediator(tabChiTieu,viewChiTieu,(tab,position)->{
            if(position == 0) tab.setText("Chi");
            else tab.setText("Thu");
        }).attach();
    }
}