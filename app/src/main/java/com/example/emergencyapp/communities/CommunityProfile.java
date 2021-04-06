package com.example.emergencyapp.communities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.emergencyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CommunityProfile extends AppCompatActivity {

    private TextView community_name_view, captain_name_view;

    private ArrayList<String> nameArrayList;
    private ArrayList<String> idArrayList;
    private ArrayAdapter<String> arrayAdapter;

    private FirebaseAuth mAuth;
    private DatabaseReference communityDB;

    private String communityName, communityID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_profile);

        community_name_view = findViewById(R.id.activity_profile_community_name);
        captain_name_view = findViewById(R.id.activity_profile_captain_name);


        Bundle extras = getIntent().getExtras();

        communityName = extras.getString("community_name");
        communityID = extras.getString("community_id");

        if(communityName == null || communityID == null){
            Log.d("CommunityProfile", "onCreate: Failed to retrieve community info from intent.");
        }

        else community_name_view.setText(communityName);
        mAuth = FirebaseAuth.getInstance();
        communityDB = FirebaseDatabase.getInstance().getReference("Communities/" + communityID);

        communityDB.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    Log.d("CommunityProfile", "onComplete: snapshot = " + task.getResult().toString());
                }

                else Log.d("CommunityProfile", "onComplete: get() for community data failed");

            }
        });

    }
}