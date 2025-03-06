package com.ucentral.software.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucentral.software.model.Account;
import com.ucentral.software.model.CreditCard;
import com.ucentral.software.model.Transaction;
import com.ucentral.software.tpc.TCPClient;
import com.ucentral.software.utils.LocalDateAdapter;
import java.time.LocalDate;
import java.util.List;

public class ManagerService {

    private Account account;
    private String SESSION_TOKEN;
    private String selectedCreditCard;

    private final TCPClient client;

    public ManagerService() {
        this.client = new TCPClient();
    }

    public void connect() {

        if (client.isRunning()) {
            return;
        }

        client.run();
    }

    // DISCONNECTION,SESSION_TOKEN
    public String disconnect() {

        if (client == null || !client.isRunning()) {
            return "OK";
        }

        String msg = SESSION_TOKEN == null || SESSION_TOKEN.isBlank()
                ? "DISCONNECTION," + SESSION_TOKEN
                : "DISCONNECTION";

        String response = client.writeMessage(msg);
        client.close();
        return response;

    }

    // LOGOUT,SESSION_TOKEN
    public String logOut() {

        if (SESSION_TOKEN == null || SESSION_TOKEN.isBlank()) {
            return "OK";
        }

        String msg = "LOGOUT," + SESSION_TOKEN;
        String response = client.writeMessage(msg);

        if (response.equals("OK")) {
            SESSION_TOKEN = "";
        }

        return response;

    }

    // LOGIN,CC,PIN
    public String logIn(String CC, String PIN) {

        String msg = "LOGIN," + CC + "," + PIN;
        SESSION_TOKEN = client.writeMessage(msg);
        return SESSION_TOKEN;

    }

    // ACCOUNT,SESSION_TOKEN
    public Boolean account() {

        String msg = "ACCOUNT," + SESSION_TOKEN;
        String response = client.writeMessage(msg);
        
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        
        account = gson.fromJson(response, Account.class);

        return response.startsWith("ERROR. ");
    }

    // NEW_CREDIT_CARD,SESSION_TOKEN
    public String creditCardApplication() {

        String msg = "NEW_CREDIT_CARD," + SESSION_TOKEN;
        return client.writeMessage(msg);

    }

    // ENTER_PURCHASE,SESSION_TOKEN,cardNumber,date,description,amount
    public String registerPurchase(LocalDate date, String description, Double amount) {

        String msg = "ENTER_PURCHASE," + SESSION_TOKEN + "," + selectedCreditCard + "," + date.toString() + "," + description + "," + amount;
        return client.writeMessage(msg);

    }

    // ENTER_PAYMENT,SESSION_TOKEN,cardNumber,amount
    public String registerPayment(Double amount) {

        String msg = "ENTER_PAYMENT," + SESSION_TOKEN + "," + selectedCreditCard + "," + amount;
        return client.writeMessage(msg);

    }

    public void setSelectedCreditCard(String creditCardNumber) {
        this.selectedCreditCard = creditCardNumber;
    }

    public List<String> getCreditCardsNumbers() {

        if (account.getCreditCards().isEmpty()) {
            return List.of();
        }

        return account.getCreditCards()
                .stream()
                .map(CreditCard::getNumber)
                .toList();

    }

    public CreditCard getCreditCard() {

        if (account.getCreditCards().isEmpty()) {
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
            creditCard.setDebt(Double.valueOf("0"));
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
