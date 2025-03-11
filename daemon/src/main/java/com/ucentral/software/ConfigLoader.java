package com.ucentral.software;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigLoader {

  Properties properties = new Properties();

  public ConfigLoader() {
    load();
  }

  private void load() {

    Path configPath = Path.of("", "resources", "config.properties").toAbsolutePath();

    if (!Files.exists(configPath)) {
      System.out.println("El archivo de configuración no existe.");
      return;
    }

    try (FileInputStream file = new FileInputStream(configPath.toFile())) {
      properties.load(file);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public Long getTimeOut() {
    String prop = properties.getProperty("app.monitoring.time-out");
    return Long.valueOf(prop);
  }

  public Integer getDefaultPort() {
    String prop = properties.getProperty("app.instances.default-port");
    return Integer.valueOf(prop);
  }

  public Integer getMaxInstances() {
    String prop = properties.getProperty("app.instances.maximum");
    return Integer.valueOf(prop);
  }

  public String getJarPath() {
    return properties.getProperty("app.instances.jar-path");
  }

}
