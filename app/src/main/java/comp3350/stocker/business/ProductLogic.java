package comp3350.stocker.business;

import java.util.Collections;
import java.util.List;

import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectDeleteException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectUpdateException;
import comp3350.stocker.objects.Product;
import comp3350.stocker.persistence.ProductDatabase;

public class ProductLogic
{

    final int NUM_FIELDS = 6;
    private ProductDatabase prodDatabase;

    public ProductLogic(ProductDatabase database)
    {
        prodDatabase = database;

    }

    public int getNumFields(){ return NUM_FIELDS; }


    public List<Product> getAllProducts() throws ObjectNotFoundException
    {
        List<Product> products = null;

        products = prodDatabase.getAllProducts();


        return Collections.unmodifiableList(products);
    }


    public boolean insert(Product product) throws ObjectCreationException
    {
        boolean result = false;

        if(product !=null)
        {
            result = prodDatabase.insert(product);
        }
        else
        {
            throw new ObjectCreationException();
        }
        return result;
    }

    public boolean delete(Product product) throws ObjectDeleteException
    {
        boolean result = false;

        if(product !=null)
        {
            result = prodDatabase.delete(product);
        }
        else
        {
            throw new ObjectDeleteException();
        }
        return result;
    }

    public boolean updatePrice(Product product, float price) throws ObjectUpdateException
    {
        boolean result = false;

        if(product !=null)
        {
            result = prodDatabase.updatePrice(product, price);
        }
        else
        {
            throw new ObjectUpdateException();
        }
        return result;
    }

    public boolean updateCost(Product product, float cost) throws ObjectUpdateException
    {
        boolean result = false;

        if(product !=null)
        {
            result = prodDatabase.updateCost(product, cost);
        }
        else
        {
            throw new ObjectUpdateException();
        }
        return result;
    }

    public boolean updateQuantity(Product product, int quantity) throws ObjectUpdateException
    {
        boolean result = false;

        if(product !=null)
        {
            result = prodDatabase.updateQuantity(product, quantity);
        }
        else
        {
            throw new ObjectUpdateException();
        }
        return result;
    }

    public boolean updateSupplier(Product product, String supp) throws ObjectUpdateException
    {
        boolean result = false;

        if(product !=null)
        {
            result = prodDatabase.updateSupplier(product, supp);
        }
        else
        {
            throw new ObjectUpdateException();
        }
        return result;
    }

    public boolean updateName(Product product, String name) throws ObjectUpdateException
    {
        boolean result = false;

        if(product !=null) {
            result = prodDatabase.updateName(product, name);
        }
        else
        {
            throw new ObjectUpdateException();
        }
        return result;
    }


    public Product searchID(String productID) throws ObjectNotFoundException
    {
        Product result = null;
        result = prodDatabase.searchID(productID);
        return result;
    }

    public Product searchName(String name) throws ObjectNotFoundException
    {
        Product result = null;

        result = prodDatabase.searchName(name);


        return result;
    }

    public boolean addTag(String productID, String tag) throws ObjectCreationException {
        boolean success;
        success=prodDatabase.addTag(productID, tag);

        return success;
    }

    public boolean editTag(String productID, String text, String currText) throws ObjectUpdateException
    {
        boolean success;
        success=prodDatabase.editTag(productID, text, currText);
        return success;
    }

    //Return the first Product found in the Product_Tags table that contains the Tag
    public Product searchTag(String tag) throws ObjectNotFoundException
    {
        Product product = null;
        product = prodDatabase.searchTag(tag);
        return product;
    }

}

