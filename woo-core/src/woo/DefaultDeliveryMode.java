package woo;

/**
 * Class DefaultDeliveryMode is an app-based notification delivery system.
 * All notifications sent with this strategy are app-leveled.
 */

public class DefaultDeliveryMode implements NotificationDeliveryMode {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012040059L;

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
    @Override
    public Notification deliverNotification(String event, String pID, int price) {
        return new Notification("", event, pID, price);
    }
    
}
