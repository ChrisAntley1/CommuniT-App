package com.example.emergencyapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class HurricanePlan extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hurrican_plan);

        // Get reference of widgets from XML layout
        final ListView lv = (ListView) findViewById(R.id.lv);
        final Button btn = (Button) findViewById(R.id.btn);
        final EditText newItem = (EditText) findViewById(R.id.EditPlanItem);

        // Initializing a new String Array
        String[] HPlan = new String[] {
                "Stay at home",
                "Don't be near windows",
                "Ensure you have enough fresh water and canned foods"
        };

        // Create a List from String Array elements
        final List<String> Hurricane_List = new ArrayList<String>(Arrays.asList(HPlan));

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, Hurricane_List);

        // DataBind ListView with items from ArrayAdapter
        lv.setAdapter(arrayAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add new Items to List
                Hurricane_List.add(String.valueOf(newItem.getText().toString()));
                /*
                    notifyDataSetChanged ()
                        Notifies the attached observers that the underlying
                        data has been changed and any View reflecting the
                        data set should refresh itself.
                 */
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
}