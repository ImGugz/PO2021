package woo.app.clients;

import pt.tecnico.po.ui.Command;
import woo.Storefront;

/**
 * Show all clients.
 */
public class DoShowAllClients extends Command<Storefront> {
  /**
   * Constructor.
   * 
   * @param storefront
   */
  public DoShowAllClients(Storefront storefront) {
    super(Label.SHOW_ALL_CLIENTS, storefront);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() {
    for (var client : _receiver.getClients()) {
      _display.addLine(client.toString());
    }
    _display.display();
  }
}
