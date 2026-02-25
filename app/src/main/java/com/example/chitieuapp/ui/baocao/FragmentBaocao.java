package com.example.chitieuapp.ui.baocao;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chitieuapp.R;
import com.example.chitieuapp.data.database.AppDatabase;

import java.util.Calendar;

public class FragmentBaocao extends Fragment {

    TextView tvThuNhapThang, tvChiTieuThang, tvTienDu;
    EditText editTextThang, editTextNam;
    Button btnXem;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1, mParam2;

    public FragmentBaocao() {}

    public static FragmentBaocao newInstance(String param1, String param2) {
        FragmentBaocao fragment = new FragmentBaocao();
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
        View view = inflater.inflate(R.layout.fragment_baocao, container, false);

        AppDatabase db = AppDatabase.getInstance(requireContext());

        tvThuNhapThang = view.findViewById(R.id.tvThuNhapThang);
        tvChiTieuThang = view.findViewById(R.id.tvChiTieuThang);
        tvTienDu       = view.findViewById(R.id.tvTienDu);
        editTextThang  = view.findViewById(R.id.editTextThangQuery);
        editTextNam    = view.findViewById(R.id.editTextNamQuery);
        btnXem         = view.findViewById(R.id.btnXem);

        // Điền sẵn tháng/năm hiện tại
        Calendar cal = Calendar.getInstance();
        editTextThang.setText(String.valueOf(cal.get(Calendar.MONTH) + 1));
        editTextNam.setText(String.valueOf(cal.get(Calendar.YEAR)));

        btnXem.setOnClickListener(v -> {
            String thangStr = editTextThang.getText().toString().trim();
            String namStr   = editTextNam.getText().toString().trim();

            if (thangStr.isEmpty() || namStr.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập tháng và năm!", Toast.LENGTH_SHORT).show();
                return;
            }

            int month = Integer.parseInt(thangStr);
            int year  = Integer.parseInt(namStr);

            if (month < 1 || month > 12) {
                Toast.makeText(getContext(), "Tháng không hợp lệ (1-12)!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tính ngày cuối tháng
            Calendar lastDay = Calendar.getInstance();
            lastDay.set(year, month - 1, 1);
            int maxDay = lastDay.getActualMaximum(Calendar.DAY_OF_MONTH);

            String fromDate = String.format("%04d-%02d-01", year, month);
            String toDate   = String.format("%04d-%02d-%02d", year, month, maxDay);

            new Thread(() -> {
                int tienChi = db.chiThuDao().getSoTienChiInRange(fromDate, toDate);
                int tienThu = db.chiThuDao().getSoTienThuInRange(fromDate, toDate);
                int tienDu  = tienThu - tienChi;

                requireActivity().runOnUiThread(() -> {
                    tvChiTieuThang.setText("Tiền chi tháng " + month + "/" + year + ": " + tienChi);
                    tvThuNhapThang.setText("Tiền thu tháng " + month + "/" + year + ": " + tienThu);
                    tvTienDu.setText("Tiền dư tháng " + month + "/" + year + ": " + tienDu);
                });
            }).start();
        });

        return view;
    }
}