package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exceptions.DuplicateSupplierKeyException;
import woo.exceptions.DuplicateSupplierException;
import woo.Storefront;

/**
 * Register supplier.
 */
public class DoRegisterSupplier extends Command<Storefront> {
  /** Input field. */
  private Input<String> _supplierKey;

  /** Input field. */
  private Input<String> _supplierName;

  /** Input field. */
  private Input<String> _supplierAddress;

  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoRegisterSupplier(Storefront receiver) {
    super(Label.REGISTER_SUPPLIER, receiver);
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
    _supplierName = _form.addStringInput(Message.requestSupplierName());
    _supplierAddress = _form.addStringInput(Message.requestSupplierAddress());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {
    try {
      _form.parse();
      _receiver.registerSupplier(_supplierKey.value(), _supplierName.value(), _supplierAddress.value());
    } catch (DuplicateSupplierException e) {
      throw new DuplicateSupplierKeyException(e.getSupplierKey());
    }
  }

}
