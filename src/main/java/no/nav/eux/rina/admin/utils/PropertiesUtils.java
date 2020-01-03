package no.nav.eux.rina.admin.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

  public static Properties getProperties(String... locations) {
    try {
      Properties properties = new Properties();
      for (String location : locations) {
        properties.load(PropertiesUtils.class.getResourceAsStream(location));
      }
      properties.putAll(System.getProperties());
      return properties;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String get(String location, String key) {
    return getProperties(location).getProperty(key);
  }
}
