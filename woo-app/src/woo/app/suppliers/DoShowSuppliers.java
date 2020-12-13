package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import woo.Storefront;

/**
 * Show all suppliers.
 */
public class DoShowSuppliers extends Command<Storefront> {
  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoShowSuppliers(Storefront receiver) {
    super(Label.SHOW_ALL_SUPPLIERS, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() {
    for (var supplier: _receiver.getSuppliers()) {
      _display.addLine(supplier.toString() + (supplier.isActive() ? Message.yes() : Message.no()));
    }
    _display.display();
  }
}
