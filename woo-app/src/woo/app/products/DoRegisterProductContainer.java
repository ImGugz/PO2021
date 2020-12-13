package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.DialogException;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.app.exceptions.UnknownServiceLevelException;
import woo.app.exceptions.UnknownServiceTypeException;
import woo.exceptions.DuplicateProductException;
import woo.exceptions.UnknownSupplierException;
import woo.exceptions.UnknownServLevelException;
import woo.exceptions.UnknownServTypeException;
import woo.Storefront;

/**
 * Register container.
 */
public class DoRegisterProductContainer extends Command<Storefront> {
  /** Input field. */
  private Input<String> _containerProductKey;

  /** Input field. */
  private Input<Integer> _containerPrice;

  /** Input field. */
  private Input<Integer> _containerCriticalSLevel;

  /** Input field. */
  private Input<String> _supplierKey;

  /** Input field. */
  private Input<String> _containerServiceType;

  /** Input field. */
  private Input<String> _containerServiceLevel;

  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoRegisterProductContainer(Storefront receiver) {
    super(Label.REGISTER_CONTAINER, receiver);
    _containerProductKey = _form.addStringInput(Message.requestProductKey());
    _containerPrice = _form.addIntegerInput(Message.requestPrice());
    _containerCriticalSLevel = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
    _containerServiceType = _form.addStringInput(Message.requestServiceType());
    _containerServiceLevel = _form.addStringInput(Message.requestServiceLevel());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();
      _receiver.registerContainer(_containerProductKey.value(), _containerPrice.value(), _containerCriticalSLevel.value(), 
                                  _supplierKey.value(), _containerServiceType.value(), _containerServiceLevel.value());
    } catch (DuplicateProductException e) {
      throw new DuplicateProductKeyException(e.getProductKey());
    } catch (UnknownSupplierException e) {
      throw new UnknownSupplierKeyException(e.getSupplierKey());
    } catch (UnknownServTypeException e) {
      throw new UnknownServiceTypeException(e.getServiceType());
    } catch (UnknownServLevelException e) {
      throw new UnknownServiceLevelException(e.getServiceLevel());
    }
  }
}
