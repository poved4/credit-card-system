package com.ucentral.software.service;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class FileService {

    public List<String> read(String uri) {
        try {

            var path = Path.of(uri);
            var rows = Files.readAllLines(path);

            if (rows.isEmpty()) {
                return List.of();
            }

            rows.remove(0);

            return rows.stream()
                    .filter(row -> !row.isEmpty() && !row.isBlank())
                    .map(row -> row.trim())
                    .toList();

        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    public Boolean write(String uri, List<String> content) {
        try {

            var path = Path.of(uri);
            Files.write(path, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return Boolean.TRUE;

        } catch (IOException e) {
            System.out.println("Error al agregar contenido al archivo: " + e.getMessage());
            return Boolean.FALSE;
        }
    }

    public Boolean reWrite(String uri, List<String> content) {
        try {

            var path = Path.of(uri);
            Files.write(path, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return Boolean.TRUE;

        } catch (IOException e) {
            System.out.println("Error al agregar contenido al archivo: " + e.getMessage());
            return Boolean.FALSE;
        }
    }

}
