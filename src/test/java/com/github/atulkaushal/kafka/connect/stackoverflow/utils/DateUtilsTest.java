package com.github.atulkaushal.kafka.connect.stackoverflow.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

/**
 * The Class DateUtilsTest.
 *
 * @author Atul
 */
public class DateUtilsTest {

  /** Max instant. */
  @Test
  public void maxInstant() {
    Instant i1 = ZonedDateTime.now().toInstant();
    Instant i2 = i1.plusSeconds(1);
    assertEquals(DateUtils.maxInstant(i1, i2), i2);
    assertEquals(DateUtils.maxInstant(i2, i1), i2);
  }
}
