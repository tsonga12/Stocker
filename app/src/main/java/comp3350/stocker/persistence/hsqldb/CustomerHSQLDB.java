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
import comp3350.stocker.objects.Customer;
import comp3350.stocker.persistence.CustomerDatabase;

//import comp3350.stocker.business.exceptions.CustomerExceptions.CustomerException;

//Customer TABLE: Customers((STRING)id, (STRING)name, (STRING)location, (LONG)phonenNum)

public class CustomerHSQLDB implements CustomerDatabase {
    private Connection dbConnection;

    ////CONSTRUCTOR
    public CustomerHSQLDB(final String dbPath)
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
    public List<Customer> getAllCustomers() throws ObjectNotFoundException
    {
        List<Customer> CustomerList = new ArrayList<>();
        PreparedStatement statement;
        String command;
        ResultSet results;
        Customer Customer;

        try
        {
            //Construct SQL command to retrieve ALL Customers from the Customer table
            command = "SELECT * FROM Customer";
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
    public boolean insert(Customer customer) throws ObjectCreationException
    {
        boolean result = false;
        PreparedStatement statement;
        String ID;
        String name;
        String location;
        long phoneNum;
        String command;
        String email;

        try
        {
            ID = customer.getLastName();
            name = customer.getFirstName();
            location = customer.getAddress();
            phoneNum = customer.getPhoneNum();
            email = customer.getEmail();


            //Construct the SQL command to be executed.
            command = "INSERT INTO Customer (cID, name, location, phoneNum, email) VALUES (?, ?, ?, ?, ?)";

            //Create the statement to be sent to the database
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, ID);
            statement.setString(2, name);
            statement.setString(3, location);
            statement.setLong(4, phoneNum);
            statement.setString(5,email);

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
    public boolean delete(Customer Customer) throws ObjectDeleteException
    {
        boolean result = false;
        PreparedStatement statement;
        String ID;
        String command;

            try {
                //extract CustomerID from product variable passed to delete()
                ID = Customer.getLastName();

                //Construct and execute SQL command.
                command = "DELETE FROM Customer WHERE cID = ?;";
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

    ////UPDATE METHODS

    //UPDATE NAME
    // not done

    public boolean updateFirstName(Customer cust, String name) throws ObjectUpdateException
    {
        boolean result;

        PreparedStatement statement;
        String ID = cust.getLastName();
        String command;

        try
        {
            command = "UPDATE Customer SET name = ? WHERE cID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, name);
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

    public boolean updateLastName(Customer cust, String name) throws ObjectUpdateException
    {
        // TODO: 2019-04-03 finish method once customerID is set to the email instead 
        return true;
//        boolean result;
//
//        PreparedStatement statement;
//        String ID = cust.getLastName();
//        String command;
//
//        try
//        {
//            command = "UPDATE Customer SET name = ? WHERE cID = ?;";
//            statement = dbConnection.prepareStatement(command);
//            statement.setString(1, name);
//            statement.setString(2, ID);
//            statement.executeUpdate();
//            statement.close();
//            result = true;
//        }
//        catch(SQLException e)
//        {
//            throw new ObjectUpdateException(e); //Pass any SQL exceptions caught up to logic to handle.
//        }
//
//        return result;
    }

    public boolean updateAddr(Customer cust, String name) throws ObjectUpdateException
    {
        boolean result;

        PreparedStatement statement;
        String ID = cust.getLastName();
        String command;

        try
        {
            command = "UPDATE Customer SET location = ? WHERE cID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, name);
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
    public boolean updatePhoneNum(Customer cust, long phoneNum) throws ObjectUpdateException
    {
        boolean result;

        PreparedStatement statement;
        String ID = cust.getLastName();
        String command;

        try
        {
            command = "UPDATE Customer SET phoneNum = ? WHERE cID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setLong(1, phoneNum);
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


    ////SEARCH METHODS
    //SEARCH ID
    public Customer searchEmail(String email) throws ObjectNotFoundException
    {
        Customer Customer = null;
        PreparedStatement statement;
        String command;
        ResultSet results;

        try
        {
            command = "SELECT * FROM Customer WHERE email = ?";

            statement = dbConnection.prepareStatement(command);
            statement.setString(1, email);
            results = statement.executeQuery();

            while( results.next() )
            {
                Customer = fromResultSet(results);
            }
            if(Customer == null)
            {
                Log.i("Customer_DATABASE: ", "Customer Returned From Search is Null");
            }
            else
            {
                Log.i("Customer_DATABASE: ", "Customer Returned From Search: " + Customer.toString());
            }

            results.close();
            statement.close();
        }
        catch(SQLException e)
        {
            throw new ObjectNotFoundException(e);
        }

        return Customer;
    }

    private Customer fromResultSet(ResultSet results) throws ObjectCreationException
    {
        Customer customer= null;

        String custID;
        String name;
        String location;
        long phoneNum;
        String email;


        try {
            custID = results.getString("cID");
            name = results.getString("name");
            location = results.getString("location");
            phoneNum = results.getLong("phoneNum");
            email = results.getString("email");
        }
        catch(SQLException e)
        {
            throw new ObjectCreationException(e);
        }

        customer = new Customer(name, custID, email, location, phoneNum);
        if(customer == null)
        {
            throw new ObjectCreationException();
        }

        return customer;
    }


    public List<Customer> searchFirstName(String first) throws ObjectNotFoundException
    {
        PreparedStatement statement;
        String command;
        ResultSet results;
        List<Customer> dummy = new ArrayList<Customer>();

        try
        {
            command = "SELECT * FROM Customer WHERE name = ?";

            statement = dbConnection.prepareStatement(command);
            statement.setString(1, first);
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

        return dummy;
    }

    public List<Customer> searchLastName(String last) throws ObjectNotFoundException
    {
        PreparedStatement statement;
        String command;
        ResultSet results;
        List<Customer> dummy = new ArrayList<Customer>();

        try
        {
            command = "SELECT * FROM Customer WHERE cID = ?";

            statement = dbConnection.prepareStatement(command);
            statement.setString(1, last);
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
            throw new ObjectNotFoundException();
        }

        return dummy;
    }

    public List<Customer> searchAddr(String addr) throws ObjectNotFoundException
    {
        PreparedStatement statement;
        String command;
        ResultSet results;
        List<Customer> dummy = new ArrayList<Customer>();

        try
        {
            command = "SELECT * FROM Customer WHERE location = ?";

            statement = dbConnection.prepareStatement(command);
            statement.setString(1, addr);
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
            throw new ObjectNotFoundException();
        }

        return dummy;
    }

    public List<Customer> searchPhoneNum(long phone) throws ObjectNotFoundException
    {
        PreparedStatement statement;
        String command;
        ResultSet results;
        List<Customer> dummy = new ArrayList<Customer>();

        try
        {
            command = "SELECT * FROM Customer WHERE phoneNum = ?";

            statement = dbConnection.prepareStatement(command);
            statement.setLong(1, phone);
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
            throw new ObjectNotFoundException();
        }

        return dummy;
    }

}