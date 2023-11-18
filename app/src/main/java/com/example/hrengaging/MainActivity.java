package com.example.hrengaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telecom.Call;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.Response;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    MenuItem menuItem;

    private NavigationView navigationView;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



;
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        ImageView editProfileImageView = findViewById(R.id.editprofile);


        editProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the EditProfile activity
                Intent editProfileIntent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(editProfileIntent);
            }
        });



        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String fullName = userProfile.fullName;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

// this drawer layout is not working
//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.nav_view);
//        MenuItem profileSettingsMenuItem = navigationView.getMenu().findItem(R.id.profilesetting);
//        View profileSettingsActionView = profileSettingsMenuItem.getActionView();
//        profileSettingsActionView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Open the EditProfile activity
//                Intent editProfileIntent = new Intent(MainActivity.this, EditProfile.class);
//                startActivity(editProfileIntent);
//
//                // Close the drawer if needed
//                drawerLayout.closeDrawer(GravityCompat.START);
//            }
//        });





//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                if (id == R.id.nav_logout) {
//                    // Handle the logout action here
//                    signOutUser(); // Replace with your logout logic
//                    return true; // Return true to indicate that the item click has been handled
//                }
//                return false;
//            }
//        });




    }






    // Placeholder function for signing out the user
    private void signOutUser() {
        // Implement your user sign-out logic here (e.g., Firebase sign out)
        // After signing out, navigate to the Login Activity
//        Intent loginIntent = new Intent(MainActivity.this, Login.class);
//        startActivity(loginIntent);
//        finish(); // Finish the current activity


        Log.d("SignOut", "Sign-out button clicked");

        // Implement your user sign-out logic here (e.g., Firebase sign out)
        FirebaseAuth.getInstance().signOut();

        // After signing out, navigate to the Login Activity
        Intent loginIntent = new Intent(MainActivity.this, Login.class);
        startActivity(loginIntent);
        finish(); // Finish the current activity
    }





    public void openSidebar(View view) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


}