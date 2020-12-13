package woo.exceptions;

/** Exception thrown when a supplier key is duplicated. */
public class DuplicateSupplierException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201709021324L;

  /** Supplier key. */
  private String _key;

  /** @param key the duplicated key */
  public DuplicateSupplierException(String key) {
    _key = key;
  }

  /**
   * @return the invalid supplier key.
   */
  public String getSupplierKey() {
    return _key;
  }

}
