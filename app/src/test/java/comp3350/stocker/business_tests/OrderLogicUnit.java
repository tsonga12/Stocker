package comp3350.stocker.business_tests;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp3350.stocker.business.OrderLogic;
import comp3350.stocker.objects.Order;
import comp3350.stocker.objects.Product;
import comp3350.stocker.persistence.OrderDatabase;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderLogicUnit {

    private OrderLogic order;
    private OrderDatabase data;
    List<Product> products;
    //private DateConverter dc = new DateConverter();

    @Before
    public void setUp() {
        data = mock(OrderDatabase.class);
        order = new OrderLogic(data);


    }

    @Test
    public void mockedOrderIOTest()
    {
//        products.add(new Product("123412", "Laptop", (float)4.32, (float)4.2,32, "Walmart"));
//        products.add(new Product("131231", "VHS Tape", (float)5.21, (float)18.23,100, "Blockbuster"));
//        products.add(new Product("456789", "Plywood", (float)1.42, (float)242.01,2, "Lowes"));
        System.out.println("Starting Mocked Order IO Test");

        Date date= new Date();
        final Order temp = new Order("123457","35353",date,124.5,"2-day express",products);
        final List<Order> orders = new ArrayList<>();
        orders.add(temp);

        try
        {
            when(data.insert(temp)).thenReturn(orders.add(temp));
            when(data.delete(temp)).thenReturn(orders.remove(temp));
            when(data.getAllOrders()).thenReturn(orders);

            assertTrue(order.insert(temp));
            assertTrue(order.getAllOrders().size() > 0);
            assertTrue(order.delete(temp));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished Mocked OrderIO Test\n");
    }

    @Test
    public void mockedOrderSearchTest()
    {
        System.out.println("Starting Mocked Order Search Test");

        long epoch1 = 1553180546692L; // = 3/21/2019
        long epoch2 = 1503180546692L; // = 8/19/2017
        Date date= new Date(epoch1);

        final Order temp = new Order("123457","35353",date,124.5,"2-day express",products);
        final List<Order> orders = new ArrayList<>();
        orders.add(temp);

        try
        {
            when(data.searchID("123457")).thenReturn(temp);
            when(data.searchSupplier("35353")).thenReturn(orders);

            Date anotherDate= new Date(epoch2);

            when(data.updateDate(temp,anotherDate)).thenReturn(true);
            when(data.updateTotal(temp,200.0)).thenReturn(true);
            when(data.updateShipping(temp,"Standard")).thenReturn(true);

            assertNotNull(order.searchID("123457"));
            assertNotNull(order.searchSupplierID("35353"));

            assertTrue(order.updateDate(temp,anotherDate));
            assertTrue(order.updateTotal(temp,200.0));
            assertTrue(order.updateShipping(temp,"Standard"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished Mocked Order Search Test\n");

    }

    @Test
    public void mockedOrderMiscTest()
    {
        System.out.println("Starting Mocked Order Misc Test");
        Date date= new Date();
        final Order temp = new Order("123457","35353",date,124.5,"2-day express",products);
        final List<Order> orders = new ArrayList<>();
        orders.add(temp);


        try
        {
            when(data.searchID("123457")).thenReturn(temp);
            Order o = new Order("123","12345",new Date(),43d,"afar",null);

            System.out.println();
            order.insert(o);

            assertNotNull(order.getNumFields());
            order.delete(o);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished Mocked Order Misc Test\n");

    }

}
