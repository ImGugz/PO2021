package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.UnknownProductException;
import woo.Storefront;

/**
 * Change product price.
 */
public class DoChangePrice extends Command<Storefront> {
  /** Input field. */
  private Input<String> _productKey;

  /** Input field. */
  private Input<Integer> _productNewPrice;
  
  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoChangePrice(Storefront receiver) {
    super(Label.CHANGE_PRICE, receiver);
    _productKey = _form.addStringInput(Message.requestProductKey());
    _productNewPrice = _form.addIntegerInput(Message.requestPrice());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();
      _receiver.changeProductPrice(_productKey.value(), _productNewPrice.value());
    } catch (UnknownProductException e) {
      throw new UnknownProductKeyException(e.getProductKey());
    }
  }
}
