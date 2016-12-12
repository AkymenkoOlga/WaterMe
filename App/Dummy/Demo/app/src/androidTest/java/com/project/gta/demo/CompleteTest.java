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
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CompleteTest {

    @Rule
    public ActivityTestRule<MainMenu> mActivityTestRule = new ActivityTestRule<>(MainMenu.class);

    @Test
    public void completeTest() {
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

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.BTNreload), withText("Reload"),
                        withParent(withId(R.id.LLbluetooth)),
                        isDisplayed()));
        appCompatButton3.perform(click());


        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.BTNconnect_bt), withText("connect"), isDisplayed()));
        appCompatButton5.perform(click());
        SystemClock.sleep(9000);

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction switch_2 = onView(
                allOf(withId(R.id.SWnotifications), isDisplayed()));
        switch_2.perform(click());

        ViewInteraction switch_3 = onView(
                allOf(withId(R.id.SWnotifications), isDisplayed()));
        switch_3.perform(click());

        ViewInteraction switch_4 = onView(
                allOf(withId(R.id.SWsounds), isDisplayed()));
        switch_4.perform(click());

        ViewInteraction switch_5 = onView(
                allOf(withId(R.id.SWsounds), isDisplayed()));
        switch_5.perform(click());

        ViewInteraction switch_6 = onView(
                allOf(withId(R.id.SWled), isDisplayed()));
        switch_6.perform(click());
        SystemClock.sleep(2000);

        ViewInteraction switch_7 = onView(
                allOf(withId(R.id.SWled), isDisplayed()));
        switch_7.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.BTNraspberrywifi), withText("Raspberry WiFi"), isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.TXTwifi_id), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.TXTwifi_id), isDisplayed()));
        appCompatEditText2.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.TXTwifi_password), withContentDescription("Password"), isDisplayed()));
        appCompatEditText3.perform(replaceText("password"), closeSoftKeyboard());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.BTNsubmit), withText("Submit"), isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.BTNhelp), withText("Help"),
                        withParent(allOf(withId(R.id.mainmenu),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.BTNhelpBluetoothPi), withText("Enable Bluetooth and connect Pi"), isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.BTNhelp), withText("Help"),
                        withParent(allOf(withId(R.id.mainmenu),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.BTNhelpInstallPi), withText("Install Pi"), isDisplayed()));
        appCompatButton11.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.BTNhelp), withText("Help"),
                        withParent(allOf(withId(R.id.mainmenu),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton12.perform(click());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.BTNhelpAddPlant), withText("Add plant"), isDisplayed()));
        appCompatButton13.perform(click());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(R.id.BTNabout), withText("About"),
                        withParent(allOf(withId(R.id.mainmenu),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton14.perform(click());

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.BTNmyplants), withText("My plants"),
                        withParent(allOf(withId(R.id.mainmenu),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton15.perform(click());

    }

}
