package com.example.team7birdsofafeather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static org.junit.Assert.*;

import com.example.team7birdsofafeather.models.IStudent;
import com.example.team7birdsofafeather.models.db.Course;
import com.example.team7birdsofafeather.models.db.Student;
import com.example.team7birdsofafeather.models.db.StudentWithCourses;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowActivity;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(AndroidJUnit4.class)
public class MainActivityRobolectricTests {
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public ActivityScenarioRule<EnterPastCoursesActivity> enterPastCoursesActivityScenarioRule = new ActivityScenarioRule<EnterPastCoursesActivity>(EnterPastCoursesActivity.class);

    @Test
    public void checkBluetoothStartAndStopText() {
        ActivityScenario<MainActivity> scenario = mainActivityScenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button bluetoothStartStopButton = activity.findViewById(R.id.bluetooth_button);

            assertEquals("START", bluetoothStartStopButton.getText().toString().toUpperCase());
            bluetoothStartStopButton.performClick();
            assertEquals("STOP", bluetoothStartStopButton.getText().toString().toUpperCase());
            bluetoothStartStopButton.performClick();
            assertEquals("START", bluetoothStartStopButton.getText().toString().toUpperCase());
        });
    }

    @Test
    public void testRandomStudentMatched(){
        ActivityScenario<MainActivity> mainActivityScenario = mainActivityScenarioRule.getScenario();
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = preferences.edit();

        mainActivityScenario.moveToState((Lifecycle.State.CREATED));
        mainActivityScenario.onActivity(activity -> {
            editor.putString("0", "CSE110 - Winter 2022");
            editor.putString("1", "DSC10 - Special Summer Session 2021");
            editor.putString("numCourses", "2");
            editor.apply();

            String numCourses = preferences.getString("numCourses", "ERROR: NOT FOUND");
            assertEquals("2", numCourses);
            String course1 = preferences.getString("0", "ERROR: NOT FOUND");
            assertEquals("CSE110 - Winter 2022", course1);

            Student s1 = new Student(0, "John", "");
            Student s2 = new Student(1, "Billy", "");
            Student s3 = new Student(2, "George", "");

            Course c1 = new Course(0, 0, "CSE110", "Winter", 2022);
            Course c2 = new Course(1,1,"ECON1", "Fall", 2021);
            Course c3 = new Course(2, 2, "DSC10", "Special Summer Session", 2021);
            Course c4 = new Course(3, 2, "CSE110", "Winter", 2022);

            Set<String> userCourses = activity.getUserCourses();
            activity.update(s2, new String[] {c2.toString()});
            assertEquals(0, activity.db.studentWithCoursesDao().getAll().size());
            activity.update(s1, new String[] {c1.toString()});
            assertEquals(1, activity.db.studentWithCoursesDao().getAll().size());
            activity.update(s3, new String[] {c3.toString(), c4.toString()});
            assertEquals(2, activity.db.studentWithCoursesDao().getAll().size());


            Button refreshButton = activity.findViewById(R.id.buttonRefresh);
            refreshButton.performClick();

            List<IStudent> studentList =  activity.bofListViewAdapter.getStudents();
            assertEquals(2, studentList.size());


            IStudent s = studentList.get(0);

            assertEquals("George", s.getName());

            s = studentList.get(1);
            assertEquals("John", s.getName());

            Set<IStudent> studentListSet = new HashSet<>(studentList);
            Set<String> studentListNamesSet = new HashSet<>();
            for (IStudent si : studentListSet) {
                studentListNamesSet.add(si.getName());
            }

            assertFalse(studentListNamesSet.contains("Billy"));
        });


    }




}
