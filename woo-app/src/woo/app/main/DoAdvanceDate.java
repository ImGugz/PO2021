package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exceptions.InvalidDateException;
import woo.exceptions.InvalidDaysException;
import woo.Storefront;

/**
 * Advance current date.
 */
public class DoAdvanceDate extends Command<Storefront> {
  /** Input field. */
  private Input<Integer> _daysToAdvance;

  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoAdvanceDate(Storefront receiver) {
    super(Label.ADVANCE_DATE, receiver);
    _daysToAdvance = _form.addIntegerInput(Message.requestDaysToAdvance());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();
      _receiver.advanceDate(_daysToAdvance.value());
    } catch (InvalidDaysException e) {
      throw new InvalidDateException(e.getInvalidDays());
    }
  }
}
