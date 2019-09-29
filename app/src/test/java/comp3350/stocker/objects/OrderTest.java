package comp3350.stocker.objects;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class OrderTest {

    @Test
    public void orderTest1()
    {
        Order test;
        List<Product> products=new ArrayList<>();
        Date date = new Date();
        products.add(new Product("54321", "Endangered Emu",(float)42,(float)42,12,"Umbrella Corp"));

        System.out.println("\nStarting testOrder");

        test = new Order("122345","234566",date,122.6,"2-day express",products);

        assertNotNull(test);
        assertTrue("122345".equals(test.getOrderID()));
        assertTrue("234566".equals(test.getSuppID()));
        assertTrue("2-day express".equals(test.getShipping()));
        assertTrue(122.6 == test.getTotal());
        assertTrue(products.equals(test.getProducts()));

        System.out.println("Finished testOrder");
    }
}
