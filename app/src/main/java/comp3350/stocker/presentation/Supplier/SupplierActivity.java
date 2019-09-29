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
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectDeleteException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectUpdateException;
import comp3350.stocker.objects.Supplier;
import comp3350.stocker.presentation.FieldListAdapter;
import comp3350.stocker.presentation.ObjectActivity;


public class SupplierActivity extends AppCompatActivity implements ObjectActivity{

    Intent intent;
    String supplierID;
    Supplier supplier;
    List<String[]> fieldList;
    String successMsg = "Successfully updated Supplier";
    String failureMsg;
    SupplierLogic accessSuppliers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        accessSuppliers = new SupplierLogic(Services.getSupplierDatabase());

        intent = getIntent();
        supplierID = intent.getStringExtra("SUPPLIER");
        try {
            supplier = accessSuppliers.searchID(supplierID);
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
        header.setText(R.string.suppliers);
    }


    private List<String[]> makeFieldList(){

        List<String[]> list = new ArrayList<>();

        String[] ID = {"Supplier ID", supplier.getID()};
        list.add(ID);

        String[] name = {"Name", supplier.getName()};
        list.add(name);

        String[] location = {"Location", supplier.getLocation()};
        list.add(location);

        String[] phone = {"Phone Number", String.format("%s", supplier.getPhoneNum())};
        list.add(phone);

        if(list.size() != accessSuppliers.getNumFields()){
            failureMsg = "Incorrect supplier field data";
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
        catch (Exception e){ //TODO: Is this try-catch block actually necessary?
            showFailureActivity();
        }

        return fields;
    }

    public void onGoBackClick(View v)
    {
        Intent showActivitySupplierProfile = new Intent(this, SupplierProfileActivity.class);
        startActivity(showActivitySupplierProfile);
    }

    public void onDeleteObjectClick(View v)
    {
        try {
            accessSuppliers.delete(supplier);
            Intent showActivityProductProfile = new Intent(this, SupplierProfileActivity.class);
            startActivity(showActivityProductProfile);
        }
        catch(ObjectDeleteException e)
        {
            failureMsg = "Failed to delete supplier";
            showFailureActivity();
        }
    }

    public void onSaveObjectClick(View v)
    {
        //send current values in activity_product_new to logic layer

        updateObject();

        try {

            String supplierID = supplier.getID();

            Intent showResultActivity = new Intent(this, SupplierSuccessActivity.class);
            showResultActivity.putExtra("SUPPLIER", supplierID);
            showResultActivity.putExtra("MESSAGE", successMsg);
            startActivity(showResultActivity);
        }
        catch(Exception e)
        {
            showFailureActivity();
        }

    }

    public void updateObject(){

        try {

            String[] fields = getCurrFields();

            accessSuppliers.updateName(supplier, fields[1]);

            accessSuppliers.updateLoc(supplier, fields[2]);

            long phone = Long.parseLong(fields[3]);
            accessSuppliers.updatePhoneNum(supplier, phone);

        }
        catch (ObjectUpdateException e){
            showFailureActivity();
        }

    }


    private void showFailureActivity()
    {
        Intent showFailureActivity = new Intent(this, SupplierFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }
}
