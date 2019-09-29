package comp3350.stocker.business_tests;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.stocker.business.CustomerLogic;
import comp3350.stocker.objects.Customer;
import comp3350.stocker.persistence.CustomerDatabase;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerLogicUnit {

    private CustomerLogic customer;
    private CustomerDatabase data;

    @Before
    public void setUp() {
        data = mock(CustomerDatabase.class);
        customer = new CustomerLogic(data);
    }

    @Test
    public void mockedCustomerIOTest()
    {
        System.out.println("Starting Mocked Customer IO Test");

        final Customer temp = new Customer("Julia","farts","alot","gas plant",6969);
        final List<Customer> customers = new ArrayList<>();
        customers.add(temp);

        try
        {
            when(data.insert(temp)).thenReturn(customers.add(temp));
            when(data.delete(temp)).thenReturn(customers.remove(temp));
            when(data.getAllCustomers()).thenReturn(customers);

            assertTrue(customer.insert(temp));
            assertTrue(customer.getAllCustomers().size() > 0);
            assertTrue(customer.delete(temp));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished Mocked Customer IO Test\n");
    }

    @Test
    public void mockedCustomerSearch_UpdateTest()
    {
        System.out.println("Starting Mocked Customer Search Test");

        final Customer temp = new Customer("Julia","farts","alot","gas plant",6969);
        final List<Customer> customers = new ArrayList<>();
        customers.add(temp);

        try
        {
            when(data.searchAddr("gas plant")).thenReturn(customers);
            when(data.searchEmail("alot")).thenReturn(temp);
            when(data.searchFirstName("Julia")).thenReturn(customers);
            when(data.searchLastName("farts")).thenReturn(customers);
            when(data.searchPhoneNum(6969)).thenReturn(customers);

            when(data.updateAddr(temp,"coal plant")).thenReturn(true);
            when(data.updatePhoneNum(temp,4242)).thenReturn(true);

            assertNotNull(customer.searchAddr("gas plant"));
            assertNotNull(customer.searchEmail("alot"));
            assertNotNull(customer.searchFirstName("Julia"));
            assertNotNull(customer.searchPhoneNum(6969l));
            assertNotNull(customer.search("farts"));

            assertTrue(customer.updateAddr(temp,"coal plant"));
            assertTrue(customer.updatePhoneNum(temp,4242));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished Mocked Customer Search Test\n");
    }

    @Test
    public void mockedCustomerMiscTest()
    {
        System.out.println("Starting Mocked Customer Misc Test");

        final Customer temp = new Customer("Julia","farts","alot","gas plant",6969);
        final List<Customer> customers = new ArrayList<>();
        customers.add(temp);

        try
        {
            customer.insert(temp);
            assertTrue(customer.getNumFields()>0);

            customer.delete(temp);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Finished Mocked Customer Misc Test\n");
    }
}