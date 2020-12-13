package woo;

/**
 * This is an abstract class representing a store transaction. Subclasses
 * refine this class in accordance to whether the store bought products or
 * sold them.
 */
public abstract class Transaction implements TransactionVisitable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012040059L;
    
    /** Transaction's unique ID. */
    private int _id;

    /** Transaction's base price (before taxes). */
    private int _basePrice = 0;

    /** Transaction's payment date. */
    private int _paymentDate;

    /** Transaction's paid status (true if paid; false, otherwise) */
    private boolean _paid = false;

    /** Store's date. */
    private int _storeDate;

    /**
     * Create new Transaction.
     * 
     * @param id
     *          transaction ID.
     */
    public Transaction(int id) {
        _id = id;
    }

    /**
     * @return the transaction's unique ID
     */
    public int getID() {
        return _id;
    }

    /**
     * @return the transaction's payment date.
     */
    public int getPaymentDate() {
        return _paymentDate;
    }
    
    /**
     * Returns transaction paid status.
     * 
     * @return true if transaction is paid; false, otherwise.
     */
    public boolean getPaymentStatus() {
        return _paid;
    }

    /**
     * Sets transaction payment date and transaction as paid.
     * Called upon store order or client sale payment.
     * 
     * @param date
     *          date of payment.
     */
    public void setPaymentDate(int date) {
        _paymentDate = date;
        _paid = true;
    }

    /**
     * Updates transaction with current store date.
     * @param date
     *          store date.
     */
    public void setCurrentStoreDate(int date) {
        _storeDate = date;
    }

    /**
     * Returns last saved store date on transcation.
     * @return transaction's last store date record.
     */
    public int getCurrentStoreDate() {
        return _storeDate;
    }

    /**
     * @return the transaction's base price (before taxes).
     */
    public int getBasePrice() {
        return _basePrice;
    }

    /**
     * Sets transaction's base price (before taxes).
     */
    public void setBasePrice(int basePrice) {
        _basePrice = basePrice;
    }

    /**
     * Pays the transaction.
     */
    public abstract void pay();

}