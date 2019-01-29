package StockExercise;

import StockExercise.Given.ObservableStock;
import StockExercise.Given.ObserverStockExchangeCenter;
import StockExercise.Given.StockType;

import java.util.ArrayList;
import java.util.List;

public class ObservableStockImpl extends ObservableStock {

    //@TODO: Add any necessary fields
    private double price;
    private ObserverStockExchangeCenter oc;

    public ObservableStockImpl(StockType name){
        super(name);
    }

    public void notifyPriceChange(double price){
        if (oc!=null) {
            oc.getOwnedStock().put(super.getName(),price);
        }
    }
    public void registerStockExchangeCenter(ObserverStockExchangeCenter oc){
        this.oc = oc;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
        notifyPriceChange(price);
    }
}
