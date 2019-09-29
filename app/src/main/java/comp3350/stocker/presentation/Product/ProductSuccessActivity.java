package comp3350.stocker.presentation.Product;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import comp3350.stocker.R;
import comp3350.stocker.presentation.MainActivity;
import comp3350.stocker.presentation.ObjectSuccessActivity;

public class ProductSuccessActivity extends AppCompatActivity implements ObjectSuccessActivity {

    Intent intent;
    String productID;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_success);

        if(getExtras()) {

            assignHeader();

            assignButtons();

            assignMessage();
        }

    }

    public boolean getExtras(){

        boolean result = false;

        try{
            intent = getIntent();
            productID = intent.getStringExtra("PRODUCT");
            message = intent.getStringExtra("MESSAGE");

            result = true;
        }
        catch (Exception extras){
            // TODO: 2019-03-07 did not receive required msgs
        }

        return result;
    }

    public void assignHeader()
    {
        TextView header = findViewById(R.id.objectSuccessHeader);
        header.setText(R.string.products);
    }

    public void assignButtons(){

        TextView viewObject = findViewById(R.id.objectSuccessViewBtn);
        viewObject.setText(R.string.product_view_profile);

        TextView addObject = findViewById(R.id.objectSuccessAddBtn);
        addObject.setText(R.string.product_more);
    }

    public void assignMessage(){
        TextView msg = findViewById(R.id.objectSuccesMsg);
        msg.setText(message);
    }


    public void onViewObjectClick(View v){

        // opens the product profile of the just created product

        Intent showNewProductProfile = new Intent(this, ProductActivity.class);
        showNewProductProfile.putExtra("PRODUCT", productID);
        startActivity(showNewProductProfile);


    }


    public void onNewObjectClick(View v){

        //opens activity_product_new to create another new product

        Intent showActivityProductNew = new Intent(this, ProductNewActivity.class);
        startActivity(showActivityProductNew);


    }

    public void onHomeClick(View v){

        //return to activity_main

        Intent showActivityMain = new Intent(this, MainActivity.class);
        startActivity(showActivityMain);
    }

}
