package com.example.team7birdsofafeather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team7birdsofafeather.models.IStudent;
import com.example.team7birdsofafeather.models.db.AppDatabase;
import com.example.team7birdsofafeather.models.db.Course;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BoFDetailActivity extends AppCompatActivity {
    private AppDatabase db;
    private IStudent student;

    private RecyclerView coursesRecyclerView;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private CoursesViewAdapter coursesViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bofdetail);

        //TextView studentNotesView = findViewById(R.id.student_detail_notes);
        TextView studentNameView = findViewById(R.id.tvStudentName);

        Intent intent = getIntent();
        int personId = intent.getIntExtra("student_id", 0);

        db = AppDatabase.singleton(this);
        student = db.studentWithCoursesDao().get(personId);
        List<Course> courses = db.courseDao().getForPerson(personId);

        Set<String> userCourses = getUserCourses();
        List<Course> matched = new ArrayList<>();

        for (int i = 0; i < courses.size(); i++) {
            if (userCourses.contains(courses.get(i).toString())) {
                matched.add(courses.get(i));
            }
        }


        //Sets name in text view
        studentNameView.setText(student.getName());

        //Set up recycler view to show our database contents
        coursesRecyclerView = findViewById(R.id.courses_view);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);

        coursesViewAdapter = new CoursesViewAdapter(matched);
        coursesRecyclerView.setAdapter(coursesViewAdapter);

        //studentNotesView.setText(String.join("\n", student.getCourses()));
        //String studentName = intent.getStringExtra("student_name");
        //String[] studentCourses = intent.getStringArrayExtra("student_courses");

        //studentNameView.setText(studentName);

        //setTitle(studentName);
        //studentNotesView.setText(String.join("\n", studentCourses));
    }

    public void onGoBackClicked(View view) {
        finish();
    }

    public Set<String> getUserCourses() {
        SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        Map<String, ?> allEntries = preferences.getAll();
        Set<String> userCourses = new HashSet<>();

        for (int i = 0; i < Integer.parseInt(preferences.getString("numCourses", "0")); i++) {
            userCourses.add(allEntries.get(String.valueOf(i)).toString());
        }
        return userCourses;
    }
}