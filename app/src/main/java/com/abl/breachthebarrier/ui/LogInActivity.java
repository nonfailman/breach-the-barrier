package com.abl.breachthebarrier.ui;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abl.breachthebarrier.R;
import com.abl.breachthebarrier.data.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    View decorView;
    Window window;
    int windowOptions;

    private FirebaseAuth firebaseAuth;

    private EditText editTextLogIn;
    private EditText editTextPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        firebaseAuth = FirebaseAuth.getInstance();
        decorView = getWindow().getDecorView();
        window = getWindow();
        editTextLogIn = (EditText) findViewById(R.id.editTextUserName);
        editTextPwd = (EditText) findViewById(R.id.editTextPassword);
        Button buttonLogIn = (Button) findViewById(R.id.buttonLogIn);
        Button buttonSignIn = (Button) findViewById(R.id.buttonSignIn);

        windowOptions = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setFlags(windowOptions, windowOptions);
        getSupportActionBar().hide();

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogInButtonClicked();
            }
        });
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignInButtonClicked();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }



    private void logIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(LogInActivity.this, "Вы успешно вошли!",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Ошибка авторизации.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void onLogInButtonClicked(){
        logIn(editTextLogIn.getText().toString(), editTextPwd.getText().toString());
    }

    private void onSignInButtonClicked(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            UserData.getInstance();
            UserData.setUserEmail(user.getEmail());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void reload(){

    }
}