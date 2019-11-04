package com.rrajasek.firebasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    private Spinner uidSpinner;
    private EditText updateNameText;
    private Button updateButton;
    private Button deleteButton;

    private Map<String, String> nameMap;

    private String selectedUid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        db = FirebaseFirestore.getInstance();

        uidSpinner = findViewById(R.id.uid_spinner);
        updateNameText = findViewById(R.id.update_name_field);
        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.delete_button);

        updateButton.setOnClickListener(view -> {
            String name = updateNameText.getText().toString();
            db.collection(ProfileContract.COLLECTION_NAME).document(selectedUid)
                    .update("name", name).addOnSuccessListener(aVoid -> finish());
        });

        deleteButton.setOnClickListener(view -> {
            db.collection(ProfileContract.COLLECTION_NAME).document(selectedUid)
                    .delete().addOnSuccessListener(aVoid -> finish());
        });
    }

    public void populateSpinner(ArrayList<String> uidList) {
        ArrayAdapter<String> uidAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, uidList);
        uidAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        uidSpinner.setAdapter(uidAdapter);

        uidSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String uid = (String) adapterView.getItemAtPosition(i);
                String name = nameMap.getOrDefault(uid, "");
                updateNameText.setText(name);
                selectedUid = uid;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                String uid = (String) adapterView.getItemAtPosition(0);
                String name = nameMap.getOrDefault(uid, "");
                updateNameText.setText(name);
                selectedUid = uid;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        db.collection(ProfileContract.COLLECTION_NAME)
//                .whereEqualTo("position","GS")
                .get().addOnSuccessListener((queryDocumentSnapshots -> {
            ArrayList<String> uidList = new ArrayList<>();
            nameMap = new HashMap<>();

            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                String name = doc.getString(ProfileContract.FIELD_NAME);
                String uid = doc.getId();

                uidList.add(uid);

                nameMap.put(uid, name);
            }
            populateSpinner(uidList);
        }));
    }
}
