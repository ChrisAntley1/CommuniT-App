package com.example.emergencyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Plan_View_Page extends AppCompatActivity {
    private CardView hurricane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan__view__page);
        hurricane = findViewById(R.id.hurricane);
        hurricane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Plan_View_Page.this, HurricanePlan.class));
            }
        });
    }
}