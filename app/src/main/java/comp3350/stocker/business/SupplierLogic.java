package comp3350.stocker.business;

import java.util.Collections;
import java.util.List;

import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectDeleteException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectUpdateException;
import comp3350.stocker.objects.Supplier;
import comp3350.stocker.persistence.SupplierDatabase;

public class SupplierLogic
{
    final int NUM_FIELDS = 4;

    private SupplierDatabase suppDatabase;


    public SupplierLogic(SupplierDatabase database)
    {
        this.suppDatabase = database;

    }

    public int getNumFields(){ return NUM_FIELDS; }



    //Calling code in Presentation layer should handle the ObjectException
    public List<Supplier> getAllSuppliers() throws ObjectNotFoundException
    {
        List<Supplier> suppliers = null;

        suppliers = suppDatabase.getAllSuppliers();


        return Collections.unmodifiableList(suppliers);
    }

    public boolean insert(Supplier supplier) throws ObjectCreationException
    {
        boolean result = false;

        if (supplier != null)
        {
                result = suppDatabase.insert(supplier);
        }
        else
        {
            throw new ObjectCreationException();
        }

        return result;
    }

    public boolean delete(Supplier supplier) throws ObjectDeleteException
    {
        boolean result = false;

        if (supplier != null)
        {
            result = suppDatabase.delete(supplier);
        }
        else
        {
            throw new ObjectDeleteException();
        }

        return result;
    }

    public boolean updateSupplier(Supplier supplier, String[] currFields) throws ObjectUpdateException
    {
        boolean result = false;

        String[] fields = currFields;

        updateName(supplier,fields[0]);
        updateLoc(supplier,fields[2]);
        long phone = Long.parseLong(fields[3]);
        updatePhoneNum(supplier,phone);

        result = true;


        return result;
    }

    public boolean updateName(Supplier supplier, String name) throws ObjectUpdateException
    {
        boolean result = false;

        if (supplier != null)
        {
            result = suppDatabase.updateName(supplier, name);
        }
        else
        {
            throw new ObjectUpdateException();
        }

        return result;
    }

    public boolean updateLoc(Supplier supplier, String location) throws ObjectUpdateException
    {
        boolean result = false;

        if (supplier != null)
        {
            result = suppDatabase.updateLoc(supplier, location);
        }
        else
        {
            throw new ObjectUpdateException();
        }

        return result;
    }

    public boolean updatePhoneNum(Supplier supplier, long phoneNum) throws ObjectUpdateException
    {
        boolean result = false;

        if (supplier != null)
        {
            result = suppDatabase.updatePhoneNum(supplier, phoneNum);
        }
        else
        {
            throw new ObjectUpdateException();
        }

        return result;
    }


    public Supplier searchID(String suppID) throws ObjectNotFoundException
    {

        Supplier result = null;

        result = suppDatabase.searchID(suppID);


        return result;

    }//searchEmail

    public Supplier searchName(String name) throws ObjectNotFoundException
    {
        Supplier result = null;

        result = suppDatabase.searchName(name);

        return result;
    }

    public Supplier searchLoc(String location) throws ObjectNotFoundException
    {
        Supplier result = null;

        result = suppDatabase.searchLoc(location);

        return result;
    }


}
