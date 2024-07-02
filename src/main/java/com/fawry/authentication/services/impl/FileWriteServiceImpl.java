package com.fawry.authentication.services.impl;

import com.fawry.authentication.model.User;
import com.fawry.authentication.services.FileWriterService;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileWriteServiceImpl implements FileWriterService {

    private final String FILE_PATH = "registered_users.txt";
    private PrintWriter writer;

    public FileWriteServiceImpl() {
        createWriter();
    }

    private synchronized void createWriter() {
        if (writer == null) {
            try {
                writer = new PrintWriter(new FileWriter(FILE_PATH, true));
            } catch (Exception ex) {
                throw new RuntimeException("Can't write into file " + ex.getMessage());
            }
        }
    }

    private synchronized void createHeaderIfNeeded() {
        try {
            if (!fileContainsHeader()) {
                writer.println(String.format("%-30s %-30s %-30s", "Username", "Email", "Password"));
                writer.flush();
            }
        } catch (Exception ex) {
            throw new RuntimeException("Can't create the file header " + ex.getMessage());
        }
    }

    @Override
    public synchronized void writeUserToFile(User user) {
        createHeaderIfNeeded();
        String userData = String.format("%-30s %-30s %-30s", user.getUsername(), user.getEmail(), user.getPassword());
        writer.println(userData);
        writer.flush();
    }

    private boolean fileContainsHeader() throws IOException {
        return Files.lines(Paths.get(FILE_PATH))
                .anyMatch(line -> line.contains("Username") && line.contains("Email") && line.contains("Password"));
    }
}
