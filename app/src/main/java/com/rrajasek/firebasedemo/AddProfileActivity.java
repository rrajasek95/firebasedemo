package com.rrajasek.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddProfileActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    private EditText cruzIdText;
    private EditText nameText;
    private EditText yearJoinedText;
    private Spinner positionSpinner;
    private EditText coursesText;

    private Button saveProfileButton;
    private Map<String, String> positionCode = new HashMap<>();

    private String spinnerItem = "";

    private void populatePositionSpinner(Spinner positionSpinner) {
        positionCode.put("Graduate", "GS");
        positionCode.put("Undergraduate", "US");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.position_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        positionSpinner.setAdapter(adapter);

        positionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerItem = positionCode.getOrDefault(adapterView.getItemAtPosition(i), "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinnerItem = "";
            }
        });
    }

    private boolean inputValid(String cruzId, String name, Integer yearJoined, String position, String courses) {
        return !cruzId.isEmpty() && !name.isEmpty() && yearJoined > 0 && !position.isEmpty() && !courses.isEmpty();
    }

    private String readS(EditText v) {
        return v.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        db = FirebaseFirestore.getInstance();
        cruzIdText = findViewById(R.id.cruzid_input);
        nameText = findViewById(R.id.name_input);
        yearJoinedText = findViewById(R.id.year_input);
        positionSpinner = findViewById(R.id.position_input);
        coursesText = findViewById(R.id.courses_input);
        saveProfileButton = findViewById(R.id.add_profile_save_button);

        populatePositionSpinner(positionSpinner);
        saveProfileButton.setOnClickListener((view) -> {
            String cruzId = readS(cruzIdText);
            String name = readS(nameText);
            Integer yearJoined = Integer.parseInt(readS(yearJoinedText));
            String position = spinnerItem;
            String courses = readS(coursesText);

            if (inputValid(cruzId, name, yearJoined, position, courses)) {
                String[] courseArray = courses.split(",");
                Map<String, Object> profileData = new HashMap<>();
                profileData.put("cruzid", cruzId);
                profileData.put("name", name);
                profileData.put("name", name);
                profileData.put("position", position);
                profileData.put("year_joined", yearJoined);
                profileData.put("courses_taken", Arrays.asList(courseArray));

                db.collection("profiles")
                        .add(profileData).addOnSuccessListener(documentReference -> {
                            Toast.makeText(getApplicationContext(), R.string.save_profile_success, Toast.LENGTH_SHORT).show();
                            finish();
                        });
            } else {
                Toast.makeText(getApplicationContext(), R.string.save_profile_validation_error, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
