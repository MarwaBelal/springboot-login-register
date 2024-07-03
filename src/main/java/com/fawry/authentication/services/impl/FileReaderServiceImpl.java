package com.fawry.authentication.services.impl;

import com.fawry.authentication.model.User;
import com.fawry.authentication.services.FileReaderService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Service
public class FileReaderServiceImpl implements FileReaderService {

  private final String FILE_PATH = "registered_users.txt";
  private BufferedReader bufferedReader;

  public FileReaderServiceImpl() {

    createReader();
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

  @Override
  public Optional<User> getUserFromFile(String email) throws IOException {

    String line;
    bufferedReader.mark(0);
    bufferedReader.reset();

    while ((line = bufferedReader.readLine()) != null) {
      System.out.println(line);

      String[] userData = line.split("\\s+");
      if (userData.length == 3 && userData[1].equals(email)) {
        User user = new User();
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
}
