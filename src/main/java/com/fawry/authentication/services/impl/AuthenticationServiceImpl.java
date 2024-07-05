package com.fawry.authentication.services.impl;

import com.fawry.authentication.common.model.RequestLoginModel;
import com.fawry.authentication.common.model.ResponseAuthenticationModel;
import com.fawry.authentication.common.model.UserModel;
import com.fawry.authentication.exceptions.customExceptions.NotAddedException;
import com.fawry.authentication.exceptions.customExceptions.NotAuthenticatedException;
import com.fawry.authentication.repository.UserRepository;
import com.fawry.authentication.repository.entity.User;
import com.fawry.authentication.services.AuthenticationService;
import com.fawry.authentication.utils.AESEncryptionUtil;
import com.fawry.authentication.utils.FileReaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    @Override
    public void registerUser(final UserModel userModel) throws Exception {
        try {
            User user = new User();
            user.setUsername(userModel.getUsername());
            user.setEmail(userModel.getEmail());
            user.setPassword(AESEncryptionUtil.encrypt(userModel.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new NotAddedException("Error while adding user");
        }
    }

    @Override
    public ResponseAuthenticationModel loginUser(RequestLoginModel user) throws Exception {
        UserModel foundUser =
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

    @Override
    public List<UserModel> listUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserModel userModel = new UserModel();
                    userModel.setUsername(user.getUsername());
                    userModel.setEmail(user.getEmail());
                    userModel.setId(user.getId());
                    userModel.setPassword("*******");
                    return userModel;
                }).toList();
    }
}
