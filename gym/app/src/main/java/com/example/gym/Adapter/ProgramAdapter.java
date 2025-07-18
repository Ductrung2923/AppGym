package com.example.gym.Adapter;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Activity.PlanActivity;
import com.example.gym.Model.Program;
import com.example.gym.R;
import android.content.Context;
import java.util.List;
public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder> {

    private List<Program> programs;
    private Context context;
    public ProgramAdapter(Context context,List<Program> programs) {
        this.context = context;
        this.programs = programs;
    }

    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_program, parent, false);
        return new ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {
        Program program = programs.get(position);
        holder.title.setText(program.getTagline());
        holder.subtitle.setText(program.getSubtitle());
        holder.image.setImageResource(program.getImageResId());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlanActivity.class);
            intent.putExtra("title", program.getTagline());
            intent.putExtra("subtitle", program.getSubtitle());
            intent.putExtra("image", program.getImageResId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return programs.size();
    }

    public static class ProgramViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, subtitle;

        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_background);
            title = itemView.findViewById(R.id.txt_tagline);
            subtitle = itemView.findViewById(R.id.txt_subtitle);
        }
    }
}
