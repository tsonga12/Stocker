package comp3350.stocker.presentation.Product;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import comp3350.stocker.R;

public class ProductSearchResultsActivity extends AppCompatActivity{

    Intent intent;
    String productID;
    TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search_results);

        intent = getIntent();
        productID = intent.getStringExtra("PRODUCT");

        result = findViewById(R.id.searchMsg);

        if(productID != null){
            Intent showActivityProduct = new Intent(this, ProductActivity.class);
            showActivityProduct.putExtra("PRODUCT", productID);
            startActivity(showActivityProduct);
        }
        else{
            result.setText("Product not found");
        }

    }


    public void onGoBackClick(View v){
        Intent showActivityProductSearch = new Intent(this, ProductSearchActivity.class);
        startActivity(showActivityProductSearch);
    }




}
