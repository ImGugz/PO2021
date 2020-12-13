package woo.exceptions;

public class IncorrectSupplierException extends Exception {
/** Serial number for serialization. */
  private static final long serialVersionUID = 202009200054L;

  /** Supplier key. */
  private String _skey;

  /** Product key. */
  private String _pkey;

  /** 
   * @param skey supplier key.
   * @param pkey product key. 
   */
  public IncorrectSupplierException(String skey, String pkey) {
    _skey = skey;
    _pkey = pkey;
  }

  /**
   * @return the incorrent supplier key.
   */
  public String getSupplierKey() {
    return _skey;
  }

  /**
   * @return the product key whose is supplier isn't given supplier key.
   */
  public String getProductKey() {
    return _pkey;
  }
  
}