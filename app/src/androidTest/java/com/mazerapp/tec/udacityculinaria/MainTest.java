package com.mazerapp.tec.udacityculinaria;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mazerapp.tec.udacityculinaria.modules.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@RunWith(AndroidJUnit4.class)
public class MainTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkFirstRecipeInformations() {
        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.rv_ingredients))
                .check(matches(hasDescendant(withText(containsString("2 CUP")))));
        onView(withId(R.id.rv_ingredients))
                .check(matches(hasDescendant(withText(containsString("6 TBLSP")))));
        onView(withId(R.id.rv_ingredients))
                .check(matches(hasDescendant(withText(containsString("0.5 CUP")))));

        onView(withId(R.id.rv_steps))
                .check(matches(hasDescendant(withText(containsString("Intro")))));
        onView(withId(R.id.rv_steps))
                .check(matches(hasDescendant(withText(containsString("Recipe Introduction")))));
        onView(withId(R.id.rv_steps))
                .check(matches(hasDescendant(withText(containsString("Step 1")))));


        onView(withId(R.id.rv_steps))
                .check(matches(hasDescendant(withText(containsString("Starting prep")))));
        onView(withId(R.id.rv_steps))
                .check(matches(hasDescendant(withText(containsString("Step 2")))));
        onView(withId(R.id.rv_steps))
                .check(matches(hasDescendant(withText(containsString("Prep the cookie crust.")))));

    }

    @Test
    public void checkStepsFromFirstRecipe(){
        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.rv_steps))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_step_description)).check(matches(withText(is("Recipe Introduction"))));

        onView(withId(R.id.btn_next_step)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(containsString("Preheat the oven"))));
        onView(withId(R.id.iv_placeholder)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.frame_video)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.btn_previous_step)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(is("Recipe Introduction"))));
    }

    @Test
    public void checkLastRecipeInformations() {
        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(3, click()));

        onView(withId(R.id.rv_ingredients))
                .check(matches(hasDescendant(withText(containsString("2 CUP")))));
        onView(withId(R.id.rv_ingredients))
                .check(matches(hasDescendant(withText(containsString("6 TBLSP")))));
        onView(withId(R.id.rv_ingredients))
                .check(matches(hasDescendant(withText(containsString("250 G")))));

        onView(withId(R.id.rv_steps))
                .check(matches(hasDescendant(withText(containsString("Intro")))));
        onView(withId(R.id.rv_steps))
                .check(matches(hasDescendant(withText(containsString("Recipe Introduction")))));
        onView(withId(R.id.rv_steps))
                .check(matches(hasDescendant(withText(containsString("Step 1")))));


        onView(withId(R.id.rv_steps))
                .check(matches(hasDescendant(withText(containsString("Starting prep.")))));
        onView(withId(R.id.rv_steps))
                .check(matches(hasDescendant(withText(containsString("Step 2")))));
        onView(withId(R.id.rv_steps))
                .check(matches(hasDescendant(withText(containsString("Prep the cookie crust.")))));
    }

    @Test
    public void checkStepsFromLastRecipe(){
        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(3, click()));

        onView(withId(R.id.rv_steps))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.tv_step_description)).check(matches(withText(is("Recipe Introduction"))));

        onView(withId(R.id.btn_next_step)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(containsString("Preheat the oven"))));
        onView(withId(R.id.iv_placeholder)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.frame_video)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.btn_previous_step)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(is("Recipe Introduction"))));

    }





}
