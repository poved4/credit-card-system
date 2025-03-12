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

  public String accountFile() {
    return properties.getProperty("database.account");
  }

  public String transactionsFile() {
    return properties.getProperty("database.transactions");
  }

  public String creditCardFile() {
    return properties.getProperty("database.creditCard");
  }

  public String sessionFile() {
    return properties.getProperty("database.session");
  }

}
