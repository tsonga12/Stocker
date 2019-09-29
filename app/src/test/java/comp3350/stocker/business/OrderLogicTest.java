package comp3350.stocker.business;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp3350.stocker.application.DateConverter;
import comp3350.stocker.application.Services;
import comp3350.stocker.objects.Order;
import comp3350.stocker.objects.Product;
import comp3350.stocker.persistence.OrderDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class OrderLogicTest {

    private OrderDatabase data;
    private OrderLogic order;
    private List<Product> products;
    @Before
    public void setUp()
    {
        data = Services.getOrderDatabase();
        order = new OrderLogic(data);
        products =  new ArrayList<Product>();
        products.add(new Product("123412", "Laptop", (float)4.32, (float)4.2,32, "Walmart"));
        products.add(new Product("131231", "VHS Tape", (float)5.21, (float)18.23,100, "Blockbuster"));
        products.add(new Product("456789", "Plywood", (float)1.42, (float)242.01,2, "Lowes"));

    }

    @Test
    public void order_Logic_Integration_IO() //insert and delete
    {
        System.out.println("Beginning OrderLogic insert and delete Test");
        Date date = new Date();

        OrderLogic temp = new OrderLogic(Services.getOrderDatabase());
        OrderDatabase data = Services.getOrderDatabase();

        try {
            int count = data.getAllOrders().size();

            assertNotNull(temp);
            assertNotNull(data);

            temp.insert(new Order("", "786343", date, 0.0, "", products ));
            assertTrue(data.getAllOrders().size() - 1 == count);

            temp.delete(new Order("", "786343", date, 0.0, "",products));
            assertTrue(data.getAllOrders().size() == count);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Finished OrderLogic insert and delete Test\n");
    }

    @Test
    public void order_Logic_Integration_Search() //searchEmail
    {
        System.out.println("Starting OrderLogic Search Test");
        Date date = new Date();

        OrderLogic temp = new OrderLogic(Services.getOrderDatabase());
        OrderDatabase data = Services.getOrderDatabase();


        try
        {
            int count = data.getAllOrders().size();
            temp.insert(new Order("", "786343", date, 0.0f,"",products));

            assertNotNull(temp.searchID(""));
            assertNotNull(temp.searchSupplierID("786343"));

            temp.delete(new Order("", "786343", date, 0.0f,"",products));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Finished OrderLogic searchID and searchSupplierID Tests\n");
    }

    @Test
    public void order_Logic_Integration_Update()
    {
        System.out.println("Starting OrderLogic Update Test");

        long epoch1 = 1553180546692L; // = 3/21/2019
        long epoch2 = 1503180546692L; // = 8/19/2017
        String dateStr1;
        String dateStr2;
        double testTotal = 100.00;
        String testShipping = "Priority";
        OrderLogic db = new OrderLogic(Services.getOrderDatabase());
        DateConverter dc = new DateConverter();
        Date date = new Date(epoch1); // 1553180573 = 3/21/2019, 10:02:53 AM Current Unix Epoch Time
        Order order = new Order("123451", "786343", date, 44.44, "Ground", products);
        Order o;

        try
        {
            db.insert(order);
            o = db.searchID(order.getOrderID());
            java.sql.Date sDate = dc.toSqlDate(date.getTime());
            dateStr1 = o.getDate().toString();
            dateStr2 = sDate.toString();

            assertTrue(dateStr1.compareTo(dateStr2) == 0);


            date = new Date(epoch2);
            sDate = dc.toSqlDate(date.getTime());
            db.updateDate(order, sDate);
            o = db.searchID(order.getOrderID());
            dateStr1 = o.getDate().toString();
            dateStr2 = sDate.toString();

            assertTrue(dateStr1.compareTo(dateStr2) == 0);

            db.updateTotal(order, testTotal);
            assertTrue(db.searchID(order.getOrderID()).getTotal() == testTotal);

            db.updateShipping(order, testShipping);
            assertTrue(db.searchID(order.getOrderID()).getShipping().compareTo(testShipping) == 0);

            //Clean up.
            db.delete(order);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished OrderLogic Update Test\n");
    }


    @Test
    public void order_Presentation_Integration_String()
    {
        System.out.println("Starting OrderLogic String Test");

        OrderLogic temp = new OrderLogic(Services.getOrderDatabase());
        Date date = new Date();

        try {
            temp.insert(new Order("12345", "786343", date, 200.0f,"ground",products));

            temp.delete(new Order("12345", "786343", date, 200.0f,"ground",products));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished OrderLogic String Test\n");
    }

    @Test
    public void order_Presentation_Integration_misc() throws Exception
    {
        System.out.println("Starting OrderLogic Misc Test");

        OrderLogic temp = new OrderLogic(Services.getOrderDatabase());
        Date date= new Date();
        assert(temp.getNumFields()>0);


        List<Order> orderList = temp.getAllOrders();
        assertNotNull(orderList);


        try {
            Order o = new Order("12345","786343",date,200.0,"ground",products);
            assertTrue(temp.insert(o));
            assertNotNull(temp.getProductList(o));
            temp.delete(new Order("12345", "786343", date, 200.0,"ground",products));
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished OrderLogic Misc Test\n");
    }
}
