package comp3350.stocker.business.exceptions.ObjectExceptions;

//Generic exception to be thrown when something goes wrong in the SupplierLogic class, basically
//just a wrapper for an SQLException
public class ObjectException extends Exception {

    public ObjectException() { super(); }

}
