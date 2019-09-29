package comp3350.stocker.business.exceptions.ObjectExceptions;

import java.sql.SQLException;

public class ObjectNotFoundException extends SQLException {

    public ObjectNotFoundException() { super(); }

    public ObjectNotFoundException(SQLException e)
    {
        super(e);
    }
}
