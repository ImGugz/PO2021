package woo.app.main;

import pt.tecnico.po.ui.Command;
import woo.Storefront;

/**
 * Show global balance.
 */
public class DoShowGlobalBalance extends Command<Storefront> {
  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoShowGlobalBalance(Storefront receiver) {
    super(Label.SHOW_BALANCE, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    _display.popup(Message.currentBalance(_receiver.getAvailableBalance(), _receiver.getAccountingBalance()));
  }
}
