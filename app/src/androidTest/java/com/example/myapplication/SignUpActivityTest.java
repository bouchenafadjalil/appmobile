package com.example.myapplication;


// ./gradlew createDebugCoverageReport
// app/build/reports/coverage/androidTest/debug/connected/index.html.
 import static androidx.test.espresso.Espresso.onView;
 import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
 import static androidx.test.espresso.matcher.ViewMatchers.withId;
 import static org.junit.Assert.assertEquals;
 import android.content.SharedPreferences;

 import android.preference.PreferenceManager;
 import android.view.View;

 import androidx.test.core.app.ActivityScenario;
 import androidx.test.espresso.Espresso;
 import androidx.test.espresso.action.ViewActions;
 import androidx.test.espresso.assertion.ViewAssertions;
 import androidx.test.ext.junit.rules.ActivityScenarioRule;

 import org.junit.Before;
import org.junit.Rule;
 import org.junit.Test;
 public class SignUpActivityTest {
     @Rule
 public ActivityScenarioRule<SignUpActivity>
 activityRule = new ActivityScenarioRule<>(SignUpActivity.class);
 private View decorView;

 @Before
 public void loadDecorView() {
         activityRule.getScenario().onActivity(activity ->
                 decorView = activity.getWindow().getDecorView());
         }

         @Test
 public void testCorrectCredentials() {
         Espresso.onView(withId(R.id.family_name))
         .perform(ViewActions.typeText("bouchenafa"));
         onView(withId(R.id.first_name))
                 .perform(ViewActions.typeText("abdou"));
         onView(withId(R.id.email))
                     .perform(ViewActions.typeText("abdou@bouchenafa.com"));
         onView(withId(R.id.age))
                     .perform(ViewActions.typeText("21"));
         onView(withId(R.id.address))
                     .perform(ViewActions.typeText("ouargla"));

         Espresso.pressBack();
         onView(withId(R.id.signup_button)).perform(ViewActions.click());

         // Check if user info is saved to shared preferences
         try {
             Thread.sleep(3000);
             } catch (InterruptedException e) {
             e.printStackTrace();
         }

         // Check if HomeActivity is launched after successful signup
         onView(withId(R.id.welcome_textview))
         .check(ViewAssertions.matches(isDisplayed()));

         // Assert that the HomeActivity is started
         ActivityScenario<HomeActivity>
         scenario = ActivityScenario.launch(HomeActivity.class);

         scenario.onActivity(activity -> {
             // Assert that the user information is saved to SharedPreferences
             SharedPreferences preferences =
                     PreferenceManager.getDefaultSharedPreferences(activity);

            assertEquals("bouchenafa", preferences.getString("family_name", ""));
            assertEquals("abdou", preferences.getString("first_name", ""));
            assertEquals(21, preferences.getInt("age", 0));
            assertEquals("abdou@bouchenafa.com", preferences.getString("email", ""));
            assertEquals("ouargla", preferences.getString("address", ""));

         });
        }

 }