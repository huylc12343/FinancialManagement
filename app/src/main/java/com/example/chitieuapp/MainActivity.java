package com.example.chitieuapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewMain;
    BottomNavigationView bottomNav;

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
        viewMain = findViewById(R.id.ViewMain);
        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_nhap) {
                viewMain.setCurrentItem(0);
                return true;
            }

            if (item.getItemId() == R.id.nav_baocao) {
                viewMain.setCurrentItem(1);
                return true;
            }

            if (item.getItemId() == R.id.nav_khac) {
                viewMain.setCurrentItem(2);
                return true;
            }

            return false;
        });
        ViewPageAdapter adapter = new ViewPageAdapter(this);
        viewMain.setAdapter(adapter);
        viewMain.setUserInputEnabled(false);


    }
}