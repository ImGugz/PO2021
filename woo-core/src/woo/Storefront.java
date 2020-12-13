package woo;

import woo.exceptions.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * Storefront: façade for the core classes.
 */
public class Storefront {
  /** Storefront's save filename. */
  private String _filename;

  /** The actual store. */
  private Store _store = new Store();

  /** Flag on whether store has been modified since previous save. */
  private boolean _save = true;

  /**
   * @return the storefront's save filename.
   */
  public String getFilename() {
    return _filename;
  }

  /**
   * @return the storefront's save flag.
   */
  public boolean getSave() {
    return _save;
  }

  /* ------------------------------------- LOAD AND SAVE ------------------------------------- */

  /**
   * Imports a text file used to initialize app.
   * 
   * @param textfile
   *          text file being imported.
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _store.importFile(textfile);
    } catch (IOException | BadEntryException | DuplicateSupplierException | 
             UnknownSupplierException | DuplicateClientException | DuplicateProductException | 
             UnknownServTypeException | UnknownServLevelException e) {
      throw new ImportFileException(textfile);
    }
  }

  /**
   * Loads store data from a previous session from a previously saved file.
   * 
   * @param filename
   *          file being opened.
   * @throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException {
    try {
      ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
      _store = (Store) in.readObject();
      in.close();
      _filename = filename;
    } catch (IOException | ClassNotFoundException e) {
      throw new UnavailableFileException(filename);
    }
  }

  /**
   * Saves current store data in storefront's associated filename.
   * 
   * @throws MissingFileAssociationException
   */
  public void save() throws MissingFileAssociationException {
    try {
      if (_save == true) {
        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
        out.writeObject(_store);
        out.close();
        _save = false;
      }
    } catch(IOException e) {
      throw new MissingFileAssociationException();
    }
  }

  /**
   * Sets storefront's filename and saves current store.
   * 
   * @param filename
   *          new storefront filename.
   * @throws MissingFileAssociationException
   */
  public void saveAs(String filename) throws MissingFileAssociationException {
    _filename = filename;
    save();
  }

  /* ------------------------------------------ DATE ------------------------------------------ */

  /**
   * Returns the store's current date.
   * 
   * @return store current date.
   */
  public int getDate() {
    return _store.getDate();
  }

  /**
   * Advances store's date.
   * 
   * @param days
   *          date days being advanced.
   * @throws InvalidDaysException
   *            if days <= 0.
   */
  public void advanceDate(int days) throws InvalidDaysException {
    _save = true;
    _store.advanceDate(days);
  }

  /* -------------------------------------- STORE BALANCE ------------------------------------- */

  /**
   * Returns store's available balance.
   * 
   * @return store available balance.
   */
  public int getAvailableBalance() {
    return _store.getAvailableBalance();
  }

  /**
   * Returns store's accounting balance.
   * 
   * @return store accounting balance.
   */
  public int getAccountingBalance() {
    return _store.getAccountingBalance();
  }

  /* ---------------------------------------- PRODUCTS ---------------------------------------- */

  /**
   * Return all the store products as an unmodifiable collection.
   * 
   * @return a collection with all store products.
   */
  public Collection<Product> getProducts() {
    return _store.getProducts();
  }

  /**
   * Registers a box in store.
   * 
   * @param pID
   *          box product ID.
   * @param price
   *          box product price.
   * @param cValue
   *          box product critical stock level.
   * @param sID
   *          supplier ID.
   * @param serviceType
   *          box service type.
   * @param amount
   *          qty of boxes registered
   * @throws DuplicateProductException
   * @throws UnknownSupplierException
   * @throws UnknownServTypeException
   */
  public void registerBox(String pID, int price, int cValue, String sID, String serviceType) throws DuplicateProductException, UnknownSupplierException, UnknownServTypeException {
    _save = true;
    _store.registerBox(pID, price, cValue, sID, serviceType, 0);
  }

