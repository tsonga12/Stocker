package comp3350.stocker.business_tests;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import comp3350.stocker.objects.*;
import comp3350.stocker.persistence.SupplierDatabase;
import comp3350.stocker.business.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SupplierLogicUnit {

    private SupplierLogic supp;
    private SupplierDatabase data;

    @Before
    public void setUp() {
        data = mock(SupplierDatabase.class);
        supp = new SupplierLogic(data);
    }

    @Test
    public void mockedSupplierIOTest()
    {
        System.out.println("Starting Mocked Supplier IO Test");

        final Supplier temp = new Supplier("Windows","walls","balls",6969);
        final List<Supplier> customers = new ArrayList<>();
        customers.add(temp);

        try
        {
            when(data.insert(temp)).thenReturn(customers.add(temp));
            when(data.delete(temp)).thenReturn(customers.remove(temp));
            when(data.getAllSuppliers()).thenReturn(customers);

            assertTrue(supp.insert(temp));
            assertTrue(supp.getAllSuppliers().size() > 0);
            assertTrue(supp.delete(temp));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished Mocked SupplierIO Test\n");
    }

    @Test
    public void mockedSupplierSearchTest()
    {
        System.out.println("Starting Mocked Supplier Search Test");

        final Supplier temp = new Supplier("Windows","walls","balls",6969);
        final List<Supplier> customers = new ArrayList<>();
        customers.add(temp);

        try
        {
            when(data.searchID("Windows")).thenReturn(temp);
            when(data.searchLoc("balls")).thenReturn(temp);
            when(data.searchName("Windows")).thenReturn(temp);
            when(data.searchPhoneNum(6969l)).thenReturn(temp);

            when(data.updateName(temp,"Mount rushmore")).thenReturn(true);
            when(data.updatePhoneNum(temp,4242)).thenReturn(true);
            when(data.updateLoc(temp,"pluto")).thenReturn(true);

            assertNotNull(supp.searchID("Windows"));
            assertNotNull(supp.searchLoc("balls"));
            assertNotNull(supp.searchName("Windows"));

            assertTrue(supp.updateLoc(temp,"pluto"));
            assertTrue(supp.updatePhoneNum(temp,4242));
            assertTrue(supp.updateName(temp,"Mount rushmore"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished Mocked Supplier Search Test\n");

    }

    @Test
    public void mockedSupplierMiscTest()
    {
        System.out.println("Starting Mocked Supplier Misc Test");

        final Supplier temp = new Supplier("Windows","walls","balls",6969);
        final List<Supplier> customers = new ArrayList<>();
        customers.add(temp);

        try
        {
            supp.insert(temp);
            assertNotNull(supp.getNumFields());
            supp.delete(temp);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished Mocked Supplier Misc Test\n");

    }
}
