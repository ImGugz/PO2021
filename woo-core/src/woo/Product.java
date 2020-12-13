package woo;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * This is an abstract class representing a Store product. All products
 * in Store have an unique ID, a Supplier, its price, critical stock levels and
 * its stock in Store. Products implement Observable interface, because we want
 * to notify Observers when products either lower their price or restock.
 */
public abstract class Product implements ObservableProduct {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012040059L;
    
    /** Product's unique ID. */
    private String _id;

    /** Product's supplier ID. */
    private Supplier _supplier;

    /** Product's price. */
    private int _price;

    /** Product's critical stock level. */
    private int _criticalValue;

    /** Product's quantity in stock. */
    private int _stock;
    
    /** Array containing observers who want to be notified about this product's events. */
    private Map<ProductObserver, Boolean> _observers = new HashMap<ProductObserver, Boolean>();

    /**
     * Create a product.
     * 
     * @param supplier 
     *          product supplier.
     * @param id
     *          product ID.
     * @param price 
     *          product price.
     * @param criticalValue
     *          product critical stock level.
     * @param amount 
     *          quantity being added to stock.
     */
    public Product(Supplier supplier, String id, int price, int criticalValue, int amount) {
        _id = id;
        _supplier = supplier;
        _price = price;
        _criticalValue = criticalValue;
        _stock = amount;
    }

    /**
     * @return the product's ID.
     */
    public String getID() {
        return _id;
    }

    /**
     * @return the product's Supplier ID.
     */
    public Supplier getSupplier() {
        return _supplier;
    }

    /**
     * @return the product's price.
     */
    public int getPrice() {
        return _price;
    }

    /**
     * @return the product's critical value.
     */
    public int getCriticalValue() {
        return _criticalValue;
    }

    /**
     * @return the product's amount of units in stock.
     */
    public int getStock() {
        return _stock;
    }

    public int getExistenceLevel() {
        return _stock * _price;
    }

    /**
     * Adds stock to product.
     * 
     * @param qty
     *          amount being added to stock.
     */
    public void addStock(int qty) {
        _stock += qty;
        if (_stock == qty) {
            notifyObservers("NEW");
        }
    }

    /**
     * Removes stock from a product.
     * 
     * @param qty
     *          amount being removed from stock.
     */
    public void removeStock(int qty) {
        _stock -= qty;
    }

    /**
     * Updates product's price.
     * 
     * @param price
     *          new product price.
     */
    public void changePrice(int price) {
        int old = _price;
        if (price > 0) {
            _price = price;
            if (price < old) {
                notifyObservers("BARGAIN");
            }
        }
    }

    /**
     * Registers an Observer to be notified about events to this product.
     * 
     * @param observer
     *          observer who wants to be notified about this product's events.
     */
    @Override
    public void registerObserver(ProductObserver observer) {
        _observers.put(observer, true);
    }

    /**
     * Removes an Observer as interested about this product's events.
     * 
     * @param observer
     *          observer being removed as interested about this product's events.
     */
    @Override
    public void removeObserver(ProductObserver observer) {
        _observers.put(observer, false);
    }

    /**
     * Notifies Observers about a given event.
     * 
     * @param event
     *          event to notify Observers. 
     */
    @Override
    public void notifyObservers(String event) {
        for (Map.Entry<ProductObserver, Boolean> observer : _observers.entrySet()) {
            if (observer.getValue() == true) {
                observer.getKey().update(event, _id, _price);
            }
        }
    }

    /**
     * Returns all Observers interested in this product as an unmodifiable map.
     * 
     * @return collection with all Product Observers.
     */
    public Map<ProductObserver, Boolean> getObservers() {
        return Collections.unmodifiableMap(_observers);
    }

    /**
     * @return the specific product's payment period variable.
     */
    public abstract int getN();

    /**
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString() {
        return "|" + getID() + "|" + getSupplier().getID() + "|" + getPrice() + "|" + getCriticalValue() + "|" + getStock();
    }

}
