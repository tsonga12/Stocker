package comp3350.stocker.presentation.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comp3350.stocker.R;
import comp3350.stocker.application.Services;
import comp3350.stocker.business.CustomerLogic;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectDeleteException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectUpdateException;
import comp3350.stocker.objects.Customer;
import comp3350.stocker.presentation.FieldListAdapter;
import comp3350.stocker.presentation.ObjectActivity;

public class CustomerActivity extends AppCompatActivity implements ObjectActivity {

    Intent intent;
    List<String[]> fieldList;
    String customerID;
    Customer customer;
    CustomerLogic accessCustomers;
    String successMsg = "Successfully updated Customer";
    String failureMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        accessCustomers = new CustomerLogic(Services.getCustomerDatabase());

        intent = getIntent();
        customerID = intent.getStringExtra("CUSTOMER");
        try {
            customer = accessCustomers.searchEmail(customerID);
            if (customer == null) {
                showFailureActivity();
            }
        }
        catch(ObjectNotFoundException e)
        {
            showFailureActivity();
        }
        assignHeader();

        fieldList = populateFieldList();

    }


    public void assignHeader()
    {
        TextView header = findViewById(R.id.objectHeader);
        header.setText(R.string.customers);
    }

    private List<String[]> makeFieldList(){

        List<String[]> list = new ArrayList<>();

        String[] email = {"E-mail", customer.getEmail()};
        list.add(email);

        String[] firstName = {"First Name", customer.getFirstName()};
        list.add(firstName);

        String[] lastName = {"Last Name", customer.getLastName()};
        list.add(lastName);


        String[] address = {"Address", customer.getAddress()};
        list.add(address);

        String[] phone = {"Phone number", String.format("%s",customer.getPhoneNum())};
        list.add(phone);

        if(list.size() != accessCustomers.getNumFields()){
            showFailureActivity();
        }

        return list;

    }

    public List<String[]> populateFieldList(){


        fieldList = makeFieldList();

        try{
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            FieldListAdapter adapter = new FieldListAdapter(fieldList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        }
        catch(Exception e){
            showFailureActivity();
        }


        return fieldList;

    }

    public String[] getCurrFields(){

        String[] fields = null;

        try{

            fields = new String[fieldList.size()];

            for(int i = 0; i < fieldList.size(); i++){

                fields[i] = fieldList.get(i)[1];

            }
        }
        catch (Exception e){
            showFailureActivity();
        }

        return fields;
    }



    public void onGoBackClick(View v)
    {
        Intent showActivityCustomerProfile = new Intent(this, CustomerProfileActivity.class);
        startActivity(showActivityCustomerProfile);
    }

    public void onDeleteObjectClick(View v)
    {
        try {
            accessCustomers.delete(customer);
            successMsg = "Successfully deleted customer";
            Intent showActivityCustomerProfile = new Intent(this, CustomerFailureActivity.class);
            startActivity(showActivityCustomerProfile);
        }
        catch(ObjectDeleteException e)
        {
            failureMsg = "Failed to delete customer";
            showFailureActivity();
        }
    }

    public void onSaveObjectClick(View v)
    {
        //send current values in activity_product_new to logic layer

        updateObject();

        try {
                String custID = customer.getEmail();
                Intent showCustomerSuccess = new Intent(this, CustomerSuccessActivity.class);
                showCustomerSuccess.putExtra("CUSTOMER", custID);
                showCustomerSuccess.putExtra("MESSAGE", successMsg);
                startActivity(showCustomerSuccess);
        }
        catch (Exception e){
            showFailureActivity();
        }


    }

    public void updateObject(){


        try{

            // TODO: 2019-04-03 figure out wtf field the customer ID is supposed to be  
            String[] fields = getCurrFields();

            accessCustomers.updateFirstName(customer, fields[1]);

            accessCustomers.updateLastName(customer, fields[2]);

            accessCustomers.updateAddr(customer, fields[3]);

            long phone = Long.parseLong(fields[4]);
            accessCustomers.updatePhoneNum(customer, phone);

        }
        catch (ObjectUpdateException e)
        {
            showFailureActivity();
        }


    }

    private void showFailureActivity()
    {
        Intent showFailureActivity = new Intent(this, CustomerFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }
}
