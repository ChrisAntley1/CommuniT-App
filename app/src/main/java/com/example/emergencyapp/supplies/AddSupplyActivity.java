package com.example.emergencyapp.supplies;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.emergencyapp.MainActivity;
import com.example.emergencyapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class AddSupplyActivity extends AppCompatActivity  {

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_supply);

        saveButton = findViewById(R.id.save_supply_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddSupplyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }


}