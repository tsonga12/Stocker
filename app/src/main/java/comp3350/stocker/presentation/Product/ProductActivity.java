package comp3350.stocker.presentation.Product;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comp3350.stocker.R;
import comp3350.stocker.application.Services;
import comp3350.stocker.business.ProductLogic;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectDeleteException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectUpdateException;
import comp3350.stocker.objects.Product;
import comp3350.stocker.presentation.FieldListAdapter;
import comp3350.stocker.presentation.ObjectActivity;

public class ProductActivity extends AppCompatActivity implements ObjectActivity {

    Intent intent;
    String productID;
    Product product;
    List<String[]> fieldList;
    String successMsg = "Successfully updated product";
    String failureMsg;
    ProductLogic accessProducts;
    private String tag = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        accessProducts = new ProductLogic(Services.getProductDatabase());

        intent = getIntent();
        productID = intent.getStringExtra("PRODUCT");
        try {
            product = accessProducts.searchID(productID);
        }
        catch(ObjectNotFoundException e)
        {
            showFailureActivity();
        }

        assignHeader();

        fieldList = populateFieldList();

    }


    public void assignHeader()
    {
        TextView header = findViewById(R.id.objectHeader);
        header.setText(R.string.products);
    }

    private void createTagList()
    {
        List<String> tagList = product.getTags();
        final TextView[] tags = new TextView[tagList.size()];
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(200,100);
        lp.setMargins(10,0,10,0);

        try
        {
            HorizontalScrollView tagView = findViewById(R.id.tagList);
            tagView.setVisibility(View.VISIBLE);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            Button add = new Button(this);
            add.setText("+");
            add.setTextSize(20);
            add.setTypeface(null, Typeface.BOLD);
            add.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v)
                {
                    addTag();
                }
            });
            linearLayout.addView(add);

            for(int i = 0; i < tags.length; i++)
            {
                final int x = i;
                tags[i] = new TextView(this);
                tags[i].setText(tagList.get(i));
                tags[i].setTextSize(20);
                tags[i].setTypeface(null, Typeface.BOLD);
                tags[i].setLayoutParams(lp);
                tags[i].setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v)
                    {
                        editTag(tags[x]);
                    }
                });
                linearLayout.addView(tags[i]);
            }
            tagView.addView(linearLayout);
        }
        catch(Exception e) //TODO: David: 3/27/2019 - Figure out a more specific exception type that is supposed to be caught here
        {
            showFailureActivity();
        }
    }

    public void editTag(final TextView tag)
    {
        final int id = 42;
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Edit Tag");

        final EditText input = new EditText(this);
        input.setId(id);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        adb.setView(input);

        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    accessProducts.editTag(productID, input.getText().toString(), tag.getText().toString());
                    tag.setText(input.getText().toString());
                }
                catch(ObjectUpdateException e)
                {
                    showFailureActivity();
                }
            }
        });

        adb.show();
    }

    private void addTag()
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("New Tag");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        adb.setView(input);

        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tag = input.getText().toString();
                try {
                    accessProducts.addTag(productID, tag);
                }
                catch(ObjectCreationException e)
                {
                    showFailureActivity();
                }
            }
        });

        adb.show();



    }

    private List<String[]> makeFieldList(){
        List<String[]> list = new ArrayList<>();

        String[] ID = {"ProductID", product.getID()};
        list.add(ID);

        String[] name = {"Name", product.getName()};
        list.add(name);

        String[] cost = {"Cost", String.format("%s", product.getCost())};
        list.add(cost);

        String[] price = {"Price", String.format("%s", product.getPrice())};
        list.add(price);

        String[] quantity = {"Quantity", String.format("%s", product.getQuantity())};
        list.add(quantity);

        String[] supplier = {"Supplier", product.getSupplier()};
        list.add(supplier);

        if(list.size() != accessProducts.getNumFields()){
            showFailureActivity();
        }

        return list;
    }


    public List<String[]> populateFieldList(){


        fieldList = makeFieldList();

        createTagList();

        try{
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            FieldListAdapter adapter = new FieldListAdapter(fieldList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        Intent showActivityProductProfile = new Intent(this, ProductProfileActivity.class);
        startActivity(showActivityProductProfile);
    }

    public void onDeleteObjectClick(View v)
    {
        try {
            accessProducts.delete(product);
            Intent showActivityProductProfile = new Intent(this, ProductProfileActivity.class);
            startActivity(showActivityProductProfile);
        }
        catch(ObjectDeleteException e)
        {
            failureMsg = "Failed to delete product";
            showFailureActivity();
        }
    }


    public void onSaveObjectClick(View v)
    {
        //send current values in activity_product_new to logic layer

        updateObject();

        try {

             String productID = product.getID();
             Intent showProductSuccess = new Intent(this, ProductSuccessActivity.class);
             showProductSuccess.putExtra("PRODUCT", productID);
             showProductSuccess.putExtra("MESSAGE", successMsg);
             startActivity(showProductSuccess);
        }
        catch(Exception e)
        {
            showFailureActivity();
        }

    }


    public void updateObject(){

        try {

            String[] fields = getCurrFields();

            accessProducts.updateName(product, fields[1]);

            float cost = Float.parseFloat(fields[2]);
            accessProducts.updateCost(product, cost);

            float price = Float.parseFloat(fields[3]);
            accessProducts.updatePrice(product, price);

            int quantity = Integer.parseInt(fields[4]);
            accessProducts.updateQuantity(product, quantity);

            accessProducts.updateSupplier(product, fields[5]);
        }
        catch (ObjectUpdateException e){
            showFailureActivity();
        }



    }

    private void showFailureActivity()
    {
        Intent showFailureActivity = new Intent(this, ProductFailureActivity.class);
        showFailureActivity.putExtra("MESSAGE", failureMsg);
        startActivity(showFailureActivity);
    }
}
