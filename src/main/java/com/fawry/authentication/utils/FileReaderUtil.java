package com.fawry.authentication.utils;

import com.fawry.authentication.common.model.UserModel;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Service
public class FileReaderUtil {

    private static final String FILE_PATH = "registered_users.txt";
    private static BufferedReader bufferedReader;

    public FileReaderUtil() {

        createReader();
    }

    public static Optional<UserModel> getUserFromFile(String email) throws IOException {

        String line;
        bufferedReader.mark(0);
        bufferedReader.reset();

        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);

            String[] userData = line.split("\\s+");
            if (userData.length == 3 && userData[1].equals(email)) {
                UserModel user = new UserModel();
                user.setUsername(userData[0]);
                user.setEmail(userData[1]);
                user.setPassword(userData[2]);
                System.out.println(user);


                bufferedReader.close();
                bufferedReader = new BufferedReader(new FileReader(FILE_PATH));
                return Optional.of(user);
            }
        }

        bufferedReader.close();
        bufferedReader = new BufferedReader(new FileReader(FILE_PATH));
        return Optional.empty();
    }

    private synchronized void createReader() {
        if (bufferedReader == null) {
            try {
                bufferedReader = new BufferedReader(new FileReader(FILE_PATH));
            } catch (Exception ex) {
                throw new RuntimeException("Can't read from file " + ex.getMessage());
            }
        }
    }
}
