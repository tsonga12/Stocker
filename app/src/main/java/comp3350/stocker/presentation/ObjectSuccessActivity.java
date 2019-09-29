package comp3350.stocker.presentation;

import android.view.View;

public interface ObjectSuccessActivity {

    //gets string extras sent from previous activity
    public boolean getExtras();

    //assign header at top of page
    public void assignHeader();

    //assign value to 3 btns
    public void assignButtons();

    //assign string to message in centre of page
    public void assignMessage();

    //goes to create new object activity
    public void onNewObjectClick(View v);

    //returns to the main activity page
    public void onHomeClick(View v);
}
