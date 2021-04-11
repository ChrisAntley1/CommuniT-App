package com.example.emergencyapp.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.emergencyapp.R;
import com.example.emergencyapp.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button register;
    private ProgressBar progressBar;
    private EditText editName, editPhone, editPassword, editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        register = findViewById(R.id.activity_register_register_button);
        editName = findViewById(R.id.activity_register_name_edit);
        editPhone = findViewById(R.id.activity_register_phone_edit);
        editEmail = findViewById(R.id.activity_register_email_edit);
        editPassword = findViewById(R.id.activity_register_password_edit);
        progressBar = findViewById(R.id.activity_register_progressbar);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String email = editEmail.getText().toString().trim();
        final String name = editName.getText().toString().trim();
        final String phone = editPhone.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(name.isEmpty()){
            editName.setError("Name required.");
            editName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editEmail.setError("Email address required.");
            editEmail.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            editPhone.setError("Zip code required.");
            editPhone.requestFocus();
            return;
        }

        if(!Patterns.PHONE.matcher(phone).matches()){
            editPhone.setError("Please enter a valid phone number.");
            editPhone.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please enter a valid email address.");
            editEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editPassword.setError("Password required.");
            editPassword.requestFocus();
            return;
        }

        //validate email and password
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please enter a valid email address.");
            editEmail.requestFocus();
            return;
        }

        if(password.length() < 6){
            editPassword.setError("Password must be at least 6 characters.");
            editPassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            User user = new User(name, phone, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "User has been successfully registered!", Toast.LENGTH_LONG).show();

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                                startActivity(new Intent(RegisterActivity.this, AddressActivity.class));
                                            }
                                        }, 2000);
                                        // Email Verification if we want at some point
//                                        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                                    }

                                    else {
                                        Toast.makeText(RegisterActivity.this, "Failed to register.", Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }

                        else {
                            Toast.makeText(RegisterActivity.this, "Failed to register.", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}