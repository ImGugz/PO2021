package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Menu;
import woo.Storefront;

/**
 * Open menu for transaction management.
 */
public class DoOpenMenuTransactions extends Command<Storefront> {
  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoOpenMenuTransactions(Storefront receiver) {
    super(Label.OPEM_MENU_TRANSACTIONS, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    Menu menu = new woo.app.transactions.Menu(_receiver);
    menu.open();
  }

}
