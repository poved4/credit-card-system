package com.ucentral.software.tpc;

import com.ucentral.software.configuration.Connection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {

    private static volatile Boolean isRunning = Boolean.FALSE;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Boolean isRunning() {
        return isRunning;
    }

    public String run() {

        try {

            isRunning = Boolean.TRUE;

            socket = new Socket(Connection.HOST, Connection.PORT);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            return "CONNECTED TO SERVER";

        } catch (Exception e) {
            return "ERROR. CONNECTING TO SERVER. " + e.getMessage();
        }

    }

    public String writeMessage(String message) {

        try {

            if (socket == null && !socket.isConnected() && out == null && in == null) {
                throw new RuntimeException("NO CONNECTION ACTIVE.");
            }

            out.writeUTF(message);
            out.flush();

            return in.readUTF();

        } catch (IOException e) {
            return "Error al comunicarse con el servidor: " + e.getMessage();
        }

    }

    public void close() {

        try {

            isRunning = Boolean.FALSE;

            if (in != null) {
                in.close();
            }

            if (out != null) {
                out.close();
            }

            if (socket != null && !socket.isClosed()) {
                socket.close();
            }

            System.out.println("DISCONNECTED FROM SERVER");

        } catch (IOException e) {
            System.err.println("ERROR WHILE DISCONNECTING FROM SERVER. " + e.getMessage());
        }

    }

}
