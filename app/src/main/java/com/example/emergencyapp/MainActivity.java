package com.example.emergencyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.emergencyapp.communities.CommunitiesActivity;
import com.example.emergencyapp.login.LoginActivity;
import com.example.emergencyapp.postDisasterReport.ReportActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;


public class MainActivity extends Activity {

    private CardView communitiesCard, suppliesCard, plansCard, alertsCard;
    private FirebaseAnalytics analytics;
    private Button signOut, reportButton;
    private FirebaseAuth mAuth;
    private TextView currentCommunity;
    private String communityName;
    private DatabaseReference reference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        communitiesCard = findViewById(R.id.communitiesCard);
        suppliesCard = findViewById(R.id.suppliesCard);
        plansCard = findViewById(R.id.plansCard);
        alertsCard = findViewById(R.id.alertsCard);
        analytics = FirebaseAnalytics.getInstance(this);
        signOut = findViewById(R.id.activity_main_sign_out_button);
        mAuth = FirebaseAuth.getInstance();
        currentCommunity = findViewById(R.id.activity_main_current_community);
        reportButton = findViewById(R.id.activity_main_report_button);
        reference = FirebaseDatabase.getInstance().getReference();

//        Bundle extras = getIntent().getExtras();
//        communityName = extras.getString("selectedCommunity");
//        currentCommunity.setText(communityName);


        communitiesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommunitiesActivity.class);
                intent.putExtra("selectedCommunity", communityName);

                startActivityForResult(intent, 101);
                analytics.logEvent("communities_clicked", null);
            }
        });

        suppliesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                analytics.logEvent("supplies_clicked", null);
            }
        });

        plansCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Plan_View_Page.class));
                analytics.logEvent("plans_clicked", null);
            }
        });

        alertsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                analytics.logEvent("alerts_clicked", null);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                analytics.logEvent("sign_out_clicked", null);
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReportActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        reference.child("Users").child(mAuth.getCurrentUser().getUid()).child("selectedCommunity").child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    communityName = task.getResult().getValue(String.class);
                    Log.println(Log.INFO, "Main", "Main successfully acquired selected community: " + communityName);
                    currentCommunity.setText(communityName);

                }
            }
        });
    }
}