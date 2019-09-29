package comp3350.stocker.presentation.Order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import comp3350.stocker.R;

public class OrderSearchResultsActivity extends AppCompatActivity{

    Intent intent;
    String orderID;
    TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search_results);

        intent = getIntent();
        orderID = intent.getStringExtra("ORDER");

        result = findViewById(R.id.searchMsg);

        if (orderID != null) {
            Intent showActivityOrder = new Intent(this, OrderActivity.class);
            showActivityOrder.putExtra("ORDER", orderID);
            startActivity(showActivityOrder);
        } else {
            searchFailure();
        }

    }




    public void onGoBackClick(View v){
        Intent showActivityOrderSearch = new Intent(this, OrderSearchActivity.class);
        startActivity(showActivityOrderSearch);
    }

    public void searchFailure()
    {
        result.setText("Order not found");
    }



}
