package com.fawry.authentication.services.impl;

import com.fawry.authentication.model.User;
import com.fawry.authentication.services.AuthenticationService;
import com.fawry.authentication.services.FileWriterService;
import com.fawry.authentication.utils.AESEncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private FileWriterService fileWriteService;

    @Override
    public synchronized void registerUser(User user) throws Exception {
        String encryptedPassword = AESEncryptionUtil.encrypt(user.getPassword());
        user.setPassword(encryptedPassword);
        fileWriteService.writeUserToFile(user);
    }

    @Override
    public String loginUser(User user) {
        // TODO Halwagi
        return "";
    }
}
