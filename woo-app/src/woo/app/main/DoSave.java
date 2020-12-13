package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import woo.exceptions.MissingFileAssociationException;
import woo.Storefront;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<Storefront> {
  /** Input field. */
  private Input<String> _filenameToSave;

  /**
   * Constructor.
   * 
   * @param receiver
   */
  public DoSave(Storefront receiver) {
    super(Label.SAVE, receiver);
    if (_receiver.getFilename() == null) {
      _filenameToSave = _form.addStringInput(Message.newSaveAs());
    }
  }

  /**
   * @see pt.tecnico.po.ui.Command#execute()
   */
  @Override
  public final void execute() {
      try {
        if (_receiver.getFilename() == null) {
          _form.parse();
          _receiver.saveAs(_filenameToSave.value());
        } else {
          _receiver.save();
        }
      } catch (MissingFileAssociationException e) {
        e.printStackTrace();
      }
  }
}
