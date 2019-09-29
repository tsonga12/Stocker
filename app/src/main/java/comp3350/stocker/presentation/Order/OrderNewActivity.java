package comp3350.stocker.presentation.Order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp3350.stocker.R;
import comp3350.stocker.application.Services;
import comp3350.stocker.business.OrderLogic;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.objects.Order;
import comp3350.stocker.objects.Product;
import comp3350.stocker.presentation.ObjectNewActivity;
import comp3350.stocker.presentation.OrderListAdapter;

public class OrderNewActivity extends AppCompatActivity implements ObjectNewActivity {

    private OrderLogic accessOrders;
    private Order order;
    String successMsg = "Successfully created order";
    String failureMsg = "Failed to create order";
    public List<String[]> fieldList; //string[0] == name of field; string[1] == user input value



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        accessOrders = new OrderLogic(Services.getOrderDatabase());
        //accessOrders = new OrderLogic(new OrderStub());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_new);

        assignHeader();

        assignButtons();

        //contains list of field names, and corresponding user input string values
        fieldList = populateFieldList();

    }

    public void assignHeader(){
        TextView header = findViewById(R.id.newObjectHeader);
        header.setText(R.string.orders);
    }

    public void assignButtons(){

        TextView btn = findViewById(R.id.newObjectBtn);
        btn.setText(R.string.order_create);
    }

    private List<String[]> makeFieldList(){

        List<String[]> list = new ArrayList<>();

        String[] ID = {"Order ID", ""};
        list.add(ID);

        String[] suppID = {"Supplier ID", ""};
        list.add(suppID);

        String[] date = {"Date(dd/mm/yyyy)", ""};
        list.add(date);

        String[] total = {"Total", ""};
        list.add(total);

        String[] shipping = {"Shipping method", ""};
        list.add(shipping);

        if(list.size() != accessOrders.getNumFields()){
            showFailureActivity();
        }

        return list;

    }

    // displays a list of all the product's fields and values using
    // uses custom recyclerView adapter 'FieldListAdapter.java'
    public List<String[]> populateFieldList(){

        fieldList = makeFieldList();

        try{
            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            //create new view adapter from list of fields
            OrderListAdapter adapter = new OrderListAdapter(this, null, fieldList,null, null);

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



    public void onCreateObjectClick(View v){


        //send current values in activity_product_new to logic layer

        createObjectFromText();

        if (order != null) {
            String orderID = order.getOrderID();

            Intent showOrderSuccess = new Intent(this, OrderSuccessActivity.class);
            showOrderSuccess.putExtra("ORDER", orderID);
            showOrderSuccess.putExtra("MESSAGE", successMsg);
            startActivity(showOrderSuccess);
        } else {
            showFailureActivity();
        }

    }

    // TODO: 2019-03-20 only creates simple fields for now, does not link to customer or products
    public void createObjectFromText()
    {
        try {
            //retrieve current String values in EditText fields
            String[] currFields = getCurrFields();

            String orderID = currFields[0];

            String supplierID = currFields[1];

            String date = currFields[2];
            Date orderDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);


            String total = currFields[3];
            Double orderTotal = Double.parseDouble(total);

            String shipping = currFields[4];

            List<Product> products = new ArrayList<>();


            //create new product
            order = new Order(orderID, supplierID, orderDate, orderTotal, shipping, products);
            accessOrders.insert(order);
            //if product is not created, show failure page
            if(order == null){
                showFailureActivity();
            }
        }
        catch(ObjectCreationException e){
            showFailureActivity();
        }
        catch(ParseException p)
        {
            showFailureActivity();
        }
    }

    public void onGoBackClick(View v){
        Intent showActivityProductProfile = new Intent(this, OrderProfileActivity.class);
        startActivity(showActivityProductProfile);
    }

    private void showFailureActivity()
    {
        Intent showFailureActivity = new Intent(this, OrderFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }
}
