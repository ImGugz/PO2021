package woo;

import java.io.Serializable;

/**
 * Interface that allows interface to be visited by some Visitor.
 */
public interface TransactionVisitable extends Serializable {
    /**
     * Accepts the visitor.
     * @param visitor
     *          visitor that's visiting Visitable.
     */
    void accept(TransactionVisitor visitor);
}
