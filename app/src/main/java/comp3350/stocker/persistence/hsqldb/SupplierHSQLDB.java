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
import comp3350.stocker.objects.Supplier;
import comp3350.stocker.persistence.SupplierDatabase;

//SUPPLIER TABLE: Suppliers((STRING)id, (STRING)name, (STRING)location, (LONG)phonenNum)

public class SupplierHSQLDB implements SupplierDatabase {
    private Connection dbConnection;

    ////CONSTRUCTOR
    public SupplierHSQLDB(final String dbPath)
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

    ////GET ALL SUPPLIERS
    public List<Supplier> getAllSuppliers() throws ObjectNotFoundException
    {
        List<Supplier> supplierList = new ArrayList<>();
        PreparedStatement statement;
        String command;
        ResultSet results;
        Supplier supplier;

        try
        {
            //Construct SQL command to retrieve ALL suppliers from the supplier table
            command = "SELECT * FROM SUPPLIERS";
            statement = dbConnection.prepareStatement(command);

            //Results will be a Set containing all records in the supplier table
            results = statement.executeQuery();

            //Iterate through the ResultSet, passing the set to
            //fromResultSet() construct a supplier which is then added to the supplierList
            while( results.next() )
            {
                supplier = fromResultSet(results);
                supplierList.add(supplier);
            }

            //Close ResultSet and PreparedStatement to prevent memory leaks
            results.close();
            statement.close();
        }
        catch(SQLException e)
        {
            throw new ObjectNotFoundException(e);
        }

        return supplierList;
    }

