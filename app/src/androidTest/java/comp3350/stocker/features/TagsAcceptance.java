package comp3350.stocker.features;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.contrib.*;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.stocker.R;
import comp3350.stocker.presentation.MainActivity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.pressKey;

import android.support.test.espresso.matcher.RootMatchers;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TagsAcceptance
{
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void editTag()
    {
        // navigate to the tags
        onView(ViewMatchers.withId(R.id.product_btn)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listObjects)).atPosition(0).perform(click());

        closeSoftKeyboard();

        // click the tag and edit the name
        onView(withText("CPU")).perform(click());

        onView(withId(42)).perform(typeText("Apples"));

        closeSoftKeyboard();

        // confirm
        onView(withText("OK")).perform(click());

        onView(withId(R.id.saveObjectBtn)).perform(click());

        // check to see that its been changed
        onView(withId(R.id.objectSuccessViewBtn)).perform(click());

        closeSoftKeyboard();

        onView(withText("Apples")).perform(click());


    }
}
