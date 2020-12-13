package woo;

import java.io.Serializable;

/**
 * This class OrderElement is a part of Order class. Orders are composed
 * of OrderElements. 
 */
public class OrderElement implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012100059L;

    /** An order's product's ID. */
    private String _productID;

    /** An order's product's qty. */
    private int _productQty;

    /**
     * Constructor.
     * 
     * @param productID
     *          product ID.
     * @param productQty
     *          product qty.
     */
    public OrderElement(String productID, int productQty) {
        _productID = productID;
        _productQty = productQty;
    }

    /**
     * Returns a product ID associated to an Order.
     * 
     * @return an order's product's ID.
     */
    public String getProductID() {
        return _productID;
    }

    /**
     * Returns a product quantity associated to an Order.
     * 
     * @return an order's product's quantity.
     */
    public int getProductQty() {
        return _productQty;
    }
    
    /**
     * Updates a product's ID.
     * 
     * @param pID
     *          product ID.
     */
    public void setProductID(String pID) {
        _productID = pID;
    }

    /**
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString() {
        return _productID + "|" + _productQty;
    }
}
