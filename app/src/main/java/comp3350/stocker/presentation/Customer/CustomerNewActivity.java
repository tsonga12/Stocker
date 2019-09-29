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
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.objects.Customer;
import comp3350.stocker.presentation.FieldListAdapter;
import comp3350.stocker.presentation.ObjectNewActivity;

public class CustomerNewActivity extends AppCompatActivity implements ObjectNewActivity {

    private CustomerLogic accessCustomers;
    private Customer customer;
    String successMsg = "Successfully created customer";
    String failureMsg = "Failed to create customer";
    public List<String[]> fieldList; //string[0] == name of field; string[1] == user input value



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        accessCustomers = new CustomerLogic(Services.getCustomerDatabase());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_new);

        assignHeader();

        assignButtons();

        //contains list of field names, and corresponding user input string values
        fieldList = populateFieldList();

    }

    public void assignHeader(){
        TextView header = findViewById(R.id.newObjectHeader);
        header.setText(R.string.customers);
    }

    public void assignButtons(){

        TextView btn = findViewById(R.id.newObjectBtn);
        btn.setText(R.string.customer_create);
    }

    private List<String[]> makeFieldList(){

        List<String[]> list = new ArrayList<>();

        String[] email = {"E-mail", ""};
        list.add(email);

        String[] firstName = {"First Name", ""};
        list.add(firstName);

        String[] lastName = {"Last Name", ""};
        list.add(lastName);

        String[] address = {"Address", ""};
        list.add(address);

        String[] phone = {"Phone number", ""};
        list.add(phone);

        if(list.size() != accessCustomers.getNumFields()){
            showFailureActivity();
        }

        return list;

    }

    // displays a list of all the product's fields and values using
    // uses custom recyclerView adapter 'FieldListAdapter.java'
    public List<String[]> populateFieldList(){

        fieldList = makeFieldList();



        try{
            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            //create new view adapter from list of fields
            FieldListAdapter adapter = new FieldListAdapter(fieldList);

            //set layout of recyclerView and assign field adapter
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        }
        catch(Exception e){
            showFailureActivity();
        }

        return fieldList;

    }


    //retrieves and returns string values currently in editText fields(user input)
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



    public void onCreateObjectClick(View v){


        //send current values in activity_product_new to logic layer

        createObjectFromText();

        if (customer != null) {
            String customerID = customer.getEmail();
            Intent showCustomerSuccess = new Intent(this, CustomerSuccessActivity.class);
            showCustomerSuccess.putExtra("CUSTOMER", customerID);
            showCustomerSuccess.putExtra("MESSAGE", successMsg);
            startActivity(showCustomerSuccess);
        } else {
            showFailureActivity();
        }

    }

    public void createObjectFromText()
    {
        try {
            //retrieve current String values in EditText fields
            String[] currFields = getCurrFields();

            String custEmail = currFields[0];
            String custFirstName = currFields[1];
            String custLastName = currFields[2];
            String custAddress = currFields[3];
            String custPhone = currFields[4];

            //convert String to number fields
            long phone = Long.parseLong(custPhone);

            //create new product
            customer = new Customer(custFirstName, custLastName, custEmail, custAddress, phone);
            accessCustomers.insert(customer);

            //if product is not created, show failure page
            if(customer == null){
                showFailureActivity();
            }
        }
        catch(ObjectCreationException e){
            showFailureActivity();
        }
    }

    public void onGoBackClick(View v){
        Intent showActivityProductProfile = new Intent(this, CustomerProfileActivity.class);
        startActivity(showActivityProductProfile);
    }

    private void showFailureActivity()
    {
        Intent showFailureActivity = new Intent(this, CustomerFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }
}
