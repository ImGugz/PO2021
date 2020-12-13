package woo;

/**
 * Class Sale is a subclass of a Transaction and it's instantiated
 * when the Store sells products to costumers (Clients).
 */
public class Sale extends Transaction {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012040059L;
    
    /** Client who made the Store sale. */
    private Client _client;

    /** Product sold to Client. */
    private Product _product;

    /** Limit date to sale payment. */
    private int _limitDate;

    /** Amount of Product demanded by Client. */
    private int _productQty;

    /** Total paid sale price. */
    private double _paidPrice;

    /**
     * Create sale.
     * 
     * @param id
     *          transaction ID.
     * @param clientID
     *          client ID.
     * @param product
     *          product bought.
     * @param limitDate
     *          limit date to pay (no fines)
     * @param amount
     *          qty of product bought.
     */
    public Sale(int id, Client client, Product product, int limitDate, int amount) {
        super(id);
        _client = client;
        _product = product; 
        _limitDate = limitDate;
        _productQty = amount;
    }

    /**
     * @return the sale's total price (after taxes).
     * the value returned from this funtion depends on the Client status
     * and the date where the payment was made.
     */
    public double getTotalPrice() {
        if (getPaymentStatus() == true) {
            return _paidPrice;
        }
        int N = _product.getN();
        int paymentGap = getLimitDateGap(); 
        if (paymentGap >= 0) {
            if (paymentGap >= N) {
                return getBasePrice() * _client.getStatus().p1Modifier();
            } else {
                return getBasePrice() * _client.getStatus().p2Modifier(paymentGap);
            }
        } else {
            paymentGap *= -1;
            if (0 < paymentGap && paymentGap <= N) {
                return getBasePrice() * _client.getStatus().p3Modifier(-paymentGap);
            } else {
                return getBasePrice() * _client.getStatus().p4Modifier(-paymentGap);
            }
        }
    }

    /**
     * @return the sale's client.
     */
    public Client getClient() {
        return _client;
    }

    /**
     * @return the sale's product.
     */
    public Product getProduct() {
        return _product;
    }

    /**
     * @return the sale's limit payment date
     */
    public int getLimitDate() {
        return _limitDate;
    }

    /**
     * @return the sale's amount of products
     */
    public int getProductQuantity() {
        return _productQty;
    }

    /**
     * Returns the difference between limit payment date and store's current date.
     * 
     * @return >= 0 if paid on time ; < 0 fines may be applied.
     */
    public int getLimitDateGap() {
        return _limitDate - getCurrentStoreDate();
    }

    /**
     * Updates sale paidPrice and paymentDate and makes client pay the sale.
     */
    @Override
    public void pay() {
        _paidPrice = getTotalPrice();
        setPaymentDate(getCurrentStoreDate());
        _client.paySale(this);
    }

    @Override
    public void accept(TransactionVisitor visitor) {
        visitor.visit(this);
    }

    /**
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString() {
        return getID() + "|" + getClient().getID() + "|" + getProduct().getID() + "|" + 
        getProductQuantity() + "|" + getBasePrice() + "|" + (int) getTotalPrice() + "|" + getLimitDate()
        + (getPaymentStatus() == true ? "|" + getPaymentDate() : "");
    }
}
