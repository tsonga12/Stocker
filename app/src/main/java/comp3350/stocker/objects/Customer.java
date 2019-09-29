package comp3350.stocker.objects;

public class Customer {

    private static final String TAG = "CUSTOMER";

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private long phoneNum;


    public Customer(String firstName, String lastName, String email, String address, long phoneNum)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;

    }


    //Getters and Setters
    public String getFirstName() { return firstName; }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getAddress()
    {
        return address;
    }

    public long getPhoneNum()
    {
        return phoneNum;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }


    public void setEmail(String newEmail)
    {
        this.email = newEmail;
    }

    public void setAddress(String newAddress)
    {
        this.address = newAddress;
    }

    public void setPhoneNum(long newPhoneNum)
    {
        this.phoneNum = newPhoneNum;
    }

    public String toString()
    {
        return (firstName + " " + lastName + "\n" + email + "\n" + address + "\n" + phoneNum + "\n");
    }

    public boolean equals(Customer cust)
    {
        boolean result = false;

        if(this.email.equals(cust.getEmail()))
        {
            result = true;
        }

        return result;
    }



}
