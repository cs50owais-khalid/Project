package com.example.hrengaging;

import static java.nio.charset.StandardCharsets.UTF_8;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EditProfile extends AppCompatActivity {

    EditText fullNameEditText, emailEditText, passwordEditText;
    private Button saveChangesButton;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fullNameEditText = findViewById(R.id.profilename);
        emailEditText = findViewById(R.id.profileemail);
        passwordEditText = findViewById(R.id.profilepassword);
        saveChangesButton = findViewById(R.id.btnsave);

        firebaseAuth = FirebaseAuth.getInstance();
        userReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());

        fullNameEditText.setText(GlobalVar.currentUser.getFullName());
        // Load country names

        String[] countryNamesArray = loadCountryNames();
        // Set up the country spinner
        Spinner countrySpinner = findViewById(R.id.profilecountrySpinner);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryNamesArray);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);



        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    private void saveChanges() {
        String fullName = fullNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        // Password should not be retrieved here

        // Rest of your saveChanges method remains unchanged
    }

    private String[] loadCountryNames() {
        List<String> countryNames = new ArrayList<>();

        try {
            InputStream inputStream = getAssets().open("countries.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                json = new String(buffer, StandardCharsets.UTF_8);
            }

            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObject = jsonArray.getJSONObject(i);
                JSONObject nameObject = countryObject.getJSONObject("name");
                String commonName = nameObject.getString("common");
                countryNames.add(commonName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return countryNames.toArray(new String[0]);
    }
}
