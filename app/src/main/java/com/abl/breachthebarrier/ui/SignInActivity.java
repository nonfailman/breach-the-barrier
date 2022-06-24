package com.abl.breachthebarrier.ui;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abl.breachthebarrier.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText editTextNewUserName;
    private EditText editTextNewPassword;
    private EditText editTextRepeatNewPassword;

    private View decorView;
    private Window window;
    private int windowOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        firebaseAuth = FirebaseAuth.getInstance();

        decorView = getWindow().getDecorView();
        window = getWindow();

        Button buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        editTextNewUserName = (EditText) findViewById(R.id.edit_text_new_user_name);
        editTextNewPassword = (EditText) findViewById(R.id.edit_text_new_password);
        editTextRepeatNewPassword = findViewById(R.id.edit_text_repeat_new_password);

        windowOptions = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setFlags(windowOptions, windowOptions);
        getSupportActionBar().hide();

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignInButtonClicked();
            }
        });
    }

    private void createAccount(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(SignInActivity.this, "Ваш аккаунт создан!",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Ошибка регистрации.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void onSignInButtonClicked(){
        String password = editTextNewPassword.getText().toString();
        String repeatedPassword = editTextRepeatNewPassword.getText().toString();
        if(password.equals(repeatedPassword)){
            createAccount(editTextNewUserName.getText().toString(), password);
        } else {
            Toast.makeText(SignInActivity.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
        }
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {
        finish();
    }
}