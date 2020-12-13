package woo;

import java.io.Serializable;

/**
 * Interface to define Notification Delivery strategy.
 * This interface is common to all delivery types, and declares
 * 3 methods that updates notifications, clears them and returns them.
 */
public interface NotificationDeliveryMode extends Serializable {
    /**
     * Add notification to pending notifications.
     * 
     * @param event
     *          notification event (NEW or BARGAIN)
     * @param pID
     *          product ID.
     * @param price
     *          product price.
     */
    Notification deliverNotification(String event, String pID, int price); // May be added a String containing notification delivery type.
}
