package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exceptions.UnknownTransactionKeyException;
import woo.exceptions.UnknownTransactionException;
import woo.Storefront;

/**
 * Pay transaction (sale).
 */
public class DoPay extends Command<Storefront> {
  /** Input field */
  private Input<Integer> _transactionID;
  
  /**
   * Constructor.
   * 
   * @param storefront
   */
  public DoPay(Storefront storefront) {
    super(Label.PAY, storefront);
    _transactionID = _form.addIntegerInput(Message.requestTransactionKey());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();
      _receiver.pay(_transactionID.value());
    } catch(UnknownTransactionException e) {
      throw new UnknownTransactionKeyException(e.getTransactionKey());
    }
   }

}
