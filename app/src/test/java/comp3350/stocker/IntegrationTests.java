package comp3350.stocker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.stocker.business.CustomerLogicTest;
import comp3350.stocker.business.OrderLogicTest;
import comp3350.stocker.business.ProductLogicTest;
import comp3350.stocker.business.SupplierLogicTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CustomerLogicTest.class,
        OrderLogicTest.class,
        ProductLogicTest.class,
        SupplierLogicTest.class
})

public class IntegrationTests {
}
