package com.ucentral.software;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class Supervisor {

    private final Path JAR_PATH = Path.of("servidor-1.0.0.jar");
    private final long TIMEOUT = 120_000;

    public void run() {

        while (true) {

            try {

                if (!isServerRunning()) {
                    restartServer();
                }

                Thread.sleep(TIMEOUT);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Supervisor interrupted: " + e.getMessage());
                return;
            }

        }

    }

    private Boolean isServerRunning() {

        return ProcessHandle.allProcesses()
                .map(ProcessHandle::info)
                .map(ProcessHandle.Info::commandLine)
                .flatMap(Optional::stream)
                .anyMatch(cmd -> cmd.contains(JAR_PATH.toString()));

    }

    private void restartServer() {

        try {

            new ProcessBuilder("java", "-jar", JAR_PATH.toString())
                    .inheritIO()
                    .start();

            System.out.println("Starting server...");

        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }

    }

}
