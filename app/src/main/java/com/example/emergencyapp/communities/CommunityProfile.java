package com.example.emergencyapp.communities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.emergencyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Set;

public class CommunityProfile extends AppCompatActivity {

    private TextView communityNameView, captainNameView;
    private ListView memberListView;
    private ArrayList<String> nameArrayList;
    private Set<String> idSetList;
    private ArrayAdapter<String> arrayAdapter;

    private Community community;
    private FirebaseAuth mAuth;
    private DatabaseReference communityDB;

    private String communityName, communityID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_profile);

        communityNameView = findViewById(R.id.activity_profile_community_name);
        captainNameView = findViewById(R.id.activity_profile_captain_name);
        memberListView = findViewById(R.id.activity_profile_member_list);

        Bundle extras = getIntent().getExtras();

        communityName = extras.getString("community_name");
        communityID = extras.getString("community_id");

        if(communityName == null || communityID == null){
            Log.d("CommunityProfile", "onCreate: Failed to retrieve community info from intent.");
        }

        else communityNameView.setText(communityName);
        mAuth = FirebaseAuth.getInstance();
        communityDB = FirebaseDatabase.getInstance().getReference("Communities/" + communityID);
        nameArrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameArrayList);
        memberListView.setAdapter(arrayAdapter);

        communityDB.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){

                    community = task.getResult().getValue(Community.class);
                    Log.d("CommunityProfile", "onComplete: community = " + community.toString());
                    idSetList = community.memberList.keySet();
                    nameArrayList.addAll(community.memberList.values());
                    arrayAdapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(memberListView);

                    captainNameView.setText("Block Captain: " + community.captain.username);

                }

                else Log.d("CommunityProfile", "onComplete: get() for community data failed");

            }
        });

    }


    //helper method for making inner listview work properly, since it technically should be inside a scroll view
    public static void setListViewHeightBasedOnChildren(@NotNull ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}