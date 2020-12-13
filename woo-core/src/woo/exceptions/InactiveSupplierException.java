package woo.exceptions;

/** Exception for reporting unauthorized supplier attempts. */
public class InactiveSupplierException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009200054L;
  
    /** Unknown key. */
    private String _key;
  
    /** @param key unauthorized key to report. */
    public InactiveSupplierException(String key) {
      _key = key;
    }
    
    /**
   * @return the inactive supplier key.
   */
    public String getSupplierKey() {
      return _key;
    }
  
  }