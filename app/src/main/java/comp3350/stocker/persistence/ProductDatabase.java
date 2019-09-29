package comp3350.stocker.persistence;

import java.sql.SQLException;
import java.util.List;

import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectCreationException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectDeleteException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectNotFoundException;
import comp3350.stocker.business.exceptions.ObjectExceptions.ObjectUpdateException;
import comp3350.stocker.objects.Product;

public interface ProductDatabase {

     List<Product> getAllProducts() throws ObjectNotFoundException; // done

     boolean insert(Product product) throws ObjectCreationException; // done

     boolean addTag(String productID, String tag) throws ObjectCreationException;

     boolean delete(Product product) throws ObjectDeleteException; // done

     boolean updatePrice(Product product, float price) throws ObjectUpdateException; // done

     boolean updateCost(Product product, float cost) throws ObjectUpdateException; // done

     boolean updateQuantity(Product product, int quantity) throws ObjectUpdateException; // done

     boolean updateSupplier(Product product, String supp) throws ObjectUpdateException; // done

     boolean updateName(Product product, String name) throws ObjectUpdateException;// done

     Product searchID(String id) throws ObjectNotFoundException; // done

     Product searchName(String name) throws ObjectNotFoundException; // done

     Product searchTag(String tag) throws ObjectNotFoundException;

     boolean editTag(String productID, String text, String currText) throws ObjectUpdateException;

}
