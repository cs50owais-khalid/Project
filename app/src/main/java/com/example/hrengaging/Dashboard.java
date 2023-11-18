package com.example.hrengaging;



import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class Dashboard extends AppCompatActivity {

    private CircleImageView profileImageView;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    //    private FirebaseDatabase database;
//    private DatabaseReference userRef;
    private FirebaseUser user;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    private TextView username;

    private String fullName;
    ImageView backbtn;
    private static final String USERS ="users";
    String email;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        username = findViewById(R.id.userName);
        backbtn = findViewById(R.id.arrow);


        //retrieve data from firestore
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();

        if (user==null){
            Toast.makeText(Dashboard.this,"Something went wrong!!!",Toast.LENGTH_SHORT).show();
        }
        else{
            showUserProfile(user);
        }

//       backbtn
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showUserProfile(FirebaseUser user) {
        String userID = user.getUid();

        // Extract the data
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                helperClass helperClass = snapshot.getValue(com.example.hrengaging.helperClass.class);
                if (helperClass != null) {
                    // Update the fullName variable with the name from the database
                    fullName = helperClass.getFullname();

                    // Update the TextView with the retrieved name
                    username.setText(fullName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Dashboard", "Database Error: " + error.getMessage());
                Toast.makeText(Dashboard.this, "Something went wrong!!! Sorry.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

