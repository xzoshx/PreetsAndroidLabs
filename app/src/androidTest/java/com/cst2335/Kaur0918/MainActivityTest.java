package com.cst2335.Kaur0918;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(withId(R.id.passwordEditText));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.passwordTextView));
        textView.check(matches(withText("You shall not pass!")));
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

    /**
     * Test case to check if password is missing an uppercase letter.
     * Expected behavior: The checkPasswordComplexity() function should return false.
     */
    @Test
    public void testFindMissingUppercase(){//find the view
        ViewInteraction appCompatEditText=onView(withId(R.id.passwordEditText));
        //type in password1234#4*
        appCompatEditText.perform(replaceText("pasword123#$*"));
        //find the button
        ViewInteraction materialButton=onView(withId(R.id.loginButton));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView=onView(withId(R.id.passwordTextView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test case to check if password is missing a lowercase letter.
     * Expected behavior: The checkPasswordComplexity() function should return false.
     */
    @Test
    public void testFindMissingLowercase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passwordEditText));
        appCompatEditText.perform(replaceText("PASSWORD@1"));

        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.passwordTextView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test case to check if password is missing a digit.
     * Expected behavior: The checkPasswordComplexity() function should return false.
     */
    @Test
    public void testFindMissingDigit() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passwordEditText));
        appCompatEditText.perform(replaceText("Password!"));

        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.passwordTextView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test case check if password is missing a special character.
     * Expected behavior: The checkPasswordComplexity() function should return false.
     */
    @Test
    public void testFindMissingSpecialCharacter() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passwordEditText));
        appCompatEditText.perform(replaceText("Password1"));
        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.passwordTextView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test case to test that the password meets all the requirements
     * Expected behavior: The checkPasswordComplexity() function should return true.
     */
    @Test
    public void testCheckPasswordAllRequirements() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passwordEditText));
        appCompatEditText.perform(replaceText("Password@1"));
        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.passwordTextView));
        textView.check(matches(withText("Your password meets the requirements")));
    }

//final version

}

