package comp3350.stocker.objects;

public class Supplier {

    private static final String TAG = "SUPPLIER";

    private String suppID;
    private String name;
    private String location;
    private long phoneNum;


    public Supplier(String suppID, String name, String location, long phoneNum)
    {
        this.suppID = suppID;
        this.name= name;
        this.location= location;
        this.phoneNum= phoneNum;

    }


    public String getName() {
        return name;
    }
    public String getLocation() {
        return location;
    }
    public long getPhoneNum() {
        return phoneNum;
    }
    public String getID() {
        return suppID;
    }

    public void setID(String id){ this.suppID = id; };
    public void setName(String name) {
        this.name = name;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }



    public String toString() {
        return "SupplierID: " + suppID + ", Name: " + name  + ", Location:" + location  +
                ", Phone Number:" + phoneNum ;
    }

    public boolean equals(Supplier supp)
    {
        boolean result = false;

        if(this.suppID.equals(supp.getID()))
        {
            result = true;
        }

        return result;
    }

}
