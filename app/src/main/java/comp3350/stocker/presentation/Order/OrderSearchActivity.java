package comp3350.stocker.presentation.Order;

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
import comp3350.stocker.business.OrderLogic;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.objects.Order;
import comp3350.stocker.presentation.ObjectSearchActivity;

import java.sql.SQLException;

public class OrderSearchActivity extends AppCompatActivity implements ObjectSearchActivity {


    OrderLogic accessOrders;
    Order order;
    public String[] searchOptions;
    public int searchBy;
    public Spinner options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        accessOrders = new OrderLogic(Services.getOrderDatabase());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        // create an strings for options for order search
        searchOptions = new String[1];
        searchOptions[0] = "Order ID";

        assignHeader();

        assignButtons();

        //assigns search options to a drop-down menu
        assignSearchOptionSpinner();

    }


    public void assignHeader(){
        TextView header = findViewById(R.id.searchObjectHeader);
        header.setText(R.string.orders);
    }

    public void assignButtons(){

        TextView btn = findViewById(R.id.searchObjectBtn);
        btn.setText(R.string.order_search);
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
        Intent showActivityOrderProfile = new Intent(this, OrderProfileActivity.class);
        startActivity(showActivityOrderProfile);
    }

    public void onSearchObjectClick(View v){

        try {
            //get the position of the currently selected search option
            searchBy = options.getSelectedItemPosition();

            //get the users string input for the search
            EditText textView = findViewById(R.id.searchObject);
            String input = textView.getText().toString();

            if (input.length() > 0) { //if user input not empty

                try {
                    if (searchBy == 0) { //order email
                        order = accessOrders.searchID(input);
                    }

                    String orderID = null;

                    if (order != null) { //if order found, get email
                        orderID = order.getOrderID();
                    }

                    Intent showSearchResults = new Intent(this, OrderActivity.class);
                    //send orderID(or null if not found) to searchResults activity
                    showSearchResults.putExtra("ORDER", orderID);
                    startActivity(showSearchResults);
                }
                catch (ObjectNotFoundException e){
                    throw e;
                }

            }
        }
        catch (Exception e){
            showFailureActivity();
        }

    }

    public void showFailureActivity()
    {
        String failureMsg = "Failed to Display Order List";
        Intent showFailureActivity = new Intent(this, OrderFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }

}
