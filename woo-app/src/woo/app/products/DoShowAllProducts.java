package woo.app.products;

import pt.tecnico.po.ui.Command;
import woo.Storefront;

/**
 * Show all products.
 */
public class DoShowAllProducts extends Command<Storefront> {
  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoShowAllProducts(Storefront receiver) {
    super(Label.SHOW_ALL_PRODUCTS, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    for (var product: _receiver.getProducts()) {
      _display.addLine(product.toString());
    }
    _display.display();
  }

}
