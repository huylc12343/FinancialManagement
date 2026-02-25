package com.example.chitieuapp.ui.baocao;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chitieuapp.R;
import com.example.chitieuapp.data.database.AppDatabase;
import com.example.chitieuapp.data.entity.ChiThu;

import java.util.Calendar;
import java.util.List;

public class FragmentBaocao extends Fragment {

    TextView tvThuNhapThang, tvChiTieuThang, tvTienDu;
    EditText editTextThang, editTextNam;
    Button btnXem;
    RecyclerView recyclerView;
    ChiThuBaoCaoAdapter adapter;
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
    private void showEditDialog(ChiThu chiThu) {

        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_edit_chithu, null);

        EditText edtSoTien = dialogView.findViewById(R.id.edtSoTien);
        EditText edtGhiChu = dialogView.findViewById(R.id.edtGhiChu);

        edtSoTien.setText(String.valueOf(chiThu.getSotien()));
        edtGhiChu.setText(chiThu.getGhichu());

        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Sửa giao dịch")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {

                    int soTien = Integer.parseInt(edtSoTien.getText().toString());
                    String ghiChu = edtGhiChu.getText().toString();

                    chiThu.setSotien(soTien);
                    chiThu.setGhichu(ghiChu);

                    new Thread(() -> {
                        AppDatabase.getInstance(requireContext())
                                .chiThuDao()
                                .update(chiThu);

                        requireActivity().runOnUiThread(() -> {
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Đã cập nhật!", Toast.LENGTH_SHORT).show();
                        });
                    }).start();

                })
                .setNegativeButton("Hủy", null)
                .show();
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
        recyclerView = view.findViewById(R.id.recyclerViewChiTiet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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

                List<ChiThu> dsChiThuThang = db.chiThuDao().getAllInRange(fromDate, toDate);

                requireActivity().runOnUiThread(() -> {

                    tvChiTieuThang.setText("Tiền chi tháng " + month + "/" + year + ": " + tienChi);
                    tvThuNhapThang.setText("Tiền thu tháng " + month + "/" + year + ": " + tienThu);
                    tvTienDu.setText("Tiền dư tháng " + month + "/" + year + ": " + tienDu);

                    adapter = new ChiThuBaoCaoAdapter(dsChiThuThang);
                    recyclerView.setAdapter(adapter);
                    ItemTouchHelper.SimpleCallback simpleCallback =
                            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                                @Override
                                public boolean onMove(RecyclerView recyclerView,
                                                      RecyclerView.ViewHolder viewHolder,
                                                      RecyclerView.ViewHolder target) {
                                    return false;
                                }

                                @Override
                                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                                    int position = viewHolder.getAdapterPosition();
                                    ChiThu chiThu = adapter.getItem(position);

                                    // Khôi phục item về vị trí cũ (không xóa ngay)
                                    adapter.notifyItemChanged(position);

                                    // Hiện dialog xác nhận
                                    new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                                            .setTitle("Xác nhận xóa")
                                            .setMessage("Bạn có chắc muốn xóa giao dịch này không?")
                                            .setPositiveButton("Đồng ý", (dialog, which) -> {
                                                new Thread(() -> {
                                                    db.chiThuDao().delete(chiThu);
                                                    requireActivity().runOnUiThread(() -> {
                                                        adapter.removeItem(position);
                                                        Toast.makeText(getContext(), "Đã xóa!", Toast.LENGTH_SHORT).show();
                                                    });
                                                }).start();
                                            })
                                            .setNegativeButton("Từ chối", (dialog, which) -> {
                                                // Không làm gì, item đã được restore ở trên
                                                dialog.dismiss();
                                            })
                                            .setCancelable(false)
                                            .show();
                                }
                                @Override
                                public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                                        RecyclerView.ViewHolder viewHolder,
                                                        float dX, float dY,
                                                        int actionState, boolean isCurrentlyActive) {

                                    View itemView = viewHolder.itemView;
                                    Paint paint = new Paint();

                                    if (dX < 0) {
                                        paint.setColor(Color.RED);
                                        c.drawRect(
                                                itemView.getRight() + dX,
                                                itemView.getTop(),
                                                itemView.getRight(),
                                                itemView.getBottom(),
                                                paint
                                        );

                                        paint.setColor(Color.WHITE);
                                        paint.setTextSize(40);
                                        paint.setFakeBoldText(true);
                                        c.drawText("XÓA",
                                                itemView.getRight() - 120,
                                                itemView.getTop() + (itemView.getHeight() / 2),
                                                paint);
                                    }

                                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                                }
                            };
                    adapter.setOnItemDoubleClickListener(chiThu -> {
                        showEditDialog(chiThu);
                    });
                    new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
                });

            }).start();
        });

        return view;
    }
}