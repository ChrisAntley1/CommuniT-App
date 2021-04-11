package com.example.emergencyapp.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.emergencyapp.MainActivity;
import com.example.emergencyapp.R;
import com.example.emergencyapp.UserAddress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddressActivity extends AppCompatActivity {

    private EditText editStreet, editCity, editState, editZipCode;
    private Button submitAddress;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference userDB;
    private static final String TAG = "AddressActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        editStreet = findViewById(R.id.activity_address_street_edit);
        editCity = findViewById(R.id.activity_address_city_edit);
        editState = findViewById(R.id.activity_address_state_edit);
        editZipCode = findViewById(R.id.activity_address_zipcode_edit);
        submitAddress = findViewById(R.id.activity_address_submit_button);
        progressBar = findViewById(R.id.activity_address_progressbar);


        mAuth = FirebaseAuth.getInstance();
        userDB = FirebaseDatabase.getInstance().getReference("Users");


        submitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAddress();
            }
        });
    }

    private void submitAddress() {

        String street = editStreet.getText().toString().trim();
        String city = editCity.getText().toString().trim();
        String state = editState.getText().toString().trim();
        String zipCode = editZipCode.getText().toString().trim();

        if(street.isEmpty() && city.isEmpty() && state.isEmpty() && zipCode.isEmpty()) {
            street = ((int) (Math.random() * 1000)) + " Peachtree Street";
            city = "Atlanta";
            state = "Georgia";
            zipCode = "30303";
            Log.d(TAG, "submitAddress: all empty");
        }

        else {
            if(street.isEmpty()){
                editStreet.setError("Street address required.");
                editStreet.requestFocus();
                return;

            }
            if(city.isEmpty()){
                editCity.setError("City required.");
                editCity.requestFocus();
                return;

            }
            if(state.isEmpty()){
                editState.setError("State required.");
                editState.requestFocus();
                return;

            }
            if(zipCode.isEmpty()){
                editZipCode.setError("Zip Code required.");
                editZipCode.requestFocus();
                return;

            }
        }

        UserAddress address = new UserAddress(street, city, state, Integer.parseInt(zipCode));
        progressBar.setVisibility(View.VISIBLE);


        userDB.child(mAuth.getCurrentUser().getUid()).child("address").setValue(address).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Log.d(TAG, "onComplete: successfully added user's address to database.");
                    Toast.makeText(AddressActivity.this, "Successfully submitted address!", Toast.LENGTH_LONG).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            startActivity(new Intent(AddressActivity.this, MainActivity.class));
                        }
                    }, 2000);

                }

                else {
                    Log.d(TAG, "onComplete: failed to add user's address to database.");
                    Toast.makeText(AddressActivity.this, "Something went wrong, please try again.", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}