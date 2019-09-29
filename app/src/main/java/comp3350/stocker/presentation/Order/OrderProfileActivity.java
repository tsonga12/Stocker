package comp3350.stocker.presentation.Order;

/*
This Activity is called when the user presses the Orders button on the MainActivity.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import comp3350.stocker.R;
import comp3350.stocker.application.Services;
import comp3350.stocker.business.OrderLogic;
import comp3350.stocker.objects.Order;
import comp3350.stocker.presentation.MainActivity;
import comp3350.stocker.presentation.ObjectProfileActivity;

public class OrderProfileActivity extends AppCompatActivity implements ObjectProfileActivity
{

    private OrderLogic accessOrders;
    private List<Order> orderList;
    ArrayAdapter<Order> orderArrayAdapter;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_profile);

        accessOrders = new OrderLogic(Services.getOrderDatabase());

        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        assignHeader();

        populateObjectList();
    }

    public void assignHeader()
    {
        TextView header = (TextView) findViewById(R.id.objectHeader);
        header.setText(R.string.orders);
    }

    public void populateObjectList()
    {
        try
        {
            orderList = accessOrders.getAllOrders();

            orderArrayAdapter = new ArrayAdapter<Order>(this, android.R.layout.simple_list_item_activated_2,android.R.id.text1,orderList)
            {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    TextView text1 = view.findViewById(android.R.id.text1);
                    TextView text2 = view.findViewById(android.R.id.text2);

                    text1.setText(orderList.get(position).getOrderID());

                    Date date = orderList.get(position).getDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    text2.setText(dateFormat.format(date));

                    return view;
                }
            };

            final ListView listView = findViewById(R.id.listObjects);
            listView.setAdapter(orderArrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    onObjectClick(view, position);
                }
            });


        }
        catch (final Exception e)
        {
            listFailure();
        }
    }

    public void onGoBackClick(View v)
    {
        Intent showActivityMain = new Intent(this, MainActivity.class);
        startActivity(showActivityMain);
    }

    public void onAddNewObjectClick(View v){
        Intent showActivityNewOrder = new Intent(this, OrderNewActivity.class);
        startActivity(showActivityNewOrder);
    }

    public void onSearchObjectClick(View v){
        Intent showActivitySearchOrder = new Intent(this, OrderSearchActivity.class);
        startActivity(showActivitySearchOrder);
    }

    public void onObjectClick(View v, int position){

        Order order = orderList.get(position);


        if(order != null) {

            String orderID = order.getOrderID();
            Intent showActivityOrder = new Intent(this, OrderActivity.class);
            showActivityOrder.putExtra("ORDER", orderID);
            startActivity(showActivityOrder);
        }
        else
        {
            showFailureActivity();
        }
    }

    private void listFailure(){

        String msg = "There are no orders to display";
        TextView empty = findViewById(R.id.listEmpty);
        empty.setText(msg);

    }

    private void showFailureActivity()
    {
        String failureMsg = "Failed to Display Order List";
        Intent showFailureActivity = new Intent(this, OrderFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }


}
