package com.example.emergencyapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;


public class MainActivity extends Activity {

    private CardView communitiesCard;
    private CardView suppliesCard;
    private CardView plansCard;
    private CardView alertsCard;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        communitiesCard = findViewById(R.id.communitiesCard);
        suppliesCard = findViewById(R.id.suppliesCard);
        plansCard = findViewById(R.id.plansCard);
        alertsCard = findViewById(R.id.alertsCard);

        communitiesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        suppliesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        plansCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        alertsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

}