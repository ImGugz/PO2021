package woo;

import java.io.Serializable;

/**
 * Class Notification is set to be instantiated when a Client 
 * wants to be received about specific product events, like restock
 * or sales.
 */

public class Notification implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012040059L;
    
    /** Notification delivery mode name. */
    private String _deliveryMode;

    /** Notification event - NEW or BARGAIN */
    private String _event;

    /** Notification's product ID. */
    private String _productID;

    /** Notification's price. */
    private int _price;

    /**
     * Constructor.
     * 
     * @param deliveryMode
     *          delivery mode name.
     * @param event
     *          notifcation event.
     * @param productID
     *          notification's product ID.
     * @param price
     *          notifications' product price.
     */
    public Notification(String deliveryMode, String event, String productID, int price) {
        _deliveryMode = deliveryMode;
        _event = event;
        _productID = productID;
        _price = price;
    }

    /**
     * @return notification's delivery mode name.
     */
    public String getDeliveryModeName() {
        return _deliveryMode;
    }

    /**
     * @return notification's event.
     */
    public String getEvent() {
        return _event;
    }

    /**
     * @return notification's product ID.
     */
    public String getProductID() {
        return _productID;
    }

    /**
     * @return notification's product price.
     */
    public int getProductPrice() {
        return _price;
    }

    /**
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString() {
        return getDeliveryModeName() + getEvent() + "|" + getProductID() + "|" + getProductPrice();
    }
}
