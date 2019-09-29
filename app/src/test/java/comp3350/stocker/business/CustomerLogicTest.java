package comp3350.stocker.business;

import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import comp3350.stocker.application.Services;
import comp3350.stocker.objects.Customer;
import comp3350.stocker.persistence.CustomerDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CustomerLogicTest {

    @Test
    public void customer_Logic_Integration_IO() //insert and delete
    {
        System.out.println("Starting CustomerLogic insert and delete Test");

        CustomerLogic temp = new CustomerLogic(Services.getCustomerDatabase());
        CustomerDatabase data = Services.getCustomerDatabase();

        try
        {
            int count = data.getAllCustomers().size();

            assertNotNull(temp);
            assertNotNull(data);

            temp.insert(new Customer("", "", "", "", 0));
            assertTrue(data.getAllCustomers().size() - 1 == count);

            temp.delete(new Customer("", "", "", "", 0));
            assertTrue(data.getAllCustomers().size() == count);

            System.out.println("Finished CustomerLogic insert and delete Test\n");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void customer_Logic_Integration_Search() //searchEmail
    {
        System.out.println("Starting CustomerLogic Search Test");

        CustomerLogic temp = new CustomerLogic(Services.getCustomerDatabase());
        CustomerDatabase data = Services.getCustomerDatabase();
        Customer homelessguy = new Customer("joe", "homeless", "ihaveno@home.ca", "anywhere", 0);

        try
        {
            temp.insert(homelessguy);
            int count = data.getAllCustomers().size();
            assertNotNull(data.getAllCustomers());

            assertNotNull(temp.search("homeless"));
            assertNotNull(temp.searchAddr("anywhere").get(0));
            assertNotNull(temp.searchEmail("ihaveno@home.ca"));
            assertNotNull(temp.searchPhoneNum(0l).get(0));
            assertNotNull(temp.searchFirstName("joe").get(0));

            temp.delete(homelessguy);

            assertNotNull(temp);
            assertNotNull(data);
            assertNull(temp.search(null));
            assertNull(temp.searchEmail("2041234567"));
            assertTrue(temp.searchEmail("-1") == null);
            assertTrue(temp.searchEmail(null) == null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished CustomerLogic Search Test\n");
    }

    @Test
    public void customer_Logic_Integration_Update()
    {
        System.out.println("Starting CustomerLogic Update Test");

        Customer homelessguy = new Customer("joe", "homeless", "ihaveno@home.ca", "anywhere", 0);

        CustomerLogic temp = new CustomerLogic(Services.getCustomerDatabase());

        try {
            temp.insert(homelessguy);
            assertNotNull(temp.search("homeless"));

            temp.updateAddr(homelessguy, "playboy mansion");

            assertTrue("playboy mansion".equals(temp.search("homeless").getAddress()));

            temp.updatePhoneNum(homelessguy, 1234567890);

            assertTrue(temp.search("homeless").getPhoneNum() == 1234567890);

            temp.delete(homelessguy);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished CustomerLogic Update Test\n");
    }

    @Test
    public void customer_Presentation_Integration_String()
    {
        System.out.println("Starting CustomerLogic String Test");

        CustomerLogic temp = new CustomerLogic(Services.getCustomerDatabase());
        try {
            temp.insert(new Customer("", "moses", "", "", 0));

           // assertTrue(temp.getCustomerString("moses").length == 5);

            temp.delete(new Customer("", "moses", "", "", 0));
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished CustomerLogic String Test\n");
    }

    @Test
    public void customer_Logic_Integration_Misc()
    {
        System.out.println("Starting CustomerLogic Misc Test");
        CustomerLogic logic = new CustomerLogic(Services.getCustomerDatabase());

        int numFields = logic.getNumFields();
        assertNotNull(numFields);
        assertEquals(numFields, 5);

        try {
            List<Customer> customerList = logic.getAllCustomers();
            assertNotNull(customerList);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("Finished CustomerLogic Misc Test");
    }
}
