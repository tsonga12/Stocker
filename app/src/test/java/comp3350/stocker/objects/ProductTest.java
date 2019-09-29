package comp3350.stocker.objects;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProductTest
{

        @Test
        public void testProduct1()
        {
            Product product;

            System.out.println("\nStarting testProduct");

            product = new Product("54321", "Endangered Emu",(float)42,(float)42,12,"Umbrella Corp");
            product.addTag("Movie");

            assertNotNull(product);
            assertTrue("54321".equals(product.getID()));
            assertTrue("Endangered Emu".equals(product.getName()));
            assertTrue("Umbrella Corp".equals(product.getSupplier()));
            assertTrue(12 == product.getQuantity());
            assertTrue((42 - product.getPrice()) < .01);
            assertTrue(product.getTags()!=null);

            System.out.println("Finished testProduct");
        }
}
