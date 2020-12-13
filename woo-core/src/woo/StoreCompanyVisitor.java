package woo;

/**
 * This class is a concrete visitor, where orders and sales update
 * company's total balance, by overriding each visit method.
 */
public class StoreCompanyVisitor implements TransactionVisitor {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012090259L;
    /** Company initial balance. */
    private float _totalBalance = 0;

    /**
     * Visit an order and decrease company's total balance.
     * 
     * @param order
     *          order being visited.
     */
    @Override
	public void visit(Order order) {
        _totalBalance -= order.getBasePrice();
	}

    /**
     * Visit a sale and increase company's total balance.
     * 
     * @param sale
     *          sale being visited.
     */
	@Override
	public void visit(Sale sale) {
        _totalBalance += sale.getTotalPrice();
    }

    /**
     * Returns company's total balance rounded to nearest integer.
     * 
     * @return company's total balance.
     */
    public int getTotalBalance() {
        return Math.round(_totalBalance);
    }
    
}
