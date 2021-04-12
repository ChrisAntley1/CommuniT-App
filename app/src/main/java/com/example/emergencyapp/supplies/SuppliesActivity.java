package com.example.emergencyapp.supplies;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.emergencyapp.MainActivity;
import com.example.emergencyapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class SuppliesActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supply_card);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuppliesActivity.this, AddSupplyActivity.class);
                startActivity(intent);

            }
        });

    }


}