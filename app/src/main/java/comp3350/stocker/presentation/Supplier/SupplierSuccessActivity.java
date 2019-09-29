package comp3350.stocker.presentation.Supplier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import comp3350.stocker.R;
import comp3350.stocker.presentation.MainActivity;
import comp3350.stocker.presentation.ObjectSuccessActivity;
import comp3350.stocker.presentation.Product.ProductActivity;

public class SupplierSuccessActivity extends AppCompatActivity {

    Intent intent;
    String supplierID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_success);

        //get ID of new product for viewObject btn
        intent = getIntent();
        supplierID = intent.getStringExtra("SUPPLIER");

        assignHeader();

        assignButtons();

        assignMessage();

    }

    public void assignHeader()
    {
        TextView header = findViewById(R.id.objectSuccessHeader);
        header.setText(R.string.suppliers);
    }

    public void assignButtons(){

        TextView viewObject = findViewById(R.id.objectSuccessViewBtn);
        viewObject.setText(R.string.supplier_view_profile);

        TextView addObject = findViewById(R.id.objectSuccessAddBtn);
        addObject.setText(R.string.supplier_more);
    }

    public void assignMessage(){
        TextView msg = findViewById(R.id.objectSuccesMsg);
        msg.setText(R.string.supplier_success_msg);
    }


    public void onViewObjectClick(View v){

        // opens the product profile of the just created product

        Intent showNewSupplierProfile = new Intent(this, SupplierActivity.class);
        showNewSupplierProfile.putExtra("SUPPLIER", supplierID);
        startActivity(showNewSupplierProfile);


    }


    public void onNewObjectClick(View v){

        //opens activity_product_new to create another new product

        Intent showActivitySupplierNew = new Intent(this, SupplierNewActivity.class);
        startActivity(showActivitySupplierNew);


    }

    public void onHomeClick(View v){

        //return to activity_main

        Intent showActivityMain = new Intent(this, MainActivity.class);
        startActivity(showActivityMain);
    }

}
