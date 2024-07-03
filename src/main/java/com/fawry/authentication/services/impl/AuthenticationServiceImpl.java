package com.fawry.authentication.services.impl;

import com.fawry.authentication.exceptions.customExceptions.NotAuthonoticatedException;
import com.fawry.authentication.model.RequestLoginModel;
import com.fawry.authentication.model.ResponseAuthenticationModel;
import com.fawry.authentication.model.User;
import com.fawry.authentication.services.AuthenticationService;
import com.fawry.authentication.services.FileReaderService;
import com.fawry.authentication.services.FileWriterService;
import com.fawry.authentication.utils.AESEncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired private FileWriterService fileWriteService;

  @Autowired private FileReaderService fileReaderService;

  @Override
  public synchronized ResponseAuthenticationModel registerUser(User user) throws Exception {
    String encryptedPassword = AESEncryptionUtil.encrypt(user.getPassword());
    user.setPassword(encryptedPassword);
    User savedUser = fileWriteService.writeUserToFile(user);
    return ResponseAuthenticationModel.builder()
        .email(savedUser.getEmail())
        .username(savedUser.getUsername())
        .build();
  }

  @Override
  public ResponseAuthenticationModel loginUser(RequestLoginModel user) throws Exception {
    User foundUser =
        fileReaderService
            .getUserFromFile(user.getEmail())
            .orElseThrow(() -> new NotAuthonoticatedException("User not found"));

    String decryptedPassword = AESEncryptionUtil.decrypt(foundUser.getPassword());

    System.out.println("Decrypted Password: " + decryptedPassword);
    System.out.println("Provided Password: " + user.getPassword());

    if (!decryptedPassword.equals(user.getPassword())) {
      System.out.println("Wrong password");
      throw new NotAuthonoticatedException("Wrong password");
    }
    return ResponseAuthenticationModel.builder()
        .email(foundUser.getEmail())
        .username(foundUser.getUsername())
        .build();
  }
}
