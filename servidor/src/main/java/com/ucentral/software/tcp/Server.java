package com.ucentral.software.tcp;

import com.ucentral.software.repository.PersistenceService;
import com.ucentral.software.service.FileService;
import com.ucentral.software.ui.Menu;
import com.ucentral.software.utils.ConfigLoader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

  private ServerSocket server;
  private final AtomicInteger index = new AtomicInteger(0);
  private static volatile Boolean isRunning = Boolean.FALSE;

  public void run(final Integer PORT) {

    try {

      server = new ServerSocket(PORT);
      isRunning = Boolean.TRUE;

      System.out.println("SERVER STARTED ON PORT: " + PORT);

      new Menu(this).init();
      FileService sFile = new FileService();
      ConfigLoader config = new ConfigLoader();
      PersistenceService sPersistence = new PersistenceService(sFile, config);

      while (isRunning) {

        Socket clientSocket = server.accept();

        if (clientSocket.isConnected()) {

          ClientConnection client = new ClientConnection(
                  index.incrementAndGet(),
                  clientSocket,
                  sPersistence
          );

          new Thread(client).start();

        }

      }

    } catch (Exception e) {
      System.err.println("SERVER STARTUP ERROR. " + e.getMessage());
    } finally {
      stop();
    }

  }

  public void stop() {

    try {

      isRunning = Boolean.FALSE;

      if (server != null && !server.isClosed()) {
        server.close();
        System.out.println("SERVER STOPPED.");
      }

    } catch (IOException e) {
      System.err.println("ERROR CLOSING SERVER: " + e.getMessage());
    }

  }

}
