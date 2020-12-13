package woo.exceptions;

public class UnknownServTypeException extends Exception {
    /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192335L;

  /** Unknown type. */
  private String _type;

  /** @param type Unknown type to report. */
  public UnknownServTypeException(String type) {
    _type = type;
  }

  /**
   * @return the invalid product service type.
   */
  public String getServiceType() { return _type; }

}
