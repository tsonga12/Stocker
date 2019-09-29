package comp3350.stocker.persistence;

import java.util.List;

import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectDeleteException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectUpdateException;
import comp3350.stocker.objects.Customer;

public interface CustomerDatabase {

     List<Customer> getAllCustomers() throws ObjectNotFoundException; // DONE

     boolean insert(Customer cust) throws ObjectCreationException; // DONE

     boolean delete(Customer cust) throws ObjectDeleteException; // DONE

     boolean updateFirstName(Customer cust, String name) throws ObjectUpdateException;

     boolean updateLastName(Customer cust, String name) throws ObjectUpdateException;

     boolean updateAddr(Customer cust, String addr) throws ObjectUpdateException; // DONE

     boolean updatePhoneNum(Customer cust, long phoneNum) throws ObjectUpdateException; // DONE

     Customer searchEmail(String email) throws ObjectNotFoundException; // DONE

     List<Customer> searchFirstName(String firstName) throws ObjectNotFoundException;

     List<Customer> searchLastName(String lastName) throws ObjectNotFoundException;

     List<Customer> searchAddr(String addr) throws ObjectNotFoundException;

     List<Customer> searchPhoneNum(long phoneNum) throws ObjectNotFoundException;

}
