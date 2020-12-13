package woo;

import java.io.Serializable;

/**
 * The Observer interface is going to be implemented by all the Observers
 * (in this case, for now, Clients are the only ones), so they all have to 
 * implement the update() method.
 */

public interface ProductObserver extends Serializable {
    /**
     * Adds a notification to array of Client notifications.
     * 
     * @param event
     *          notification event.
     * @param pID
     *          product ID.
     * @param price
     *          product price.
     */
    void update(String event, String pID, int price);
}
