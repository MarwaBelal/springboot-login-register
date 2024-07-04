package com.fawry.authentication.utils;

import com.fawry.authentication.model.User;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileWriterUtil {

    private static final String FILE_PATH = "registered_users.txt";
    private static PrintWriter writer;

    public FileWriterUtil() {
        createWriter();
    }

    public static synchronized User writeUserToFile(User user) {
        createHeaderIfNeeded();
        String userData = String.format("%-30s %-30s %-30s", user.getUsername(), user.getEmail(), user.getPassword());
        writer.println(userData);
        writer.flush();

        return user;
    }

    private static synchronized void createHeaderIfNeeded() {
        try {
            if (!fileContainsHeader()) {
                writer.println(String.format("%-30s %-30s %-30s", "Username", "Email", "Password"));
                writer.flush();
            }
        } catch (Exception ex) {
            throw new RuntimeException("Can't create the file header " + ex.getMessage());
        }
    }

    private static boolean fileContainsHeader() throws IOException {
        return Files.lines(Paths.get(FILE_PATH))
                .anyMatch(line -> line.contains("Username") && line.contains("Email") && line.contains("Password"));
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
}
