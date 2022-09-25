package com.example.team7birdsofafeather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class ConfirmNameProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_name_profile);
        loadProfile();
    }

    @Override
    protected void onDestroy() {
        //saveProfile();
        super.onDestroy();
    }

    public void loadProfile() {
        SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        String name_string = preferences.getString("name", "");
        EditText nameView = findViewById(R.id.editFirstName);
        nameView.setText(name_string);
    }

    public boolean saveProfile() {
        SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        EditText nameView = findViewById(R.id.editFirstName);
        String nameString = nameView.getText().toString();
        if (nameString.equals("") || nameString.equals(" ")) {
            Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_SHORT).show();
            return false;
        }

        editor.putString("name", cleanName(nameView.getText().toString()));
        editor.apply();

        Map<String, ?> allEntries = preferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }
        Toast.makeText(this, "Name set!", Toast.LENGTH_SHORT).show();
        return true;
    }

    public void onLaunchProfileClicked(View view) {
        boolean saved = saveProfile();
        if (saved) {
            finish();
        } else return;
    }

    private String cleanName(String name) {
        name = name.toLowerCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}