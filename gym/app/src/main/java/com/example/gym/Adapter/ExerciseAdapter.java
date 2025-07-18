package com.example.gym.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Model.Exercise;
import com.example.gym.R;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<Exercise> exercises;

    public ExerciseAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.txtName.setText(exercise.getName());
        holder.txtSets.setText(exercise.getSets());
        holder.imgExercise.setImageResource(exercise.getImageResId());
        holder.imgTarget.setImageResource(exercise.getTargetResId());
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        ImageView imgExercise, imgTarget;
        TextView txtName, txtSets;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            imgExercise = itemView.findViewById(R.id.img_exercise);
            imgTarget = itemView.findViewById(R.id.img_target);
            txtName = itemView.findViewById(R.id.txt_exercise_name);
            txtSets = itemView.findViewById(R.id.txt_sets);
        }
    }
}
