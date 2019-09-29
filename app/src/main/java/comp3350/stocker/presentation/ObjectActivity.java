package comp3350.stocker.presentation;

import android.view.View;

import java.util.List;

public interface ObjectActivity {

    //assign header at top of page
    public void assignHeader();

    //generates list of object fields for activity
    public List<String[]> populateFieldList();

    //gets the strings currently in all the EditText fields(user input)
    public String[] getCurrFields();

    //returns to the object profile page
    public void onGoBackClick(View v);

    public void updateObject();


}
