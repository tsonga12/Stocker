package comp3350.stocker.persistence;

import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectDeleteException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectUpdateException;
import comp3350.stocker.objects.Supplier;
import java.util.List;
import java.sql.SQLException;

public interface SupplierDatabase {

     List<Supplier> getAllSuppliers() throws ObjectNotFoundException;

     boolean insert(Supplier supp) throws ObjectCreationException;

     boolean delete(Supplier supp) throws ObjectDeleteException;

     boolean updateName(Supplier supp, String name) throws ObjectUpdateException;

     boolean updateLoc(Supplier supp, String location) throws ObjectUpdateException;

     boolean updatePhoneNum(Supplier supp, long phoneNum) throws ObjectUpdateException;

     Supplier searchID(String id) throws ObjectNotFoundException;

     Supplier searchName(String name) throws ObjectNotFoundException;

     Supplier searchLoc(String location) throws ObjectNotFoundException;

     Supplier searchPhoneNum(long phoneNum) throws ObjectNotFoundException;


}
