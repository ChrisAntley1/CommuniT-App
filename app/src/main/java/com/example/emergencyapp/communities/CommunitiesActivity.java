package com.example.emergencyapp.communities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.emergencyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommunitiesActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText communityText, memberText, zipCodeText;
    public Button communityButton, memberButton;
    public ProgressBar progressBar;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private DatabaseReference userNode;
    private String username;

    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communities);


        communityText = findViewById(R.id.communityText);
        memberText = findViewById(R.id.memberText);
        zipCodeText = findViewById(R.id.zipCodeText);
        communityButton = findViewById(R.id.addCommunity);
        memberButton = findViewById(R.id.addMember);
        progressBar = findViewById(R.id.activity_communities_progressbar);
        communityButton.setOnClickListener(this);

        progressBar.setVisibility(View.VISIBLE);

        arrayList = new ArrayList<>();
        listView = findViewById(R.id.communityList);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Communities");
        userNode = rootNode.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        userNode.child("communityList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(String.class);
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.addMember:
                addNewMember();
                break;

            case R.id.addCommunity:
                checkNewCommunityValues();
        }
    }

    private void checkNewCommunityValues() {

        final String community = communityText.getText().toString().trim();
        final String zipCode = zipCodeText.getText().toString().trim();


        if(community.isEmpty()){
            communityText.setError("Please enter a name for your community.");
            return;
        }

        if(community.length() < 6){
            communityText.setError("Community name must be longer than 6 characters.");
            return;
        }

        if(zipCode.isEmpty()){
            zipCodeText.setError("Please enter your community's zip code.");
            return;
        }

        if(zipCode.length() != 5){
            zipCodeText.setError("Please enter a valid 5 digit zip code.");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        userNode.child("name").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(CommunitiesActivity.this, "Failed to get username.", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            return;
                        }

                        username = task.getResult().getValue(String.class);
                        createNewCommunity(username, community, zipCode);
                     }
                 }

        );
    }

    private void createNewCommunity(final String username, String community, String zipCode) {
        final String communityID = community;

        Community newCom = new Community(community, Integer.parseInt(zipCode), username);
        reference.child(communityID).setValue(newCom).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    //add user to Community member list; do this here instead of in Community constructor to give user a proper index in Firebase
                    reference.child(communityID).child("members").push().setValue(username);

                    //add community to user's community list on Firebase
                    userNode.child("communityList").push().setValue(communityID).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(CommunitiesActivity.this, "Community successfully created and added to user's community list!", Toast.LENGTH_LONG).show();

                            }

                            else Toast.makeText(CommunitiesActivity.this, "Failed to add community to user's communities and probably failed to make community as well. Probably.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private void addNewMember() {

        String member = memberText.getText().toString().trim();
        String community = communityText.getText().toString().trim();

        if(member.isEmpty()){
            memberText.setError("Please enter your new member's name.");
            return;
        }

        if(member.length() < 4){
            memberText.setError("Member's name must be longer than 4 characters");
            return;
        }

        if(community.isEmpty()){
            communityText.setError("Please enter a name for your community.");
            return;
        }

        if(community.length() < 6){
            communityText.setError("Community name must be longer than 6 characters.");
            return;
        }

        Query communityQuery = reference.child(community);

    }
}