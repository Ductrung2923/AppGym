package com.example.gym.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Activity.PlanDetailActivity;
import com.example.gym.R;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {

    private List<String> plans;
    private Context context; // 👈 Thêm context

    public PlanAdapter(List<String> plans) {
        this.plans = plans;
    }

    // 👇 Hàm setter để nhận context từ ngoài (gọi khi set adapter)
    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plan, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        String planName = plans.get(position);
        holder.txtPlan.setText(planName);

        // 👉 Bổ sung sự kiện click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlanDetailActivity.class);
            intent.putExtra("plan_name", planName); // Gửi tên PLAN nếu cần
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder {
        TextView txtPlan;
        ImageView arrow;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPlan = itemView.findViewById(R.id.txt_plan);
            arrow = itemView.findViewById(R.id.arrow_icon);
        }
    }
}
