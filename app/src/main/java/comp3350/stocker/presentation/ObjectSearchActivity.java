package comp3350.stocker.presentation;

import android.view.View;

public interface ObjectSearchActivity {

    //assign header at top of page
    public void assignHeader();

    //assign string to search btn
    public void assignButtons();

    //assign search options to drop-down menu
    public void assignSearchOptionSpinner();

    //returns to the object profile page
    public void onGoBackClick(View v);

    //initiates search for the object and creates new searchResults activity
    public void onSearchObjectClick(View v);

    //shows failure page
    public void showFailureActivity();

}
