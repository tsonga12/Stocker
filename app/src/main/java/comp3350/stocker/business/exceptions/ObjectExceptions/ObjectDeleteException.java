package comp3350.stocker.business.exceptions.ObjectExceptions;

import java.sql.SQLException;

public class ObjectDeleteException extends SQLException {

    public ObjectDeleteException() { super(); }

    public ObjectDeleteException(SQLException e) { super(e); }
}
