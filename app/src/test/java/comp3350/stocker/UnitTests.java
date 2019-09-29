package comp3350.stocker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.stocker.business_tests.*;
import comp3350.stocker.objects.CustomerTest;
import comp3350.stocker.objects.OrderTest;
import comp3350.stocker.objects.ProductTest;
import comp3350.stocker.objects.SupplierTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        CustomerTest.class,
        ProductTest.class,
        SupplierTest.class,
        OrderTest.class,
        CustomerLogicUnit.class,
        OrderLogicUnit.class,
        ProductLogicUnit.class,
        SupplierLogicUnit.class
})

public class UnitTests
{
}
