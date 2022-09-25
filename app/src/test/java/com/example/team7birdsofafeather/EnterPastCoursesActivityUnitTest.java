package com.example.team7birdsofafeather;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class EnterPastCoursesActivityUnitTest {

    @Rule
    public ActivityScenarioRule<EnterPastCoursesActivity> scenarioRule = new ActivityScenarioRule<>(EnterPastCoursesActivity.class);

    @Test
    public void test_user_enters_name() {
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();
        editor.apply();

        ActivityScenario<EnterPastCoursesActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            EditText yearEditText = activity.findViewById(R.id.yearEditText);
            EditText courseEditText = activity.findViewById(R.id.courseEditText);
            Spinner quarterEditText = activity.findViewById(R.id.quarterSpinner);
            Button enterCourseButton = activity.findViewById(R.id.enterCourseButton);
            Button nextScreenButton = activity.findViewById(R.id.nextScreenButton);

            yearEditText.setText("2022");
            quarterEditText.setSelection(1);
            courseEditText.setText("CSE101");
            enterCourseButton.performClick();
            yearEditText.setText("2022");
            quarterEditText.setSelection(1);
            courseEditText.setText("CSE110");
            enterCourseButton.performClick();
            courseEditText.setText("CSE105");
            enterCourseButton.performClick();

            nextScreenButton.performClick();
        });

        Map<String, ?> allEntries = preferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }

        String numCourses = preferences.getString("numCourses", "ERROR: NOT FOUND");
        assertEquals("2", numCourses);
        String course1 = preferences.getString("0", "ERROR: NOT FOUND");
        assertEquals("CSE101 - Winter 2022", course1);
        String course2 = preferences.getString("1", "ERROR: NOT FOUND");
        assertEquals("CSE110 - Winter 2022", course2);
        String course3 = preferences.getString("2", "ERROR: NOT FOUND");
        assertEquals("ERROR: NOT FOUND", course3);
    }
}