  /**
   * Registers a container in store.
   * 
   * @param pID
   *          container product ID.
   * @param price 
   *          container product price.
   * @param cValue
   *          container product critical stock level.
   * @param sID
   *          supplier ID.
   * @param serviceType
   *          container service type.
   * @param serviceLevel
   *          container service level.
   * @param amount
   *          qty of containers registered
   * @throws DuplicateProductException
   * @throws UnknownSupplierException
   * @throws UnknownServTypeException
   * @throws UnknownServLevelException
   */
  public void registerContainer(String pID, int price, int cValue, String sID, String serviceType, String serviceLevel) throws DuplicateProductException, UnknownSupplierException, UnknownServTypeException, UnknownServLevelException {
    _save = true;
    _store.registerContainer(pID, price, cValue, sID, serviceType, serviceLevel, 0);
  }

  /**
   * Registers a book in store.
   * 
   * @param pID
   *          book product ID.
   * @param title
   *          book title.
   * @param author
   *          book author.
   * @param isbn
   *          book isbn.
   * @param price
   *          book product price.
   * @param cValue
   *          book product critical stock level.
   * @param sID
   *          supplier ID.
   * @param amount
   *          qty of books registered.
   * @throws DuplicateProductException
   * @throws UnknownSupplierException
   */
  public void registerBook(String id, String title, String author, String isbn, int price, int cValue, String sID) throws DuplicateProductException, UnknownSupplierException {
    _save = true;
    _store.registerBook(id, title, author, isbn, price, cValue, sID, 0);
  }

  /**
   * Changes a product's price.
   * 
   * @param pID
   *          product ID.
   * @param newPrice
   *          new product price.
   * @throws UnknownProductException
   */
  public void changeProductPrice(String pID, int newPrice) throws UnknownProductException {
    _save = true;
    _store.changeProductPrice(pID, newPrice);
  }

  /* --------------------------------------- CLIENTS ---------------------------------------- */

  /**
   * Returns a client given a client unique ID.
   * 
   * @param cID
   *          client ID.
   * @return
   * @throws UnknownClientException
   */
  public Client getClient(String cID) throws UnknownClientException {
    return _store.getClient(cID);
  }

  /**
   * Returns all the store clients as an unmodifiable collection.
   * 
   * @return a collection with all store clients.
   */
  public Collection<Client> getClients() {
    return _store.getClients();
  }

  /**
   * Registers a client in store.
   * 
   * @param cID
   *          client ID.
   * @param name
   *          client name.
   * @param address
   *          client address.
   * @throws DuplicateClientException
   */
  public void registerClient(String cID, String name, String address) throws DuplicateClientException {
    _save = true;
    _store.registerClient(cID, name, address);
  }

  /**
   * Returns a client's notifications as an unmodifiable collection.
   * 
   * @param cID
   *          client ID.
   * @return a collection with all client's notifications.
   * @throws UnknownClientException
   */
  public Collection<Notification> getClientNotifications(String cID) throws UnknownClientException {
    return _store.getClientNotifications(cID);
  }

  /**
   * Activates/Deactivates a client's specific product notifications.
   * 
   * @param cID
   *          client ID.
   * @param pID
   *          product ID.
   * @throws UnknownClientException
   * @throws UnknownProductException
   */
  public boolean areProductNotificationsOn(String cID, String pID) throws UnknownClientException, UnknownProductException {
    _save = true;
    return _store.areProductNotificationsOn(cID, pID);
  }
  
  /**
   * Returns all the client transactions as an unmodifiable collection.
   * 
   * @param cID
   *          client ID.
   * @return a collection with all the client transactions.
   * @throws UnknownClientException
   */
  public Collection<Sale> getClientTransactions(String cID) throws UnknownClientException {
    return _store.getClientTransactions(cID);
  }

  /* -------------------------------------- SUPPLIERS --------------------------------------- */

