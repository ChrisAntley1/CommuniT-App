package com.example.emergencyapp.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencyapp.MainActivity;
import com.example.emergencyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView createAccount, forgotPassword, banner;
    private Button login;
    private EditText editEmail, editPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createAccount = findViewById(R.id.activity_login_create_account_text);
        forgotPassword = findViewById(R.id.activity_login_forgot_password_text);
        banner = findViewById(R.id.activity_login_banner);
        login = findViewById(R.id.activity_login_login_button);
        editEmail = findViewById(R.id.activity_login_email_edit);
        editPassword = findViewById(R.id.activity_login_password_edit);
        progressBar = findViewById(R.id.activity_login_progressbar);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(this);
        createAccount.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        banner.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.activity_login_create_account_text:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.activity_login_forgot_password_text:
                Toast.makeText(LoginActivity.this, "I ain't implement this shit yet",
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.activity_login_banner:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.activity_login_login_button:
                userLogin();
                break;
        }


    }

    private void userLogin() {
        String email, password;
        email = editEmail.getText().toString().trim();
        password = editPassword.getText().toString().trim();

        if(email.isEmpty()) {
            editEmail.setError("Please enter your email.");
            editEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please enter a valid email.");
            editEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editPassword.setError("Please enter your password.");
            editPassword.requestFocus();
            return;
        }

        if(password.length() < 6) {
            editPassword.setError("Minimum password length is 6 characters.");
            editPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

//                    if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
//                        //check for email verification
//                    }
                    //go to main activity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Toast.makeText(LoginActivity.this, "Welcome! We have no association with Emergen-C and denounce all forms of Vitamin C.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Incorrect email or password.", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}