package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.exceptions.UnknownSupplierException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.Storefront;

/**
 * Enable/disable supplier transactions.
 */
public class DoToggleTransactions extends Command<Storefront> {
  /** Input field. */
  private Input<String> _supplierKey;

  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoToggleTransactions(Storefront receiver) {
    super(Label.TOGGLE_TRANSACTIONS, receiver);
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {
    try {
      _form.parse();
      _display.popup(_receiver.areSupplierTransactionsOn(_supplierKey.value()) ? 
                     Message.transactionsOn(_supplierKey.value()) : Message.transactionsOff(_supplierKey.value()));
    } catch (UnknownSupplierException e) {
      throw new UnknownSupplierKeyException(e.getSupplierKey());
    }
  }

}
