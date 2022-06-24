package com.abl.breachthebarrier.ui.main.leaderboard;

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

import com.abl.breachthebarrier.data.Leader;
import com.abl.breachthebarrier.databinding.FragmentLeaderboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardFragment extends Fragment {

    private FragmentLeaderboardBinding binding;

    private FirebaseFirestore db;

    private List<Leader> leaderList;

    private List<TextView> textViewsPos;
    private List<TextView> textViewsScore;

    private TextView textViewPos1;
    private TextView textViewPos2;
    private TextView textViewPos3;
    private TextView textViewPos4;
    private TextView textViewPos5;
    private TextView textViewPos1Score;
    private TextView textViewPos2Score;
    private TextView textViewPos3Score;
    private TextView textViewPos4Score;
    private TextView textViewPos5Score;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LeaderboardViewModel leaderboardViewModel =
                new ViewModelProvider(this).get(LeaderboardViewModel.class);

        binding = FragmentLeaderboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();
        leaderList = new ArrayList<>();

        final TextView textView = binding.textNotifications;
        leaderboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInterface();
        getDataFromDb();
    }

    private void initInterface(){
        textViewsPos = new ArrayList<>();
        textViewsScore = new ArrayList<>();

        textViewPos1 = binding.textLeaderName;
        textViewPos2 = binding.textLeaderName1;
        textViewPos3 = binding.textLeaderName2;
        textViewPos4 = binding.textLeaderName3;
        textViewPos5 = binding.textLeaderName4;

        textViewsPos.add(textViewPos1);
        textViewsPos.add(textViewPos2);
        textViewsPos.add(textViewPos3);
        textViewsPos.add(textViewPos4);
        textViewsPos.add(textViewPos5);

        textViewPos1Score = binding.textLeaderScore;
        textViewPos2Score = binding.textLeaderScore1;
        textViewPos3Score = binding.textLeaderScore2;
        textViewPos4Score = binding.textLeaderScore3;
        textViewPos5Score = binding.textLeaderScore4;

        textViewsScore.add(textViewPos1Score);
        textViewsScore.add(textViewPos2Score);
        textViewsScore.add(textViewPos3Score);
        textViewsScore.add(textViewPos4Score);
        textViewsScore.add(textViewPos5Score);
    }

    private void getDataFromDb(){
        db.collection("user_scores")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        leaderList.clear();
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("RecordsGet", document.getId() + "=>" + document.getData());
                                Leader leader = document.toObject(Leader.class);
                                leaderList.add(leader);
                            }
                            setData(leaderList);
                        }
                    }
                });
    }

    private void setData(List<Leader> leaderList) throws NullPointerException{
        Collections.sort(leaderList);

        for(int i=0; i<leaderList.size(); i++){
            textViewsPos.get(i).setText(leaderList.get(i).getUser());
            textViewsScore.get(i).setText(leaderList.get(i).getScore().toString());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}