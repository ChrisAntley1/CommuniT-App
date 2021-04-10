package com.example.emergencyapp.postDisasterReport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.emergencyapp.R;
import com.tomer.fadingtextview.FadingTextView;

public class ReportActivity extends AppCompatActivity {

    private Button beginButton, assignButton;
    private CheckBox safeCheckBox, familyCheckBox;
    private LinearLayout confirmView, messageLayout;
    private FadingTextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        messageLayout = findViewById(R.id.activity_report_message_layout);
        messageTextView = findViewById(R.id.activity_report_fading_message);
        confirmView = findViewById(R.id.activity_report_confirm_view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                messageTextView.stop();
                messageLayout.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        confirmView.setVisibility(View.VISIBLE);
                    }
                }, 500);
            }
        }, 6500);
        beginButton = findViewById(R.id.activity_report_begin_button);
        assignButton = findViewById(R.id.activity_report_assign_button);

        safeCheckBox = findViewById(R.id.activity_report_safe_checkbox);
        familyCheckBox = findViewById(R.id.activity_report_family_checkbox);



        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //stuff
                if(safeCheckBox.isChecked() && familyCheckBox.isChecked()){

                    startActivity(new Intent(ReportActivity.this, WalkThrough.class));
                }

                else {
                    Toast.makeText(ReportActivity.this,
                            "Please confirm your own safety and that of your family before beginning block observations.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //things having to do with passing report duty to designated block members
            }
        });


    }
}