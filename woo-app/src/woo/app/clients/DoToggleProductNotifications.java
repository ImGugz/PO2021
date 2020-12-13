package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exceptions.UnknownClientKeyException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.UnknownClientException;
import woo.exceptions.UnknownProductException;
import woo.Storefront;

/**
 * Toggle product-related notifications.
 */
public class DoToggleProductNotifications extends Command<Storefront> {
  /** Input field. */
  private Input<String> _clientKey;

  /** Input field. */
  private Input<String> _productKey;

  /**
   * Constructor.
   * 
   * @param storefront
   */
  public DoToggleProductNotifications(Storefront storefront) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, storefront);
    _clientKey = _form.addStringInput(Message.requestClientKey());
    _productKey = _form.addStringInput(Message.requestProductKey());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {
    try {
      _form.parse();
      _display.popup(_receiver.areProductNotificationsOn(_clientKey.value(), _productKey.value()) ? 
                     Message.notificationsOn(_clientKey.value(), _productKey.value()) : Message.notificationsOff(_clientKey.value(), _productKey.value()));
    } catch (UnknownClientException e) {
      throw new UnknownClientKeyException(e.getClientKey());
    } catch (UnknownProductException e) {
      throw new UnknownProductKeyException(e.getProductKey());
    }
  }

}
