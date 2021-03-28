package com.example.emergencyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.emergencyapp.communities.CommunitiesActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;


public class MainActivity extends Activity {

    private CardView communitiesCard;
    private CardView suppliesCard;
    private CardView plansCard;
    private CardView alertsCard;
    private FirebaseAnalytics analytics;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        communitiesCard = findViewById(R.id.communitiesCard);
        suppliesCard = findViewById(R.id.suppliesCard);
        plansCard = findViewById(R.id.plansCard);
        alertsCard = findViewById(R.id.alertsCard);
        analytics = FirebaseAnalytics.getInstance(this);

        communitiesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CommunitiesActivity.class));
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

    }


}