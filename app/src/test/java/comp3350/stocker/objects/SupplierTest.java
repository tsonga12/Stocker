package comp3350.stocker.objects;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SupplierTest
{
    @Test
    public void supplierTest1()
    {
        Supplier local;

        System.out.println("\nStarting supplierTest");

        local = new Supplier("123456","Umbrella Corp","Atlantis",123456789);

        assertNotNull(local);
        assertTrue("123456".equals(local.getID()));
        assertTrue("Umbrella Corp".equals(local.getName()));
        assertTrue("Atlantis".equals(local.getLocation()));
        assertTrue(123456789 - local.getPhoneNum() == 0);

        System.out.println("Finished supplierTest\n");
    }
}
