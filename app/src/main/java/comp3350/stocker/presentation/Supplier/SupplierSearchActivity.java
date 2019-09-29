package comp3350.stocker.presentation.Supplier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import comp3350.stocker.R;
import comp3350.stocker.application.Services;
import comp3350.stocker.business.SupplierLogic;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.objects.Supplier;
import comp3350.stocker.presentation.ObjectSearchActivity;

public class SupplierSearchActivity extends AppCompatActivity implements ObjectSearchActivity {

    // TODO: 2019-03-05 Ava the search needs to be refactored to offer options to search by each field
    // should be able to search by partial "terms" and the search results should populate a list of all matches

    SupplierLogic accessSuppliers;
    Supplier supplier;
    public String[] searchOptions;
    public int searchBy;
    public Spinner options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        accessSuppliers = new SupplierLogic(Services.getSupplierDatabase());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        //create strings for options for supplier search
        searchOptions = new String[2];
        searchOptions[0] = "Name";
        searchOptions[1] = "ID";

        assignHeader();

        assignButtons();

        //assign search options to drop-down menu
        assignSearchOptionSpinner();

    }


    public void assignHeader(){
        TextView header = findViewById(R.id.searchObjectHeader);
        header.setText(R.string.suppliers);
    }

    public void assignButtons(){

        TextView btn = findViewById(R.id.searchObjectBtn);
        btn.setText(R.string.supplier_search);
    }

    //assign search options to drop-down menu
    public void assignSearchOptionSpinner(){

        searchBy = 0; //default searches by first option

        options = findViewById(R.id.search_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,searchOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        options.setAdapter(adapter);

    }


    public void onGoBackClick(View v){
        Intent showActivitySupplierProfile = new Intent(this, SupplierProfileActivity.class);
        startActivity(showActivitySupplierProfile);
    }

    public void onSearchObjectClick(View v){

        try {
            //get the position of the currently selected search option
            searchBy = options.getSelectedItemPosition();

            //get the users string input for the search
            EditText textView = findViewById(R.id.searchObject);
            String input = textView.getText().toString();

            if (input.length() > 0) {

                if (searchBy == 0) { // name
                    supplier = accessSuppliers.searchName(input);
                }

                if (searchBy == 1) { //supplierId
                    supplier = accessSuppliers.searchID(input);
                }

                String supplierID = null;

                if(supplier != null){ //if supplier found, get ID
                    supplierID = supplier.getID();
                }

                Intent showSearchResults = new Intent(this, SupplierSearchResultsActivity.class);
                //send supplierID(or null if not found) to searchResults
                showSearchResults.putExtra("SUPPLIER", supplierID);
                startActivity(showSearchResults);

            }
        }
        catch(ObjectNotFoundException e)
        {
            showFailureActivity();
        }

    }

    public void showFailureActivity()
    {
        String failureMsg = "Encountered an Error When Searching for Supplier";
        Intent showFailureActivity = new Intent(this, SupplierFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }

}
