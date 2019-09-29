package comp3350.stocker.objects;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CustomerTest
{
        @Test
        public void testCustomer1()
        {
            Customer test;
            System.out.println("\nStarting testCustomer");

            test = new Customer("John","Doe","john@john.com","The Moon",123456789);

            assertNotNull(test);
            assertTrue("John".equals(test.getFirstName()));
            assertTrue("Doe".equals(test.getLastName()));
            assertTrue("john@john.com".equals(test.getEmail()));
            assertTrue(123456789 == test.getPhoneNum());

            System.out.println("Finished testCustomer");
        }
}
