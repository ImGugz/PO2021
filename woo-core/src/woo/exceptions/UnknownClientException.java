package woo.exceptions;

/** Exception for unknown client keys. */
public class UnknownClientException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192335L;

  /** Unknown key. */
  private String _key;

  /** @param key Unknown key to report. */
  public UnknownClientException(String key) {
    _key = key;
  }

  /**
   * @return the invalid client key.
   */
  public String getClientKey() {
    return _key;
  }

}
