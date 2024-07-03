package com.fawry.authentication.services.impl;

import com.fawry.authentication.model.RequestLoginModel;
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
  public synchronized void registerUser(User user) throws Exception {
    //        String encryptedPassword = AESEncryptionUtil.encrypt(user.getPassword());
    //        user.setPassword(user.getPassword());
    fileWriteService.writeUserToFile(user);
  }

  public User loginUser(RequestLoginModel user) throws Exception {
    // TODO: Replace with actual authentication logic
    System.out.println("Logging in user with email: " + user.getEmail());

    System.out.println(user.getEmail());
    User foundUser =
        fileReaderService
            .getUserFromFile(user.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

    //        String decryptedPassword1 = AESEncryptionUtil.decrypt(foundUser.getPassword());
    //    System.out.println(decryptedPassword1);

    // Decrypt stored password and compare with provided password
    //        try {
    //            String decryptedPassword = AESEncryptionUtil.decrypt(foundUser.getPassword());

    //            System.out.println("Decrypted Password: " + decryptedPassword);
    //            System.out.println("Provided Password: " + user.getPassword());

    if (!foundUser.getPassword().equals(user.getPassword())) {
      throw new AuthenticationException("Wrong password");
    }

    return foundUser;
    //        } catch (Exception e) {
    //      throw new RuntimeException("Error during authentication", e);
    //        }
  }
}
