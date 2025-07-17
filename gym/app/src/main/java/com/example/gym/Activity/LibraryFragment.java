package com.example.gym.Activity;

import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Adapter.ProgramAdapter;
import com.example.gym.Model.Program;
import com.example.gym.R;

import java.util.ArrayList;
import java.util.List;
public class LibraryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgramAdapter adapter;
    private List<Program> programList;

    public LibraryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        recyclerView = view.findViewById(R.id.recycler_programs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        programList = new ArrayList<>();
        programList.add(new Program("GET IN SHAPE", "Full Body Split", R.drawable.program1));
        programList.add(new Program("GET LEAN", "Full Body Split", R.drawable.program4));
        programList.add(new Program("OVERALL FITNESS", "Full Body Split", R.drawable.program7));
        programList.add(new Program("BUILD MUSCLE", "Full Body Split", R.drawable.program3));
        programList.add(new Program("LOSE WEIGHT", "Full Body Split", R.drawable.program6));
        programList.add(new Program("BUILD STRENGTH", "Full Body Split", R.drawable.program2));

        adapter = new ProgramAdapter(getContext(), programList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}

