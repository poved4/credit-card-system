package com.ucentral.software.utils;

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

  public String getHost() {
    return properties.getProperty("client.host");
  }

  public Integer getPort() {
    String prop = properties.getProperty("client.port");
    return Integer.valueOf(prop);
  }
  
  public Integer getRetries() {
    String prop = properties.getProperty("client.retries");
    return Integer.valueOf(prop);
  }
  
  public Long getTimeOut() {
    String prop = properties.getProperty("client.time-out");
    return Long.valueOf(prop);
  }

}
