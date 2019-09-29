package comp3350.stocker.presentation.Order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import comp3350.stocker.R;
import comp3350.stocker.presentation.MainActivity;
import comp3350.stocker.presentation.ObjectSuccessActivity;

public class OrderSuccessActivity extends AppCompatActivity implements ObjectSuccessActivity {

    Intent intent;
    String orderID;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_success);

        //get ID of new product for viewObject btn
        intent = getIntent();
        //orderID = intent.getStringExtra("ORDER");

        if(getExtras()) {
            assignHeader();

            assignButtons();

            assignMessage();
        }
    }


    public boolean getExtras(){

        boolean result = false;

        orderID = intent.getStringExtra("ORDER");
        message = intent.getStringExtra("MESSAGE");
        result = true;


        return result;
    }

    public void assignHeader()
    {
        TextView header = findViewById(R.id.objectSuccessHeader);
        header.setText(R.string.orders);
    }

    public void assignButtons(){

        TextView viewObject = findViewById(R.id.objectSuccessViewBtn);
        viewObject.setText(R.string.order_view_profile);

        TextView addObject = findViewById(R.id.objectSuccessAddBtn);
        addObject.setText(R.string.order_more);
    }

    public void assignMessage() {
        TextView msg = findViewById(R.id.objectSuccesMsg);
        msg.setText(message);
    }

    public void onViewObjectClick(View v){

        Intent showNewOrderProfile = new Intent(this, OrderActivity.class);
        showNewOrderProfile.putExtra("ORDER", orderID);
        startActivity(showNewOrderProfile);
    }

    public void onNewObjectClick(View v) {
        Intent showNewOrderProfile = new Intent(this, OrderNewActivity.class);
        startActivity(showNewOrderProfile);

    }

    public void onHomeClick(View v){

        Intent showActivityMain = new Intent(this, MainActivity.class);
        startActivity(showActivityMain);
    }
}
