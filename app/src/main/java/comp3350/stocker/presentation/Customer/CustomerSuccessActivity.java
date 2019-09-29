package comp3350.stocker.presentation.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import comp3350.stocker.R;
import comp3350.stocker.presentation.MainActivity;
import comp3350.stocker.presentation.ObjectSuccessActivity;

public class CustomerSuccessActivity extends AppCompatActivity implements ObjectSuccessActivity{

    Intent intent;
    String customerID;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_success);

        //get ID of new product for viewObject btn
        intent = getIntent();

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
            customerID = intent.getStringExtra("CUSTOMER");
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
        header.setText(R.string.customers);
    }

    public void assignButtons(){

        TextView viewObject = findViewById(R.id.objectSuccessViewBtn);
        viewObject.setText(R.string.customer_view_profile);

        TextView addObject = findViewById(R.id.objectSuccessAddBtn);
        addObject.setText(R.string.customer_more);
    }

    public void assignMessage() {
        TextView msg = findViewById(R.id.objectSuccesMsg);
        msg.setText(message);
    }

    public void onViewObjectClick(View v){
        Intent showNewCustomerProfile = new Intent(this, CustomerActivity.class);
        showNewCustomerProfile.putExtra("CUSTOMER", customerID);
        startActivity(showNewCustomerProfile);
    }

    public void onNewObjectClick(View v) {
        Intent showNewCustomerProfile = new Intent(this, CustomerNewActivity.class);
        showNewCustomerProfile.putExtra("CUSTOMER", customerID);
        startActivity(showNewCustomerProfile);

    }

    public void onHomeClick(View v){

        Intent showActivityMain = new Intent(this, MainActivity.class);
        startActivity(showActivityMain);
    }
}
