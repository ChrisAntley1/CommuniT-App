package com.example.emergencyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class CommunitiesActivity extends Activity{

    private CardView churchCard;
    private CardView streetCard;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communities);

        churchCard = findViewById(R.id.churchCard);
        streetCard = findViewById(R.id.streetCard);

        churchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommunitiesActivity.this, MainActivity.class));
                Toast.makeText(CommunitiesActivity.this, "Showing plans for Oak Hill Church Community",
                        Toast.LENGTH_LONG).show();
            }
        });

        streetCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommunitiesActivity.this, MainActivity.class));
                Toast.makeText(CommunitiesActivity.this, "Showing plans for Mainstreet Community",
                        Toast.LENGTH_LONG).show();

            }
        });

    }
}
