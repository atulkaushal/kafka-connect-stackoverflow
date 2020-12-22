package com.github.atulkaushal.kafka.connect.stackoverflow;

import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSourceConnectorConfig.AUTH_PASSWORD_CONFIG;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSourceConnectorConfig.AUTH_USERNAME_CONFIG;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSourceConnectorConfig.BATCH_SIZE_CONFIG;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSourceConnectorConfig.SINCE_CONFIG;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSourceConnectorConfig.TAGS_CONFIG;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSourceConnectorConfig.TOPIC_CONFIG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The Class StackOverFlowConnectorConfigTest.
 *
 * @author Atul
 */
public class StackOverFlowConnectorConfigTest {

  /** The config def. */
  private ConfigDef configDef = StackOverFlowSourceConnectorConfig.conf();

  /** The config. */
  private Map<String, String> config;

  /** Sets the up initial config. */
  @BeforeEach
  public void setUpInitialConfig() {
    config = new HashMap<>();
    config.put(TAGS_CONFIG, "java");
    config.put(SINCE_CONFIG, Instant.now().toString());
    config.put(BATCH_SIZE_CONFIG, "5");
    config.put(TOPIC_CONFIG, "stackoverflow-questions");
  }

  /** Doc. */
  @Test
  public void doc() {
    System.out.println(StackOverFlowSourceConnectorConfig.conf().toRst());
  }

  /** Initial config is valid. */
  @Test
  public void initialConfigIsValid() {
    assertTrue(
        configDef
            .validate(config)
            .stream()
            .allMatch(configValue -> configValue.errorMessages().size() == 0));
  }

  /** Can read config correctly. */
  @Test
  public void canReadConfigCorrectly() {
    StackOverFlowSourceConnectorConfig config = new StackOverFlowSourceConnectorConfig(this.config);
    config.getAuthPassword();
  }

  /** Validate since. */
  @Test
  public void validateSince() {
    config.put(SINCE_CONFIG, "not-a-date");
    ConfigValue configValue = configDef.validateAll(config).get(SINCE_CONFIG);
    assertTrue(configValue.errorMessages().size() > 0);
  }

  /** Validate batch size. */
  @Test
  public void validateBatchSize() {
    config.put(BATCH_SIZE_CONFIG, "-1");
    ConfigValue configValue = configDef.validateAll(config).get(BATCH_SIZE_CONFIG);
    assertTrue(configValue.errorMessages().size() > 0);

    config.put(BATCH_SIZE_CONFIG, "101");
    configValue = configDef.validateAll(config).get(BATCH_SIZE_CONFIG);
    assertTrue(configValue.errorMessages().size() > 0);
  }

  /** Validate username. */
  @Test
  public void validateUsername() {
    config.put(AUTH_USERNAME_CONFIG, "username");
    ConfigValue configValue = configDef.validateAll(config).get(AUTH_USERNAME_CONFIG);
    assertEquals(configValue.errorMessages().size(), 0);
  }

  /** Validate password. */
  @Test
  public void validatePassword() {
    config.put(AUTH_PASSWORD_CONFIG, "password");
    ConfigValue configValue = configDef.validateAll(config).get(AUTH_PASSWORD_CONFIG);
    assertEquals(configValue.errorMessages().size(), 0);
  }
}
