package com.example.chitieuapp.ui.nhap;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chitieuapp.R;
import com.example.chitieuapp.data.database.AppDatabase;
import com.example.chitieuapp.data.entity.ChiThu;

import java.util.Calendar;
import java.util.List;

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
        EditText editTextNote = view.findViewById(R.id.editTextText);
        EditText editTextMoney = view.findViewById(R.id.editTextNumber);

        AppDatabase db = AppDatabase.getInstance(requireContext());
        new Thread(() -> {
            List<ChiThu> list = db.chiThuDao().getAll();
            for (ChiThu item : list) {
                Log.d("DB_TEST", item.ghichu + " - " + item.sotien +" - "+item.ngay + " - " +item.danhmuc + " - " + (item.type == 0 ? "Chi" : "Thu"));
            }
        }).start();
        TextView editTextDate2 = view.findViewById(R.id.editTextDate2);
        btnLuu = view.findViewById(R.id.btnLuu);

// 🔥 XỬ LÝ NÚT LƯU
        btnLuu.setOnClickListener(v -> {

            String note = editTextNote.getText().toString().trim();
            String moneyStr = editTextMoney.getText().toString().trim();
            String date = editTextDate2.getText().toString().trim();

            if (note.isEmpty() || moneyStr.isEmpty() || date.isEmpty() || selectedCategory == null) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(moneyStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Số tiền không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lấy tên category từ tag hoặc text
            String category = selectedCategory.getTag() != null
                    ? selectedCategory.getTag().toString()
                    : "Khác";

            // type = 0 vì đây là màn hình Chi
            ChiThu chiThu = new ChiThu(
                    date,       // ngay
                    note,       // ghichu
                    amount,     // sotien
                    category,   // danhmuc
                    0           // type (0 = Chi)
            );

            // ⚠ Room không cho chạy DB trên main thread
            new Thread(() -> {
                db.chiThuDao().insert(chiThu);
            }).start();

            Toast.makeText(getContext(), "Đã lưu thành công", Toast.LENGTH_SHORT).show();

            // Reset form
            editTextNote.setText("");
            editTextMoney.setText("");
            editTextDate2.setText("");
            if (selectedCategory != null) {
                selectedCategory.setSelected(false);
                selectedCategory = null;
            }
        });
        editTextDate2.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (datePicker, y, m, d) -> {
                        String date = String.format("%04d-%02d-%02d", y, m + 1, d);
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