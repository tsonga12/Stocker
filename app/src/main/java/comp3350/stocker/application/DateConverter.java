package comp3350.stocker.application;
import java.sql.Date;

public class DateConverter {

    public DateConverter()
    {

    }

    public Date toSqlDate(long dateTime)
    {
        Date d = new java.sql.Date(dateTime);
        return d;
    }
}
