package comp3350.stocker.business;

import org.junit.Test;

import java.util.List;

import comp3350.stocker.application.Services;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.objects.Supplier;
import comp3350.stocker.persistence.SupplierDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SupplierLogicTest {

    private SupplierLogic supp;
    private SupplierDatabase data;

    @Test
    public void supplier_Logic_Integration_IO() //insert and delete
    {
        System.out.println("Beginning SupplierLogic insert and delete Test");

        SupplierLogic temp = new SupplierLogic(Services.getSupplierDatabase());
        SupplierDatabase data = Services.getSupplierDatabase();

        List<Supplier> tempArr;

        try {

            int count = temp.getAllSuppliers().size();
            tempArr = temp.getAllSuppliers();

            assertNotNull(tempArr);
            assertNotNull(temp);
            assertNotNull(data);

            temp.insert(new Supplier("", "", "", 123456789));
            assertTrue(temp.getAllSuppliers().size() - 1 == count);

            temp.delete(new Supplier("", "", "", 123456789));
            assertTrue(temp.getAllSuppliers().size() == count);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Finished SupplierLogic insert and delete Test\n");
    }

    @Test
    public void supplier_Logic_Integration_Search() //searchEmail
    {
        System.out.println("Starting SupplierLogic Search Test");

        SupplierLogic temp = new SupplierLogic(Services.getSupplierDatabase());
        SupplierDatabase data = Services.getSupplierDatabase();

        try
        {
            int count = data.getAllSuppliers().size();
            temp.insert(new Supplier("", "", "", 123456789));

            assertNotNull(temp.searchID(""));
            assertNotNull(temp.searchLoc(""));
            assertNotNull(temp.searchName(""));

            temp.delete(new Supplier("", "", "", 123456789));

            assertTrue(temp.searchID("-1") == null);
            assertTrue(temp.searchID(null) == null);

        }
        catch(Exception e)
        {
            System.out.println("SupplierLogic Caught NULL Exception on searchID(null), as it should.");
            System.out.println("Finished SupplierLogic searchEmail Test\n");
        }
        System.out.println("Finished SupplierLogic searchEmail Test\n");
    }

    @Test
    public void supplier_Logic_Integration_Update()
    {
        System.out.println("Starting SupplierLogic Update Test");

        SupplierLogic temp = new SupplierLogic(Services.getSupplierDatabase());

        Supplier thing = new Supplier("12345", "moses", "moon", 000);

        try
        {
            temp.insert(thing);

            temp.updateLoc(thing,"Mars");
            assertEquals(temp.searchID("12345").getLocation(),"Mars");

            temp.updateName(thing,"super jesus");
            assertEquals(temp.searchID("12345").getName(),"super jesus");

            temp.updatePhoneNum(thing,111);
            assertEquals(temp.searchID("12345").getPhoneNum(),111);

            temp.delete(thing);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished SupplierLogic Update Test\n");
    }


    @Test
    public void supplier_Presentation_Integration_String()
    {
        System.out.println("Starting SupplierLogic String Test");

        SupplierLogic temp = new SupplierLogic(Services.getSupplierDatabase());

        try {
            temp.insert(new Supplier("12345", "moses", "moon", 000));

           // assertTrue(temp.getSupplierString("12345").length == 4);

            temp.delete(new Supplier("12345", "moses", "moon", 000));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished SupplierLogic String Test\n");
    }

    @Test
    public void supplier_Presentation_Integration_misc() throws Exception
    {
        System.out.println("Starting SupplierLogic Misc Test");

        SupplierLogic temp = new SupplierLogic(Services.getSupplierDatabase());
        Supplier tempSupp = null;
        assertTrue(temp.getNumFields()==4);

        try {
            tempSupp=new Supplier("12345","satan","mars",123456);
            assertNotNull(tempSupp);
            assertTrue(temp.insert(tempSupp));
            temp.delete(new Supplier("12345", "satan", "mars", 123456));
        }
        catch(ObjectCreationException e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished SupplierLogic Misc Test\n");
    }
}
