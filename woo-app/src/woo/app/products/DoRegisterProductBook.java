package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.exceptions.DuplicateProductException;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.exceptions.UnknownSupplierException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.Storefront;

/**
 * Register book.
 */
public class DoRegisterProductBook extends Command<Storefront> {
  /** Input field. */
  private Input<String> _bookProductKey;

  /** Input field. */
  private Input<String> _bookTitle;

  /** Input field. */
  private Input<String> _bookAuthor;

  /** Input field. */
  private Input<String> _bookISBN;

  /** Input field. */
  private Input<Integer> _bookPrice;

  /** Input field. */
  private Input<Integer> _bookCriticalSLevel;

  /** Input field. */
  private Input<String> _supplierKey;

  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoRegisterProductBook(Storefront receiver) {
    super(Label.REGISTER_BOOK, receiver);
    _bookProductKey = _form.addStringInput(Message.requestProductKey());
    _bookTitle = _form.addStringInput(Message.requestBookTitle());
    _bookAuthor = _form.addStringInput(Message.requestBookAuthor());
    _bookISBN = _form.addStringInput(Message.requestISBN());
    _bookPrice = _form.addIntegerInput(Message.requestPrice());
    _bookCriticalSLevel = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();
      _receiver.registerBook(_bookProductKey.value(), _bookTitle.value(), _bookAuthor.value(), 
                             _bookISBN.value(), _bookPrice.value(), _bookCriticalSLevel.value(), 
                             _supplierKey.value());
    } catch (DuplicateProductException e) {
      throw new DuplicateProductKeyException(e.getProductKey());
    } catch (UnknownSupplierException e) {
      throw new UnknownSupplierKeyException(e.getSupplierKey());
    }
  }
}
