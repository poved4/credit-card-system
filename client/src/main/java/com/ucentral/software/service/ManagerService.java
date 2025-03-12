package com.ucentral.software.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucentral.software.model.Account;
import com.ucentral.software.model.CreditCard;
import com.ucentral.software.model.Transaction;
import com.ucentral.software.tpc.TCPClient;
import com.ucentral.software.utils.ConfigLoader;
import com.ucentral.software.utils.LocalDateAdapter;
import java.time.LocalDate;
import java.util.List;

public class ManagerService {

  private Account account;
  private String SESSION_TOKEN;
  private String selectedCreditCard;
  private boolean serverAvailable = false;

  private final TCPClient client;
  private final ConfigLoader config;

  public ManagerService() {
    this.config = new ConfigLoader();
    this.client = new TCPClient(config);
  }

  public void connect() {

    if (client.isRunning()) {
      System.out.println("El cliente ya esta conectado al servidor.");
      return;
    }

    String response = client.connect();

    if (client.isRunning()) {
      serverAvailable = true;
    }

    System.out.println(response);
  }

  public String disconnect() {

    if (!client.isRunning()) {
      return "OK";
    }

    String msg = "DISCONNECTION";
    String response = client.writeMessage(msg);

    if (response.equalsIgnoreCase("OK")) {
      client.close();
    }

    serverAvailable = false;
    return response;

  }

  public boolean isServerAvailable() {
    return serverAvailable;
  }

  public String logOut() {

    if (SESSION_TOKEN == null || SESSION_TOKEN.isBlank()) {
      return "OK";
    }

    String msg = "LOGOUT," + SESSION_TOKEN;
    String response = client.writeMessage(msg);

    if (response.equalsIgnoreCase("OK")) {
      SESSION_TOKEN = "";
    }

    return response;

  }

  public String logIn(String CC, String PIN) {

    String msg = "LOGIN," + CC + "," + PIN;
    SESSION_TOKEN = client.writeMessage(msg);
    return SESSION_TOKEN;

  }

  public Boolean account() {

    String msg = "ACCOUNT," + SESSION_TOKEN;
    String response = client.writeMessage(msg);

    Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();

    account = gson.fromJson(response, Account.class);

    return response.startsWith("ERROR. ");

  }

  public String creditCardApplication() {

    String msg = "NEW_CREDIT_CARD," + SESSION_TOKEN;
    return client.writeMessage(msg);

  }

  public String registerPurchase(LocalDate date, String description, Double amount) {
    String msg = "ENTER_PURCHASE," + SESSION_TOKEN + "," + selectedCreditCard + "," + date.toString() + ","
        + description + "," + amount;
    return client.writeMessage(msg);
  }

  public String registerPayment(Double amount) {
    String msg = "ENTER_PAYMENT," + SESSION_TOKEN + "," + selectedCreditCard + "," + amount;
    return client.writeMessage(msg);
  }

  public void setSelectedCreditCard(String creditCardNumber) {
    this.selectedCreditCard = creditCardNumber;
  }

  public List<String> getCreditCardsNumbers() {
    if (account == null || account.getCreditCards().isEmpty()) {
      return List.of();
    }

    return account.getCreditCards()
        .stream()
        .map(CreditCard::getNumber)
        .toList();
  }

  public CreditCard getCreditCard() {
    if (account == null || account.getCreditCards().isEmpty()) {
      return null;
    }

    CreditCard creditCard = account.getCreditCards()
        .stream()
        .filter(card -> card.getNumber().equals(selectedCreditCard))
        .findFirst()
        .orElse(null);

    if (creditCard == null) {
      return null;
    }

    if (creditCard.getTransactions().isEmpty()) {
      creditCard.setDebt(0.0);
      return creditCard;
    }

    Double debt = creditCard.getTransactions()
        .stream()
        .mapToDouble(Transaction::getAmount)
        .sum();

    creditCard.setDebt(debt);
    return creditCard;
  }

  public List<Transaction> getTransactionByDate(LocalDate startDate, LocalDate endDate) {
    var creditCard = getCreditCard();

    if (creditCard == null || creditCard.getTransactions().isEmpty()) {
      return List.of();
    }

    return creditCard.getTransactions()
        .stream()
        .filter(t -> t.getDate().isAfter(startDate))
        .filter(t -> t.getDate().isBefore(endDate))
        .toList();
  }

  public Account getAccount() {
    return this.account;
  }

  public List<CreditCard> getCreditCards() {
    return this.account.getCreditCards();
  }

}
