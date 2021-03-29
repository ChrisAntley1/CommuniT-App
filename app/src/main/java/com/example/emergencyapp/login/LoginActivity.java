package com.example.emergencyapp.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencyapp.MainActivity;
import com.example.emergencyapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 120;
    private FirebaseAuth mAuth;
    private TextView createAccount, forgotPassword;
    private Button login;
    private SignInButton googleLogin;
    private EditText editEmail, editPassword;
    private ProgressBar progressBar;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createAccount = findViewById(R.id.activity_login_create_account_text);
        forgotPassword = findViewById(R.id.activity_login_forgot_password_text);
        login = findViewById(R.id.activity_login_login_button);
        editEmail = findViewById(R.id.activity_login_email_edit);
        editPassword = findViewById(R.id.activity_login_password_edit);
        progressBar = findViewById(R.id.activity_login_progressbar);
        googleLogin = findViewById(R.id.activity_login_google_login_button);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(this);
        createAccount.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        googleLogin.setOnClickListener(this);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.activity_login_create_account_text:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.activity_login_forgot_password_text:
                startActivity(new Intent(this, ResetPasswordActivity.class));
                Toast.makeText(LoginActivity.this, "Surprise I implemented this shit!",
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.activity_login_google_login_button:
                signIn();
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
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Exception exception = task.getException();
            if(task.isSuccessful()){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d("Login Activity", "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("Login Activity", "Google sign in failed", e);
                }
            }

            else {
                Log.w("Login Activity", exception.toString());
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login Activity", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            User newUser = new User(user.getDisplayName(), 11111, user.getEmail());

                            FirebaseDatabase.getInstance().getReference().child("Users").push().setValue(newUser);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login Activity", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }
}