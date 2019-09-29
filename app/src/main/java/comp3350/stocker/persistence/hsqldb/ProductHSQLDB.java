package comp3350.stocker.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectDeleteException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectUpdateException;
import comp3350.stocker.objects.Product;
import comp3350.stocker.persistence.ProductDatabase;

//import comp3350.stocker.business.exceptions.CustomerExceptions.CustomerException;

//Customer TABLE: Customers((STRING)id, (STRING)name, (STRING)location, (LONG)phonenNum)

public class ProductHSQLDB implements ProductDatabase {
    private Connection dbConnection;

    ////CONSTRUCTOR
    public ProductHSQLDB(final String dbPath)
    {
        try
        {
            this.dbConnection = DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath, "SA", "");
        }
        catch(SQLException e)
        {
            System.out.println("ERROR:" + e.getMessage());
        }
    }

    ////GET ALL CustomerS
    public List<Product> getAllProducts() throws ObjectNotFoundException
    {
        List<Product> CustomerList = new ArrayList<Product>();
        PreparedStatement statement;
        String command;
        ResultSet results;
        Product Customer;

        try
        {
            //Construct SQL command to retrieve ALL Customers from the Customer table
            command = "SELECT * FROM Product";
            statement = dbConnection.prepareStatement(command);

            //Results will be a Set containing all records in the Customer table
            results = statement.executeQuery();

            //Iterate through the ResultSet, passing the set to
            //fromResultSet() construct a Customer which is then added to the CustomerList
            while( results.next() )
            {
                Customer = fromResultSet(results);
                CustomerList.add(Customer);
            }

            //Close ResultSet and PreparedStatement to prevent memory leaks
            results.close();
            statement.close();
        }
        catch(SQLException e)
        {
            throw new ObjectNotFoundException(e);
        }

        return CustomerList;
    }

    ////INSERT METHOD
    public boolean insert(Product customer) throws ObjectCreationException
    {
        boolean result = false;
        PreparedStatement statement;
        String ID;
        String name;
        float cost;
        float price;
        String command;
        String supplier;
        int quant;

        try
        {
            ID = customer.getID();
            name = customer.getName();
            cost = customer.getCost();
            price = customer.getPrice();
            supplier = customer.getSupplier();
            quant = customer.getQuantity();


            //Construct the SQL command to be executed.
            command = "INSERT INTO Product (pID, name, cost, price, quantity, supplier) VALUES (?, ?, ?, ?, ?, ?)";

            //Create the statement to be sent to the database
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, ID);
            statement.setString(2, name);
            statement.setFloat(3, cost);
            statement.setFloat(4, price);
            statement.setInt(5,quant);
            statement.setString(6,supplier);

            //Execute the statement
            statement.executeUpdate();

            //close the statement to prevent memory leaks
            statement.close();

            //Insert was successful, return true
            result = true;
        }
        catch(SQLException e)
        {
            throw new ObjectCreationException(e);
        }

        return result;
    }

    public boolean addTag(String productID, String tag) throws ObjectCreationException {
        boolean success=false;
        PreparedStatement statement;
        String command = "INSERT INTO Product_Tags VALUES(?,?);";
        try {
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, productID);
            statement.setString(2, tag);
            statement.executeUpdate();
            statement.close();
            success=true;
        }
        catch(SQLException e)
        {
            throw new ObjectCreationException(e);
        }
        return success;
    }

    public Product searchTag(String tag) throws ObjectNotFoundException
    {
        Product product = null;
        String pID;
        PreparedStatement statement;
        String command = "SELECT pID FROM Product_Tags WHERE text = ?;";
        ResultSet results;

        try {
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, tag);
            results = statement.executeQuery();
            if(results.next() ) {
                pID = results.getString("pID");
                product = searchID(pID);
            }
            else
            {
                throw new SQLException();
            }

        }
        catch(SQLException e)
        {
            throw new ObjectNotFoundException(e);
        }
        return product;
    }

    ////DELETE METHOD
    public boolean delete(Product Customer) throws ObjectDeleteException
    {
        boolean result = false;
        PreparedStatement statement;
        String ID;
        String command;

        //Check to see if the CustomerID exists in the database
            try {
                //extract CustomerID from product variable passed to delete()
                ID = Customer.getID();

                //Construct and execute SQL command.
                command = "DELETE FROM Product WHERE pID = ?;";
                statement = dbConnection.prepareStatement(command);
                statement.setString(1, ID);
                statement.executeUpdate();
                statement.close();
                result = true;
            }
            catch(SQLException e)
            {
                throw new ObjectDeleteException(e);
            }
            return result;
    }

    public boolean editTag(String productID, String text, String currText) throws ObjectUpdateException
    {
        boolean success= false;
        PreparedStatement statement;
        String command = "UPDATE Product_Tags SET text = ? WHERE (pID = ?) AND (text = ?);";

        try
        {
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, text);
            statement.setString(2, productID);
            statement.setString(3, currText);
            statement.executeUpdate();
            statement.close();
            success = true;
        }
        catch(SQLException e)
        {
            throw new ObjectUpdateException(e);
        }
        return success;

    }

    ////UPDATE METHODS

    //UPDATE NAME
    // not done
    public boolean updatePrice(Product supp, float price) throws ObjectUpdateException
    {
        boolean result;

        PreparedStatement statement;
        String ID = supp.getID();
        String command;

        try
        {
            command = "UPDATE Product SET price = ? WHERE pID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setFloat(1, price);
            statement.setString(2, ID);
            statement.executeUpdate();
            statement.close();
            result = true;
        }
        catch(SQLException e)
        {
            throw new ObjectUpdateException(e); //Pass any SQL exceptions caught up to logic to handle.
        }

        return result;
    }

    //UPDATE PHONE NUMBER
    public boolean updateCost(Product supp, float cost) throws ObjectUpdateException
    {
        boolean result;

        PreparedStatement statement;
        String suppID = supp.getID();
        String command;

        try
        {
            command = "UPDATE Product SET cost = ? WHERE pID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setFloat(1, cost);
            statement.setString(2, suppID);
            statement.executeUpdate();
            statement.close();
            result = true;
        }
        catch(SQLException e)
        {
            throw new ObjectUpdateException(e); //Pass any SQL exceptions caught up to logic to handle.
        }
        return result;
    }

    public boolean updateQuantity(Product item, int quant) throws ObjectUpdateException
    {
        boolean result;

        PreparedStatement statement;
        String suppID = item.getID();
        String command;

        try
        {
            command = "UPDATE Product SET quantity = ? WHERE pID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setFloat(1, quant);
            statement.setString(2, suppID);
            statement.executeUpdate();
            statement.close();
            result = true;
        }
        catch(SQLException e)
        {
            throw new ObjectUpdateException(e); //Pass any SQL exceptions caught up to logic to handle.
        }
        return result;
    }

    public boolean updateSupplier(Product item, String supp) throws ObjectUpdateException
    {
        boolean result;

        PreparedStatement statement;
        String suppID = item.getID();
        String command;

        try
        {
            command = "UPDATE Product SET supplier = ? WHERE pID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, supp);
            statement.setString(2, suppID);
            statement.executeUpdate();
            statement.close();
            result = true;
        }
        catch(SQLException e)
        {
            throw new ObjectUpdateException(e); //Pass any SQL exceptions caught up to logic to handle.
        }
        return result;
    }

    public boolean updateName(Product item, String supp) throws ObjectUpdateException
    {
        boolean result;

        PreparedStatement statement;
        String suppID = item.getID();
        String command;

        try
        {
            command = "UPDATE Product SET name = ? WHERE pID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, supp);
            statement.setString(2, suppID);
            statement.executeUpdate();
            statement.close();
            result = true;
        }
        catch(SQLException e)
        {
            throw new ObjectUpdateException(e); //Pass any SQL exceptions caught up to logic to handle.
        }
        return result;
    }




    private Product fromResultSet(ResultSet results) throws SQLException
    {
        Product product= null;

        String prodID;
        String name;
        float cost;
        float price;
        String supplier;
        int quant;

        prodID = results.getString("pID");
        name = results.getString("name");
        quant = results.getInt("quantity");
        price = results.getFloat("price");
        cost = results.getFloat("cost");
        supplier = results.getString("supplier");

        product = new Product(prodID,name,cost,price,quant,supplier);
        product.setTagList(getTagList(prodID));
        if(product == null)
        {
            throw new ObjectCreationException();
        }

        return product;
    }

    private List<String> getTagList(String productID) throws SQLException
    {
        List<String> tagList =  new ArrayList<String>();
        PreparedStatement statement;
        String command = "SELECT text FROM Product_Tags WHERE pID = ?;";
        ResultSet results;

        try {
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, productID);
            results = statement.executeQuery();

            while( results.next() )
            {
                tagList.add(results.getString("TEXT"));
            }

            statement.close();
            results.close();
        }
        catch(SQLException e)
        {
            throw new ObjectNotFoundException(e);
        }


        return tagList;
    }


    public Product searchName(String supplier) throws ObjectNotFoundException
    {
        PreparedStatement statement;
        String command;
        ResultSet results;
        List<Product> dummy = new ArrayList<Product>();

        try
        {
            command = "SELECT * FROM Product WHERE name = ?";

            statement = dbConnection.prepareStatement(command);
            statement.setString(1, supplier);
            results = statement.executeQuery();

            while( results.next() )
            {
                dummy.add(fromResultSet(results));
            }

            results.close();
            statement.close();
        }
        catch(SQLException e)
        {
            throw new ObjectNotFoundException(e);
        }

        return dummy.get(0);
    }


    public Product searchID(String ID) throws ObjectNotFoundException
    {
        Product customer = null;
        PreparedStatement statement;
        String command;
        ResultSet results;
        List<Product> dummy = new ArrayList<Product>();

        try
        {
            command = "SELECT * FROM Product WHERE pID = ?";

            statement = dbConnection.prepareStatement(command);
            statement.setString(1, ID);
            results = statement.executeQuery();

            while( results.next() )
            {
                customer = fromResultSet(results);
            }
            if(customer == null)
            {
                Log.i("Customer_DATABASE: ", "Customer Returned From Search is Null");
                throw new ObjectCreationException();
            }
            else
            {
                Log.i("Customer_DATABASE: ", "Customer Returned From Search: " + customer.toString());
            }

            results.close();
            statement.close();
        }
        catch(SQLException e)
        {
            throw new ObjectNotFoundException(e);
        }

        return customer;
    }

}