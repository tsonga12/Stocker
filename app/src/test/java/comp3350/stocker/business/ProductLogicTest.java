package comp3350.stocker.business;

import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import comp3350.stocker.application.Services;
import comp3350.stocker.objects.Product;
import comp3350.stocker.persistence.ProductDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProductLogicTest {

    @Test
    public void product_Logic_Integration_IO() //insert and delete
    {
        System.out.println("Starting ProductLogic insert and delete Test");

        ProductLogic temp = new ProductLogic(Services.getProductDatabase());

        try {
            List<Product> stuff = temp.getAllProducts();
            int count = stuff.size();

            assertNotNull(temp);

            temp.insert(new Product("12345", " ", (float) 0.0, (float) 0, 0, " "));
            assertTrue(temp.getAllProducts().size() - 1 == count);

            temp.delete(new Product("12345", " ", (float) 0.0, (float) 0, 0, " "));
            assertTrue(temp.getAllProducts().size() == count);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Finished ProductLogic insert and delete Test\n");
    }

    @Test
    public void product_Logic_Integration_Search() //searchEmail
    {
        System.out.println("Starting ProductLogic Search Test");

        ProductLogic temp = new ProductLogic(Services.getProductDatabase());
        ProductDatabase data = Services.getProductDatabase();

        try
        {
            temp.insert(new Product("", "", (float) 0.0, (float) 0, 0, ""));
            temp.addTag("","");
            int count = temp.getAllProducts().size();

            assertNotNull(temp.searchID(""));
            assertNotNull(temp.searchName(""));
            assertNotNull(temp.searchTag(""));

            temp.delete(new Product("", "", (float) 0.0, (float) 0, 0, ""));
            assertNotNull(temp);
            assertNotNull(data);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished ProductLogic searchEmail Test\n");
    }

    @Test
    public void product_Logic_Integration_Update()
    {
        System.out.println("Starting ProductLogic Update Test");

        Product indie = new Product("12345", "the ark", 1.0f, 1.0f, 1,"indiana jones");
        indie.addTag("Hitler");
        ProductLogic temp = new ProductLogic(Services.getProductDatabase());
        try {
            temp.insert(indie);

            assertTrue(temp.editTag("12345","Stalin","Hitler"));

            temp.updateCost(indie, 2.0f);
            assertEquals(2.0f, temp.searchID("12345").getCost(), .01);

            temp.updateName(indie, "crystal skull");
            assertEquals(temp.searchID("12345").getName(), "crystal skull");

            temp.updatePrice(indie, 2.0f);
            assertEquals(2.0f, temp.searchID("12345").getPrice(), .01);

            temp.updateQuantity(indie, 2);
            assertTrue(temp.searchID("12345").getQuantity() == 2);

            temp.updateSupplier(indie, "the nazis");
            assertEquals(temp.searchID("12345").getSupplier(), "the nazis");

            temp.delete(indie);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished ProductLogic Update Test\n");
    }

    @Test
    public void product_Presentation_Integration_String()
    {
        System.out.println("Starting ProductLogic String Test");

        ProductLogic temp = new ProductLogic(Services.getProductDatabase());

        Product indie = new Product("12345", "the ark", 1.0f, 1.0f, 1,"indiana jones");

        try {
            temp.insert(indie);

           // assertTrue(temp.getProductString("12345").length == 6);

            temp.delete(indie);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        System.out.println("Finished ProductLogic String Test\n");
    }

    @Test
    public void product_Presentation_Integration_Misc()
    {
        System.out.println("Starting ProductLogic Misc Test");

        ProductLogic temp = new ProductLogic(Services.getProductDatabase());

        assert(temp.getNumFields()>0);


        System.out.println("Finished ProductLogic Misc Test\n");
    }

}
