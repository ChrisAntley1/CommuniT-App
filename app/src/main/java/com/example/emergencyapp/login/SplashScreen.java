package com.example.emergencyapp.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.example.emergencyapp.MainActivity;
import com.example.emergencyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        Intent intent;
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            intent = new Intent(SplashScreen.this, LoginActivity.class);
            nextScreen(intent);
        }
        else {
            reference.child("Users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {

                    if(task.isSuccessful()){
                        User userData = task.getResult().getValue(User.class);
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        intent.putExtra("selectedCommunity", userData.selectedCommunity.name);
                        Toast.makeText(SplashScreen.this, "The current community is: " + userData.selectedCommunity.name, Toast.LENGTH_LONG).show();
                        nextScreen(intent);
                    }

                    else {
                        Toast.makeText(SplashScreen.this, "Task failed.", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(1500);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        findViewById(R.id.imageView).startAnimation(rotateAnimation);

    }

    private void nextScreen(final Intent intent){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}