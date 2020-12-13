package woo;

public class SelectionClient extends ClientStatus {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012040059L;
    
    /**
     * Constructor.
     * 
     * @param client
     *          client being set as Selection Status.
     */
    public SelectionClient(Client client) {
        super(client);
    }

    /**
     * Selection Client P2 Modifier.
     * 
     * @param paymentGap
     *          difference between current date and limit date.
     * @return modifier to multiply to base sale price relative to period P2.
     */
    @Override
    public double p2Modifier(int paymentGap) {
        return paymentGap >= 2 ? 0.95 : 1.0;
    }

    /**
     * Selection Client P3 Modifier.
     * 
     * @param paymentGap
     *          difference between current date and limit date.
     * @return modifier to multiply to base sale price relative to period P3.
     */
    @Override
    public double p3Modifier(int paymentGap) {
        return paymentGap == -1 ? 1.0 : 1.0 + 0.02 * (-paymentGap);
    }

    /**
     * Selection Client P4 Modifier.
     * 
     * @param paymentGap
     *          difference between current date and limit date.
     * @return modifier to multiply to base sale price relative to period P4.
     */
    @Override
    public double p4Modifier(int paymentGap) {
        return 1 + 0.05 * (-paymentGap);
    }

    /**
     * Selection Client pay sale. 
     * Client can "level-down" to Normal if payment is done 2 days after its limit.
     * Client can also "level-up" if paid within payment date limit and racks up more than 25000 points.
     * 
     * @param sale
     *          sale being paid.
     */
    @Override
    public void pay(Sale sale) {
        int paymentGap = sale.getLimitDateGap();
        if (paymentGap < -2) {
            _client.updateScore(_client.getScore() / 10);
            _client.updateStatus(new NormalClient(_client));
            return;
        }
        if (paymentGap >= 0) {
            _client.updateScore(_client.getScore() + 10 * (int) sale.getTotalPrice());
        }
        if (_client.getScore() > 25000) {
            _client.updateStatus(new EliteClient(_client));
        }
    }

    /**
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString() {
        return "SELECTION";
    }

}
