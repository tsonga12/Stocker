package comp3350.stocker.presentation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import comp3350.stocker.R;
import comp3350.stocker.application.Services;
import comp3350.stocker.business.SupplierLogic;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.objects.Order;
import comp3350.stocker.objects.Product;
import comp3350.stocker.objects.Supplier;
import comp3350.stocker.presentation.Order.OrderActivity;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>{

    private static Context context;
    private Order order;
    private static List<String[]> listData;//list of order fields
    private SupplierLogic accessSuppliers;
    private ArrayAdapter<String>  suppAdapter; //supplier view adapter
    private static String suppID; //current supplier ID
    private static String[] suppList; //list of all suppliers
    private static List<Product> myProducts;
    private String[][] productsOwn; //list of this order's products
    private static int numProducts; //number of products in order
    private static int numFields;
    private ArrayAdapter<String>[] prodAdapters; //product view adapter
    private static int numButtons; //number of buttons to attach to view
    private boolean dataToDisplay;

    public String getSupplierID(){ return suppID; }

    final int SUPP_ID_POS = 1; //position of supplierID in listData

    // RecyclerView recyclerView;
    public OrderListAdapter(Context context, Order order, List<String[]> listData, String suppID, List<Product> products) throws ObjectNotFoundException {

        this.context = context;
        this.order = order;
        this.listData = listData;
        this.suppID = suppID;
        myProducts = products;
        suppList = null;
        productsOwn = null;
        numButtons = 1;

        if(listData != null) {
            numFields = listData.size();

            if(listData.get(0)[1] != "") {
                dataToDisplay = true;
            }
            else{
                dataToDisplay = false;
            }
        }



        try { //access supplier information for supplier list & create adapter
            accessSuppliers = new SupplierLogic(Services.getSupplierDatabase());

            suppList = getSupplierList();
            if(suppList != null){
                suppAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, suppList);
            }
        }
        catch (ObjectNotFoundException e){
            Log.i("ORDER_LIST_ADAPTER", "supplier list not set to ArrayAdapter");
        }

        if(products == null) {
            numButtons = 0;
            numProducts = 0;
        }
        else {
            try { //get product details and create array of product adapters
                productsOwn = getProductsOwn(products);

                if (productsOwn != null) {

                    int numProducts = productsOwn.length;

                    prodAdapters = new ArrayAdapter[numProducts];

                    for (int i = 0; i < numProducts; i++) {

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, productsOwn[i]);
                        prodAdapters[i] = adapter;

                    }

                }
            } catch (Exception e) {
                Log.i("ORDER_LIST_ADAPTER", "could not create list of product IDs");
                throw e;
            }
        }

    }




    public String[][] getProductsOwn(List<Product> products) {

        try {

            numProducts = products.size();

            productsOwn = new String[numProducts][2];

            for (int i = 0; i < numProducts; i++) {

                productsOwn[i][0] = (products.get(i)).getName();
                productsOwn[i][1] = "Remove";

            }

        }
        catch (Exception e){
            Log.i("ORDER_LIST_ADAPTER", "could not create list of productOwn");
            throw e;
        }

        return productsOwn;

    }

    public String[] getSupplierList() throws ObjectNotFoundException {

        try {
            List<Supplier> suppliers = accessSuppliers.getAllSuppliers();

            suppList = new String[suppliers.size()];

            int i = 0; // index count for adding to suppList
            int j = 0;

            //if suppID supplied: set at top of suppList
            if(suppID != null) {
                //the given suppID
                suppList[j] = suppID;
                j++;
            }
            else{
                Supplier supp = suppliers.get(i);
                suppID = supp.getID();
                suppList[j] = suppID;
                i++;
                j++;
            }

            while( i<suppliers.size() ){
                Supplier supp = suppliers.get(i);
                String addID = supp.getID();

                if(!addID.equals(suppID)) {//if not suppID at top of list, add
                    suppList[j] = addID;
                    j++;
                }

                i++;
            }
        }
        catch (ObjectNotFoundException e){
            throw e;
        }

        return suppList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.field_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        System.out.println(holder.spinner.getVisibility());

        if(position >= listData.size()){ //if past fieldList

            if(position == getItemCount()-1){ //if last item add button
                holder.textView.setVisibility(View.INVISIBLE);
                holder.editText.setVisibility(View.INVISIBLE);
                holder.button.setVisibility(View.VISIBLE);
            }
            else { //add products
                int currProd = position - listData.size();
                int currDisplay = currProd + 1;
                holder.editText.setVisibility(View.INVISIBLE);
                holder.textView.setText("Product " + currDisplay);
                holder.spinner.setVisibility(View.VISIBLE);
                holder.spinner.setAdapter(prodAdapters[currProd]);
            }
        }
        else { //fieldList items

            final String[] fieldData = listData.get(position);
            holder.textView.setText(fieldData[0]);

            //if currently at supplierID field, display spinner of supplierIDs
            if(position == 0 && dataToDisplay){
                holder.editText.setVisibility(View.INVISIBLE);
                holder.idText.setVisibility(View.VISIBLE);

                holder.idText.setText(fieldData[1]);
            }
            else if (position == SUPP_ID_POS) {
                holder.editText.setVisibility(View.INVISIBLE);
                holder.spinner.setVisibility(View.VISIBLE);
                holder.spinner.setAdapter(suppAdapter);
            } else {
                holder.editText.setText(fieldData[1]);
                holder.editText.setVisibility(View.VISIBLE);
                holder.idText.setVisibility(View.INVISIBLE);
                holder.spinner.setVisibility(View.INVISIBLE);
            }
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // determine if any function is desirable onClick(I don't think so, but this function must be overridden anyways)
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        int itemCount = listData.size() + numProducts + numButtons;
        return itemCount;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EditText editText;
        public TextView idText;
        public TextView textView;
        public Spinner spinner;
        public Button button;
        public RelativeLayout relativeLayout;



        public ViewHolder(View itemView) {
            super(itemView);
            this.editText = (EditText) itemView.findViewById(R.id.editText);
            this.idText = (TextView) itemView.findViewById(R.id.idText);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.spinner = (Spinner) itemView.findViewById(R.id.spinner);
            this.button = (Button) itemView.findViewById(R.id.button);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id){

                    if(getAdapterPosition() == 1) { // if  curr view position is supplierID
                        //assign supplier ID
                        suppID = suppList[position];
                        listData.get(1)[1] = suppID;

                    }
                    else{ //curr view position is on a product

                        if(context instanceof OrderActivity) {
                            OrderActivity orderActivity = (OrderActivity) context;

                            int currProduct = getAdapterPosition() - numFields - numButtons + 1;
                            System.out.println("currProduct: " + currProduct);
                            System.out.println(myProducts.size());
                            Product product = myProducts.get(currProduct);

                            if (position == 0) {
                                orderActivity.cancelRemoveProductFlag(product);
                            }

                            if (position == 1) {
                                orderActivity.setRemoveProductFlag(product);
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent){

                }
            });
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                //each time the text is changed, the value in the listData is updated to current String
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    listData.get(getAdapterPosition())[1] = editText.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
}

