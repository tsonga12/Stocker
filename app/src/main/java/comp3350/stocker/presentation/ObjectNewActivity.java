package comp3350.stocker.presentation;

import android.view.View;

import java.util.List;

public interface ObjectNewActivity {

    //assign header at top of page
    public void assignHeader();

    //assign buttons
    public void assignButtons();

    //generates list of object fields for activity
    public List<String[]> populateFieldList();

    //retrieves current values of editText fields
    public String[] getCurrFields();

    //uses current values from text fields to create new product
    public void createObjectFromText();

    //returns to the object profile page
    public void onGoBackClick(View v);


}
