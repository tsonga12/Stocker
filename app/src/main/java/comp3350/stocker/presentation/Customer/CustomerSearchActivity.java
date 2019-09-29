package comp3350.stocker.presentation.Customer;

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
import comp3350.stocker.business.CustomerLogic;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.objects.Customer;
import comp3350.stocker.presentation.ObjectSearchActivity;

import java.sql.SQLException;

public class CustomerSearchActivity extends AppCompatActivity implements ObjectSearchActivity {

    // TODO: 2019-03-05 Ava the search needs to be refactored to offer options to search by each field
    // should be able to search by partial "terms" and the search results should populate a list of all matches

    CustomerLogic accessCustomers;
    Customer customer;
    public String[] searchOptions;
    public int searchBy;
    public Spinner options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        accessCustomers = new CustomerLogic(Services.getCustomerDatabase());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        // create an strings for options for customer search
        searchOptions = new String[1];
        searchOptions[0] = "Email";

        assignHeader();

        assignButtons();

        //assigns search options to a drop-down menu
        assignSearchOptionSpinner();

    }


    public void assignHeader(){
        TextView header = findViewById(R.id.searchObjectHeader);
        header.setText(R.string.customers);
    }

    public void assignButtons(){

        TextView btn = findViewById(R.id.searchObjectBtn);
        btn.setText(R.string.customer_search);
    }

    //assign search options to drop-down menu
    public void assignSearchOptionSpinner(){

        searchBy = 0; //default searches by first option

        options = findViewById(R.id.search_options);

        //create an adapter for the drop-down menu
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,searchOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        options.setAdapter(adapter);

    }



    public void onGoBackClick(View v){
        Intent showActivityCustomerProfile = new Intent(this, CustomerProfileActivity.class);
        startActivity(showActivityCustomerProfile);
    }

    public void onSearchObjectClick(View v){

        try {
            //get the position of the currently selected search option
            searchBy = options.getSelectedItemPosition();

            //get the users string input for the search
            EditText textView = findViewById(R.id.searchObject);
            String input = textView.getText().toString();

            if (input.length() > 0) { //if user input not empty

                if (searchBy == 0) { //customer email
                    customer = accessCustomers.searchEmail(input);
                }

                String customerID = null;

                if(customer != null){ //if customer found, get email
                    customerID = customer.getEmail();
                }

                Intent showSearchResults = new Intent(this, CustomerActivity.class);
                //send customerID(or null if not found) to searchResults activity
                showSearchResults.putExtra("CUSTOMER", customerID);
                startActivity(showSearchResults);

            }
        }
        catch (ObjectNotFoundException e){
            showFailureActivity();
        }

    }

    public void showFailureActivity()
    {
        String failureMsg = "Failed to Display Customer List";
        Intent showFailureActivity = new Intent(this, CustomerFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }

}
