package com.example.emergencyapp.postDisasterReport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.emergencyapp.R;
import com.tomer.fadingtextview.FadingTextView;

public class WalkThrough extends AppCompatActivity {

    private Button submitButton;
    private CheckBox gasCheckBox, fireCheckBox, floodCheckBox, structureCheckBox;
    private FadingTextView messageTextView;
    private LinearLayout observeLayout, messageLayout;
    private EventReport report;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);

        messageLayout = findViewById(R.id.activity_walkthrough_message_layout);
        messageTextView = findViewById(R.id.activity_walkthrough_fading_message);
        observeLayout = findViewById(R.id.activity_walkthrough_observe_view);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                messageTextView.stop();
                messageLayout.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        observeLayout.setVisibility(View.VISIBLE);
                    }
                }, 500);
            }
        }, 13500);

        submitButton = findViewById(R.id.activity_walkthrough_submit_button);

        gasCheckBox = findViewById(R.id.activity_walkthrough_gas_checkbox);
        fireCheckBox = findViewById(R.id.activity_walkthrough_fire_checkbox);
        floodCheckBox = findViewById(R.id.activity_walkthrough_flood_checkbox);
        structureCheckBox = findViewById(R.id.activity_walkthrough_structure_checkbox);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //gather checkbox statuses, compile into intent, open medical status activity

                report = new EventReport(gasCheckBox.isChecked(), fireCheckBox.isChecked(), floodCheckBox.isChecked(), structureCheckBox.isChecked());
                Intent intent = new Intent(WalkThrough.this, MedicalStatus.class);
                intent.putExtra("report", report);
                startActivity(intent);
            }
        });


    }
}