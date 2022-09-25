package com.example.team7birdsofafeather;

import android.util.Log;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EnterPastCoursesActivityLogging {
    private static final String SUCC = "Success!";
    @Rule
    public ActivityScenarioRule<EnterPastCoursesActivity> scenarioRule = new ActivityScenarioRule<>(EnterPastCoursesActivity.class);

    @Test
    public void onCreate() {
        Log.i(SUCC, "trivial method");
    }
}
