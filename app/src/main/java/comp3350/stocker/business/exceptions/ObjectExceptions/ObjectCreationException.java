package comp3350.stocker.business.exceptions.ObjectExceptions;

import java.sql.SQLException;

public class ObjectCreationException extends SQLException {

    public ObjectCreationException() { super(); }

    public ObjectCreationException(SQLException e) { super(e); }
}
