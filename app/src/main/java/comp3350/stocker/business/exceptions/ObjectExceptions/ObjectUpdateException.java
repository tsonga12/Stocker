package comp3350.stocker.business.exceptions.ObjectExceptions;

import java.sql.SQLException;

public class ObjectUpdateException extends SQLException {

    public ObjectUpdateException() { super(); }


    public ObjectUpdateException(SQLException e) { super(e); }

}
