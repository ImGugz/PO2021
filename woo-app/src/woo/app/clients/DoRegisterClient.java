package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exceptions.DuplicateClientKeyException;
import woo.exceptions.DuplicateClientException;
import woo.Storefront;

/**
 * Register new client.
 */
public class DoRegisterClient extends Command<Storefront> {
  /** Input field. */
  private Input<String> _clientKey;

  /** Input field. */
  private Input<String> _clientName;

  /** Input field. */
  private Input<String> _clientAddress;

  /**
   * Constructor.
   * 
   * @param storefront
   */
  public DoRegisterClient(Storefront storefront) {
    super(Label.REGISTER_CLIENT, storefront);
    _clientKey = _form.addStringInput(Message.requestClientKey());
    _clientName = _form.addStringInput(Message.requestClientName());
    _clientAddress = _form.addStringInput(Message.requestClientAddress());
  }
  
  /** @see pt.tecnico.po.ui.Command#execute() */ 
  @Override
  public void execute() throws DialogException {
    try {
      _form.parse();
      _receiver.registerClient(_clientKey.value(), _clientName.value(), _clientAddress.value());
    } catch (DuplicateClientException e) {
      throw new DuplicateClientKeyException(e.getClientKey());
    }
  }

}
