package comp3350.stocker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.stocker.features.CustomerAcceptance;
import comp3350.stocker.features.OrderAcceptanceTest;
import comp3350.stocker.features.ProductAcceptance;
import comp3350.stocker.features.SupplierAcceptance;
import comp3350.stocker.features.TagsAcceptance;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CustomerAcceptance.class,
        OrderAcceptanceTest.class,
        ProductAcceptance.class,
        SupplierAcceptance.class,
        TagsAcceptance.class
})
public class AllAcceptanceTests {
}
