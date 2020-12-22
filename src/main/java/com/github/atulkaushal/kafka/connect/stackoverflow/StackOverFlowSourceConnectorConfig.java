package com.github.atulkaushal.kafka.connect.stackoverflow;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Map;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;

import com.github.atulkaushal.kafka.connect.stackoverflow.validators.BatchSizeValidator;
import com.github.atulkaushal.kafka.connect.stackoverflow.validators.TimeStampValidator;

/**
 * The Class StackOverflowSourceConnectorConfig.
 *
 * @author Atul
 */
public class StackOverFlowSourceConnectorConfig extends AbstractConfig {

  /** The Constant TOPIC_CONFIG. */
  public static final String TOPIC_CONFIG = "topic";

  /** The Constant TOPIC_DOC. */
  private static final String TOPIC_DOC = "Topic to write to";

  /** The Constant TAGS_CONFIG. */
  public static final String TAGS_CONFIG = "track.tags";

  /** The Constant TAGS_DOC. */
  private static final String TAGS_DOC = "Tags you'd like to follow";

  /** The Constant SINCE_CONFIG. */
  public static final String SINCE_CONFIG = "since.timestamp";

  /** The Constant SINCE_DOC. */
  private static final String SINCE_DOC =
      "Only issues updated at or after this time are returned.\n"
          + "This is a timestamp in ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ.\n"
          + "Defaults to a year from first launch.";

  /** The Constant BATCH_SIZE_CONFIG. */
  public static final String BATCH_SIZE_CONFIG = "batch.size";

  /** The Constant BATCH_SIZE_DOC. */
  private static final String BATCH_SIZE_DOC =
      "Number of data points to retrieve at a time. Defaults to 100 (max value)";

  /** The Constant AUTH_USERNAME_CONFIG. */
  public static final String AUTH_USERNAME_CONFIG = "auth.username";

  /** The Constant AUTH_USERNAME_DOC. */
  private static final String AUTH_USERNAME_DOC = "Optional Username to authenticate calls";

  /** The Constant AUTH_PASSWORD_CONFIG. */
  public static final String AUTH_PASSWORD_CONFIG = "auth.password";

  /** The Constant AUTH_PASSWORD_DOC. */
  private static final String AUTH_PASSWORD_DOC = "Optional Password to authenticate calls";

  /**
   * Instantiates a new stack overflow source connector config.
   *
   * @param config the config
   * @param parsedConfig the parsed config
   */
  public StackOverFlowSourceConnectorConfig(ConfigDef config, Map<String, String> parsedConfig) {
    super(config, parsedConfig);
  }

  /**
   * Instantiates a new stack overflow source connector config.
   *
   * @param parsedConfig the parsed config
   */
  public StackOverFlowSourceConnectorConfig(Map<String, String> parsedConfig) {
    this(conf(), parsedConfig);
  }

  /**
   * Conf.
   *
   * @return the config def
   */
  public static ConfigDef conf() {
    return new ConfigDef()
        .define(TOPIC_CONFIG, Type.STRING, Importance.HIGH, TOPIC_DOC)
        .define(TAGS_CONFIG, Type.STRING, Importance.HIGH, TAGS_DOC)
        .define(
            BATCH_SIZE_CONFIG,
            Type.INT,
            100,
            new BatchSizeValidator(),
            Importance.LOW,
            BATCH_SIZE_DOC)
        .define(
            SINCE_CONFIG,
            Type.STRING,
            ZonedDateTime.now().minusYears(1).toInstant().toString(),
            new TimeStampValidator(),
            Importance.HIGH,
            SINCE_DOC)
        .define(AUTH_USERNAME_CONFIG, Type.STRING, "", Importance.HIGH, AUTH_USERNAME_DOC)
        .define(AUTH_PASSWORD_CONFIG, Type.PASSWORD, "", Importance.HIGH, AUTH_PASSWORD_DOC);
  }

  /**
   * Gets the tags.
   *
   * @return the tags
   */
  public String getTags() {
    return this.getString(TAGS_CONFIG);
  }

  /**
   * Gets the batch size.
   *
   * @return the batch size
   */
  public Integer getBatchSize() {
    return this.getInt(BATCH_SIZE_CONFIG);
  }

  /**
   * Gets the since.
   *
   * @return the since
   */
  public Instant getSince() {
    return Instant.parse(this.getString(SINCE_CONFIG));
  }

  /**
   * Gets the topic.
   *
   * @return the topic
   */
  public String getTopic() {
    return this.getString(TOPIC_CONFIG);
  }

  /**
   * Gets the auth username.
   *
   * @return the auth username
   */
  public String getAuthUsername() {
    return this.getString(AUTH_USERNAME_CONFIG);
  }

  /**
   * Gets the auth password.
   *
   * @return the auth password
   */
  public String getAuthPassword() {
    return this.getPassword(AUTH_PASSWORD_CONFIG).value();
  }
}
