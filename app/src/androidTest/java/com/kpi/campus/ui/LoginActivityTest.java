package com.kpi.campus.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.kpi.campus.R;
import com.kpi.campus.ui.activity.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.kpi.campus.custom.OrientationChangeAction.orientationLandscape;
import static com.kpi.campus.custom.OrientationChangeAction.orientationPortrait;

/**
 * Created by Administrator on 29.02.2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Rule
    ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void inputCredentials() {
        onView(withId(R.id.edit_text_input_login)).perform(typeText("mylogin"), closeSoftKeyboard());
        onView(withId(R.id.edit_text_input_password)).perform(typeText("mypassword"), closeSoftKeyboard());
    }

    @Test
    public void rotateScreen() {
        onView(isRoot()).perform(orientationLandscape());
        onView(isRoot()).perform(orientationPortrait());
    }
}
