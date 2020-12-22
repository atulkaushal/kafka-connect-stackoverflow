package com.github.atulkaushal.kafka.connect.stackoverflow.utils;

public class VersionUtil {
  public static String getVersion() {
    try {
      return VersionUtil.class.getPackage().getImplementationVersion();
    } catch (Exception ex) {
      return "0.0.0.0";
    }
  }
}
