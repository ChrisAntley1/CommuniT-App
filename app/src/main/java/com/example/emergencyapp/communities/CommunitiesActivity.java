package com.example.emergencyapp.communities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CommunitiesActivity extends AppCompatActivity  {

    public EditText communityText, newMemberEmailText, zipCodeText, inviteCodeText;
    public Button newCommunityButton, memberButton, copyCodeButton, joinButton;
    public ProgressBar progressBar;
    public TextView currentCommunityView;
    private FirebaseDatabase rootNode;
    private FirebaseAuth mAuth;
    private DatabaseReference communityDB;
    private DatabaseReference userDBEntry;
    private String username, userID;

    private ListView listView;
    private ArrayList<String> nameArrayList;
    private ArrayList<String> idArrayList;
    private ArrayAdapter<String> arrayAdapter;
    private final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private CurrentCommunityObject currentCommunityEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communities);



        //Get Views
        communityText = findViewById(R.id.communityText);
        zipCodeText = findViewById(R.id.zipCodeText);
        newCommunityButton = findViewById(R.id.addCommunity);
        memberButton = findViewById(R.id.addMember);
        progressBar = findViewById(R.id.activity_communities_progressbar);
        newMemberEmailText = findViewById(R.id.member_email_text);
        inviteCodeText = findViewById(R.id.community_invite_code_text);
        copyCodeButton = findViewById(R.id.copyCommunityCode);
        currentCommunityView = findViewById(R.id.current_community);
        joinButton = findViewById(R.id.join_community_button);


        //get Database and auth
        rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        communityDB = rootNode.getReference("Communities");
        userDBEntry = rootNode.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        userID = mAuth.getCurrentUser().getUid();
         userDBEntry.child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DataSnapshot> task) {
                 username = task.getResult().getValue(String.class);
             }
         });

         userDBEntry.child("selectedCommunity").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DataSnapshot> task) {
                 if (task.isSuccessful()){

                     currentCommunityEntry = task.getResult().getValue(CurrentCommunityObject.class);
                     currentCommunityView.setText(currentCommunityEntry.name);
                 }
             }
         });

        //Set up community list View adapter
        nameArrayList = new ArrayList<>();
        idArrayList = new ArrayList<>();

        listView = findViewById(R.id.communityList);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameArrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                final String name = listView.getItemAtPosition(i).toString();
                currentCommunityEntry = new CurrentCommunityObject(idArrayList.get(i), name);


                userDBEntry.child("selectedCommunity").setValue(currentCommunityEntry).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            currentCommunityView.setText(name);
                            Log.d("CommunitiesActivity", "Successfully set selected community in database.");
                        }
                    }
                });
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {

                Intent intent = new Intent(CommunitiesActivity.this, CommunityProfile.class);
                Log.d("CommunitiesActivity", "onItemClick: values going into intent are: "
                        + nameArrayList.get(i) + " and " + idArrayList.get(i));
                intent.putExtra("community_name", nameArrayList.get(i));
                intent.putExtra("community_id", idArrayList.get(i));
                startActivity(intent);

                return true;
            }
        });

        userDBEntry.child("communityList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String name = "";
                String id = "";

                name = snapshot.getValue(String.class);
                Log.println(Log.INFO, "CommunitiesActivity", "value is " + name);
                id = snapshot.getKey();
                Log.println(Log.INFO, "CommunitiesActivity", "key is " + id);

                nameArrayList.add(name);
                idArrayList.add(id);
                arrayAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                setListViewHeightBasedOnChildren(listView);
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

        memberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommunitiesActivity.this, "this don't do nuthin", Toast.LENGTH_SHORT).show();
                addNewMember();
            }
        });

        newCommunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNewCommunityValues();
            }
        });

        copyCodeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                copyInviteCode();
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                joinCommunity();
            }
        });
    }

    private void joinCommunity() {

        String passCode = inviteCodeText.getText().toString();

        if (passCode.isEmpty()){
            inviteCodeText.setError("Please enter a community passCode.");
            return;

        }

        //if passcode is too short then stuff

        communityDB.orderByChild("passCode").equalTo(passCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    Log.d("CommunitiesActivity", "onDataChange: "+ snapshot.getValue().toString());
                    String key = "none";
                    String communityName = "none";
                    Iterable<DataSnapshot> iterable = snapshot.getChildren();
                    for(DataSnapshot child: iterable){
                        key = child.getKey();
                        Log.d("CommunitiesActivity", "onDataChange: key holds value: " + key);
                        communityName = child.child("name").getValue(String.class);
                    }

                    final String finalKey = key;
                    final String finalCommunityName = communityName;
                    userDBEntry.child("communityList").orderByKey().equalTo(finalKey).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (!snapshot.exists()){
                                createListEntries(finalCommunityName, finalKey);
                                Log.d("CommunitiesActivity", "snapshot did not exist, creating list entries...");
                            }

                            else Toast.makeText(CommunitiesActivity.this, "Already apart of this community.", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                else {
                    Log.d("CommunitiesActivity", "Snapshot did not exist!");
                    Toast.makeText(CommunitiesActivity.this, "Community with this code was not found.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        createNewCommunity(community, zipCode);
    }

    private void createNewCommunity(final String communityName, String zipCode) {

        final String communityID = communityDB.push().getKey();

        HashMap<String, String> memberList = new HashMap<>();
        memberList.put(userID, username);
        Captain captain = new Captain(userID, username);
        String passCode = createPassCode();

        Log.d("CommunitiesActivity", "createNewCommunity: passCode is " + passCode);
        Community newCom = new Community(communityName, Integer.parseInt(zipCode), memberList, captain, passCode);

        communityDB.child(communityID).setValue(newCom).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    //add community to user's community list on Firebase; set as selectedCommunity
                    userDBEntry.child("communityList").child(communityID).setValue(communityName);
                    userDBEntry.child("selectedCommunity").child("cID").setValue(communityID);
                    userDBEntry.child("selectedCommunity").child("name").setValue(communityName).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                else Toast.makeText(CommunitiesActivity.this, "Failed to create community.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String createPassCode() {
        String passCode = "";
        Random r = new Random();
        for (int i = 0; i < 12; i++){
            passCode = passCode + alphabet.charAt(r.nextInt(alphabet.length()));
        }

        return passCode;
    }

    private void createListEntries(final String communityName, String key){

        communityDB.child(key).child("memberList").child(userID).setValue(username);
        Log.d("CommunitiesActivity", "key value = " + key + ", community name = " + communityName);
        userDBEntry.child("communityList").child(key).setValue(communityName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CommunitiesActivity.this, "Welcome to " + communityName + "! Tap this community in your community list to begin.", Toast.LENGTH_LONG).show();
                }

                else Toast.makeText(CommunitiesActivity.this, "Failed to add community to user list.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addNewMember() {

        String member = newMemberEmailText.getText().toString().trim();

        if(member.isEmpty()){
            newMemberEmailText.setError("Please enter your new member's name.");
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(member).matches()){
            newMemberEmailText.setError("Please enter a valid email.");
            return;
        }

    }

    private void copyInviteCode(){

        final String cID = currentCommunityEntry.cID;

        if(cID.equals("0")){
            Toast.makeText(CommunitiesActivity.this, "You are not yet in a community (or have somehow unselected your current community).", Toast.LENGTH_LONG).show();
            return;
        }

        communityDB.child(cID).child("captain").child("uID").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()){

                    String captainID = task.getResult().getValue(String.class);

                    if(captainID == null){
                        Toast.makeText(CommunitiesActivity.this, "Retrieved null value.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(userID.equals(captainID)){

                        communityDB.child(cID).child("passCode").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {

                                if (task.isSuccessful()){

                                    //copy to user's clipboard
                                    Toast.makeText(CommunitiesActivity.this, "PassCode copied to clipboard.", Toast.LENGTH_LONG).show();
                                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("PassCode", task.getResult().getValue(String.class));
                                    clipboard.setPrimaryClip(clip);
                                }

                                else Toast.makeText(CommunitiesActivity.this, "Failed to retrieve passCode.", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    else {

                        Toast.makeText(CommunitiesActivity.this, "You must be the captain of this community to get the passCode.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                else Toast.makeText(CommunitiesActivity.this, "Failed to retrieve community captain ID.", Toast.LENGTH_LONG).show();
            }
        });
    }

    //helper method for making inner listview work properly, since it technically should be inside a scroll view
    public static void setListViewHeightBasedOnChildren(ListView listView) {
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