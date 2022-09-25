package com.example.team7birdsofafeather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EnterPastCoursesActivity extends AppCompatActivity {
    private int numCourses = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_past_courses);

        Spinner spinner = (Spinner) findViewById(R.id.quarterSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quarters_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void addCoursesToSharedPreferences(View view) {
        SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        EditText yearEditText = findViewById(R.id.yearEditText);
        Spinner quarterEditText = findViewById(R.id.quarterSpinner);
        EditText courseEditText = findViewById(R.id.courseEditText);

        if (courseEditText.getText().toString().equals("") || yearEditText.getText().toString().equals("")) {
            Toast.makeText(this, "Please enter a valid course", Toast.LENGTH_SHORT).show();
            return;
        }

        String courseString = courseEditText.getText().toString() + " - " +
                quarterEditText.getSelectedItem().toString() + " " +
                yearEditText.getText().toString();

        yearEditText.getText().clear();
        courseEditText.getText().clear();

        editor.putString(String.valueOf(numCourses), courseString);
        editor.apply();
        numCourses++;

        Toast.makeText(this, "Course Added!", Toast.LENGTH_SHORT).show();
    }


    public void whenFinished(View view) {
        SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("numCourses", String.valueOf(numCourses));
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), ConfirmNameProfileActivity.class);
        startActivity(intent);
        finish();
    }

    //public static int getNumCourses(){return numCourses;}
}