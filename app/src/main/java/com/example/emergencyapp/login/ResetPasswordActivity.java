package com.example.emergencyapp.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.emergencyapp.R;
import com.example.emergencyapp.communities.CommunitiesActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {


    private Button resetButton;
    private EditText emailEdit;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetButton = findViewById(R.id.activity_reset_reset_button);
        emailEdit = findViewById(R.id.activity_reset_email_edit);
        progressBar = findViewById(R.id.activity_reset_progressbar);
        resetButton.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        resetPassword();
    }

    private void resetPassword() {

        String email = emailEdit.getText().toString().trim();

        if(email.isEmpty()){
            emailEdit.setError("Please enter your email address.");
            emailEdit.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEdit.setError("Please enter a valid email address.");
            emailEdit.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()) Toast.makeText(ResetPasswordActivity.this, "Success. Check your email to reset your password.", Toast.LENGTH_LONG).show();

                else Toast.makeText(ResetPasswordActivity.this, "Something went wrong, check your email and try again.", Toast.LENGTH_LONG).show();

                progressBar.setVisibility(View.GONE);
                finish();
            }
        });

    }
}