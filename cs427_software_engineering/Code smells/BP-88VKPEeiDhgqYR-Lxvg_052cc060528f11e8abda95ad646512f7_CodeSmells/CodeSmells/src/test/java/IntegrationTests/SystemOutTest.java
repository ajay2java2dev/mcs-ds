package IntegrationTests;

import components.Restaurant;
import components.SeatingSystem;
import entities.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import terminals.CustomerTerminal;
import terminals.KitchenTerminal;
import terminals.ServiceDeskTerminal;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static Util.FileUtil.writeLinesToFile;
import static Util.StringUtil.getLastLine;
import static org.junit.Assert.assertEquals;

public class SystemOutTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private ServiceDeskTerminal sdt;
    private KitchenTerminal kt;

    @Before
    public void setUp() {


        sdt = new ServiceDeskTerminal();
        sdt.start();
        String[] fakeTableConfig = {
                "0, 2",
        };
        String[] fakeMenu = {
                "Steak, Beef, 15.0, 234",
                "Pork Rib, Pork, 12.0, 259",
        };
        writeLinesToFile(fakeTableConfig, "src/test/file/tables.txt");
        writeLinesToFile(fakeMenu, "src/test/file/menu.txt");
        kt = sdt.grandOpening("MyRest", "src/test/file/tables.txt", "src/test/file/menu.txt");
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void tearDown() {
        sdt.closeBusinesss();
        sdt.end();
        sdt = null;
        System.setOut(System.out);
        System.setErr(System.err);
    }


    @Test
    public void twoPeopleOneDishPrint() {
        CustomerTerminal ct = sdt.checkIn(2);
        assertEquals("terminals.ServiceDeskTerminal: INFORMATION - New table 0 checked in, number" +
                " of people: 2", outContent.toString().trim());
        SingleTable table = ct.getTable();
        Restaurant re = Restaurant.getInstance();
        SeatingSystem ss = re.getSeatingSystem();

        ct.orderDish("Pork Rib");
        Order order = kt.getOrder();
        MenuItem item = order.getOrderedItem();
        kt.serveOrder(new Serving(table, new Dish(item)));
        sdt.serveDish();

        assertEquals("terminals.ServiceDeskTerminal: INFORMATION - Pork Rib served to table 0",
                getLastLine(outContent.toString()).trim());

        ct.checkout();
        sdt.checkOut(table);
        assertEquals("terminals.ServiceDeskTerminal: INFORMATION - Table 0 checked out.",
                getLastLine(outContent.toString()).trim());

        sdt.checkOut(table);
        assertEquals("terminals.ServiceDeskTerminal: ERROR - Vacating table 0 failed.",
                getLastLine(outContent.toString()).trim());
    }


}
