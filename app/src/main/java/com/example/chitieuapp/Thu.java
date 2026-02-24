package com.example.chitieuapp;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class Thu extends Fragment {

    private LinearLayout selectedCategory = null;

    public Thu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thu, container, false);

        TextView editTextDateThu = view.findViewById(R.id.editTextDateThu);

        editTextDateThu.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (datePicker, y, m, d) -> {
                        String date = d + "/" + (m + 1) + "/" + y;
                        editTextDateThu.setText(date);
                    }, year, month, day);

            datePickerDialog.show();
        });
        setupCategoryButtons(view);
        return view;
    }
    private void setupCategoryButtons(View view) {
        int[] btnIds = {R.id.btnLuong, R.id.btnThuong, R.id.btnDauTu,
                R.id.btnBanHang, R.id.btnChoVay, R.id.btnTroCap,
                R.id.btnQuaTang, R.id.btnFreelance, R.id.btnThuNhapKhac};

        for (int id : btnIds) {
            LinearLayout btn = view.findViewById(id);

            btn.setOnClickListener(v -> {

                // Nếu click lại chính nó → bỏ chọn
                if (v == selectedCategory) {
                    v.setSelected(false);
                    selectedCategory = null;
                    return;
                }

                // Bỏ chọn cái cũ
                if (selectedCategory != null) {
                    selectedCategory.setSelected(false);
                }

                // Chọn cái mới
                v.setSelected(true);
                selectedCategory = (LinearLayout) v;
            });
        }
    }
}