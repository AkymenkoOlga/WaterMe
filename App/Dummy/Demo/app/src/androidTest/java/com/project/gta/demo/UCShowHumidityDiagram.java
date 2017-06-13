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
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UCShowHumidityDiagram {

    @Rule
    public ActivityTestRule<MainMenu> mActivityTestRule = new ActivityTestRule<>(MainMenu.class);

    @Test
    public void uCShowHumidityDiagram() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.BTNmyplants), withText("My plants"),
                        withParent(allOf(withId(R.id.mainmenu),
                                withParent(withId(R.id.activity_main_menu)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.changescale), withContentDescription("Change scale"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction editText = onView(
                allOf(withClassName(is("android.widget.EditText")),
                        withParent(allOf(withId(R.id.custom),
                                withParent(withId(R.id.customPanel)))),
                        isDisplayed()));
        editText.perform(click());

        ViewInteraction editText2 = onView(
                allOf(withClassName(is("android.widget.EditText")),
                        withParent(allOf(withId(R.id.custom),
                                withParent(withId(R.id.customPanel)))),
                        isDisplayed()));
        editText2.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        withParent(allOf(withId(R.id.buttonPanel),
                                withParent(withId(R.id.parentPanel)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.image1),
                        withParent(allOf(withId(R.id.LayoutPlants1),
                                withParent(withId(R.id.activity_single_plant_menu)))),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.BTNgetHumidity), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.BTNgraph), withText("View Graph"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.BTNrefresh), withText("Refresh"),
                        withParent(allOf(withId(R.id.activity_humidity_diagram),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Nach oben"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.BTNgraph), withText("View Graph"), isDisplayed()));
        appCompatButton6.perform(click());

    }

}
