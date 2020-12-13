package woo;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Class Supplier represents a Store supplier. They respond to store orders on demand.
 */
public class Supplier implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012040059L;
    
    /** Supplier's unique ID. */
    private String _id;

    /** Supplier's name. */
    private String _name;

    /** Supplier's address. */
    private String _address;

    /** Supplier's active status. */
    private boolean _active = true;

    /** Supplier's list of orders. */
    private List<Order> _transactions = new ArrayList<Order>();

    /**
     * Create supplier.
     * 
     * @param id
     *          supplier ID.
     * @param name
     *          supplier name.
     * @param address
     *          supplier address.
     */
    public Supplier(String id,String name, String address) {
        _id = id;
        _name = name;
        _address = address;
    }

    /**
     * @return the supplier's unique ID
     */
    public String getID() {
        return _id;
    }

    /**
     * @return the supplier's name
     */
    public String getName() {
        return _name;
    }

    /**
     * @return the supplier's address
     */
    public String getAddress() {
        return _address;
    }

    /**
     * @return the supplier's active status.
     */
    public boolean isActive() {
        return _active;
    }

    /**
     * Changes supplier's active status.
     * 
     * @return true if supplier was inactive and is now active; false, otherwise.
     */
    public boolean toggleTransactions() {
        if (isActive()) {
            _active = false;
        } else {
            _active = true;
        }
        return _active;
    } 
    
    /**
     * Returns list of supplier's orders.
     * 
     * @return a list containing all supplier's orders.
     */
    public Collection<Order> getTransactions() {
        return Collections.unmodifiableCollection(_transactions);
    } 

    /**
     * Adds an order to supplier's orders list.
     * 
     * @param order
     *          order being added to orders list.
     */
    public void addOrder(Order order) {
        _transactions.add(order);
    }

    /**
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString() {
        return getID() + "|" + getName() + "|" + getAddress() + "|";
    }

}
