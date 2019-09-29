package comp3350.stocker.application;

import comp3350.stocker.persistence.CustomerDatabase;
import comp3350.stocker.persistence.OrderDatabase;
import comp3350.stocker.persistence.SupplierDatabase;
import comp3350.stocker.persistence.ProductDatabase;


import comp3350.stocker.persistence.hsqldb.CustomerHSQLDB;
import comp3350.stocker.persistence.hsqldb.OrderHSQLDB;
import comp3350.stocker.persistence.hsqldb.SupplierHSQLDB;
import comp3350.stocker.persistence.hsqldb.ProductHSQLDB;


public class Services
{
    private static CustomerDatabase customerDatabase;
    private static ProductDatabase productDatabase;
    private static SupplierDatabase supplierDatabase;
    private static OrderDatabase orderDatabase;

    private static String dbName = "SC";

    public static synchronized CustomerDatabase getCustomerDatabase()
    {
        if(customerDatabase == null)
        {
            //customerDatabase = new CustomerStub();
            customerDatabase = new CustomerHSQLDB(dbName);
        }

        return customerDatabase;
    }

    public static synchronized ProductDatabase getProductDatabase()
    {
        if(productDatabase == null)
        {
            //productDatabase = new ProductStub();
            productDatabase = new ProductHSQLDB(dbName);
        }

        return productDatabase;
    }

    public static synchronized SupplierDatabase getSupplierDatabase()
    {
        if(supplierDatabase == null)
        {
            //supplierDatabase = new SupplierStub();
            supplierDatabase = new SupplierHSQLDB(dbName);
        }

        return supplierDatabase;
    }

    public static synchronized OrderDatabase getOrderDatabase()
    {
        if(orderDatabase == null)
        {
            //orderDatabase = new OrderStub();
            orderDatabase = new OrderHSQLDB(dbName);
        }

        return orderDatabase;
    }

    public static void setDBPathName(final String name) {

        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dbName = name;
    }

    public static String getDBPathName()
    {
        return dbName;
    }

}
