package woo.app.transactions;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Form;
import woo.app.exceptions.UnauthorizedSupplierException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.app.exceptions.WrongSupplierException;
import woo.exceptions.InactiveSupplierException;
import woo.exceptions.IncorrectSupplierException;
import woo.exceptions.UnknownProductException;
import woo.exceptions.UnknownSupplierException;
import woo.OrderElement;
import woo.Storefront;

/**
 * Register order.
 */
public class DoRegisterOrderTransaction extends Command<Storefront> {
  /** Input field. */
  private Input<String> _supplierKey;

  /** Input field. */
  private Input<String> _productKey;

  /** Input field. */
  private Input<Integer> _productQty;

  /** Input field. */
  private Input<Boolean> _moreProducts;

  /** New form to accept more products. */
  private Form _newForm = new Form();

  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoRegisterOrderTransaction(Storefront receiver) {
    super(Label.REGISTER_ORDER_TRANSACTION, receiver);
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
    _productKey = _newForm.addStringInput(Message.requestProductKey());
    _productQty = _newForm.addIntegerInput(Message.requestAmount());
    _moreProducts = _newForm.addBooleanInput(Message.requestMore());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    _newForm.parse();
    List<OrderElement> _products = new ArrayList<OrderElement>();
    _products.add(new OrderElement(_productKey.value(), _productQty.value()));
    while (_moreProducts.value() == true) {
      _newForm.parse();
      _products.add(new OrderElement(_productKey.value(), _productQty.value()));
    }
    try {
      _receiver.registerOrderTransaction(_supplierKey.value(), Collections.unmodifiableCollection(_products));
    } catch (UnknownSupplierException e) {
        throw new UnknownSupplierKeyException(e.getSupplierKey());
    } catch (UnknownProductException e) {
        throw new UnknownProductKeyException(e.getProductKey());
    } catch (InactiveSupplierException e) {
      throw new UnauthorizedSupplierException(e.getSupplierKey());
    } catch (IncorrectSupplierException e) {
        throw new WrongSupplierException(e.getSupplierKey(), e.getProductKey());
    } finally {
      _products.clear(); // Clear structure after each order
    }
  }

}
