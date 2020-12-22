package com.github.atulkaushal.kafka.connect.stackoverflow.validators;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;

/**
 * The Class TimeStampValidator.
 *
 * @author Atul
 */
public class TimeStampValidator implements ConfigDef.Validator {

  /**
   * Ensure valid.
   *
   * @param name the name
   * @param value the value
   */
  @Override
  public void ensureValid(String name, Object value) {
    String timestamp = (String) value;
    try {
      Instant.parse(timestamp);
    } catch (DateTimeParseException e) {
      throw new ConfigException(name, value, "Unable to parse date.");
    }
  }
}
