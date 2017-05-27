package com.project.gta.demo;


import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UCConnectApptoRaspberryPiviaBluetooth {

    @Rule
    public ActivityTestRule<MainMenu> mActivityTestRule = new ActivityTestRule<>(MainMenu.class);

    @Test
    public void uCConnectApptoRaspberryPiviaBluetooth() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.BTNsettings), withText("Settings"),
                        withParent(allOf(withId(R.id.mainmenu),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.BTNbluetooth), withText("Bluetooth"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.SWbluetooth), isDisplayed()));
        switch_.perform(click());


        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.BTNconnect_bt), withText("connect"), isDisplayed()));
        appCompatButton4.perform(click());
        SystemClock.sleep(9000);
    }

}
