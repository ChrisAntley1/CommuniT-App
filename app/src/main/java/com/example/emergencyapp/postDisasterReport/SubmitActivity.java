package com.example.emergencyapp.postDisasterReport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.emergencyapp.R;

public class SubmitActivity extends AppCompatActivity {

    private Button submitButton, reviewButton;

    private EventReport report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        Bundle extra = getIntent().getExtras();
        report = (EventReport) extra.get("report");

        Log.d("SubmitActivity", "onCreate: report contains these members: " + report.injuredMembers.toString());
    }
}