package woo.exceptions;

/** Exception thrown when a client key is duplicated. */
public class DuplicateClientException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201709021324L;

  /** Client key. */
  private String _key;

  /** @param key the duplicated key */
  public DuplicateClientException(String key) {
    _key = key;
  }

  /**
   * @return the invalid client key.
   */
  public String getClientKey() {
    return _key;
  }

}
