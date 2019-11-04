package com.rrajasek.firebasedemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ViewRandomProfileActivity extends AppCompatActivity {

    private static final String TAG = ViewRandomProfileActivity.class.getSimpleName();
    private FirebaseFirestore db;
    private TextView randomCruzIdText;
    private TextView randomNameText;
    private TextView randomYearText;
    private TextView randomPositionText;
    private TextView randomCoursesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_random_profile);
        db = FirebaseFirestore.getInstance();
        randomCruzIdText = findViewById(R.id.random_cruzid_text);
        randomNameText = findViewById(R.id.random_name_text);
        randomYearText = findViewById(R.id.random_year_text);
        randomPositionText = findViewById(R.id.random_position_text);
        randomCoursesText = findViewById(R.id.random_courses_text);

    }

    @Override
    protected void onResume() {
        super.onResume();
        populateRandomProfile();
    }

    private void populateRandomProfile() {
        db.collection(ProfileContract.COLLECTION_NAME).get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Profile> profiles = new ArrayList<>();
            for (DocumentSnapshot doc: queryDocumentSnapshots) {
                String name = doc.getString(ProfileContract.FIELD_NAME);
                String cruzId = doc.getString(ProfileContract.FIELD_CRUZID);
                Long yearJoined = doc.getLong(ProfileContract.FIELD_YEAR_JOINED);
                String position = doc.getString(ProfileContract.FIELD_POSITION);
                List<String> courses = (List<String>) doc.get(ProfileContract.FIELD_COURSES_TAKEN);

                profiles.add(new Profile(cruzId, name, position, yearJoined, courses));
                Log.d(TAG, name);
            }

            Random random = new Random();
            Profile randomProfile = profiles.get(random.nextInt(profiles.size()));

            randomCruzIdText.setText(randomProfile.getCruzId());
            randomNameText.setText(randomProfile.getName());
            randomYearText.setText(String.valueOf(randomProfile.getYearJoined()));
            randomPositionText.setText(randomProfile.getPosition());
            randomCoursesText.setText(randomProfile.getCoursesTaken().toString());

        });
    }
}
