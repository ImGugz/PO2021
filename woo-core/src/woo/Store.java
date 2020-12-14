package woo;

import woo.exceptions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.Collections;

/**
 * Class Store implements a store.
 */
public class Store implements Serializable {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  /** Store transactions. */
  private Map<Integer, Transaction> _transactions = new TreeMap<Integer, Transaction>();

  /** Store clients. */
  private Map<String, Client> _clients = new TreeMap<String, Client>(String.CASE_INSENSITIVE_ORDER);

  /** Store products. */
  private Map<String, Product> _products = new TreeMap<String, Product>(String.CASE_INSENSITIVE_ORDER);

  /** Store suppliers. */
  private Map<String, Supplier> _suppliers = new TreeMap<String, Supplier>(String.CASE_INSENSITIVE_ORDER);

  /** Current date. */
  private int _date = 0;

  /** Transaction ID. */
  private int _transactionID = 0;

  /* ------------------------------------- IMPORT FILE ------------------------------------- */

  /**
   * Imports a text file used to initialize app.
   * 
   * @param textfile
   *          text file being imported.
   */
  void importFile(String txtfile) throws IOException, BadEntryException, DuplicateSupplierException, UnknownSupplierException, 
                                         DuplicateClientException, UnknownServTypeException, DuplicateProductException, UnknownServLevelException {
    BufferedReader reader = new BufferedReader(new FileReader(txtfile));
    String line;
    while ((line = reader.readLine()) != null) {
      String[] fields = line.split("\\|");
      registerFromFields(fields);
    }
    reader.close();
  }

  /**
   * Aux method to importFile.
   * 
   * @param fields
   *          array of fields containing specific input attributes.
   * @throws BadEntryException
   * @throws DuplicateSupplierException
   * @throws UnknownSupplierException
   * @throws DuplicateClientException
   * @throws UnknownServTypeException
   * @throws DuplicateProductException
   * @throws UnknownServLevelException
   */
  void registerFromFields(String[] fields) throws BadEntryException, DuplicateSupplierException, UnknownSupplierException, 
                                          DuplicateClientException, UnknownServTypeException, DuplicateProductException, UnknownServLevelException {
    Pattern patSupplier = Pattern.compile("^(SUPPLIER)");
    Pattern patClient = Pattern.compile("^(CLIENT)");
    Pattern patBox = Pattern.compile("^(BOX)");
    Pattern patContainer = Pattern.compile("^(CONTAINER)");
    Pattern patBook = Pattern.compile("^(BOOK)");
    if (patSupplier.matcher(fields[0]).matches()) {
      registerSupplier(fields[1], fields[2], fields[3]);
    } else if (patClient.matcher(fields[0]).matches()) {
      registerClient(fields[1], fields[2], fields[3]);
    } else if (patBox.matcher(fields[0]).matches()) {
      int price = Integer.parseInt(fields[4]);
      int cValue = Integer.parseInt(fields[5]);
      int amount = Integer.parseInt(fields[6]);
      registerBox(fields[1], price, cValue, fields[3], fields[2], amount);
    } else if (patContainer.matcher(fields[0]).matches()) {
      int price = Integer.parseInt(fields[5]);
      int cValue = Integer.parseInt(fields[6]);
      int amount = Integer.parseInt(fields[7]);
      registerContainer(fields[1], price, cValue, fields[4], fields[2], fields[3], amount);
    } else if (patBook.matcher(fields[0]).matches()) {
      int price = Integer.parseInt(fields[6]);
      int cValue = Integer.parseInt(fields[7]);
      int amount = Integer.parseInt(fields[8]);
      registerBook(fields[1], fields[2], fields[3], fields[4], price, cValue, fields[5], amount);
    } else {
      throw new BadEntryException(fields[0]);
    }
  }

  /* ------------------------------------------ DATE ------------------------------------------ */

  /**
   * Returns the store's current date.
   * 
   * @return store current date.
   */
  public int getDate() {
    return _date;
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
    if (days <= 0) {
      throw new InvalidDaysException(days);
    }
    _date += days;
  }

  /* -------------------------------------- STORE BALANCE ------------------------------------- */

  /**
   * Returns store's available balance.
   * 
   * @return store available balance.
   */
  public int getAvailableBalance() {
    StoreCompanyVisitor visitor = new StoreCompanyVisitor();
    for (Transaction t : _transactions.values()) {
      if (t.getPaymentStatus() == true) {
        t.accept(visitor);
      }
    }
    return visitor.getTotalBalance();
  }

