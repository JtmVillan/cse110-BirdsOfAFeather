package com.example.team7birdsofafeather;


import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

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
public class ConfirmNameProfileActivityUnitTest {

    @Rule
    public ActivityScenarioRule<ConfirmNameProfileActivity> scenarioRule = new ActivityScenarioRule<>(ConfirmNameProfileActivity.class);

    @Test
    public void test_user_enters_name() {
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();
        editor.apply();

        ActivityScenario<ConfirmNameProfileActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            EditText testName = activity.findViewById(R.id.editFirstName);
            Button confirm_btn = activity.findViewById(R.id.confirm_btn);
            testName.setText("teST");
            confirm_btn.performClick();
        });

        scenario.moveToState(Lifecycle.State.DESTROYED);

        Map<String, ?> allEntries = preferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }

        String firstName = preferences.getString("name", "ERROR: NOT FOUND");
        assertEquals("Test", firstName);
    }
}
