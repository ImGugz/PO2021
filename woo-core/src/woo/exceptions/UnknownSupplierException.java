package woo.exceptions;

/** Exception for unknown supplier keys. */
public class UnknownSupplierException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009200054L;

  /** Unknown key. */
  private String _key;

  /** @param key Unknown key to report. */
  public UnknownSupplierException(String id) {
    _key = id;
  }

  /**
   * @return the invalid supplier key.
   */
  public String getSupplierKey() {
    return _key;
  }

}