  /**
   * Returns store's accounting balance.
   * 
   * @return store accounting balance.
   */
  public int getAccountingBalance() {
    StoreCompanyVisitor visitor = new StoreCompanyVisitor();
    for (Transaction t : _transactions.values()) {
      t.setCurrentStoreDate(_date);
      t.accept(visitor);
    }
    return visitor.getTotalBalance();
  }

  /* ---------------------------------------- PRODUCTS ---------------------------------------- */

  /**
   * Returns a Product given a unique product ID.
   * 
   * @param pID
   *          product ID.
   * @return
   * @throws UnknownProductException
   */
  private Product getProduct(String pID) throws UnknownProductException {
    Product product = _products.get(pID);
    if (product == null) {
      throw new UnknownProductException(pID);
    }
    return product;
  }

  /**
   * @return a Collection with all Products available in store.
   */
  public Collection<Product> getProducts() {
    return Collections.unmodifiableCollection(_products.values());
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
  public void registerBox(String pID, int price, int cValue, String sID, String serviceType, int amount) throws DuplicateProductException, UnknownSupplierException, UnknownServTypeException {
    Product product = _products.get(pID);
    if (product != null) {
      throw new DuplicateProductException(pID);
    }
    Supplier supplier = getSupplier(sID);
    Box box = new Box(supplier, pID, price, cValue, serviceType, amount);
    _products.put(pID, box);
    for (Client client: _clients.values()) {
      box.registerObserver(client);
    }
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
  public void registerContainer(String pID, int price, int cValue, String sID, String serviceType, String serviceLevel, int amount) throws DuplicateProductException, UnknownSupplierException, UnknownServTypeException, UnknownServLevelException {
    Product product = _products.get(pID);
    if (product != null) {
      throw new DuplicateProductException(pID);
    }
    Supplier supplier = getSupplier(sID);
    Container container = new Container(supplier, pID, price, cValue, amount, serviceType, serviceLevel);
    _products.put(pID, container);
    for (Client client: _clients.values()) {
      container.registerObserver(client);
    }
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
  public void registerBook(String pID, String title, String author, String isbn, int price, int cValue, String sID, int amount) throws DuplicateProductException, UnknownSupplierException {
    Product product = _products.get(pID);
    if (product != null) {
      throw new DuplicateProductException(pID);
    }
    Supplier supplier = getSupplier(sID);
    Book book = new Book(supplier, pID, price, cValue, title, author, isbn, amount);
    _products.put(pID, book);
    for (Client client: _clients.values()) {
      book.registerObserver(client);
    }
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
    getProduct(pID).changePrice(newPrice);
  }

  /* --------------------------------------- CLIENTS ---------------------------------------- */

  /**
   * Returns a client given a client unique ID.
   * 
   * @param cID
   *          client ID.
   * @return client given unique ID.
   * @throws UnknownClientException
   */
  public Client getClient(String cID) throws UnknownClientException {
    Client client = _clients.get(cID);
    if (client == null) {
      throw new UnknownClientException(cID);
    }
    return client;
  }
  
  /**
   * Returns all the store clients as an unomodifiable collection.
   * 
   * @return a collection with all store clients.
   */
  public Collection<Client> getClients() {
    return Collections.unmodifiableCollection(_clients.values());
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
    Client client = getClient(cID);
    return client.getClientNotifications();
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
    Client client = _clients.get(cID);
    if (client != null) {
      throw new DuplicateClientException(cID);
    }
    Client newClient = new Client(cID, name, address);
    _clients.put(cID, newClient);
    for (Product product: _products.values()) {
      product.registerObserver(newClient);
    }
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
    Client client = getClient(cID);
    Product product = getProduct(pID);
    if (product.getObservers().get(client) == true) {
      product.removeObserver(client);
    } else {
      product.registerObserver(client);
    }
    return product.getObservers().get(client);
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
    return getClient(cID).getClientSales();
  }

  /* -------------------------------------- SUPPLIERS --------------------------------------- */

  /**
   * Returns a supplier given its unique ID.
   * 
   * @param supID
   *          supplier ID.
   * @return supplier given unique ID.
   * @throws UnknownSupplierException
   */
  private Supplier getSupplier(String supID) throws UnknownSupplierException {
    Supplier supplier = _suppliers.get(supID);
    if (supplier == null) {
      throw new UnknownSupplierException(supID);
    }
    return supplier;
  }

  /**
   * Returns all the store suppliers as an unmodifiable collection.
   * 
   * @return a collection with all store suppliers.
   */
  public Collection<Supplier> getSuppliers() {
    return Collections.unmodifiableCollection(_suppliers.values());
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
    Supplier supplier = _suppliers.get(sID);
    if (supplier != null) {
      throw new DuplicateSupplierException(sID);
    }
    _suppliers.put(sID, new Supplier(sID, name, address));
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
    return getSupplier(sID).toggleTransactions();
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
    return getSupplier(sID).getTransactions();
  }

  /* ------------------------------------- TRANSACTIONS ------------------------------------- */

  /**
   * Returns a transaction given a unique transaction ID.
   * 
   * @param id
   *          transaction unique ID.
   * @return a transaction given its id.
   * @throws UnknownTransactionException
   */
  public Transaction getTransaction(int id) throws UnknownTransactionException {
    Transaction transaction = _transactions.get(id);
    if (transaction == null) {
      throw new UnknownTransactionException(id);
    }
    transaction.setCurrentStoreDate(_date);
    return transaction;
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
  public void registerSaleTransaction(String cID, int limDate, String pID, int qty) throws UnknownClientException, UnknownProductException, NoEnoughStockProductException {
    Client client = getClient(cID);
    Product product = getProduct(pID);
    if (product.getStock() < qty) {
      throw new NoEnoughStockProductException(pID, qty, product.getStock());
    }
    product.removeStock(qty);
    Sale sale = new Sale(_transactionID, client, product, limDate, qty);
    sale.setCurrentStoreDate(_date);
    sale.setBasePrice(product.getPrice() * qty);
    client.addSale(sale);
    _transactions.put(_transactionID++, sale);
  }

  /**
   * Registers a store order.
   * 
   * @param supID
   *          supplier ID.
   * @param products
   *          map containing product and its quantities ordered.
   * @throws UnknownSupplierException
   * @throws UnknownProductException
   * @throws InactiveSupplierException
   * @throws IncorrectSupplierException
   */
  public void registerOrderTransaction(String supID, Collection<OrderElement> products) throws UnknownSupplierException, UnknownProductException, 
                                                                                    InactiveSupplierException, IncorrectSupplierException {
    Supplier supplier = getSupplier(supID);
    if (supplier.isActive() == false) {
      throw new InactiveSupplierException(supID);
    }
    for (OrderElement e : products) {
      Product p = getProduct(e.getProductID());
      if (p.getSupplier() != supplier) {
        throw new IncorrectSupplierException(supID, e.getProductID());
      }
    }
    Order order = new Order(_transactionID, supplier.getID());
    int totalCost = 0;
    for (OrderElement e : products) {
      Product p = getProduct(e.getProductID());
      int qty = e.getProductQty();
      e.setProductID(p.getID());
      p.addStock(qty);
      order.addProduct(e);
      totalCost += p.getPrice() * qty;
    }
    order.setPaymentDate(_date);
    order.setBasePrice(totalCost);
    supplier.addOrder(order);
    _transactions.put(_transactionID++, order);
  }

  /**
   * Pays a sale given its unique transaction ID.
   * 
   * @param id
   *          sale ID.
   * @throws UnknownTransactionException
   */
  public void pay(int id) throws UnknownTransactionException {
    Transaction transaction = getTransaction(id);
    if (transaction.getPaymentStatus() == false) {
      transaction.setCurrentStoreDate(_date);
      transaction.pay();
    }
  }

  /* --------------------------------------- LOOKUPS ---------------------------------------- */

  /**
   * Returns all store products whose price is cheapear than given price as an unmodifiable collection.
   * 
   * @param price
   *          reference price.
   * @return a collection with all products whose price is under given price.
   */
  public Collection<Product> lookupProductsUnderPrice(int price) {
    return Collections.unmodifiableCollection(_products.values().stream().filter(p->p.getPrice() < price).collect(Collectors.toList()));
  }

  /**
   * Returns a given client's paid sales as an unmodifiable collection.
   * 
   * @param cID
   *          client ID.
   * @return a collection with client's paid sales.
   * @throws UnknownClientException
   */
  public Collection<Sale> lookupPaymentsByClient(String cID) throws UnknownClientException {
    Client client = getClient(cID);
    return Collections.unmodifiableCollection(client.getClientSales().stream().filter(s->s.getPaymentStatus()).collect(Collectors.toList()));
  }

}
