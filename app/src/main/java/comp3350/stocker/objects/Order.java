package comp3350.stocker.objects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Order {

    private static final String TAG = "ORDER";

    private String orderID;
    private String suppID;
    private List<Product> products;
    private SimpleDateFormat dateFormat;
    private Date date;
    private double total;
    private String shipping;


    private List<String[]> fieldList;

    public Order(String orderID, String suppID, Date date, double total, String shipping, List<Product> products) {
        this.orderID = orderID;
        this.suppID = suppID;
        this.date = date;
        this.total = total;
        this.shipping = shipping;
        this.products = products;

        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    }


    //Getters and Setters
    public String getOrderID()
    {
        return this.orderID;
    }
    public String getSuppID()
    {
        return this.suppID;
    }
    public Date getDate()
    {
        return this.date;
    }
    public double getTotal()
    {
        return this.total;
    }
    public String getShipping()
    {
        return this.shipping;
    }
    public List<Product> getProducts()
    {
        return products;
    }
    public SimpleDateFormat getDateFormat() { return dateFormat; }

    public void setDate(Date date)
    {
        this.date = date;
    }
    public void setTotal(double newTotal)
    {
        this.total = newTotal;
    }
    public void setShipping(String newShipping)
    {
        this.shipping = newShipping;
    }



    public boolean equals(Order order) {
        boolean result = false;

        if(order.getOrderID().equals(orderID)){
            result = true;
        }

        return result;
    }


}
