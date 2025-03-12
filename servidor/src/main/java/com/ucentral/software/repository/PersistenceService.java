package com.ucentral.software.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucentral.software.model.Account;
import com.ucentral.software.model.CreditCard;
import com.ucentral.software.model.Session;
import com.ucentral.software.model.Transaction;
import com.ucentral.software.service.FileService;
import com.ucentral.software.utils.ConfigLoader;
import com.ucentral.software.utils.LocalDateAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PersistenceService {

  private final String SEPARATOR = ",";

  private final FileService sFile;
  private final ConfigLoader config;

  private List<Session> findSessionsByCC(String CC) {

    return sFile.read(config.sessionFile())
            .stream()
            .map(row -> row.split(SEPARATOR))
            .filter(fields -> fields[0].equals(CC))
            .map(fields -> {
              return Session.builder()
                      .CC(fields[0].trim())
                      .sessionToken(fields[1].trim())
                      .date(LocalDateTime.parse(fields[2].trim()))
                      .build();
            })
            .toList();

  }

  public Session findSessionByToken(String SESSION_TOKEN) {

    return sFile.read(config.sessionFile())
            .stream()
            .map(row -> row.split(SEPARATOR))
            .filter(fields -> fields[1].equals(SESSION_TOKEN))
            .findFirst()
            .map(fields -> {
              return Session.builder()
                      .CC(fields[0].trim())
                      .sessionToken(fields[1].trim())
                      .date(LocalDateTime.parse(fields[2].trim()))
                      .build();
            })
            .orElse(null);

  }

  private String saveSession(String CC) {

    String sessionToken = UUID.randomUUID().toString();

    String row = new StringBuilder()
            .append(CC)
            .append(SEPARATOR)
            .append(sessionToken)
            .append(SEPARATOR)
            .append(LocalDateTime.now())
            .append(System.lineSeparator())
            .toString();

    sFile.write(config.sessionFile(), List.of(row));
    return sessionToken;

  }

  private Account findAccountByCC(String CC) {

    return sFile.read(config.accountFile())
            .stream()
            .map(row -> row.split(SEPARATOR))
            .filter(fields -> fields.length >= 3 && fields[0].equals(CC))
            .findFirst()
            .map(fields -> {
              return Account.builder()
                      .CC(fields[0].trim())
                      .pin(fields[1].trim())
                      .name(fields[2].trim())
                      .creditCards(findCreditCardsByCC(CC))
                      .build();
            })
            .orElseThrow(() -> new IllegalArgumentException("Account does not exist"));

  }

  private List<CreditCard> findCreditCardsByCC(String CC) {

    return sFile.read(config.creditCardFile())
            .stream()
            .map(row -> row.split(SEPARATOR))
            .filter(fields -> fields.length >= 3 && fields[0].equals(CC))
            .map(fields -> {
              return CreditCard.builder()
                      .number(fields[1].trim())
                      .balance(Double.valueOf(fields[2].trim()))
                      .transactions(findTransactionsByCardNumber(fields[1].trim()))
                      .build();
            })
            .toList();

  }

  private List<Transaction> findTransactionsByCardNumber(String cardNumber) {

    return sFile.read(config.transactionsFile())
            .stream()
            .map(row -> row.split(SEPARATOR))
            .filter(fields -> fields.length >= 5 && fields[1].equals(cardNumber))
            .map(fields -> {
              return Transaction.builder()
                      .date(LocalDate.parse(fields[2].trim()))
                      .description(fields[3].trim())
                      .amount(Double.valueOf(fields[4].trim()))
                      .build();
            })
            .toList();

  }

  private String saveTransaction(String CC, String cardNumber, LocalDate date, String description, Double amount) {

    String row = new StringBuilder()
            .append(CC)
            .append(SEPARATOR)
            .append(cardNumber)
            .append(SEPARATOR)
            .append(date.toString())
            .append(SEPARATOR)
            .append(description)
            .append(SEPARATOR)
            .append(amount)
            .append(System.lineSeparator())
            .toString();

    sFile.write(config.transactionsFile(), List.of(row));
    return "OK";

  }

  public String logOut(String SESSION_TOKEN) {

    List<String> sessions = new ArrayList<>(0);

    sFile.read(config.sessionFile()).forEach(session -> {
      String[] fields = session.split(SEPARATOR);
      if (!fields[1].equals(SESSION_TOKEN)) {
        sessions.add(session);
      }
    });

    sessions.addFirst("CC,SESSION_TOKEN,DATE");
    sFile.reWrite(config.sessionFile(), sessions);
    return "OK";

  }

  public String logIn(String CC, String PIN) {

    List<Session> sessions = findSessionsByCC(CC);
    if (!sessions.isEmpty()) {
      throw new RuntimeException("a session is in progress");
    }

    Account account = findAccountByCC(CC);
    if (!account.getPin().equals(PIN)) {
      throw new IllegalArgumentException("invalid credentials");
    }

    return saveSession(CC);

  }

  public String account(String CC) {

    Account account = findAccountByCC(CC);

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    return gson.toJson(account);

  }

  public String creditCardApplication(String CC) {

    // generar numero unico de tardeja de credito
    final String cardNumber;
    List<String> creditCards = sFile.read(config.creditCardFile());

    do {

      var longitud = 16;
      var sb = new StringBuilder(longitud);

      for (int i = 0; i < longitud; i++) {
        var digito = ThreadLocalRandom.current().nextInt(10);
        sb.append(digito);
      }

      Boolean existingCard = creditCards.stream()
              .filter(row -> row.split(SEPARATOR)[1].equals(sb.toString()))
              .findFirst()
              .isPresent();

      if (!existingCard) {
        cardNumber = sb.toString();
        break;
      }

    } while (true);

    // generar cupo de la tarjeta de credito
    int minCupo = 1_000_000;
    int cupoAleatorio = ThreadLocalRandom.current().nextInt(minCupo, (minCupo * 10) + 1);
    final var QOUTA = BigDecimal.valueOf(cupoAleatorio);

    String row = new StringBuilder()
            .append(CC)
            .append(SEPARATOR)
            .append(cardNumber)
            .append(SEPARATOR)
            .append(QOUTA)
            .append(System.lineSeparator())
            .toString();

    sFile.write(config.creditCardFile(), List.of(row));
    return "OK";
  }

  public String registerPurchase(String CC, String cardNumber, LocalDate date, String description, Double amount) {

    return saveTransaction(CC, cardNumber, date, description, amount);

  }

  public String registerPayment(String CC, String cardNumber, Double amount) {

    return saveTransaction(
            CC,
            cardNumber,
            LocalDate.now(),
            "ABONO SUCURSAL VIRTUAL",
            -amount);

  }

}
