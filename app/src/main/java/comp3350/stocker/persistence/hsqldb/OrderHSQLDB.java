package comp3350.stocker.persistence.hsqldb;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.stocker.application.DateConverter;
import comp3350.stocker.application.Services;
import comp3350.stocker.business.ProductLogic;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectDeleteException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectUpdateException;
import comp3350.stocker.objects.Order;
import comp3350.stocker.objects.Product;
import comp3350.stocker.persistence.OrderDatabase;

public class OrderHSQLDB implements OrderDatabase {

    private Connection dbConnection;

    public OrderHSQLDB(final String dbPath)
    {
        try
        {
            this.dbConnection = DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath, "SA", "");
        }
        catch(SQLException se)
        {
            //TODO: Better exception handling.
            System.out.println("ERROR CONNECTING TO DATABASE: " + se.getMessage());
        }
    }
    @Override
    public List<Order> getAllOrders() throws ObjectNotFoundException {
        List<Order> orderList = new ArrayList<Order>();
        PreparedStatement statement;
        String command;
        ResultSet results;
        Order order;

        try
        {
            command = "SELECT * FROM Order;";
            statement = dbConnection.prepareStatement(command);
            results = statement.executeQuery();

            while( results.next() )
            {
                order = fromResultSet(results);
                orderList.add(order);
            }

            results.close();
            statement.close();
        }
        catch(SQLException e)
        {
            throw new ObjectNotFoundException(e);
        }

        return orderList;
    }

    //TODO: Add Order's productList to productlist table in db
    @Override
    public boolean insert(Order order) throws ObjectCreationException {
        PreparedStatement statement;
        String command;
        boolean result = false;
        DateConverter dc = new DateConverter();

        String orderID;
        String suppID;
        Date date;
        double total;
        String shipping;

        try
        {
            orderID = order.getOrderID();
            suppID = order.getSuppID();
            date = dc.toSqlDate(order.getDate().getTime());
            total = order.getTotal();
            shipping = order.getShipping();

            command = "INSERT INTO Order(oID, suppID, date, total, shipping) VALUES (?, ?, ?, ?, ?);";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, orderID);
            statement.setString(2, suppID);
            //TODO: Look into changing Order objects Date field to java.sql.Date type
            statement.setDate(3, date);
            statement.setDouble(4, total);
            statement.setString(5, shipping);
            statement.executeUpdate();
            statement.close();
            //TODO: call insertProductList()
            insertProductList(order.getOrderID(), order.getProducts());
            result=true;

        }
        catch(SQLException e)
        {
            throw new ObjectCreationException(e);
        }
        return result;

    }

    public void insertProductList(String orderID, List<Product> productList) throws ObjectCreationException
    {
        PreparedStatement statement;
        String command;

        try
        {
            command = "INSERT INTO Order_Products(oID, pID) VALUES (?, ?);";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, orderID);

            for(int i = 0; i < productList.size(); i++)
            {
                statement.setString(2, productList.get(i).getID());
                statement.executeUpdate();
            }

            statement.close();
        }
        catch(SQLException e)
        {
            throw new ObjectCreationException(e);
        }
    }

    public void insertProduct(String orderID, String productID) throws ObjectUpdateException
    {
        PreparedStatement statement;
        String command;

        try
        {
            command = "INSERT INTO Order_Products(oID, pID) VALUES (?, ?);";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, orderID);

            statement.setString(2, productID);
            statement.executeUpdate();

            statement.close();
        }
        catch(SQLException e)
        {
            throw new ObjectUpdateException(e);
        }
    }

    public void removeProduct(String orderID, String productID) throws ObjectDeleteException
    {
        PreparedStatement statement;
        String command;

        try
        {
            command = "DELETE FROM Order_Products WHERE (oID = ?) AND (pID = ?)";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, orderID);

            statement.setString(2, productID);
            statement.executeUpdate();

            statement.close();
        }
        catch(SQLException e)
        {
            throw new ObjectDeleteException();
        }
    }

    @Override
    public boolean delete(Order order) throws ObjectDeleteException {
        PreparedStatement statement;
        String orderID;
        String command;

        try
        {
            orderID = order.getOrderID();
            command = "DELETE FROM Order WHERE oID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, orderID);
            statement.executeUpdate();

            command = "DELETE FROM Order_Products WHERE oID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, orderID);
            statement.executeUpdate();
            statement.close();
        }
        catch(SQLException e)
        {
            throw new ObjectDeleteException(e);
        }
        return true;
    }


    //TODO: Change return type to void...method either updates successfully, or throws exception.
    @Override
    public boolean updateDate(Order order, java.util.Date date) throws ObjectUpdateException {
        DateConverter dc;
        PreparedStatement statement;
        String command;
        boolean result = false;

        try
        {
            dc = new DateConverter();
            command = "UPDATE Order SET date = ? WHERE oID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setDate(1, dc.toSqlDate(date.getTime()));
            statement.setString(2, order.getOrderID());
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

    @Override
    public boolean updateTotal(Order order, double total) throws ObjectUpdateException {
        PreparedStatement statement;
        String command;
        boolean result = false;

        try
        {
            command = "UPDATE Order SET total = ? WHERE oID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setDouble(1, total);
            statement.setString(2, order.getOrderID());
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

    @Override
    public boolean updateShipping(Order order, String shipping) throws ObjectUpdateException {
        PreparedStatement statement;
        String command;
        boolean result = false;

        try
        {
            command = "UPDATE Order SET shipping = ? WHERE oID = ?;";
            statement =  dbConnection.prepareStatement(command);
            statement.setString(1, shipping);
            statement.setString(2, order.getOrderID());
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

    @Override
    public boolean updateSupplier(Order order, String supplierID) throws ObjectUpdateException {
        PreparedStatement statement;
        String command;
        boolean result = false;

        try
        {
            command = "UPDATE Order SET suppID = ? WHERE oID = ?;";
            statement =  dbConnection.prepareStatement(command);
            statement.setString(1, supplierID);
            statement.setString(2, order.getOrderID());
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

    @Override
    public Order searchID(String orderID) throws ObjectNotFoundException {
        PreparedStatement statement = null;
        String command;
        ResultSet results = null;
        Order order = null;

        try
        {
            command = "SELECT * FROM Order WHERE oID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, orderID);

            results = statement.executeQuery();
            while( results.next() )
            {
                order = fromResultSet(results);
            }
            statement.close();
            results.close();
        }
        catch(SQLException e)
        {
            throw new ObjectNotFoundException(e);
        }



        return order;
    }

    @Override
    public List<Order> searchSupplier(String suppID) throws ObjectNotFoundException {
        PreparedStatement statement = null;
        String command;
        ResultSet results = null;
        Order order = null;
        List<Order> orderList = new ArrayList<Order>();

        try
        {
            command = "SELECT * FROM Order WHERE oID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, suppID);

            results = statement.executeQuery();
            while( results.next() )
            {
                order = fromResultSet(results);
                orderList.add(order);
            }
            statement.close();
            results.close();
        }
        catch(SQLException e)
        {
            throw new ObjectNotFoundException(e);
        }

        return orderList;
    }



    //TODO: Need to add ProductList when creating Order object.
    private Order fromResultSet(ResultSet results) throws ObjectCreationException
    {
        Order order = null;

        String orderID;
        String suppID;
        Date date;
        double total;
        String shipping;
        List<Product> productList;

        try {
        orderID = results.getString("oID");
        suppID = results.getString("suppID");
        date = results.getDate("date");
        total = results.getDouble("total");
        shipping = results.getString("shipping");
            productList = createProductList(orderID);
        }
        catch(SQLException e)
        {
            throw new ObjectCreationException(e);
        }
        order = new Order(orderID, suppID, date, total, shipping, productList);
        if(order == null)
        {
            throw new ObjectCreationException();
        }

        return order;

    }

    public List<Product> createProductList(String orderID) throws ObjectCreationException
    {
        List<Product> products = new ArrayList<Product>();
        Product p;
        String pID;
        PreparedStatement statement;
        String command;
        ResultSet results;

        //TODO: Look into alternatives maybe...
        //TODO: We could directly access the ProductDatabase i suppose but that seems very bad...
        ProductLogic accessProducts = new ProductLogic(Services.getProductDatabase());

        try
        {
            command = "SELECT pID FROM Order_Products WHERE oID = ?;";
            statement = dbConnection.prepareStatement(command);
            statement.setString(1, orderID);
            results = statement.executeQuery();
            while( results.next() )
            {
                pID = results.getString("pID");
                p = accessProducts.searchID(pID);
                products.add(p);
            }
            statement.close();
            results.close();
        }
        catch(SQLException e)
        {
            throw new ObjectCreationException(e);
        }
        return products;
    }

}
