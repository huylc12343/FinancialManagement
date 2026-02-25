package com.example.chitieuapp.ui.nhap;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

public class Thu extends Fragment {

    private LinearLayout selectedCategory = null;
    LinearLayout btnLuuThu;
    public Thu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thu, container, false);
        EditText editTextNoteThu = view.findViewById(R.id.editTextTextThu);
        EditText editTextMoneyThu = view.findViewById(R.id.editTextNumberThu);
        TextView editTextDateThu = view.findViewById(R.id.editTextDateThu);
        btnLuuThu = view.findViewById(R.id.btnLuuThu);
        AppDatabase db = AppDatabase.getInstance(requireContext());
        btnLuuThu.setOnClickListener(v->{
            String noteThu = editTextNoteThu.getText().toString().trim();
            String moneyThu = editTextMoneyThu.getText().toString().trim();
            String dateThu = editTextDateThu.getText().toString().trim();
            if (noteThu.isEmpty() || moneyThu.isEmpty() || dateThu.isEmpty() || selectedCategory == null){
                Toast.makeText(getContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                return;
            }
            int amount;
            try {
                amount = Integer.parseInt(moneyThu);
            }catch (NumberFormatException e){
                Toast.makeText(getContext(), "Số tiền không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            // Lấy tên category từ tag hoặc text
            String category = selectedCategory.getTag() != null
                    ? selectedCategory.getTag().toString()
                    : "Khác";

            // type = 0 vì đây là màn hình Chi
            ChiThu chiThu = new ChiThu(
                    dateThu,       // ngay
                    noteThu,       // ghichu
                    amount,     // sotien
                    category,   // danhmuc
                    1           // type (0 = Chi)
            );
            // ⚠ Room không cho chạy DB trên main thread
            new Thread(() -> {
                db.chiThuDao().insert(chiThu);
            }).start();

            Toast.makeText(getContext(), "Đã lưu thành công", Toast.LENGTH_SHORT).show();

            // Reset form
            editTextNoteThu.setText("");
            editTextMoneyThu.setText("");
            editTextDateThu.setText("");
            if (selectedCategory != null) {
                selectedCategory.setSelected(false);
                selectedCategory = null;
            }
        });
        editTextDateThu.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (datePicker, y, m, d) -> {
                        String date = String.format("%04d-%02d-%02d", y, m + 1, d);
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