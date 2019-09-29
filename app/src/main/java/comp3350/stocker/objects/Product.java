package comp3350.stocker.objects;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private static final String TAG = "PRODUCT";

    private String name;
    private String productID;
    private float cost;
    private float price;
    private int quantity;
    private String supplier;
    private List<String> tags;


    public Product(String productID,String name,float cost, float price,int quantity,String supplier)
    {
        this.name = name;
        this.productID = productID;
        this.cost = cost;
        this.price = price;
        this.quantity = quantity;
        this.supplier = supplier;
        this.tags = new ArrayList<String>();
        //tags.add("Test1");
        //tags.add("2");
        //tags.add("button");
        //tags.add("another");

    }

    public String getID() {
        return productID;
    }
    public float getPrice() { return price; }
    public float getCost() {return cost; }
    public int getQuantity() {return quantity; }
    public String getSupplier(){ return supplier; }
    public String getName() { return name; }
    public List<String> getTags() { return tags; }

    public void setPrice(float value) { price = value; }
    public void setCost(float value) { cost = value; }
    public void setName(String name) { this.name = name; }
    public void setQuantity(int quant) {quantity = quant; }
    public void setSupplier(String supp) {supplier = supp; }
    public void setTagList(List<String> tagList) { tags = tagList;}
    public void addTag(String tag)
    {
        tags.add(tag);
    }


    public String toString()
    {
        return("Product: "+name+", Price: "+price+", Quantity: "+quantity+"\n");
    }

    public boolean equals(Product prod)
    {
        boolean result = false;

        if(this.productID.equals(prod.getID()))
        {
            result = true;
        }

        return result;
    }
}