package com.ucentral.software.tpc;

import com.ucentral.software.utils.ConfigLoader;

import lombok.RequiredArgsConstructor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@RequiredArgsConstructor
public class TCPClient {

  private static volatile boolean isRunning = false;
  private static volatile boolean connectionFailed = false;

  private Socket socket;
  private DataInputStream in;
  private DataOutputStream out;
  private Thread connectionMonitor;

  private final ConfigLoader config;

  public boolean isRunning() {
    return isRunning;
  }

  public String connect() {

    if (isRunning) {
      return "Ya esta conectado al servidor.";
    }

    int attempts = 0;
    int maxAttempts = 5;

    while (attempts < maxAttempts) {
      try {

        System.out.println("Intentando conectar al servidor... Intento " + (attempts + 1));

        socket = new Socket(config.getHost(), config.getPort());
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        isRunning = true;
        connectionFailed = false;
        System.out.println("Conectado al servidor exitosamente.");

        // Iniciar monitoreo después de conectar
        startConnectionMonitor();
        return "Conexion establecida con el servidor.";

      } catch (IOException e) {
        attempts++;
        System.err.println("Error conectando al servidor. Intento " + attempts + " de " + maxAttempts);

        try {
          Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }
      }
    }

    connectionFailed = true;
    return "No se pudo conectar al servidor después de " + maxAttempts + " intentos.";
  }

  private void startConnectionMonitor() {
    if (connectionMonitor != null && connectionMonitor.isAlive()) {
      return; // Evita iniciar múltiples hilos de monitoreo
    }

    connectionMonitor = new Thread(() -> {
      while (isRunning) {
        try {
          // Intentar enviar un mensaje de prueba al servidor
          if (!testConnection()) {
            System.out.println("Conexion caida. Intentando reconectar...");
            reconnect();
          }
          Thread.sleep(5000); // Verifica cada 5 segundos
        } catch (InterruptedException ignored) {
        }
      }
    });
    connectionMonitor.start();
  }

  private boolean testConnection() {
    try {
      if (socket == null || socket.isClosed() || !socket.isConnected()) {
        return false;
      }
      out.writeUTF("PING");
      out.flush();
      in.readUTF(); // Espera respuesta
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  private void reconnect() {
    int attempts = 0;
    int maxAttempts = 5;

    while (attempts < maxAttempts) {
      try {
        attempts++;
        System.out.println("Intento de reconexion #" + attempts);

        socket = new Socket(config.getHost(), config.getPort());
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        isRunning = true;
        connectionFailed = false;
        System.out.println("Reconectado al servidor exitosamente.");
        return;
      } catch (IOException e) {
        System.err.println("Fallo en la reconexión. Intento " + attempts + " de " + maxAttempts);

        try {
          Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }
      }
    }

    isRunning = false;
    connectionFailed = true;
    System.err.println("No se pudo reconectar despues de " + maxAttempts + " intentos.");
  }

  public void close() {
    try {
      isRunning = false;
      if (in != null)
        in.close();
      if (out != null)
        out.close();
      if (socket != null && !socket.isClosed())
        socket.close();
      System.out.println("Desconectado del servidor.");
    } catch (IOException e) {
      System.err.println("Error al cerrar la conexion: " + e.getMessage());
    }
  }

  public String writeMessage(String message) {
    try {
      if (socket == null || !socket.isConnected() || out == null || in == null) {
        throw new RuntimeException("No hay conexion activa.");
      }
      out.writeUTF(message);
      out.flush();
      return in.readUTF();
    } catch (IOException e) {
      isRunning = false;
      return "Error al comunicarse con el servidor: " + e.getMessage();
    }
  }

}
