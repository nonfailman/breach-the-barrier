package com.abl.breachthebarrier.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.abl.breachthebarrier.R;
import com.abl.breachthebarrier.data.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    private String courseTitle;

    private FirebaseFirestore db;

    private List<Question> questions;
    private boolean dataGet;
    private int questionNumber;

    private TextView question;

    private ImageView image;

    private RadioGroup radioGroup;
    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private RadioButton answer4;

    private Button submitAnswer;

    View.OnClickListener onSubmitAnswerButtonClickListener;
    View.OnClickListener onNextQuestionButtonClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Bundle arguments = getIntent().getExtras();
        courseTitle = arguments.getString("unit_title");

        db = FirebaseFirestore.getInstance();
        questions = new ArrayList<>();
        dataGet = false;
        questionNumber = 0;

        initInterface();
        getDataFromDb();

    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

    private void initInterface(){
        question = findViewById(R.id.text_question);

        image = findViewById(R.id.image_question);

        radioGroup = findViewById(R.id.radioGroup);
        answer1 = findViewById(R.id.radio_answer1);
        answer2 = findViewById(R.id.radio_answer2);
        answer3 = findViewById(R.id.radio_answer3);
        answer4 = findViewById(R.id.radio_answer4);

        submitAnswer = findViewById(R.id.button_submit_answer);

        onSubmitAnswerButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitAnswerButtonClick();
            }
        };
        onNextQuestionButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextQuestionButtonClick();
            }
        };
        submitAnswer.setOnClickListener(onSubmitAnswerButtonClickListener);
    }

    private void getDataFromDb() {
        db.collection("units")
                .document(courseTitle)
                .collection("questions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Question question = document.toObject(Question.class);
                                questions.add(question);
                                dataGet = true;
                            }
                        } else {
                            Log.d("DataGet", "Error getting documents", task.getException());
                        }
                        Log.d("DataGet",questions.toString());
                        setData(questions, 0);
                    }
                });
    }


    private void setData(List<Question> questions, int number){
        if(number > questions.size()-1){
            return;
        }
        Drawable drawable = AppCompatResources.getDrawable(CourseActivity.this, R.drawable.background_answer);
        answer1.setBackground(drawable);
        answer2.setBackground(drawable);
        answer3.setBackground(drawable);
        answer4.setBackground(drawable);

        question.setText(questions.get(number).getQuestion());

        answer1.setText(questions.get(number).getAnswer1());
        answer2.setText(questions.get(number).getAnswer2());
        answer3.setText(questions.get(number).getAnswer3());
        answer4.setText(questions.get(number).getAnswer4());
    }

    private void onSubmitAnswerButtonClick(){
        if(!dataGet){
            return;
        }
        questionNumber++;
        checkAnswer(questionNumber);
        submitAnswer.setText(R.string.text_next_question_button);
        submitAnswer.setOnClickListener(onNextQuestionButtonClickListener);
    }

    private void onNextQuestionButtonClick(){
        loadNextQuestion();
        submitAnswer.setText(R.string.text_submit_answer_button);
        submitAnswer.setOnClickListener(onSubmitAnswerButtonClickListener);
        radioGroup.clearCheck();
    }

    private void checkAnswer(int questionNumber){
        if(questionNumber > questions.size()-1){
            return;
        }
        RadioButton radioChecked = findViewById(radioGroup.getCheckedRadioButtonId());
        if(radioChecked.getText().toString().equals(questions.get(questionNumber-1).getAnswer1())){
            setRightAnswerBackground(radioChecked);
        } else{
            setWrongAnswerBackground(radioChecked );
        }
    }

    private void setWrongAnswerBackground(RadioButton radioChecked){
        Drawable drawable = AppCompatResources.getDrawable(CourseActivity.this, R.drawable.background_wrong_answer);
        radioChecked.setBackground(drawable);
    }

    private void setRightAnswerBackground(RadioButton rightAnswer){
        Drawable drawable = AppCompatResources.getDrawable(CourseActivity.this, R.drawable.background_right_answer);
        rightAnswer.setBackground(drawable);
    }

    private void loadNextQuestion(){
        setData(questions, questionNumber);
    }
}
