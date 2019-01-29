package terminals;

import components.OrderQueue;
import components.ServingQueue;
import entities.Order;
import entities.Serving;

public class KitchenTerminal extends Terminal {

    KitchenTerminal() {
        super();
    }

    public Order getOrder() {
        OrderQueue oq = OrderQueue.getInstance();
        Order order = oq.take();
        return order;
    }

    public void serveOrder(Serving serving) {
        ServingQueue sq = ServingQueue.getInstance();
        sq.put(serving);
    }


}
