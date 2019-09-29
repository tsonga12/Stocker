package comp3350.stocker.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.stocker.R;

import comp3350.stocker.application.Services;
import comp3350.stocker.presentation.Customer.CustomerProfileActivity;

import comp3350.stocker.presentation.Order.OrderProfileActivity;
import comp3350.stocker.presentation.Product.ProductProfileActivity;
import comp3350.stocker.presentation.Supplier.SupplierProfileActivity;

public class MainActivity extends AppCompatActivity {
    private static boolean copied = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!copied) {
            copyDatabaseToDevice();
            copied = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onCustomerClick(View v) {
        Intent showCustomerProfile = new Intent(this, CustomerProfileActivity.class);
        startActivity(showCustomerProfile);
    }

    public void onProductClick(View v) {
        Intent showProductProfile = new Intent(this, ProductProfileActivity.class);
        startActivity(showProductProfile);
    }

    public void onOrderClick(View v) {
        Intent showOrderProfile = new Intent(this, OrderProfileActivity.class);
        startActivity(showOrderProfile);
    }

    public void onSupplierClick(View v) {
        Intent showSupplierProfile = new Intent(this, SupplierProfileActivity.class);
        startActivity(showSupplierProfile);
    }

    public void onHomeClick(View v) {

        //return to activity_main
        //don't know if creating a new intent is the correct way to return to an already initiated page...

        Intent showActivityMain = new Intent(this, MainActivity.class);
        startActivity(showActivityMain);
    }


    /*
    Methods used from: https://code.cs.umanitoba.ca/comp3350-winter2019/sample-project/blob/master/app/src/main/java/comp3350/srsys/presentation/HomeActivity.java
    to connect Database to the Device.
     */
    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Services.setDBPathName(dataDirectory.toString() + "/" + Services.getDBPathName());
            System.out.println("***JUST SET DB PATH NAME TO: " + Services.getDBPathName());

        } catch (final IOException ioe) {
           // Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

}
