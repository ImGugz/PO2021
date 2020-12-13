package woo.app.clients;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                           
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.UnknownClientException;
import woo.Storefront;

/**
 * Show all transactions for a specific client.
 */
public class DoShowClientTransactions extends Command<Storefront> {
  /** Input field. */
  private Input<String> _clientKey;

  /**
   * Constructor.
   * 
   * @param storefront
   */
  public DoShowClientTransactions(Storefront storefront) {
    super(Label.SHOW_CLIENT_TRANSACTIONS, storefront);
    _clientKey = _form.addStringInput(Message.requestClientKey());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {
    try {
      _form.parse();
      for (var transaction : _receiver.getClientTransactions(_clientKey.value())) {
        _display.addLine(transaction.toString());
      }
      _display.display();
    } catch (UnknownClientException e) {
      throw new UnknownClientKeyException(e.getClientKey());
    }
  }

}
