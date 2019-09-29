package comp3350.stocker.presentation.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import comp3350.stocker.R;
import comp3350.stocker.presentation.MainActivity;
import comp3350.stocker.presentation.ObjectFailureActivity;

public class CustomerFailureActivity extends AppCompatActivity implements ObjectFailureActivity {

    Intent intent;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_failure);

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
        TextView header = findViewById(R.id.objectFailureHeader);
        header.setText(R.string.customers);
    }

    public void assignButtons(){

        TextView addObject = findViewById(R.id.objectFailureAddBtn);
        addObject.setText(R.string.product_more);
    }

    public void assignMessage(){
        TextView msg = findViewById(R.id.objectFailureMsg);
        msg.setText(message);
    }


    public void onNewObjectClick(View v){

        //opens activity_product_new to create another new customer

        Intent showActivityCustomerNew = new Intent(this, CustomerNewActivity.class);
        startActivity(showActivityCustomerNew);


    }

    public void onHomeClick(View v){

        //return to activity_main

        Intent showActivityMain = new Intent(this, MainActivity.class);
        startActivity(showActivityMain);
    }
}
