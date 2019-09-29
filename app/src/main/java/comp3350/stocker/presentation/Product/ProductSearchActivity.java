package comp3350.stocker.presentation.Product;

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
import comp3350.stocker.business.ProductLogic;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.objects.Product;
import comp3350.stocker.presentation.ObjectSearchActivity;

public class ProductSearchActivity extends AppCompatActivity implements ObjectSearchActivity {

    // TODO: 2019-03-05 Ava the search needs to be refactored to offer options to search by each field
            // should be able to search by partial "terms" and the search results should populate a list of all matches

    ProductLogic accessProducts;
    Product product;
    public String[] searchOptions;
    public int searchBy;
    public Spinner options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        accessProducts = new ProductLogic(Services.getProductDatabase());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        //create strings for options for product search
        searchOptions = new String[3];
        searchOptions[0] = "Name";
        searchOptions[1] = "ID";
        searchOptions[2] = "Tag";

        assignHeader();

        assignButtons();

        //assign search options to drop-down menu
        assignSearchOptionSpinner();

    }


    public void assignHeader(){
        TextView header = findViewById(R.id.searchObjectHeader);
        header.setText(R.string.products);
    }

    public void assignButtons(){

        TextView btn = findViewById(R.id.searchObjectBtn);
        btn.setText(R.string.product_profile_search);
    }

    //assign search options to drop-down menu
    public void assignSearchOptionSpinner(){

        searchBy = 0; //default searches by first option

        options = findViewById(R.id.search_options);

        //create adapter for the drop-down menu
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,searchOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        options.setAdapter(adapter);

    }


    public void onGoBackClick(View v){
        Intent showActivityProductProfile = new Intent(this, ProductProfileActivity.class);
        startActivity(showActivityProductProfile);
    }

    public void onSearchObjectClick(View v){

        try {
            //get the position of the currently selected search option
            searchBy = options.getSelectedItemPosition();

            //get the users string input for the search
            EditText textView = findViewById(R.id.searchObject);
            String input = textView.getText().toString();

            if (input.length() > 0) { // if user input not empty

                if (searchBy == 0) { // name
                    product = accessProducts.searchName(input);
                }

                if (searchBy == 1) { //productId
                    product = accessProducts.searchID(input);
                }
                if(searchBy == 2)
                {
                    product = accessProducts.searchTag(input);
                }


                String productID = null;

                if(product != null){ //if product found, get ID
                    productID = product.getID();
                }


                Intent showSearchResults = new Intent(this, ProductSearchResultsActivity.class);
                //send productID(or null if not found) to searchResults activity
                showSearchResults.putExtra("PRODUCT", productID);
                startActivity(showSearchResults);

            }
        }
        catch (ObjectNotFoundException e){
            showFailureActivity();
        }

    }

    public void showFailureActivity()
    {
        String failureMsg = "Encountered an Error While Searching";
        Intent showFailureActivity = new Intent(this, ProductFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }

}
