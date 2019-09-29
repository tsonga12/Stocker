package comp3350.stocker.business;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectDeleteException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectUpdateException;
import comp3350.stocker.objects.Order;
import comp3350.stocker.objects.Product;
import comp3350.stocker.persistence.OrderDatabase;


public class OrderLogic {

    final int NUM_FIELDS = 5;

    private OrderDatabase orderDatabase;
    //private OrderStub orderDatabase;


    public OrderLogic(OrderDatabase database)
    {
        this.orderDatabase = database;
    }

    public int getNumFields(){ return NUM_FIELDS; }


    public List<Order> getAllOrders()
    {
        List<Order> orders = null;

        try
        {
            orders = orderDatabase.getAllOrders();

        }
        catch(SQLException se)
        {
            System.out.println("ERROR: Could not retrieve Order List from Database.");
            System.out.println(se.getMessage());
            se.printStackTrace();
        }

        return orders;
    }

    public List<Product> getProductList(Order order) throws ObjectCreationException
    {
        List<Product> productList;
        productList = orderDatabase.createProductList(order.getOrderID());

        return productList;
    }


    public boolean insert(Order order) throws ObjectCreationException
    {
        boolean result = false;

        if(order !=null)
        {
            result = orderDatabase.insert(order);
        }
        else
        {
            throw new ObjectCreationException();
        }
        return result;
    }

    public boolean insertProduct(Order order, Product product) throws ObjectUpdateException
    {
        boolean result = false;

        if(order !=null)
        {
                orderDatabase.insertProduct(order.getOrderID(), product.getID());
        }
        else
        {
            throw new ObjectUpdateException();
        }
        return result;
    }

    public boolean removeProduct(Order order, Product product) throws ObjectDeleteException{
        boolean result = false;

        if(order !=null)
        {
                orderDatabase.removeProduct(order.getOrderID(), product.getID());
        }
        else
        {
            throw new ObjectDeleteException();
        }
        return result;

    }

    public boolean delete(Order order) throws ObjectDeleteException {

        boolean result = false;

        if(order != null){
            result = orderDatabase.delete(order);
        }
        else
        {
            throw new ObjectDeleteException();
        }

        return result;
    }

    public Order searchID(String orderID) throws ObjectNotFoundException {

        Order result = null;
        result = orderDatabase.searchID(orderID);

        return result;

    }

    public List<Order> searchSupplierID(String suppID) throws ObjectNotFoundException{

        List<Order> orderList;
        orderList = orderDatabase.searchSupplier(suppID);

        return orderList;
    }

    public boolean updateDate(Order order, Date date) throws ObjectUpdateException
    {
        boolean result = false;

        if(order !=null)
        {
            result = orderDatabase.updateDate(order, date);
        }
        else
        {
            throw new ObjectUpdateException();
        }

        return result;
    }

    public boolean updateTotal(Order order, Double total) throws ObjectUpdateException
    {
        boolean result = false;

        if(order !=null)
        {
            result = orderDatabase.updateTotal(order, total);
        }
        else
        {
            throw new ObjectUpdateException();
        }

        return result;
    }

    public boolean updateShipping(Order order, String shipping) throws ObjectUpdateException
    {
        boolean result = false;

        if(order !=null)
        {
            result = orderDatabase.updateShipping(order, shipping);
        }
        else
        {
            throw new ObjectUpdateException();
        }

        return result;
    }

    public boolean updateSupplier(Order order, String suppID) throws ObjectUpdateException
    {
        boolean result = false;

        if(order !=null)
        {
                result = orderDatabase.updateSupplier(order, suppID);
        }
        else
        {
            throw new ObjectUpdateException();
        }

        return result;
    }
}
