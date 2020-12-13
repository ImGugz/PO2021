package woo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Class Client contains informations about store clients. Each client
 * is identified by a key (String). It also implements Observer interface
 * to be notified about Product changes.
 */
public class Client implements ProductObserver {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012040059L;
    
    /** Client's status. */
    private ClientStatus _status = new NormalClient(this);

    /** Client's unique ID. */
    private String _id;

    /** Client's name. */
    private String _name;

    /** Client's address. */
    private String _address;

    /** Client's score (sale points). */
    private int _score = 0;

    /** Client's base sales total price. */
    private int _basePrices = 0;

    /** Client's paid sales amount. */
    private double _totalPaid = 0;

    /** Client's chosen notification delivery type. */
    private NotificationDeliveryMode _deliveryMode;

    /** The list of sales associated to this client. */
    private List<Sale> _sales = new ArrayList<Sale>();

    /** List containg client's unread notifications.  */
    private List<Notification> _notifications = new ArrayList<Notification>();

    /**
     * Calls main constructor with Default Delivery notification delivery system.. * 
     * @param id
     *          client ID.
     * @param name
     *          client name.
     * @param address
     *          client address.
     */
    public Client(String id, String name, String address) { 
        this(id, name, address, new DefaultDeliveryMode());
    }

    /**
     * Main constructor.
     * Create client.
     * 
     * @param id
     *          client ID.
     * @param name
     *          client name.
     * @param address
     *          client address.
     * @param mode
     *          client chosen delivery mode.
     */
    public Client(String id, String name, String address, NotificationDeliveryMode mode) {
        _id = id;
        _name = name;
        _address = address;
        _deliveryMode = mode;
    }

    /**
     * Returns the client's status.
     * 
     * @return the client's current status
     */
    public ClientStatus getStatus() {
        return _status;
    }

    /**
     * @return the client's ID.
     */
    public String getID() {
        return _id;
    }

    /**
     * @return the client's name.
     */
    public String getName() {
        return _name;
    }

    /**
     * @return the client's address.
     */
    public String getAddress() {
        return _address;
    }

    /**
     * @return the client's score (total points).
     */
    public int getScore() {
        return _score;
    }

    /**
     * @return the client's total amount of sale base prices.
     */
    public int getTotalBasePrices() {
        return _basePrices;
    }

    /**
     * @return the total paid in sales by the client.
     */
    public int getTotalPaid() {
        return (int) _totalPaid;
    }

    /**
     * Returns the client's chosen delivery mode.
     * 
     * @return the client's current delivery mode
     */
    public NotificationDeliveryMode getDeliveryMode() {
        return _deliveryMode;
    }

    /**
     * Returns the client's sales made as an unmodifiable collection.
     * 
     * @return a collection with the client's sales.
     */
    public Collection<Sale> getClientSales() {
        return Collections.unmodifiableCollection(_sales);
    }

    /**
     * Returns the client's pending notifications as an unmodifiable collection
     * 
     * @return a collection with the client's pending notifications.
     */
    public Collection<Notification> getClientNotifications() {
        final List<Notification> _notificationsCopy = _notifications;
        _notifications = new ArrayList<Notification>();
        return Collections.unmodifiableCollection(_notificationsCopy);
    }

    /**
     * Adds a sale to client's sales list.
     * Also updates total sale base prices.
     * 
     * @param sale
     *          sale being added to list of client's sales.
     */
    public void addSale(Sale sale) {
        _sales.add(sale);
        _basePrices += sale.getBasePrice();
    }

    /**
     * Updates client's total paid sales amount.
     * 
     * @param salePrice
     *          price of paid sale to be added to client's total paid sales.
     */
    public void addPaidSale(double salePrice) {
        _totalPaid += salePrice;
    }

    /**
     * Updates client's status.
     * 
     * @param status
     *          client's new status.
     */
    public void updateStatus(ClientStatus status) {
        _status = status;
    }

    /**
     * Updates client's points/score.
     * 
     * @param score 
     *          client's new score
     */
    public void updateScore(int score) {
        _score = score;
    }
    
    /**
     * Pays client's sale.
     * 
     * @param sale
     *          sale being paid.
     */
    public void paySale(Sale sale) {
        _status.pay(sale);
        addPaidSale(sale.getTotalPrice());
    }

    /**
     * Update client using their chosen delivery mode.
     * 
     * @param notification
     *          notification being added.
     */
    @Override
    public void update(String event, String pID, int price) {
        _notifications.add(_deliveryMode.deliverNotification(event, pID, price));
    }

    /**
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString() {
        return getID() + "|" + getName() + "|" + getAddress() + "|" + getStatus().toString() + "|" + getTotalBasePrices() + "|" + getTotalPaid(); 
    }

}
