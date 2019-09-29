package comp3350.stocker.presentation.Supplier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import comp3350.stocker.R;

public class SupplierSearchResultsActivity extends AppCompatActivity{

    Intent intent;
    String supplierID;
    TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search_results);

        intent = getIntent();
        supplierID = intent.getStringExtra("SUPPLIER");

        result = findViewById(R.id.searchMsg);

        if(supplierID != null){
            Intent showActivityProduct = new Intent(this, SupplierActivity.class);
            showActivityProduct.putExtra("SUPPLIER", supplierID);
            startActivity(showActivityProduct);
        }
        else{
            result.setText("Supplier not found");
        }

    }


    public void onGoBackClick(View v){
        Intent showActivitySupplierSearch = new Intent(this, SupplierSearchActivity.class);
        startActivity(showActivitySupplierSearch);
    }




}
