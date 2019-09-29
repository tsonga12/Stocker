package comp3350.stocker.presentation.Product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ListView;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import comp3350.stocker.R;
import comp3350.stocker.application.Services;
import comp3350.stocker.business.ProductLogic;
import comp3350.stocker.objects.Product;
import comp3350.stocker.presentation.MainActivity;
import comp3350.stocker.presentation.ObjectProfileActivity;

public class ProductProfileActivity extends AppCompatActivity implements ObjectProfileActivity {

    private ProductLogic accessProducts;
    private List<Product> productList;
    ArrayAdapter<Product> productArrayAdapter;
;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_profile);

        accessProducts = new ProductLogic(Services.getProductDatabase());

        assignHeader();

        populateObjectList();


    }

    public void assignHeader()
    {
        TextView header = findViewById(R.id.objectHeader);
        header.setText(R.string.products);
    }

    public void populateObjectList()
    {
        try
        {
            productList = accessProducts.getAllProducts();

            productArrayAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_activated_2,android.R.id.text1,productList)
            {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    TextView text1 = view.findViewById(android.R.id.text1);
                    TextView text2 = view.findViewById(android.R.id.text2);

                    text1.setText(productList.get(position).getID());
                    text2.setText(productList.get(position).getName());

                    return view;
                }
            };

            final ListView listView = findViewById(R.id.listObjects);
            listView.setAdapter(productArrayAdapter);

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
        Intent showActivityNewProduct = new Intent(this, ProductNewActivity.class);
        startActivity(showActivityNewProduct);
    }

    public void onSearchObjectClick(View v){
        Intent showActivitySearchProduct = new Intent(this, ProductSearchActivity.class);
        startActivity(showActivitySearchProduct);
    }

    public void onObjectClick(View v, int position){

        Product product = productList.get(position);

        if(product != null) {
            String productID = product.getID();
            Intent showActivityProduct = new Intent(this, ProductActivity.class);
            showActivityProduct.putExtra("PRODUCT", productID);
            startActivity(showActivityProduct);
        }
        else
        {
            showFailureActivity();
        }
    }

    private void listFailure(){

        String msg = "There are no products to display";
        TextView empty = findViewById(R.id.listEmpty);
        empty.setText(msg);

    }


    private void showFailureActivity()
    {
        String failureMsg = "Failed to Display Product List";
        Intent showFailureActivity = new Intent(this, ProductFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }
}
