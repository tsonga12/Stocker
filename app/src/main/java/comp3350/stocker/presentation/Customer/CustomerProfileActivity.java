package comp3350.stocker.presentation.Customer;

/*
This Activity is called when the user presses the Customers button on the MainActivity.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import comp3350.stocker.R;
import comp3350.stocker.application.Services;
import comp3350.stocker.business.CustomerLogic;
import comp3350.stocker.objects.Customer;
import comp3350.stocker.presentation.MainActivity;
import comp3350.stocker.presentation.ObjectProfileActivity;

public class CustomerProfileActivity extends AppCompatActivity implements ObjectProfileActivity
{

    private CustomerLogic accessCustomers;
    private List<Customer> customerList;
    ArrayAdapter<Customer> customerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_profile);

        accessCustomers = new CustomerLogic(Services.getCustomerDatabase());

        assignHeader();

        populateObjectList();
    }

    public void assignHeader()
    {
        TextView header = (TextView) findViewById(R.id.objectHeader);
        header.setText(R.string.customers);
    }

    public void populateObjectList()
    {
        try
        {
            customerList = accessCustomers.getAllCustomers();
            customerArrayAdapter = new ArrayAdapter<Customer>(this, android.R.layout.simple_list_item_activated_2,android.R.id.text1,customerList)
            {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    TextView text1 = view.findViewById(android.R.id.text1);
                    TextView text2 = view.findViewById(android.R.id.text2);

                    text1.setText(customerList.get(position).getFirstName());
                    text2.setText(customerList.get(position).getLastName());

                    return view;
                }
            };

            final ListView listView = findViewById(R.id.listObjects);
            listView.setAdapter(customerArrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    onObjectClick(view, position);
                }
            });


        }
        catch (final Exception e)
        {
            listFailure();
        }
    }

    public void onGoBackClick(View v)
    {
        Intent showActivityMain = new Intent(this, MainActivity.class);
        startActivity(showActivityMain);
    }

    public void onAddNewObjectClick(View v){
        Intent showActivityNewCustomer = new Intent(this, CustomerNewActivity.class);
        startActivity(showActivityNewCustomer);
    }

    public void onSearchObjectClick(View v){

        Intent showActivitySearchCustomer = new Intent(this, CustomerSearchActivity.class);
        startActivity(showActivitySearchCustomer);
    }

    public void onObjectClick(View v, int position){

        Customer customer = customerList.get(position);


        if(customer != null) {
            //Merge conflict: Commented out customerID as customer objects no longer have an ID attribute
            String customerID = customer.getEmail();
            Intent showActivityCustomer = new Intent(this, CustomerActivity.class);
            showActivityCustomer.putExtra("CUSTOMER", customerID);
            startActivity(showActivityCustomer);
        }
        else
        {
            showFailureActivity();
        }
    }

    private void listFailure(){

        String msg = "There are no customers to display";
        TextView empty = findViewById(R.id.listEmpty);
        empty.setText(msg);

    }

    private void showFailureActivity()
    {
        String failureMsg = "Failed to Display Customer List";
        Intent showFailureActivity = new Intent(this, CustomerFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }


}
