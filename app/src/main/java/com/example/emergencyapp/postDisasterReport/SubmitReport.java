package com.example.emergencyapp.postDisasterReport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emergencyapp.R;
import com.tomer.fadingtextview.FadingTextView;

public class SubmitReport extends AppCompatActivity {

    private Button submitButton, reviewButton;
    private TextView reportTextView;

    private LinearLayout reportLayout, messageLayout;
    private FadingTextView messageTextView;



    private EventReport report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        messageLayout = findViewById(R.id.activity_submit_message_layout);
        messageTextView = findViewById(R.id.activity_submit_fading_message);
        reportLayout = findViewById(R.id.activity_submit_report_view);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                messageTextView.stop();
                messageLayout.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reportLayout.setVisibility(View.VISIBLE);
                    }
                }, 500);
            }
        }, 7500);

        Bundle extra = getIntent().getExtras();
        report = (EventReport) extra.get("report");

        submitButton = findViewById(R.id.activity_submit_submit_button);
        reportTextView = findViewById(R.id.activity_submit_report_text);

        reportTextView.setText(report.toString());
        Log.d("SubmitActivity", "onCreate: report toString\n" + report.toString());
    }
}