    ////INSERT METHOD
    public boolean insert(Supplier supplier) throws ObjectCreationException
    {
        boolean result = false;
        PreparedStatement statement;
        String suppID;
        String name;
        String location;
        long phoneNum;
        String command;

        try
        {
            suppID = supplier.getID();
            name = supplier.getName();
            location= supplier.getLocation();
            phoneNum = supplier.getPhoneNum();

            //Construct the SQL command to be executed.
            command = "INSERT INTO SUPPLIERS (sID, name, location, phoneNum) VALUES (?, ?, ?, ?)";

            //Create the statement to be sent to the database
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, suppID);
            statement.setString(2, name);
            statement.setString(3, location);
            statement.setLong(4, phoneNum);

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

    ////DELETE METHOD
    public boolean delete(Supplier supplier) throws ObjectDeleteException
    {
        boolean result = false;
        PreparedStatement statement;
        String suppID;
        String command;

        try {
            //extract supplierID from product variable passed to delete()
            suppID = supplier.getID();

            //Construct and execute SQL command.
            command = "DELETE FROM SUPPLIERS WHERE sID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, suppID);
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

    ////UPDATE METHODS

    //UPDATE NAME
    public boolean updateName(Supplier supp, String name) throws ObjectUpdateException
    {
        boolean result;

        PreparedStatement statement;
        String suppID = supp.getID();
        String command;

        try
        {
            command = "UPDATE SUPPLIERS SET name = ? WHERE sID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, name);
            statement.setString(2, suppID);
            statement.executeUpdate();
            statement.close();
            result = true;
        }
        catch(SQLException e)
        {
            throw new ObjectUpdateException(e);
        }

        return result;
    }

    //UPDATE LOCATION
    public boolean updateLoc(Supplier supp, String location) throws ObjectUpdateException
    {
        boolean result;

        PreparedStatement statement;
        String suppID = supp.getID();
        String command;

        try
        {
            command = "UPDATE SUPPLIERS SET location = ? WHERE sID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, location);
            statement.setString(2, suppID);
            statement.executeUpdate();
            statement.close();
            result = true;
        }
        catch(SQLException e)
        {
            throw new ObjectUpdateException(e);
        }
        return result;
    }

    //UPDATE PHONE NUMBER
    public boolean updatePhoneNum(Supplier supp, long phoneNum) throws ObjectUpdateException
    {
        boolean result;

        PreparedStatement statement;
        String suppID = supp.getID();
        String command;

        try
        {
            command = "UPDATE SUPPLIERS SET phoneNum = ? WHERE sID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setLong(1, phoneNum);
            statement.setString(2, suppID);
            statement.executeUpdate();
            statement.close();
            result = true;
        }
        catch(SQLException e)
        {
            throw new ObjectUpdateException(e);
        }
        return result;
    }


    ////SEARCH METHODS
    //SEARCH ID
    public Supplier searchID(String id) throws ObjectNotFoundException
    {
        Supplier supplier = null;
        PreparedStatement statement;
        String command;
        ResultSet results;

        try
        {
            command = "SELECT * FROM SUPPLIERS WHERE sID = ?";

            statement = dbConnection.prepareStatement(command);
            statement.setString(1, id);
            results = statement.executeQuery();

            while( results.next() )
            {
                supplier = fromResultSet(results);
            }
            if(supplier == null)
            {
                Log.i("SUPPLIER_DATABASE: ", "Supplier Returned From Search is Null");
            }
            else
            {
                Log.i("SUPPLIER_DATABASE: ", "Supplier Returned From Search: " + supplier.toString());
            }

            results.close();
            statement.close();
        }
        catch(SQLException e)
        {
            throw new ObjectNotFoundException(e);
        }

        return supplier;
    }

    //SEARCH NAME
    public Supplier searchName(String name) throws ObjectNotFoundException
    {
        Supplier supplier = null;
        PreparedStatement statement;
        String command;
        ResultSet results;

        try
        {
            command = "SELECT * FROM SUPPLIERS WHERE name = ?";

            statement = dbConnection.prepareStatement(command);
            statement.setString(1, name);
            results = statement.executeQuery();

            while( results.next() )
            {
                supplier = fromResultSet(results);
            }
            if(supplier == null)
            {
                Log.i("SUPPLIER_DATABASE: ", "Supplier Returned From Search is Null");
            }
            else
            {
                Log.i("SUPPLIER_DATABASE: ", "Supplier Returned From Search: " + supplier.toString());
            }

            results.close();
            statement.close();
        }
        catch(SQLException e)
        {
            throw new ObjectNotFoundException(e);
        }

        return supplier;
    }

    //SEARCH LOCATION
    public Supplier searchLoc(String location) throws ObjectNotFoundException
    {
        Supplier supplier = null;
        PreparedStatement statement;
        String command;
        ResultSet results;

        try
        {
            command = "SELECT * FROM SUPPLIERS WHERE location = ?";

            statement = dbConnection.prepareStatement(command);
            statement.setString(1, location);
            results = statement.executeQuery();

            while( results.next() )
            {
                supplier = fromResultSet(results);
            }
            if(supplier == null)
            {
                Log.i("SUPPLIER_DATABASE: ", "Supplier Returned From Search is Null");
            }
            else
            {
                Log.i("SUPPLIER_DATABASE: ", "Supplier Returned From Search: " + supplier.toString());
            }

            results.close();
            statement.close();
        }
        catch(SQLException e)
        {
            throw new ObjectNotFoundException(e);
        }

        return supplier;
    }

    //SEARCH PHONE NUMBER
    public Supplier searchPhoneNum(long phoneNum) throws ObjectNotFoundException
    {
        Supplier supplier = null;
        PreparedStatement statement;
        String command;
        ResultSet results;

        try
        {
            command = "SELECT * FROM SUPPLIERS WHERE phoneNum = ?";

            statement = dbConnection.prepareStatement(command);
            statement.setLong(1, phoneNum);
            results = statement.executeQuery();

            while( results.next() )
            {
                supplier = fromResultSet(results);
            }
            if(supplier == null)
            {
                Log.i("SUPPLIER_DATABASE: ", "Supplier Returned From Search is Null");
            }
            else
            {
                Log.i("SUPPLIER_DATABASE: ", "Supplier Returned From Search: " + supplier.toString());
            }

            results.close();
            statement.close();
        }
        catch(SQLException e)
        {
            throw new ObjectNotFoundException(e);
        }

        return supplier;
    }

    private Supplier fromResultSet(ResultSet results) throws SQLException
    {
        Supplier supplier= null;

        String suppID;
        String name;
        String location;
        long phoneNum;


        suppID = results.getString("sID");
        name= results.getString("name");
        location = results.getString("location");
        phoneNum = results.getLong("phoneNum");

        supplier = new Supplier(suppID, name, location, phoneNum);


        return supplier;
    }

}//SupplierStub
