package com.example.gym.Adapter;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gym.Domain.Lession;
import com.example.gym.databinding.ViewholderExerciseBinding;


import java.util.ArrayList;

public class LessionsAdapter extends RecyclerView.Adapter<LessionsAdapter.Viewholder> {

    private final ArrayList<Lession> list;
    private final Context context;

    //  Thêm biến để lưu trạng thái bài học đã xem
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    //  Khởi tạo SharedPreferences trong constructor
    public LessionsAdapter(Context context, ArrayList<Lession> list) {
        this.context = context;
        this.list = list;
        preferences = context.getSharedPreferences("watched_videos", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    @NonNull
    @Override
    public LessionsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderExerciseBinding binding = ViewholderExerciseBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LessionsAdapter.Viewholder holder, int position) {
        Lession lession = list.get(position);

        holder.binding.titleTxt.setText(lession.getTitle());
        holder.binding.durationTxt.setText(lession.getDuration());

        int resId = context.getResources().getIdentifier(
                lession.getPicPath(), "drawable", context.getPackageName());

        Glide.with(context).load(resId).into(holder.binding.pic);

        //  Hiển thị dấu tích nếu đã xem
        boolean isWatched = preferences.getBoolean("watched_" + lession.getLink(), false);
        holder.binding.checkIcon.setVisibility(isWatched ? View.VISIBLE : View.GONE);

        holder.binding.getRoot().setOnClickListener(v -> {
            //  Khi người dùng nhấn vào bài học
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + lession.getLink()));
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + lession.getLink()));
            try {
                context.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                context.startActivity(webIntent);
            }

            //  Ghi nhớ trạng thái đã xem
            editor.putBoolean("watched_" + lession.getLink(), true);
            editor.apply();

            //  Cập nhật UI: hiện dấu tích xanh
            holder.binding.checkIcon.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ViewholderExerciseBinding binding;

        public Viewholder(ViewholderExerciseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
