package comp3350.stocker.presentation.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import comp3350.stocker.R;

public class CustomerSearchResultsActivity extends AppCompatActivity{

    Intent intent;
    String customerID;
    TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search_results);

        intent = getIntent();
        customerID = intent.getStringExtra("CUSTOMER");

        result = findViewById(R.id.searchMsg);

        if(customerID != null){
            Intent showActivityCustomer = new Intent(this, CustomerActivity.class);
            showActivityCustomer.putExtra("CUSTOMER", customerID);
            startActivity(showActivityCustomer);
        }
        else{
            result.setText("Customer not found");
        }

    }


    public void onGoBackClick(View v){
        Intent showActivityCustomerSearch = new Intent(this, CustomerSearchActivity.class);
        startActivity(showActivityCustomerSearch);
    }




}
