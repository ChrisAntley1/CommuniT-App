package com.example.emergencyapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencyapp.MainActivity;
import com.example.emergencyapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView createAccount, forgotPassword, banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createAccount = (TextView) findViewById(R.id.createAccount);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        banner = (TextView) findViewById(R.id.Login_Banner);
        createAccount.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        banner.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.createAccount:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.forgotPassword:
                Toast.makeText(LoginActivity.this, "I ain't implement this shit yet",
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.Login_Banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }


    }
}