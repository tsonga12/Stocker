package comp3350.stocker.persistence;

import java.util.Date;
import java.util.List;

import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectDeleteException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectUpdateException;
import comp3350.stocker.objects.Order;
import comp3350.stocker.objects.Product;

public interface OrderDatabase {

    List<Order> getAllOrders() throws ObjectNotFoundException;

    boolean insert(Order order) throws ObjectCreationException;

    boolean delete(Order order) throws ObjectDeleteException;

    //TODO: Decide which of the Update methods we want.
    //TODO: Change the return type to void...if the insert is unsuccessful SQL will throw an exception.
    boolean updateDate(Order order, Date date) throws ObjectUpdateException;

    boolean updateTotal(Order order, double total) throws ObjectUpdateException;

    boolean updateShipping(Order order, String shipping) throws ObjectUpdateException;

    boolean updateSupplier(Order order, String supplierID) throws ObjectUpdateException;

    Order searchID(String orderID) throws ObjectNotFoundException;

    List<Order> searchSupplier(String suppID) throws ObjectNotFoundException;

    void insertProduct(String orderID, String productID) throws ObjectUpdateException;

    void insertProductList(String orderID, List<Product> productList) throws ObjectCreationException;


    void removeProduct(String orderID, String productID) throws ObjectDeleteException;

    public List<Product> createProductList(String orderID) throws ObjectCreationException;


}
