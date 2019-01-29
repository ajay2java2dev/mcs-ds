package Terminals;

import Components.OrderQueue;
import Components.ServingQueue;
import Entities.Order;
import Entities.Serving;

public class KitchenTerminal extends Terminal {

    KitchenTerminal() {
        super();
    }

    public Order getOrder() {
        OrderQueue oq = OrderQueue.getInstance();
        return oq.take();
    }

    public void serveOrder(Serving serving) {
        ServingQueue sq = ServingQueue.getInstance();
        sq.put(serving);
    }

}
