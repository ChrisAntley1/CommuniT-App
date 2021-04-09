package com.example.emergencyapp.postDisasterReport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.emergencyapp.R;
import com.example.emergencyapp.communities.Community;
import com.example.emergencyapp.communities.CurrentCommunityObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MedicalStatus extends AppCompatActivity {

    private Button submitButton;
    private EventReport report;
    private ListView memberListView;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    private ArrayAdapter arrayAdapter;
    private ArrayList<String> nameArrayList;
    private ArrayList<String> idArrayList;

    private Community community;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_status);

        //views
        submitButton = findViewById(R.id.activity_medical_submit_button);
        memberListView = findViewById(R.id.activity_medical_list_view);


        //Firebase stuff
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        memberListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        nameArrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, nameArrayList);
        memberListView.setAdapter(arrayAdapter);

        memberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                if (memberListView.isItemChecked(i)) {
                    memberListView.setItemChecked(i, true);

                }
                else memberListView.setItemChecked(i, false);
            }
        });

        //retrieve database things, set list values
        reference.child("Users/" + mAuth.getCurrentUser().getUid() + "/selectedCommunity").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("MedicalStatus", "onComplete: successfully retrieved user's selected community from database");

                    CurrentCommunityObject currentCommunity = task.getResult().getValue(CurrentCommunityObject.class);

                    if (currentCommunity == null){
                        Log.d("MedicalStatus", "onComplete: currentCommunity is null.");
                    }

                    else {
                        String cID = currentCommunity.cID;
                        String name = currentCommunity.name;
                        Log.d("MedicalStatus", "onComplete: cID = " + cID + " ; community name = " + name);

                        reference.child("Communities/" + cID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {

                                if(task.isSuccessful()){
                                    Log.d("MedicalStatus", "onComplete: successfully retrieved community from database.");
                                    community = task.getResult().getValue(Community.class);

                                    if (community == null){
                                        Log.d("MedicalStatus", "onComplete: object retrieved was not a Community object.");

                                    }
                                    idArrayList = new ArrayList<>(community.memberList.keySet());
                                    nameArrayList.addAll(community.memberList.values());
                                    arrayAdapter.notifyDataSetChanged();

                                }
                            }
                        });

                    }
                }

                else {
                    Log.d("MedicalStatus", "onComplete: failed to retrieve userdata from database");
                }
            }
        });

        Bundle extra = getIntent().getExtras();
        report = (EventReport) extra.get("report");
        if (report == null) {
            Log.d("MedicalStatus", "onCreate: failed to retrieve report from intent.");
        }


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < memberListView.getCount(); i++){

                    if(!memberListView.isItemChecked(i)){
                        report.injuredMembers.put(idArrayList.get(i), nameArrayList.get(i));
                    }
                }

                Intent intent = new Intent(MedicalStatus.this, SubmitActivity.class);
                intent.putExtra("report", report);
                startActivity(intent);

            }
        });
    }
}