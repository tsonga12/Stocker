package comp3350.stocker.presentation;

import android.view.View;

public interface ObjectProfileActivity {

    //assign header at top of page
    public void assignHeader();

    //fills listView with all objects
    public void populateObjectList();

    //returns to the main activity page
    public void onGoBackClick(View v);

    //goes to the object profile page of the selected object
    public void onObjectClick(View v, int position);

}
