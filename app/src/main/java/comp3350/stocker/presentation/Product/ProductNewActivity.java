package comp3350.stocker.presentation.Product;

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
import comp3350.stocker.business.ProductLogic;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.objects.Product;
import comp3350.stocker.presentation.FieldListAdapter;
import comp3350.stocker.presentation.ObjectNewActivity;

public class ProductNewActivity extends AppCompatActivity implements ObjectNewActivity {

    private ProductLogic accessProducts;
    private Product product;
    String successMsg = "Successfully created product";
    String failureMsg = "Failed to create product";
    public List<String[]> fieldList; //string[0] == name of field; string[1] == user input value
    private int numFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        accessProducts = new ProductLogic(Services.getProductDatabase());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_new);

        assignHeader();

        assignButtons();

        //contains list of field names, and corresponding user input string values
        populateFieldList();

    }

    public void assignHeader(){
        TextView header = findViewById(R.id.newObjectHeader);
        header.setText(R.string.products);
    }

    public void assignButtons(){

        TextView btn = findViewById(R.id.newObjectBtn);
        btn.setText(R.string.product_create);
    }


    // displays a list of all the product's fields and values using
    // uses custom recyclerView adapter 'FieldListAdapter.java'
    public List<String[]> populateFieldList(){


        //get list of product fields
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

    private List<String[]> makeFieldList(){
        List<String[]> list = new ArrayList<>();

        String[] ID = {"ProductID", ""};
        list.add(ID);

        String[] name = {"Name", ""};
        list.add(name);

        String[] cost = {"Cost", ""};
        list.add(cost);

        String[] price = {"Price", ""};
        list.add(price);

        String[] quantity = {"Quantity", ""};
        list.add(quantity);

        String[] supplier = {"Supplier", ""};
        list.add(supplier);

        if(list.size() != accessProducts.getNumFields()){
            showFailureActivity();
        }

        numFields = list.size();
        return list;
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

    //initiates creation of new product from current editText fields
    // starts ProductSuccess activity if successful, productFailure if not successful
    public void onCreateObjectClick(View v){

        //send current values in activity_product_new to logic layer

            createObjectFromText();

            if (product != null) {
                String productID = product.getID();
                Intent showProductSuccess = new Intent(this, ProductSuccessActivity.class);
                showProductSuccess.putExtra("PRODUCT", productID);
                showProductSuccess.putExtra("MESSAGE", successMsg);
                startActivity(showProductSuccess);
            } else {
                showFailureActivity();
            }

    }

    //uses current values from text fields to create new product
    // converts strings to appropriate values for each field(if needed)
    public void createObjectFromText(){

        try {
            //retrieve current String values in EditText fields
            String[] currFields = getCurrFields();

            String productID = currFields[1];
            String productName = currFields[0];
            String productCost = currFields[2];
            String productPrice = currFields[3];
            String productQuantity = currFields[4];
            String productSupplier = currFields[5];

            //convert String to number fields
            float cost = Float.parseFloat(productCost);
            float price = Float.parseFloat(productPrice);
            int quantity = Integer.parseInt(productQuantity);

            //create new product
            product = new Product(productID, productName, cost, price, quantity, productSupplier);
            accessProducts.insert(product);

            //if product is not created, show failure page
            if(product == null){
                showFailureActivity();
            }
        }
        catch(ObjectCreationException e){
            showFailureActivity();
        }

    }


    public void onGoBackClick(View v){
        Intent showActivityProductProfile = new Intent(this, ProductProfileActivity.class);
        startActivity(showActivityProductProfile);
    }



    private void showFailureActivity()
    {
        Intent showFailureActivity = new Intent(this, ProductFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }

}
