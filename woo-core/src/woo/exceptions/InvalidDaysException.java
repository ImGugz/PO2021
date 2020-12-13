package woo.exceptions;

/**
 * Class for representing a invalid date to advance.
 */
public class InvalidDaysException extends Exception {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192335L;

  /** Wrong days input. */
  private int _days;

  /** @param days invalid days to report. */
  public InvalidDaysException(int days) {
    _days = days;
  }

  /**
   * @return the invalid advance date days.
   */
  public int getInvalidDays() {
      return _days;
  }
}