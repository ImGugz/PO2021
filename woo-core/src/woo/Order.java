package woo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Class Order is a subclass of a Transaction and it's instantiated
 * when the Store wants to restock products.
 */
public class Order extends Transaction {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012040059L;
    
    /** Order's supplier. */
    private String _supplierID;

    /** Order's products. */
    private List<OrderElement> _products = new ArrayList<OrderElement>();

    /**
     * Create order transaction.
     * 
     * @param id
     *          transaction ID.
     * @param supplierID
     *          supplier ID.
     */
    public Order(int id, String supplierID) {
        super(id);
        _supplierID = supplierID;
    }

    /**
     * @return the order's Supplier ID.
     */
    public String getSupplierID() {
        return _supplierID;
    }

    /**
     * Adds a product to order.
     * 
     * @param e
     *          order element being added.
     */
    public void addProduct(OrderElement e) {
        _products.add(e);
    }

    /**
     * Returns a Map containing all order products and its quantities.
     * 
     * @return map with all order products and respetive quantities.
     */
    public Collection<OrderElement> getOrderProducts() {
        return Collections.unmodifiableCollection(_products);
    }

    /**
     * "Pays the order".
     * Empty method because orders are instantly paid on Store.
     */
    @Override
    public void pay() {}

    /**
     * Accepts a concrete transaction visitor.
     */
    @Override
    public void accept(TransactionVisitor visitor) {
        visitor.visit(this);
    }

    /**
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString() {
        String base = getID() + "|" + getSupplierID() + "|" + getBasePrice() + "|" +
        getPaymentDate();
        for (OrderElement e : getOrderProducts()) {
            base += '\n' + e.getProductID() + "|" + e.getProductQty();
        }
        return base;
    }

}
