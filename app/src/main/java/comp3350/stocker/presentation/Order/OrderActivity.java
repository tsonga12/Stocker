package comp3350.stocker.presentation.Order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp3350.stocker.R;
import comp3350.stocker.application.Services;
import comp3350.stocker.business.OrderLogic;
import comp3350.stocker.business.ProductLogic;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectDeleteException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectUpdateException;
import comp3350.stocker.objects.Order;
import comp3350.stocker.objects.Product;
import comp3350.stocker.presentation.ObjectActivity;
import comp3350.stocker.presentation.OrderListAdapter;

public class OrderActivity extends AppCompatActivity implements ObjectActivity {

    Intent intent;
    List<String[]> fieldList;
    String orderID;
    Order order;
    OrderLogic accessOrders;
    ProductLogic accessProducts;
    String successMsg = "Successfully updated Order";
    String failureMsg = "Failed to update Order";
    List<Product> testProduct;
    List<Product> allProducts;
    List<Product> myProducts;
    List<Product> removeProducts;
    Product addProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        accessOrders = new OrderLogic(Services.getOrderDatabase());

        accessProducts = new ProductLogic(Services.getProductDatabase());

        intent = getIntent();

        orderID = intent.getStringExtra("ORDER");
        try {
            order = accessOrders.searchID(orderID);
            if (order == null) {
                showFailureActivity();
            }
        }
        catch(SQLException e)
        {
            showFailureActivity();
        }

        try{
            allProducts = accessProducts.getAllProducts();
            myProducts = order.getProducts();
            removeProducts = new ArrayList<>();
        }
        catch (ObjectNotFoundException e){
            Log.i("ORDER_ACTIVITY", "could not retrieve list of all products from database");
            showFailureActivity();
        }


        testProduct = new ArrayList<Product>();
        testProductList();

        assignHeader();

        fieldList = populateFieldList();

    }

    public List<Product> testProductList(){


        Product p0 = new Product("000", "p0", 8, 9, 0, "789");
        Product p1 = new Product("111", "p1", 1, 2, 3, "234");

        testProduct.add(p0);
        testProduct.add(p1);

        return testProduct;
    }


    public void assignHeader()
    {
        TextView header = findViewById(R.id.objectHeader);
        header.setText(R.string.orders);
    }

    private List<String[]> makeFieldList(){

        List<String[]> list = new ArrayList<>();

        String[] ID = {"Order ID", order.getOrderID()};
        list.add(ID);

        String[] suppID = {"Supplier ID", order.getSuppID()};
        list.add(suppID);

        SimpleDateFormat dateFormat = order.getDateFormat();
        String[] date = {"Date(dd/mm/yyyy)", dateFormat.format(order.getDate())};
        list.add(date);

        String[] total = {"Total", String.format("%s", order.getTotal())};
        list.add(total);

        String[] shipping = {"Shipping method", order.getShipping()};
        list.add(shipping);

        if(list.size() != accessOrders.getNumFields()){
            showFailureActivity();
        }

        return list;

    }


    public List<String[]> populateFieldList(){

        fieldList = makeFieldList();

        try{
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            System.out.println("P0 PRODUCT TEST ID:"+testProduct.get(0).getID());
            OrderListAdapter adapter = new OrderListAdapter(this, order, fieldList, order.getSuppID(),myProducts);

            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }
        catch(Exception e){
            showFailureActivity();
        }

        return fieldList;

    }



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



    public void onGoBackClick(View v)
    {
        Intent showActivityOrderProfile = new Intent(this, OrderProfileActivity.class);
        startActivity(showActivityOrderProfile);
    }

    public void onDeleteObjectClick(View v)
    {
        try {
            accessOrders.delete(order);
            successMsg = "Successfully deleted order";
            Intent showActivityOrderProfile = new Intent(this, OrderFailureActivity.class);
            startActivity(showActivityOrderProfile);
        }
        catch(ObjectDeleteException e)
        {
            failureMsg = "Failed to delete order";
            showFailureActivity();
        }
    }



    public void onFieldItemClick(View v){
        addProduct();
    }



    private void addProduct()
    {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Select Product");

        final Spinner spinner = new Spinner(this);


        final ArrayAdapter prodAdapter;


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                addProduct = (Product)adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                addProduct = null;
            }
        });

        prodAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allProducts);
        spinner.setAdapter(prodAdapter);


        adb.setView(spinner);

        adb.setPositiveButton("ADD PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //tag = input.getText().toString();
                if(addProduct != null) {
                    try {
                        accessOrders.insertProduct(order,addProduct);
                        refreshPage();
                    }
                    catch (ObjectUpdateException e) {
                        showFailureActivity();
                    }
                }
            }
        });

        adb.show();



    }

    public void setRemoveProductFlag(Product product){

        removeProducts.add(product);

    }

    public void cancelRemoveProductFlag(Product product){

        if(removeProducts.contains(product)){
            removeProducts.remove(product);
        }
    }

    private void removeProducts(){

        try {

            for (int i = 0; i < removeProducts.size(); i++) {
                Product product = removeProducts.get(i);
                accessOrders.removeProduct(order, product);
            }
        }
        catch (ObjectDeleteException e){
            showFailureActivity();
        }

    }

    public void refreshPage(){
        String orderID = order.getOrderID();
        Intent showOrderActivity = new Intent(this, OrderActivity.class);
        showOrderActivity.putExtra("ORDER", orderID);
        startActivity(showOrderActivity);
    }

    public void onSaveObjectClick(View v)
    {
        //send current values in activity_product_new to logic layer

        updateObject();

        removeProducts();

        String orderID = order.getOrderID();
        Intent showOrderSuccess = new Intent(this, OrderSuccessActivity.class);
        showOrderSuccess.putExtra("ORDER", orderID);
        showOrderSuccess.putExtra("MESSAGE", successMsg);
        startActivity(showOrderSuccess);


    }

    public void updateObject(){


        try{
            String[] fields = getCurrFields();

            accessOrders.updateSupplier(order, fields[1]);

            try {//needs to catch parse exception
                String date = fields[2];
                Date orderDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                accessOrders.updateDate(order, orderDate);
            }
            catch (ParseException e){
                failureMsg = "Improper date format";
                showFailureActivity();
            }

            String total = fields[3];
            double orderTotal = Double.parseDouble(total);
            accessOrders.updateTotal(order, orderTotal);

            accessOrders.updateShipping(order, fields[4]);

        }
        catch (ObjectUpdateException e)
        {
            showFailureActivity();
        }

    }

    private void showFailureActivity()
    {
        Intent showFailureActivity = new Intent(this, OrderFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }
}
