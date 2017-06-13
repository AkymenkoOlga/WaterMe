package com.project.gta.demo;


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
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UCVisithelpMenu {

    @Rule
    public ActivityTestRule<MainMenu> mActivityTestRule = new ActivityTestRule<>(MainMenu.class);

    @Test
    public void uCVisithelpMenu() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.BTNhelp), withText("Help"),
                        withParent(allOf(withId(R.id.mainmenu),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.BTNhelpBluetoothPi), withText("Enable Bluetooth and connect Pi"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.BTNhelp), withText("Help"),
                        withParent(allOf(withId(R.id.mainmenu),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.BTNhelpInstallPi), withText("Install Pi"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.BTNhelp), withText("Help"),
                        withParent(allOf(withId(R.id.mainmenu),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.BTNhelpAddPlant), withText("Add plant"), isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.BTNhelp), withText("Help"),
                        withParent(allOf(withId(R.id.mainmenu),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.BTNhelpAskus), withText("Ask Us"),
                        withParent(allOf(withId(R.id.activity_help_menu),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

    }

}
