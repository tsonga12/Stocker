package comp3350.stocker.business;

import java.util.Collections;
import java.util.List;

import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectDeleteException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectUpdateException;
import comp3350.stocker.objects.Customer;
import comp3350.stocker.persistence.CustomerDatabase;

public class CustomerLogic
{
    final int NUM_FIELDS = 5;
    private CustomerDatabase custDatabase;

    public CustomerLogic(CustomerDatabase database)
    {
        custDatabase = database;
    }

    public int getNumFields(){ return NUM_FIELDS; }


    public List<Customer> getAllCustomers() throws ObjectNotFoundException
    {
        List<Customer> customers = null;
        customers = custDatabase.getAllCustomers();


        return Collections.unmodifiableList(customers);
    }

    public boolean insert(Customer cust) throws ObjectCreationException
    {
        boolean result = false;

        if(cust !=null)
        {
            result = custDatabase.insert(cust);
        }
        else
        {
            throw new ObjectCreationException();
        }
        return result;
    }

    public boolean delete(Customer cust) throws ObjectDeleteException {

        boolean result = false;

        if(cust !=null)
        {
            result = custDatabase.delete(cust);
        }
        else
        {
            throw new ObjectDeleteException();
        }

        return result;
    }

    public boolean updateFirstName(Customer cust, String name) throws ObjectUpdateException
    {
        boolean result = false;

        if(cust !=null)
        {
            result = custDatabase.updateFirstName(cust, name);
        }
        else
        {
            throw new ObjectUpdateException();
        }

        return result;
    }

    public boolean updateLastName(Customer cust, String name) throws ObjectUpdateException
    {
        boolean result = false;

        if(cust !=null)
        {
            result = custDatabase.updateLastName(cust, name);
        }
        else
        {
            throw new ObjectUpdateException();
        }

        return result;
    }


    public boolean updateAddr(Customer cust, String addr) throws ObjectUpdateException
    {
        boolean result = false;

        if(cust !=null)
        {
            result = custDatabase.updateAddr(cust, addr);
        }
        else
        {
            throw new ObjectUpdateException();
        }

        return result;
    }

    public boolean updatePhoneNum(Customer cust, long phoneNum) throws ObjectUpdateException
    {
        boolean result = false;

        if(cust !=null)
        {
            result = custDatabase.updatePhoneNum(cust, phoneNum);
        }
        else
        {
            throw new ObjectUpdateException();
        }

        return result;
    }

    public Customer searchEmail(String email) throws ObjectNotFoundException
    {
        Customer result = null;

        result = custDatabase.searchEmail(email);



        return result;
    }

    public List<Customer> searchFirstName(String firstName) throws ObjectNotFoundException
    {
        List<Customer> result = null;

        result = custDatabase.searchFirstName(firstName);



        return result;
    }

    public List<Customer> searchAddr(String addr) throws ObjectNotFoundException
    {
        List<Customer> result = null;

        result = custDatabase.searchAddr(addr);


        return result;
    }

    public List<Customer> searchPhoneNum(Long phoneNum) throws ObjectNotFoundException
    {
        List<Customer> result = null;

        result = custDatabase.searchPhoneNum(phoneNum);


        return result;
    }

    public Customer search(String custLastName) throws ObjectNotFoundException{

        Customer cust =  null;
        List <Customer> temp = custDatabase.searchLastName(custLastName);

        if(temp != null && temp.size()>0) {
            cust = temp.get(0);
        }

        return cust;
    }
}