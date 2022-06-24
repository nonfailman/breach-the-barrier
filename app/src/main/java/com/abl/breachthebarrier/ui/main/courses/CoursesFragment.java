package com.abl.breachthebarrier.ui.main.courses;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abl.breachthebarrier.data.Course;
import com.abl.breachthebarrier.databinding.FragmentCoursesBinding;
import com.abl.breachthebarrier.ui.CourseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class CoursesFragment extends Fragment {

    private FragmentCoursesBinding binding;

    private RecyclerView recyclerView;
    private CoursesAdapter adapter;

    private FirebaseFirestore db;
    private List<Course> receivedData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        CoursesViewModel coursesViewModel =
               new ViewModelProvider(this).get(CoursesViewModel.class);

        binding = FragmentCoursesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        coursesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        db = FirebaseFirestore.getInstance();
        receivedData = new ArrayList<>();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataFromDb();
    }

    private void InitInterface(){

    }

    private void getDataFromDb(){
        db.collection("units")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        receivedData.clear();
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DataGet", document.getId() + " => " + document.getData());
                                Course course = document.toObject(Course.class);
                                receivedData.add(course);
                                setData(receivedData);
                            }
                        } else {
                            Log.w("DataGet","Error getting documents", task.getException());
                        }
                        Log.d("DataGetSuccessful", receivedData.toString());
                    }
                });
    }


    private void setData(List<Course> courses){
        CoursesAdapter.OnItemClickListener onItemClickListener = new CoursesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course, int position) {
                updateUI(course);
            }
        };

        recyclerView = binding.coursesRecyclerView;
        adapter = new CoursesAdapter(getContext(), courses, onItemClickListener);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void updateUI(Course course){
        Intent intent = new Intent(getContext(), CourseActivity.class);
        intent.putExtra("unit_title", course.getTitle());
        intent.putExtra("course_id",course.getTitle());
        Log.d("UnitTitle", course.getTitle());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}