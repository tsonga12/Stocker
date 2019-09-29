package comp3350.stocker.presentation.Supplier;

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
import comp3350.stocker.business.SupplierLogic;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.objects.Supplier;
import comp3350.stocker.presentation.FieldListAdapter;
import comp3350.stocker.presentation.ObjectNewActivity;

public class SupplierNewActivity extends AppCompatActivity implements ObjectNewActivity {

    Supplier supplier;
    SupplierLogic accessSuppliers;
    String successMsg = "Successfully created supplier";
    String failureMsg = "Failed to create supplier";
    List<String[]> fieldList; //string[0] == name of field; string[1] == user input value


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        accessSuppliers = new SupplierLogic(Services.getSupplierDatabase());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_new);

        assignHeader();
        assignButtons();

        //contains list of field names, and corresponding user input string values
        fieldList = populateFieldList();

    }
    public void assignHeader()
    {
        TextView header = findViewById(R.id.newObjectHeader);
        header.setText(R.string.suppliers);
    }

    public void assignButtons(){

        TextView viewObject = findViewById(R.id.newObjectBtn);
        viewObject.setText(R.string.supplier_add);
    }


    private List<String[]> makeFieldList(){

        List<String[]> list = new ArrayList<>();

        String[] ID = {"Supplier ID", ""};
        list.add(ID);

        String[] name = {"Name", ""};
        list.add(name);

        String[] location = {"Location", ""};
        list.add(location);

        String[] phone = {"Phone Number", ""};
        list.add(phone);

        if(list.size() != accessSuppliers.getNumFields()){
            failureMsg = "Incorrect supplier field data";
            showFailureActivity();
        }


        return list;
    }


    // displays a list of all the supplier's fields and values using
    // uses custom recyclerView adapter 'FieldListAdapter.java'
    public List<String[]> populateFieldList(){

        List<String[]> list = null;

            //get list of supplier fields
            list = makeFieldList();

            try{
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

                //create new view adapter form list of fields
                FieldListAdapter adapter = new FieldListAdapter(list);

                //set layout of recyclerView and assign field adapter
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);

            }
            catch(Exception e){  //TODO: Figure out Exception type that recyclerView can throw. otherwise this can be removed.
                showFailureActivity();
            }



        return list;

    }


    //retrieves and returns string values currently in editText fields(user input)
    //TODO: Smells: Is the try-catch needed?
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

         if (supplier != null) {
             String supplierID = supplier.getID();
             Intent showSupplierSuccess = new Intent(this, SupplierSuccessActivity.class);
             showSupplierSuccess.putExtra("SUPPLIER", supplierID);
             showSupplierSuccess.putExtra("MESSAGE", successMsg);
             startActivity(showSupplierSuccess);
         } else {
             showFailureActivity();
         }


    }

    //uses current values from text fields to create new supplier
    // converts strings to appropriate values for each field(if needed)
    public void createObjectFromText(){

        try {
            //retrieve current String values in EditText fields
            String[] currFields = getCurrFields();

            String supplierID = currFields[0];
            String supplierName = currFields[1];
            String supplierLocation = currFields[2];
            String supplierPhone = currFields[3];

            //convert String to number fields
            long phone = Long.parseLong(supplierPhone);

            //create new product
            supplier = new Supplier(supplierID, supplierName, supplierLocation, phone);
            accessSuppliers.insert(supplier);

            //if product is not created, show failure page
            if(supplier == null){
                showFailureActivity();
            }
        }
        catch(ObjectCreationException e){
            showFailureActivity();
        }

    }


    public void onGoBackClick(View v){
        Intent showActivitySupplierProfile = new Intent(this, SupplierProfileActivity.class);
        startActivity(showActivitySupplierProfile);
    }


    private void showFailureActivity()
    {
        Intent showFailureActivity = new Intent(this, SupplierFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }
}
