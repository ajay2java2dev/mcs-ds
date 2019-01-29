package Terminals;

import Components.Restaurant;
import Components.SeatingSystem;
import Components.ServingQueue;
import Entities.Dish;
import Entities.Serving;
import Entities.SingleTable;
import Entities.TerminalPrintType;

import java.util.ArrayList;
import java.util.List;

public class ServiceDeskTerminal extends Terminal {

    private List<SingleTable> tables;
    private static final String TABLE = "Table ";

    public ServiceDeskTerminal() {
        super();
        tables = new ArrayList<SingleTable>();
    }

    public KitchenTerminal grandOpening(String restaurantName,
                                        String tableConfigPath,
                                        String menuFilePath) {
        Restaurant.getOrCreateInstance(restaurantName, tableConfigPath, menuFilePath);
        return new KitchenTerminal();
    }

    public KitchenTerminal grandOpening() {

        return grandOpening("MyRestaurant",
                "tables.txt", "menu.txt");
    }

    public void closeBusinesss() {
        printToScreen("Business closed.");
        Restaurant.clearInstance();
    }

    public CustomerTerminal checkIn(int numberOfPeople) {
        Restaurant re = Restaurant.getInstance();
        SeatingSystem ss = re.getSeatingSystem();
        SingleTable table = ss.getAvailableTable(numberOfPeople);
        CustomerTerminal customerTerminal = null;
        if (table != null) {

            if (ss.occupy(table)) {
                tables.add(table);
                printToScreen("New table " + table.getIndex()
                        + " checked in, number of people: " + numberOfPeople);
                customerTerminal = new CustomerTerminal(table);
            } else {
                printToScreen(TABLE + table.getIndex()
                        + " occupied. Check in failed.", TerminalPrintType.Error);
            }
        } else {
            printToScreen("Not enough seat", TerminalPrintType.Error);
        }
        return customerTerminal;
    }

    public void checkOut(SingleTable table) {
        Restaurant re = Restaurant.getInstance();
        SeatingSystem ss = re.getSeatingSystem();
        if (table.isCheckingOut()) {

            if (ss.vacate(table)) {
                tables.remove(table);
                printToScreen(TABLE + table.getIndex()
                        + " checked out.");
            } else {
                printToScreen("Vacating table " + table.getIndex()
                        + " failed.", TerminalPrintType.Error);
            }
        }
    }

    public void serveDish() {
        ServingQueue sq = ServingQueue.getInstance();
        Serving serving = sq.take();
        Dish dish = serving.getDish();
        SingleTable table = serving.getToTable();
        table.addDish(dish);
        printToScreen(dish.getMenuItem().getDishName()
                + " served to table " + table.getIndex());
    }

}
