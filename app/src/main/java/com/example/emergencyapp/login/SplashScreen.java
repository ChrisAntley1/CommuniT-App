package com.example.emergencyapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.example.emergencyapp.MainActivity;
import com.example.emergencyapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(1500);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        findViewById(R.id.imageView).startAnimation(rotateAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user != null)
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                else startActivity(new Intent(SplashScreen.this, LoginActivity.class));

                finish();
            }
        }, 2000);

    }
}