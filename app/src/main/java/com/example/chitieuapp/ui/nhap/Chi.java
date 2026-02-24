package com.example.chitieuapp.ui.nhap;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chitieuapp.R;

import java.util.Calendar;

public class Chi extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LinearLayout selectedCategory = null;
    LinearLayout btnLuu;

    private String mParam1;
    private String mParam2;

    public Chi() {
        // Required empty public constructor
    }

    public static Chi newInstance(String param1, String param2) {
        Chi fragment = new Chi();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi, container, false);

        TextView editTextDate2 = view.findViewById(R.id.editTextDate2);
        btnLuu = view.findViewById(R.id.btnLuu);


        editTextDate2.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (datePicker, y, m, d) -> {
                        String date = d + "/" + (m + 1) + "/" + y;
                        editTextDate2.setText(date);
                    }, year, month, day);

            datePickerDialog.show();
        });
        setupCategoryButtons(view);
        return view;
    }
    private void setupCategoryButtons(View view) {
        int[] btnIds = {
                R.id.btnAn, R.id.btnDiLai, R.id.btnMuaSam,
                R.id.btnSucKhoe, R.id.btnGiaiTri, R.id.btnHoaDon,
                R.id.btnGiaoDuc, R.id.btnNhaO, R.id.btnKhac
        };

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