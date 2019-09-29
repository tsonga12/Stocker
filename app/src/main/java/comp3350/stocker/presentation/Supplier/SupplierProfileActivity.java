package comp3350.stocker.presentation.Supplier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import comp3350.stocker.R;
import comp3350.stocker.application.Services;
import comp3350.stocker.business.SupplierLogic;
import comp3350.stocker.objects.Supplier;
import comp3350.stocker.presentation.MainActivity;
import comp3350.stocker.presentation.ObjectProfileActivity;

public class SupplierProfileActivity extends AppCompatActivity implements ObjectProfileActivity
{

    private SupplierLogic accessSuppliers;
    private List<Supplier> supplierList;
    ArrayAdapter<Supplier> supplierArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_profile);
        accessSuppliers = new SupplierLogic(Services.getSupplierDatabase());

        assignHeader();

        populateObjectList();

    }

    public void assignHeader()
    {
        TextView header = (TextView) findViewById(R.id.objectHeader);
        header.setText(R.string.suppliers);
    }

    public void populateObjectList()
    {
        try
        {
            supplierList = accessSuppliers.getAllSuppliers(); //getAllSuppliers() can throw a ObjectException.
            supplierArrayAdapter = new ArrayAdapter<Supplier>(this, android.R.layout.simple_list_item_activated_2,android.R.id.text1,supplierList)
            {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    TextView text1 = view.findViewById(android.R.id.text1);
                    TextView text2 = view.findViewById(android.R.id.text2);

                    text1.setText(supplierList.get(position).getName());
                    text2.setText(supplierList.get(position).getID());

                    return view;
                }
            };

           final ListView listView = findViewById(R.id.listObjects);
            listView.setAdapter(supplierArrayAdapter);

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

    //Called when ADD NEW Button is pressed
    public void onAddNewObjectClick(View v){
        Intent showActivityNewSupplier = new Intent(this, SupplierNewActivity.class);
        startActivity(showActivityNewSupplier);
    }

    //Called when SEARCH button is pressed
    public void onSearchObjectClick(View v){
        Intent showActivitySearchSupplier = new Intent(this, SupplierSearchActivity.class);
        startActivity(showActivitySearchSupplier);
    }

    //Called when an item in the Supplier list is pressed
    public void onObjectClick(View v, int position){

        Supplier supplier = supplierList.get(position);


        if(supplier != null) {
            String supplierID = supplier.getID();
            Intent showActivitySupplier = new Intent(this, SupplierActivity.class);
            showActivitySupplier.putExtra("SUPPLIER", supplierID);
            startActivity(showActivitySupplier);
        }
        else
        {
            showFailureActivity();
        }
    }

    private void listFailure(){

        String msg = "There are no suppliers to display";
        TextView empty = findViewById(R.id.listEmpty);
        empty.setText(msg);

    }

    private void showFailureActivity()
    {
        String failureMsg = "Could Not Display Supplier List";
        Intent showFailureActivity = new Intent(this, SupplierFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }

}
