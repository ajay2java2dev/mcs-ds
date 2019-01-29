package UnitTests.Entities;

import entities.Dish;
import entities.MenuItem;
import entities.SingleTable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SingleTableTest {

    private final static int TABLE_INDEX = 1;
    private final static int SEATS = 5;
    private SingleTable table;

    @Before
    public void createTable() {
        table = new SingleTable(TABLE_INDEX, SEATS);

    }

    @Before
    public void addDishTest() {
        Dish dish1 = new Dish(new MenuItem("1", "beef", 0.1, 0.1));
        table.addDish(dish1);
        Dish[] dishes = table.getDishOnTable();
        assertEquals(dishes[0], dish1);
    }

    @Test
    public void getIndexTest() {
        assertEquals(TABLE_INDEX, table.getIndex());
    }

    @Test
    public void getNumberOfSeatsTest() {
        assertEquals(SEATS, table.getNumberOfSeats());
    }

    @Test
    public void checkingOutTest() {
        assertFalse(table.isCheckingOut());
        table.setCheckingOut(true);
        assertTrue(table.isCheckingOut());
    }
}
