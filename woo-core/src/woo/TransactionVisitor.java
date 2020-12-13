package woo;

import java.io.Serializable;

/**
 * Interface used to declare the visit operations to visitable classes Order and Sale.
 */
public interface TransactionVisitor extends Serializable {
    /**
     * Visit an order.
     * @param order
     *          order being visited.
     */
    void visit(Order order);
    /**
     * Visit a sale.
     * @param sale
     *          sale being visited.
     */
    void visit(Sale sale);
}
