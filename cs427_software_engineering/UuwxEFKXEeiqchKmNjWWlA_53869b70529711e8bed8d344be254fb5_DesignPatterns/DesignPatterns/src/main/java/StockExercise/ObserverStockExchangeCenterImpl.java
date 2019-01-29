package StockExercise;

import StockExercise.Given.ObservableStock;
import StockExercise.Given.ObserverStockExchangeCenter;
import StockExercise.Given.StockType;

public class ObserverStockExchangeCenterImpl extends ObserverStockExchangeCenter {

    public ObserverStockExchangeCenterImpl() {
        super();
    }

    public void notifyChange(StockType type, double price){
        //@TODO: Implememnt me;
        observe(new ObservableStockImpl(type));
    }

    @Override
    public void buyStock(ObservableStock s) {
        super.buyStock(s);
        s.registerStockExchangeCenter(this);
    }

    public void observe(ObservableStock o){
        //@TODO: Implememnt me
        System.out.println("ObserverStockExchangeCenterImpl.observe ,,,,,");
    }
}
