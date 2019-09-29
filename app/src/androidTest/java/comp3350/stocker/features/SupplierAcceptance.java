package comp3350.stocker.features;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.stocker.R;
import comp3350.stocker.presentation.MainActivity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SupplierAcceptance
{
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void createSupplier()
    {
        // navigate to creation page
        onView(ViewMatchers.withId(R.id.supplier_btn)).perform(click());

        onView(withId(R.id.objectAddBtn)).perform(click());

        // insert data into fields
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,typeText("999999")));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1,typeText("LeetSupp")));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(2,typeText("Downtown")));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3,typeText("6678899")));

        closeSoftKeyboard();

        //confirm and create
        onView(withId(R.id.newObjectBtn)).perform(click());

        // verify it was created
        onView(withId(R.id.objectSuccessHomeBtn)).perform(click());

        onView(withId(R.id.supplier_btn)).perform(click());

        onView(withId(R.id.listObjects)).check(matches((hasDescendant(withText("LeetSupp")))));
    }

    @Test
    public void deleteSupplier()
    {
        // navigate to delete page
        onView(withId(R.id.supplier_btn)).perform(click());

        //select first item
        onData(anything()).inAdapterView(withId(R.id.listObjects)).atPosition(1).perform(click());

        closeSoftKeyboard();

        //delete it
        onView(withId(R.id.deleteObjectBtn)).perform(click());

        //verify it was deleted
        onView(withId(R.id.listObjects)).check(matches(not(hasDescendant(withText("786343")))));
    }
}