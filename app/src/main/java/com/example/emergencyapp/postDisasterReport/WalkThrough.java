package com.example.emergencyapp.postDisasterReport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.emergencyapp.R;

public class WalkThrough extends AppCompatActivity {

    private Button submitButton;
    private CheckBox gasCheckBox, fireCheckBox, floodCheckBox, structureCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);

        submitButton = findViewById(R.id.activity_walkthrough_submit_button);

        gasCheckBox = findViewById(R.id.activity_walkthrough_gas_checkbox);
        fireCheckBox = findViewById(R.id.activity_walkthrough_fire_checkbox);
        floodCheckBox = findViewById(R.id.activity_walkthrough_flood_checkbox);
        structureCheckBox = findViewById(R.id.activity_walkthrough_structure_checkbox);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //gather checkbox statuses, compile into intent, open medical status activity

            }
        });
    }
}