package com.abl.breachthebarrier.ui.main.records;

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

import com.abl.breachthebarrier.data.Course;
import com.abl.breachthebarrier.data.Record;
import com.abl.breachthebarrier.data.UserData;
import com.abl.breachthebarrier.databinding.FragmentRecordsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;


public class RecordsFragment extends Fragment {

    private FragmentRecordsBinding binding;

    private FirebaseFirestore db;

    private TextView textViewUserName;

    private TextView textViewComplete;
    private TextView textViewWords;
    private TextView textViewScore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecordsViewModel recordsViewModel =
                new ViewModelProvider(this).get(RecordsViewModel.class);

        binding = FragmentRecordsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();

        final TextView textView = binding.textDashboard;
        recordsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInterface();
        getDataFromDb();
    }

    private void initInterface(){
        textViewUserName = binding.textViewUserName;
        textViewComplete = binding.textViewComplete;
        textViewWords = binding.textViewWords;
        textViewScore = binding.textViewScore;
    }

    private void getDataFromDb(){
        db.collection("user_scores")
                .document(UserData.getUserEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            Record record = task.getResult().toObject(Record.class);
                            setData(record);
                        }
                    }
                });
    }

    private void setData(Record record){
        textViewUserName.setText(UserData.getUserEmail());
        if(record == null){
            return;
        }
        textViewComplete.setText(record.getCoursesComplete().toString());
        textViewWords.setText(record.getWordsLearned().toString());
        textViewScore.setText(record.getScore().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}