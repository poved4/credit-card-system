package com.ucentral.software;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Monitoring {

  private final ConfigLoader config;
  Map<Integer, Process> processes = new HashMap<>();

  public Monitoring() {

    this.config = new ConfigLoader();

    Integer instances = config.getMaxInstances();
    Integer port = config.getDefaultPort();

    while (processes.size() < instances) {
      processes.put((port + processes.size()), null);
    }

  }

  public void run() {

    Long timeOut = config.getTimeOut();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    scheduler.scheduleAtFixedRate(() -> {

      System.out.println("continuous monitoring...");
      processes.forEach((clave, valor) -> {

        if (valor == null || !valor.isAlive()) {
          upInstance(clave);
        }

      });

    }, 0, timeOut, TimeUnit.SECONDS);

  }

  private void upInstance(final Integer PORT) {

    try {

      Process process = new ProcessBuilder(
          "java",
          "-jar",
          config.getJarPath(),
          String.valueOf(PORT)
      ).start();

      processes.replace(PORT, process);
      System.out.println("New instance initiated with PID " + process.pid() + " and port " + PORT);

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
