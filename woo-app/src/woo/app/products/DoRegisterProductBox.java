package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input; 
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownServiceTypeException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.DuplicateProductException;
import woo.exceptions.UnknownServTypeException;
import woo.exceptions.UnknownSupplierException;
import woo.Storefront;

/**
 * Register box.
 */
public class DoRegisterProductBox extends Command<Storefront> {
  /** Input field. */
  private Input<String> _boxProductKey;

  /** Input field. */
  private Input<Integer> _boxPrice;

  /** Input field. */
  private Input<Integer> _boxCriticalSLevel;

  /** Input field. */
  private Input<String> _supplierKey;

  /** Input field. */
  private Input<String> _boxServiceType;

  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoRegisterProductBox(Storefront receiver) {
    super(Label.REGISTER_BOX, receiver);
    _boxProductKey = _form.addStringInput(Message.requestProductKey());
    _boxPrice = _form.addIntegerInput(Message.requestPrice());
    _boxCriticalSLevel = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
    _boxServiceType = _form.addStringInput(Message.requestServiceType());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();
      _receiver.registerBox(_boxProductKey.value(), _boxPrice.value(), _boxCriticalSLevel.value(), 
                            _supplierKey.value(), _boxServiceType.value());
    } catch (DuplicateProductException e) {
      throw new DuplicateProductKeyException(e.getProductKey());
    } catch (UnknownSupplierException e) {
      throw new UnknownSupplierKeyException(e.getSupplierKey());
    } catch (UnknownServTypeException e) {
      throw new UnknownServiceTypeException(e.getServiceType());
    }
  }
}
