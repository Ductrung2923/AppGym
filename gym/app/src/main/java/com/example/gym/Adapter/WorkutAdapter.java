package com.example.gym.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Activity.WorkoutActivity;
import com.example.gym.Domain.Workout;
import com.example.gym.databinding.ViewholderWorkoutBinding;

import java.time.Instant;
import java.util.ArrayList;

public class WorkutAdapter extends RecyclerView.Adapter<WorkutAdapter.Viewholder> {
    private final ArrayList<Workout> list;
    private Context context;

    public WorkutAdapter(ArrayList<Workout> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public WorkutAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderWorkoutBinding binding = ViewholderWorkoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkutAdapter.Viewholder holder, int position) {
        holder.binding.titleTxt.setText(list.get(position).getTitle());
        int resId = context.getResources().getIdentifier(list.get(position).getPicPath(),"drawable",context.getPackageName());


        Glide.with(holder.itemView.getContext())
                .load(resId)
                .into(holder.binding.pic);
        holder.binding.excersizeTxt.setText(list.get(position).getLessions().size()+"Exercise");
        holder.binding.kcalTxt.setText(list.get(position).getKcal()+"Kcal");
        holder.binding.durationeTxt.setText(list.get(position).getDurationAll());

        holder.binding.getRoot().setOnClickListener(v -> {
                Intent intent = new Intent(context, WorkoutActivity.class);
                intent.putExtra("object",list.get(position));
                context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderWorkoutBinding binding;
        public Viewholder(ViewholderWorkoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
