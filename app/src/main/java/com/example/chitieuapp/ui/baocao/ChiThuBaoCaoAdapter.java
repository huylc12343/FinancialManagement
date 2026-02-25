package com.example.chitieuapp.ui.baocao;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chitieuapp.R;
import com.example.chitieuapp.data.entity.ChiThu;

import java.util.List;

public class ChiThuBaoCaoAdapter extends RecyclerView.Adapter<ChiThuBaoCaoAdapter.ViewHolder> {

    private List<ChiThu> list;

    public ChiThuBaoCaoAdapter(List<ChiThu> list) {
        this.list = list;
    }

    public void setData(List<ChiThu> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }
    public interface OnItemDoubleClickListener {
        void onItemDoubleClick(ChiThu chiThu);
    }

    private OnItemDoubleClickListener listener;

    public void setOnItemDoubleClickListener(OnItemDoubleClickListener listener) {
        this.listener = listener;
    }
    public void updateItem(int position, ChiThu chiThu) {
        list.set(position, chiThu);
        notifyItemChanged(position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chithu, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChiThu item = list.get(position);

        holder.tvDanhMuc.setText(item.danhmuc);
        holder.tvGhiChu.setText(item.ghichu);
        holder.tvNgay.setText(item.ngay);
        holder.tvSoTien.setText(String.format("%,d đ", item.sotien));

        if (item.type == 0) {
            // Chi - màu đỏ
            holder.tvType.setText("Chi");
            holder.tvType.setBackgroundResource(R.drawable.bg_type_circle);
            holder.tvType.getBackground().setTint(0xFFE53935);
            holder.tvSoTien.setTextColor(0xFFE53935);
        } else {
            // Thu - màu xanh
            holder.tvType.setText("Thu");
            holder.tvType.getBackground().setTint(0xFF43A047);
            holder.tvSoTien.setTextColor(0xFF43A047);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            private long lastClickTime = 0;

            @Override
            public void onClick(View v) {
                long clickTime = System.currentTimeMillis();

                if (clickTime - lastClickTime < 300) {
                    // Double click
                    if (listener != null) {
                        listener.onItemDoubleClick(list.get(holder.getAdapterPosition()));
                    }
                }

                lastClickTime = clickTime;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
    public ChiThu getItem(int position) {
        return list.get(position);
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvType, tvDanhMuc, tvGhiChu, tvNgay, tvSoTien;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType    = itemView.findViewById(R.id.tvType);
            tvDanhMuc = itemView.findViewById(R.id.tvDanhMuc);
            tvGhiChu  = itemView.findViewById(R.id.tvGhiChu);
            tvNgay    = itemView.findViewById(R.id.tvNgay);
            tvSoTien  = itemView.findViewById(R.id.tvSoTien);
        }
    }
}