package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.UnknownSupplierException;
import woo.Storefront;

/**
 * Show all transactions for specific supplier.
 */
public class DoShowSupplierTransactions extends Command<Storefront> {
  /** Input field. */
  private Input<String> _supplierKey;

  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoShowSupplierTransactions(Storefront receiver) {
    super(Label.SHOW_SUPPLIER_TRANSACTIONS, receiver);
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {
    try {
      _form.parse();
      for (var transaction: _receiver.getSupplierTransactions(_supplierKey.value())) {
        _display.addLine(transaction.toString());
      }
      _display.display();
    } catch(UnknownSupplierException e) {
      throw new UnknownSupplierKeyException(e.getSupplierKey());
    }
  }

}
