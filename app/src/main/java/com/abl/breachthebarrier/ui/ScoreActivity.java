package com.abl.breachthebarrier.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.abl.breachthebarrier.R;
import com.abl.breachthebarrier.data.Record;
import com.abl.breachthebarrier.data.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

public class ScoreActivity extends AppCompatActivity {

    private TextView rightAnswers;
    private TextView courseScore;

    private Integer rightAnswersValue;
    private Integer courseScoreValue;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        db = FirebaseFirestore.getInstance();
        initInterface();
        getResults();
        setData();
    }

    private void initInterface(){
        rightAnswers = findViewById(R.id.text_view_right_answers);
        courseScore = findViewById(R.id.text_view_course_score);
    }

    private void getResults(){
        Bundle arguments = getIntent().getExtras();
        rightAnswersValue = arguments.getInt("right_answers");
        courseScoreValue = rightAnswersValue * 2;
    }

    private void setData(){
        rightAnswers.setText(rightAnswersValue.toString());
        courseScore.setText(courseScoreValue.toString());
        updateStatistics();
    }

    private void updateStatistics(){

        final DocumentReference docRef = db.collection("user_scores").document(UserData.getUserEmail());

        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(docRef);
                Record record = snapshot.toObject(Record.class);
                if(record != null){
                    Map<String, Object> map = new HashMap<>();
                    map.put("courses_complete", record.getCoursesComplete() + 1);
                    map.put("words_learned", record.getWordsLearned() + rightAnswersValue);
                    map.put("user_score", record.getScore() + courseScoreValue);
                    transaction.update(docRef, map);
                } else{
                    transaction.set(docRef, createNewRecord());
                }
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("Transaction", "Transaction success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Transaction", "Transaction failed!");
            }
        });
    }

    private Record createNewRecord(){
        Record newRecord = new Record();
        newRecord.setUserName(UserData.getUserEmail());
        newRecord.setScore(courseScoreValue);
        newRecord.setWordsLearned(rightAnswersValue);
        newRecord.setCoursesComplete(1);
        return newRecord;
    }
}