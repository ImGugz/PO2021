package woo;

import java.io.Serializable;

/**
 * This is an abstract class representing a client status. Subclasses refine
 * this class in accordance with the client's sale payment operation.
 */

public abstract class ClientStatus implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012040059L;
    
    /** The client associated to each status. */
    protected Client _client;

    /**
     * Initializes client's status.
     * 
     * @param client
     *          client whose status is being instantiated.
     */
    public ClientStatus(Client client) {
        _client = client;
    }

    /** Period P1 discount (10%). */
    public double p1Modifier() {
        return 0.9;
    }

    /**
     * Period P2 discount.
     * 
     * @param paymentGap
     *          difference between current date and limit date.
     * @return modifier to multiply to base sale price relative to period P2.
     */
    public abstract double p2Modifier(int paymentGap);

    /**
     * Period P3 discount.
     * 
     * @param paymentGap
     *          difference between current date and limit date.
     * @return modifier to multiply to base sale price relative to period P3.
     */
    public abstract double p3Modifier(int paymentGap);

    /**
     * Period P4 discount.
     * 
     * @param paymentGap
     *          difference between current date and limit date.
     * @return modifier to multiply to base sale price relative to period P4.
     */
    public abstract double p4Modifier(int paymentGap);

    /**
     * Pays sale.
     * 
     * @param sale
     *          sale to be paid.
     */
    public abstract void pay(Sale sale);
    
}