  /**
   * Returns all the store suppliers as an unmodifiable collection.
   * 
   * @return a collection with all store suppliers.
   */
  public Collection<Supplier> getSuppliers() {
    return _store.getSuppliers();
  }

  /**
   * Registers a supplier in store.
   * 
   * @param sID
   *          supplier ID.
   * @param name
   *          supplier name.
   * @param address
   *          supplier address.
   * @throws DuplicateSupplierException
   */
  public void registerSupplier(String sID, String name, String address) throws DuplicateSupplierException {
    _save = true;
    _store.registerSupplier(sID, name, address);
  }

  /**
   * Activates/Deactivates a supplier's ability to perform transactions.
   * 
   * @param sID
   *          supplier ID.
   * @return true if supplier is now able to perform transactions; false, otherwise.
   * @throws UnknownSupplierException
   */
  public boolean areSupplierTransactionsOn(String sID) throws UnknownSupplierException {
    _save = true;
    return _store.areSupplierTransactionsOn(sID);
  }

  /**
   * Returns all the supplier transactions as an unmodifiable collection.
   * 
   * @param sID
   *          supplier ID.
   * @return a collection with all the supplier transactions.
   * @throws UnknownSupplierException
   */
  public Collection<Order> getSupplierTransactions(String sID) throws UnknownSupplierException {
    return _store.getSupplierTransactions(sID);
  }

  /* ------------------------------------- TRANSACTIONS ------------------------------------- */

  /**
   * Returns a transaction given a unique transaction ID.
   * 
   * @param tID
   *          transaction unique ID.
   * @return a transaction given its id.
   * @throws UnknownTransactionException
   */
  public Transaction getTransaction(int tID) throws UnknownTransactionException {
    return _store.getTransaction(tID);
  }

  /**
   * Registers a store sale.
   * 
   * @param cID
   *          client ID.
   * @param limDate
   *          limit payment date.
   * @param pID
   *          sold product ID .
   * @param qty
   *          product amount sold.
   * @throws UnknownClientException
   * @throws UnknownProductException
   * @throws NoEnoughStockProductException
   */
  public void registerSaleTransaction(String cID, int limDate, String pID, int qty)
      throws UnknownClientException, UnknownProductException, NoEnoughStockProductException {
    _save = true;
    _store.registerSaleTransaction(cID, limDate, pID, qty);
  }

  /**
   * Registers a store order.
   * 
   * @param sID
   *          supplier ID.
   * @param products
   *          map containing product and its quantities ordered.
   * @throws UnknownSupplierException
   * @throws UnknownProductException
   * @throws InactiveSupplierException
   * @throws IncorrectSupplierException
   */
  public void registerOrderTransaction(String sID, Collection<OrderElement> products)
      throws UnknownSupplierException, UnknownProductException, InactiveSupplierException, IncorrectSupplierException {
    _save = true;
    _store.registerOrderTransaction(sID, products);
  }

  /**
   * Pays a transaction given its unique ID.
   * 
   * @param tID
   *          transaction ID.
   * @throws UnknownTransactionException
   */
  public void pay(int tID) throws UnknownTransactionException {
    _save = true;
    _store.pay(tID);
  }

  /* --------------------------------------- LOOKUPS ---------------------------------------- */

  /**
   * Returns a given client's paid sales as an unmodifiable collection.
   * 
   * @param cID
   *          client ID.
   * @return a collection with client's paid sales.
   * @throws UnknownClientException
   */
  public Collection<Sale> lookupPaymentsByClient(String cID) throws UnknownClientException {
    return _store.lookupPaymentsByClient(cID);
  }

  /**
   * Returns all store products whose price is cheapear than given price as an unmodifiable collection.
   * 
   * @param price
   *          reference price.
   * @return a collection with all products whose price is under given price.
   */
  public Collection<Product> lookupProductsUnderPrice(int price) { 
    return _store.lookupProductsUnderPrice(price);
  }
  
}
