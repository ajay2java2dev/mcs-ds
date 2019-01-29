package Terminals;

import Components.Menu;
import Components.OrderQueue;
import Components.Restaurant;
import Entities.Dish;
import Entities.Order;
import Entities.SingleTable;

public class CustomerTerminal extends Terminal {

    private SingleTable table;

    CustomerTerminal(SingleTable table) {
        super();
        this.table = table;
    }

    public SingleTable getTable() {
        return table;
    }

    public Menu getMenu() {
        Restaurant re = Restaurant.getInstance();
        return re.getMenu();
    }

    public void orderDish(String name) {
        OrderQueue orderQueue = OrderQueue.getInstance();
        Menu menu = getMenu();
        Order newOrder = new Order(table, menu.getItemByName(name));
        orderQueue.put(newOrder);
    }

    public void checkout() {
        table.setCheckingOut(true);

    }

    public String getAllDishAsString() {
        Dish[] dishes = table.getDishOnTable();
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < dishes.length; i++) {
            result.append("Dish ").append(i).append(": ")
                    .append(dishes[i].getMenuItem().getDishName());
            if (i != dishes.length - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }
}
