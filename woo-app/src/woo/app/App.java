package woo.app;

import static pt.tecnico.po.ui.Dialog.IO;

import java.io.IOException;

import pt.tecnico.po.ui.Menu;
import woo.Storefront;
import woo.exceptions.BadEntryException;
import woo.exceptions.ImportFileException;

/** Main driver for the management application. */
public class App {

  /** @param args command line arguments. */
  public static void main(String[] args) {
    Storefront storefront = new Storefront();

    String datafile = System.getProperty("import");
    if (datafile != null) {
      try {
        storefront.importFile(datafile);
      } catch (ImportFileException e) {
        e.printStackTrace();
      }
    }

    Menu menu = new woo.app.main.Menu(storefront);
    menu.open();

    IO.close();
  }

}
