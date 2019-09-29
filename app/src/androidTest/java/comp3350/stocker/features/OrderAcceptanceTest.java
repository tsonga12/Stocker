package comp3350.stocker.features;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.contrib.*;
import android.widget.Adapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.stocker.R;
import comp3350.stocker.presentation.MainActivity;

import static android.support.test.espresso.DataInteraction.DisplayDataMatcher.*;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.pressKey;

import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class OrderAcceptanceTest
{
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void createOrder()
    {
        // navigate to creation page
        onView(ViewMatchers.withId(R.id.order_btn)).perform(click());

        onView(withId(R.id.objectAddBtn)).perform(click());

        // insert data into fields
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,typeText("00000000")));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));

        onView(withText("132737")).perform(click());

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(2,typeText("01/01/1111")));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3,typeText("9999")));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4,typeText("Pigeon")));

        closeSoftKeyboard();

        //confirm and create
        onView(withId(R.id.newObjectBtn)).perform(click());

        // verify it was created
        onView(withId(R.id.objectSuccessHomeBtn)).perform(click());

        onView(withId(R.id.order_btn)).perform(click());

        onView(withId(R.id.listObjects)).check(matches((hasDescendant(withText("00000000")))));
    }

    @Test
    public void deleteOrder()
    {
        // navigate to delete page
        onView(withId(R.id.order_btn)).perform(click());

        //select first item
        onData(anything()).inAdapterView(withId(R.id.listObjects)).atPosition(0).perform(click());

        closeSoftKeyboard();

        //delete it
        onView(withId(R.id.deleteObjectBtn)).perform(click());

        //verify it was deleted
        onView(withId(R.id.ObjectFailureHomeBtn)).perform(click());

        onView(withId(R.id.order_btn)).perform(click());

        onView(withId(R.id.listObjects)).check(matches(not(hasDescendant(withText("GGGH123")))));
    }
}
