package com.fawry.authentication.services.impl;

import com.fawry.authentication.exceptions.customExceptions.NotAuthenticatedException;
import com.fawry.authentication.model.RequestLoginModel;
import com.fawry.authentication.model.ResponseAuthenticationModel;
import com.fawry.authentication.model.User;
import com.fawry.authentication.services.AuthenticationService;
import com.fawry.authentication.utils.AESEncryptionUtil;
import com.fawry.authentication.utils.FileReaderUtil;
import com.fawry.authentication.utils.FileWriterUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public synchronized ResponseAuthenticationModel registerUser(User user) throws Exception {
        String encryptedPassword = AESEncryptionUtil.encrypt(user.getPassword());
        user.setPassword(encryptedPassword);
        User savedUser = FileWriterUtil.writeUserToFile(user);
        return ResponseAuthenticationModel.builder()
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .build();
    }

    @Override
    public ResponseAuthenticationModel loginUser(RequestLoginModel user) throws Exception {
        User foundUser =
                FileReaderUtil
                        .getUserFromFile(user.getEmail())
                        .orElseThrow(() -> new NotAuthenticatedException("User not found"));

        String decryptedPassword = AESEncryptionUtil.decrypt(foundUser.getPassword());

        System.out.println("Decrypted Password: " + decryptedPassword);
        System.out.println("Provided Password: " + user.getPassword());

        if (!decryptedPassword.equals(user.getPassword())) {
            System.out.println("Wrong password");
            throw new NotAuthenticatedException("Wrong password");
        }
        return ResponseAuthenticationModel.builder()
                .email(foundUser.getEmail())
                .username(foundUser.getUsername())
                .build();
    }
}
