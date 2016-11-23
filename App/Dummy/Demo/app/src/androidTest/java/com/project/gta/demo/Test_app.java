package com.project.gta.demo;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Test_app {

    @Rule
    public ActivityTestRule<MainMenu> mActivityTestRule = new ActivityTestRule<>(MainMenu.class);

    @Test
    public void testdatbitch() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.myplants), withText("My plants"),
                        withParent(allOf(withId(R.id.hauptmenue),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction tableRow = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.list),
                                4),
                        0),
                        isDisplayed()));
        tableRow.check(matches(isDisplayed()));

        ViewInteraction tableLayout = onView(
                allOf(childAtPosition(
                        withId(R.id.list),
                        1),
                        isDisplayed()));
        tableLayout.perform(click());

        pressBack();

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.settings), withText("Settings"),
                        withParent(allOf(withId(R.id.hauptmenue),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.switch4), isDisplayed()));
        switch_.perform(click());

        ViewInteraction switch_2 = onView(
                allOf(withId(R.id.switch6), isDisplayed()));
        switch_2.perform(click());

        ViewInteraction switch_3 = onView(
                allOf(withId(R.id.SWled), isDisplayed()));
        switch_3.perform(click());

        ViewInteraction switch_4 = onView(
                allOf(withId(R.id.switch4), isDisplayed()));
        switch_4.perform(click());

        ViewInteraction switch_5 = onView(
                allOf(withId(R.id.switch6), isDisplayed()));
        switch_5.perform(click());

        ViewInteraction switch_6 = onView(
                allOf(withId(R.id.SWled), isDisplayed()));
        switch_6.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.raspberrywifi), withText("Raspberry WiFi"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.wifi_id), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.wifi_id), isDisplayed()));
        appCompatEditText2.perform(replaceText("Simsalabim der geile Zauberer"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password), withContentDescription("Password"), isDisplayed()));
        appCompatEditText3.perform(replaceText("pop dat pssy"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.submit), withText("Submit"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.bluetooth), withText("Bluetooth"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction switch_7 = onView(
                allOf(withId(R.id.bluetoothsw), isDisplayed()));
        switch_7.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.listpaireddevices), withText("Connect to paired device"), isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Bose AE2 SoundLink\nAUDIO_VIDEO"),
                        childAtPosition(
                                allOf(withId(android.R.id.list),
                                        withParent(withId(android.R.id.content))),
                                1),
                        isDisplayed()));
        textView.perform(click());

        ViewInteraction switch_8 = onView(
                allOf(withId(R.id.bluetoothsw), isDisplayed()));
        switch_8.perform(click());

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

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.help), withText("Help"),
                        withParent(allOf(withId(R.id.hauptmenue),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.about), withText("About"),
                        withParent(allOf(withId(R.id.hauptmenue),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
