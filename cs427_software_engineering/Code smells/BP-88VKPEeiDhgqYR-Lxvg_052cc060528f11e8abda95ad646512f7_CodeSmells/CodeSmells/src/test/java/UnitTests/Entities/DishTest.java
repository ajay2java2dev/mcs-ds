package UnitTests.Entities;

import entities.Dish;
import entities.MenuItem;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DishTest {

    @Test
    public void getMenuItemTest() {
        MenuItem mi = new MenuItem("dummy", "salad", 1.23, 4.56);
        Dish dish = new Dish(mi);
        assertEquals(mi, dish.getMenuItem());
    }
}
