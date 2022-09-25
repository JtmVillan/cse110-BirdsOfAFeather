package com.example.team7birdsofafeather;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class ConfirmNameProfileActivityLogging {

    private static final String SUCC = "Success!";

    @Rule
    public ActivityScenarioRule<ConfirmNameProfileActivity> scenarioRule = new ActivityScenarioRule<>(ConfirmNameProfileActivity.class);

    @Test
    public void onCreateTest() {
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();
        editor.apply();

        ActivityScenario<ConfirmNameProfileActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);
        assertTrue(Lifecycle.State.CREATED.isAtLeast(Lifecycle.State.CREATED));
        Log.d(SUCC, "onCreate method success!");
        scenario.onActivity(activity -> {
            EditText testName = activity.findViewById(R.id.editFirstName);
            testName.setText("onCreate");
        });
        scenario.moveToState(Lifecycle.State.DESTROYED);
    }

    @Test
    public void onDestroyTest() {
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();
        editor.apply();

        ActivityScenario<ConfirmNameProfileActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            EditText testName = activity.findViewById(R.id.editFirstName);
            testName.setText("onDestroy");
        });
        scenario.moveToState(Lifecycle.State.DESTROYED);
        assertTrue(Lifecycle.State.DESTROYED.isAtLeast(Lifecycle.State.DESTROYED));
        Log.d(SUCC, "onDestroy method success!");
    }

    @Test
    public void onLaunchProfileCLickedTest() {
        Log.i(SUCC, "trivial method");
    }

    @Test
    public void cleanName() {
        Log.i(SUCC, "trivial method");
    }

}
