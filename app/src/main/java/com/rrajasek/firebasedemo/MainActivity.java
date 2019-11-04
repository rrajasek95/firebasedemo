package com.rrajasek.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button addProfileButton;
    private Button viewRandomProfileButton;
    private Button updateDeleteProfileButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addProfileButton = findViewById(R.id.add_profile_button);
        viewRandomProfileButton = findViewById(R.id.view_random_profile_button);
        updateDeleteProfileButton = findViewById(R.id.update_delete_profile_button);

        addProfileButton.setOnClickListener((view) -> {
            Intent intent = new Intent(getApplicationContext(), AddProfileActivity.class);
            startActivity(intent);
        });

        viewRandomProfileButton.setOnClickListener((view -> {
            Intent intent = new Intent(getApplicationContext(), ViewRandomProfileActivity.class);
            startActivity(intent);
        }));

        updateDeleteProfileButton.setOnClickListener((view -> {
            Intent intent = new Intent(getApplicationContext(), UpdateProfileActivity.class);
            startActivity(intent);
        }));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
