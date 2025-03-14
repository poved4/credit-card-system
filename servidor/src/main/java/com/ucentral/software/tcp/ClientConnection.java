package com.ucentral.software.tcp;

import com.ucentral.software.model.Session;
import com.ucentral.software.repository.PersistenceService;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientConnection implements Runnable {

  private final Integer ID;
  private Boolean isRunning;

  private DataInputStream in;
  private DataOutputStream out;
  private final Socket clientSocket;

  private final PersistenceService repository;

  @Override
  public void run() {

    try {

      isRunning = Boolean.TRUE;

      System.out.println("New client connected: " + clientSocket.getInetAddress());
      in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
      out = new DataOutputStream(clientSocket.getOutputStream());

      readMessages();

    } catch (Exception e) {
      System.err.println("Error initializing input stream: " + e.getMessage());
    }

  }

  public void readMessages() {

    try {

      String row;
      String response;

      while (isRunning) {

        row = in.readUTF();
        response = processMessage(row);
        sendMessage(response);

      }

    } catch (IOException e) {
      System.err.println("Error reading client " + ID + " message");
      System.err.println("ERROR. " + e.getMessage());
    } finally {
      close();
    }

  }

  public String processMessage(String message) {

    try {

      if (message == null || message.isBlank()) {
        throw new RuntimeException("MESSAGE IS EMPTY");
      }

      String[] fields = message.split(",");
      Session session = repository.findSessionByToken(fields[1]);

      Boolean requireAuthentication = !fields[0].equalsIgnoreCase("STATUS")
              && !fields[0].equalsIgnoreCase("DISCONNECTION")
              && !fields[0].equalsIgnoreCase("LOGIN");

      if (session == null && requireAuthentication) {
        throw new RuntimeException("UNAUTHORIZED");
      }

      return switch (fields[0].toUpperCase()) {

        case "STATUS" ->
          "OK";

        // DISCONNECTION,SESSION_TOKEN
        case "DISCONNECTION" -> {
          isRunning = Boolean.FALSE;
          yield "OK";
        }

        // LOGIN,CC,PIN
        case "LOGIN" ->
          repository.logIn(fields[1], fields[2]);

        // LOGOUT,SESSION_TOKEN
        case "LOGOUT" ->
          repository.logOut(fields[1]);

        // ACCOUNT,SESSION_TOKEN
        case "ACCOUNT" ->
          repository.account(session.getCC());

        // NEW_CREDIT_CARD,SESSION_TOKEN
        case "NEW_CREDIT_CARD" ->
          repository.creditCardApplication(session.getCC());

        // ENTER_PURCHASE,SESSION_TOKEN,cardNumber,date,description,amount
        case "ENTER_PURCHASE" ->
          repository.registerPurchase(
          session.getCC(),
          fields[2],
          LocalDate.parse(fields[3]),
          fields[4],
          Double.valueOf(fields[5])
          );

        // ENTER_PAYMENT,SESSION_TOKEN,cardNumber,amount
        case "ENTER_PAYMENT" ->
          repository.registerPayment(
          session.getCC(),
          fields[2],
          Double.valueOf(fields[3])
          );

        default ->
          throw new RuntimeException("not valid option");

      };

    } catch (Exception e) {
      return "ERROR. " + e.getMessage();
    }

  }

  public void sendMessage(String message) {

    try {

      out.writeUTF(message);
      out.flush();

    } catch (IOException e) {
      System.err.println("Error sending message to client " + ID);
      System.err.println("ERROR. " + e.getMessage());
    }

  }

  public void close() {

    try {

      isRunning = false;

      if (in != null) {
        in.close();
      }

      if (out != null) {
        out.close();
      }

      if (clientSocket != null && !clientSocket.isClosed()) {
        clientSocket.close();
      }

      System.out.println("ClIENT " + ID + " DISCONNECTED");

    } catch (IOException e) {
      System.err.println("Error closing client " + ID + " connection");
      System.err.println("ERROR. " + e.getMessage());
    }

  }

}
