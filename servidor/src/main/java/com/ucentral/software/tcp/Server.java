package com.ucentral.software.tcp;

import com.ucentral.software.repository.ClientRepository;
import com.ucentral.software.service.ClientService;
import com.ucentral.software.service.FileService;
import com.ucentral.software.ui.Menu;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    private final Integer PORT = 1415;
    private static volatile Boolean isRunning;

    private ServerSocket server;
    private final AtomicInteger index = new AtomicInteger(0);

    private final FileService sFile;
    private final ClientRepository repository;

    public Server() {
        isRunning = Boolean.FALSE;
        this.sFile = new FileService();
        this.repository = new ClientRepository(sFile);
    }

    public void run() {

        try {

            server = new ServerSocket(PORT);
            isRunning = Boolean.TRUE;
            new Menu(this).init();

            System.out.println("SERVER STARTED ON PORT: " + PORT);

            while (isRunning) {

                Socket clientSocket = server.accept();

                if (clientSocket.isConnected()) {

                    ClientService client = new ClientService(
                            index.incrementAndGet(),
                            clientSocket,
                            repository
                    );

                    new Thread(client).start();

                }

            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
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
