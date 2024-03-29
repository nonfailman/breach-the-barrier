package com.abl.breachthebarrier.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    private Bundle arguments;

    private String courseTitle;

    private FirebaseFirestore db;

    private List<Question> questions;
    private boolean dataGet;
    private int questionNumber;
    private int rightAnswers;

    private ActionBar actionBar;

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
        arguments = getIntent().getExtras();
        courseTitle = arguments.getString("unit_title");

        db = FirebaseFirestore.getInstance();
        questions = new ArrayList<>();
        dataGet = false;
        questionNumber = 0;
        rightAnswers = 0;

        initInterface();
        getDataFromDb();

    }

    @Override
    protected void onStart() {
        super.onStart();
        actionBar.setTitle("Вопрос " + (questionNumber+1) + " / 10");
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

    private void initInterface(){
        actionBar = getSupportActionBar();

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
        if(number >= questions.size()){
            return;
        }

        Drawable drawable = AppCompatResources.getDrawable(CourseActivity.this, R.drawable.background_answer);
        answer1.setBackground(drawable);
        answer2.setBackground(drawable);
        answer3.setBackground(drawable);
        answer4.setBackground(drawable);

        Picasso.get()
                .load(questions.get(number).getImage())
                .into(image);

        Question currentQuestion = questions.get(number);
        List<String> answers = new ArrayList<>();
        answers.add(currentQuestion.getAnswer1());
        answers.add(currentQuestion.getAnswer2());
        answers.add(currentQuestion.getAnswer3());
        answers.add(currentQuestion.getAnswer4());
        Collections.shuffle(answers);

        question.setText(questions.get(number).getQuestion());

        answer1.setText(answers.get(0));
        answer2.setText(answers.get(1));
        answer3.setText(answers.get(2));
        answer4.setText(answers.get(3));
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
        if(questionNumber >= questions.size()){
            loadScoreActivity();
            return;
        }
        loadNextQuestion();
        submitAnswer.setText(R.string.text_submit_answer_button);
        submitAnswer.setOnClickListener(onSubmitAnswerButtonClickListener);
        radioGroup.clearCheck();
        actionBar.setTitle("Вопрос " + (questionNumber+1) + " / 10");
    }

    private void checkAnswer(int questionNumber){
        if(questionNumber > questions.size()){
            return;
        }
        RadioButton radioChecked = findViewById(radioGroup.getCheckedRadioButtonId());
        if(radioChecked.getText().toString().equals(questions.get(questionNumber-1).getAnswer1())){
            setRightAnswerBackground(radioChecked);
            rightAnswers++;
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

    private void loadScoreActivity(){
        Intent intent = new Intent(CourseActivity.this, ScoreActivity.class);
        intent.putExtra("right_answers", rightAnswers);
        intent.putExtra("course", arguments.getString("course_id"));
        startActivity(intent);
        finish();
    }
}
