package com.github.atulkaushal.kafka.connect.stackoverflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import com.github.atulkaushal.kafka.connect.stackoverflow.utils.VersionUtil;

/**
 * The Class StackOverFlowSourceConnector.
 *
 * @author Atul
 */
public class StackOverFlowSourceConnector extends SourceConnector {

  /** The config. */
  private StackOverFlowSourceConnectorConfig config;

  /**
   * Version.
   *
   * @return the string
   */
  @Override
  public String version() {
    return VersionUtil.getVersion();
  }

  /**
   * Start.
   *
   * @param map the map
   */
  @Override
  public void start(Map<String, String> map) {
    config = new StackOverFlowSourceConnectorConfig(map);
  }

  /**
   * Task class.
   *
   * @return the class<? extends task>
   */
  @Override
  public Class<? extends Task> taskClass() {
    return StackOverFlowSourceTask.class;
  }

  /**
   * Task configs.
   *
   * @param i the i
   * @return the list
   */
  @Override
  public List<Map<String, String>> taskConfigs(int i) {
    // Define the individual task configurations that will be executed.
    ArrayList<Map<String, String>> configs = new ArrayList<>(1);
    configs.add(config.originalsStrings());
    return configs;
  }

  /** Stop. */
  @Override
  public void stop() {
    // TODO: Do things that are necessary to stop your connector.
  }

  /**
   * Config.
   *
   * @return the config def
   */
  @Override
  public ConfigDef config() {
    return StackOverFlowSourceConnectorConfig.conf();
  }
}
