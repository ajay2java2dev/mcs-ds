package UnitTests.Entities;

import entities.Dish;
import entities.MenuItem;
import entities.Serving;
import entities.SingleTable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServingTest {
    @Test
    public void getDishTest() {
        SingleTable table = new SingleTable(0, 2);
        MenuItem mi = new MenuItem("Cool Aid", "beef", 1.2, 500.0);
        Serving serving = new Serving(table, new Dish(mi));
        assertEquals(mi, serving.getDish().getMenuItem());
    }

    @Test
    public void getToTableTest() {
        SingleTable table = new SingleTable(0, 2);
        MenuItem mi = new MenuItem("Cool Aid", "beef", 1.2, 500.0);
        Serving serving = new Serving(table, new Dish(mi));
        assertEquals(table, serving.getToTable());
    }
}
