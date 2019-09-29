package comp3350.stocker.business_tests;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import comp3350.stocker.business.ProductLogic;
import comp3350.stocker.objects.Product;
import comp3350.stocker.persistence.ProductDatabase;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductLogicUnit {

        private ProductDatabase data;
        private ProductLogic product;

        @Before
        public void setUp() {
            data = mock(ProductDatabase.class);
            product = new ProductLogic(data);
        }

        @Test
        public void mockedProductIOTest()
        {
            System.out.println("Starting Mocked Product IO Test");

            final Product temp = new Product("Julia","farts",2.0f,1.0f,2,"test");
            final List<Product> customers = new ArrayList<>();
            customers.add(temp);

            try
            {

                when(data.insert(temp)).thenReturn(customers.add(temp));
                when(data.delete(temp)).thenReturn(customers.remove(temp));
                when(data.getAllProducts()).thenReturn(customers);

                assertTrue(product.insert(temp));
                assertTrue(product.getAllProducts().size() > 0);
                assertTrue(product.delete(temp));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            System.out.println("Finished Mocked Product IO Test\n");
        }

        @Test
        public void mockedProductSearchTest()
        {
            System.out.println("Starting Mocked Product Search Test");

            final Product temp = new Product("Julia","farts",2.0f,1.0f,2,"test");
            temp.addTag("Hitler");
            final List<Product> customers = new ArrayList<>();
            customers.add(temp);

            try
            {
                when(data.searchID("Julia")).thenReturn(temp);
                when(data.searchName("farts")).thenReturn(temp);
                when(data.editTag("Julia","Stalin","Hitler")).thenReturn(true);
                when(data.searchTag("Hitler")).thenReturn(temp);

                when(data.updatePrice(temp, 2.0f)).thenReturn(true);
                when(data.updateCost(temp,3.0f)).thenReturn(true);
                when(data.updateQuantity(temp,42)).thenReturn(true);
                when(data.updateName(temp,"Poop")).thenReturn(true);
                when(data.updateSupplier(temp,"The jedi temple")).thenReturn(true);

                assertNotNull(product.searchID("Julia"));
                assertNotNull(product.searchName("farts"));
                assertNotNull(product.searchTag("Hitler"));

                assertTrue(product.editTag("Julia","Stalin","Hitler"));
                assertTrue(product.updateCost(temp,3.0f));
                assertTrue(product.updateName(temp,"Poop"));
                assertTrue(product.updateQuantity(temp,42));
                assertTrue(product.updateSupplier(temp,"The jedi temple"));
                assertTrue(product.updatePrice(temp,2.0f));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            System.out.println("Finished Mocked Product Search Test\n");
        }

        @Test
        public void mockedProductMiscTest()
        {
            System.out.println("Starting Mocked Product Misc Test");

            final Product temp = new Product("Julia","farts",2.0f,1.0f,2,"test");

            final List<Product> customers = new ArrayList<>();
            customers.add(temp);

            try
            {
                when(data.searchID("Julia")).thenReturn(temp);

                assertTrue(product.getNumFields()>0);

                product.insert(new Product("6969","water",2.0f,2.1f,5,"sky"));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            System.out.println("Finished Mocked Product Misc Test\n");
        }
    }