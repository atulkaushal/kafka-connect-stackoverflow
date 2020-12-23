package com.github.atulkaushal.kafka.connect.stackoverflow;

import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSourceConnectorConfig.BATCH_SIZE_CONFIG;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSourceConnectorConfig.SINCE_CONFIG;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSourceConnectorConfig.TAGS_CONFIG;
import static com.github.atulkaushal.kafka.connect.stackoverflow.StackOverFlowSourceConnectorConfig.TOPIC_CONFIG;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * The Class StackOverFlowConnectorTest.
 *
 * @author Atul
 */
public class StackOverFlowConnectorTest {

  /**
   * Initial config.
   *
   * @return the map
   */
  private Map<String, String> initialConfig() {
    Map<String, String> baseProps = new HashMap<>();
    baseProps.put(TAGS_CONFIG, "Java");
    baseProps.put(SINCE_CONFIG, Instant.now().toString());
    baseProps.put(BATCH_SIZE_CONFIG, "10");
    baseProps.put(TOPIC_CONFIG, "stackoverflow-issues");
    return (baseProps);
  }

  /** Task configs should return one task config. */
  @Test
  public void taskConfigsShouldReturnOneTaskConfig() {
    StackOverFlowSourceConnector statckOverFlowConnector = new StackOverFlowSourceConnector();
    statckOverFlowConnector.start(initialConfig());
    assertEquals(statckOverFlowConnector.taskConfigs(1).size(), 1);
    assertEquals(statckOverFlowConnector.taskConfigs(10).size(), 1);
  }
}
