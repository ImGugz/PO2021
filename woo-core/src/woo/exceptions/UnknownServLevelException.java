package woo.exceptions;

public class UnknownServLevelException extends Exception {
    
/** Serial number for serialization. */
  private static final long serialVersionUID = 202009192335L;

  /** Unknown level. */
  private String _level;

  /** @param level Unknown level to report. */
  public UnknownServLevelException(String level) {
    _level = level;
  }

  /**
   * @return the invalid product service level.
   */
  public String getServiceLevel() { return _level; }
}
