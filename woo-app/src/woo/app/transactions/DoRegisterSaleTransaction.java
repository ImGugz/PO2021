package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exceptions.UnknownClientKeyException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.app.exceptions.UnavailableProductException;
import woo.exceptions.UnknownClientException;
import woo.exceptions.UnknownProductException;
import woo.exceptions.NoEnoughStockProductException;
import woo.Storefront;

/**
 * Register sale.
 */
public class DoRegisterSaleTransaction extends Command<Storefront> {
  /** Input field. */
  private Input<String> _clientKey;

  /** Input field. */
  private Input<Integer> _limitDate;

  /** Input field. */
  private Input<String> _productKey;

  /** Input field. */
  private Input<Integer> _productQty;

  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoRegisterSaleTransaction(Storefront receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    _clientKey = _form.addStringInput(Message.requestClientKey());
    _limitDate = _form.addIntegerInput(Message.requestPaymentDeadline());
    _productKey = _form.addStringInput(Message.requestProductKey());
    _productQty = _form.addIntegerInput(Message.requestAmount());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();
      _receiver.registerSaleTransaction(_clientKey.value(), _limitDate.value(), _productKey.value(), _productQty.value());
    } catch (UnknownClientException e) {
      throw new UnknownClientKeyException(e.getClientKey());
    } catch (UnknownProductException e) {
      throw new UnknownProductKeyException(e.getProductKey());
    } catch (NoEnoughStockProductException e) {
      throw new UnavailableProductException(e.getProductKey(), e.getRequested(), e.getAvailable());
    }
  }

}
