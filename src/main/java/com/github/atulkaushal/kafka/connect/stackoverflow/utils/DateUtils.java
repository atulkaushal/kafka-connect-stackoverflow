package com.github.atulkaushal.kafka.connect.stackoverflow.utils;

import java.time.Instant;

/**
 * The Class DateUtils.
 *
 * @author Atul
 */
public class DateUtils {

  /**
   * Max instant.
   *
   * @param i1 the i 1
   * @param i2 the i 2
   * @return the instant
   */
  public static Instant maxInstant(Instant i1, Instant i2) {
    return i1.compareTo(i2) > 0 ? i1 : i2;
  }
}
