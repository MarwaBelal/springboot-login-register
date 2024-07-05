package com.fawry.authentication.services.impl;

import com.fawry.authentication.common.model.RequestLoginModel;
import com.fawry.authentication.common.model.ResponseAuthenticationModel;
import com.fawry.authentication.common.model.UserModel;
import com.fawry.authentication.exceptions.customExceptions.NotAuthenticatedException;
import com.fawry.authentication.exceptions.customExceptions.UserIsAlreadyExistException;
import com.fawry.authentication.repository.UserRepository;
import com.fawry.authentication.repository.entity.User;
import com.fawry.authentication.services.AuthenticationService;
import com.fawry.authentication.utils.AESEncryptionUtil;
import com.fawry.authentication.utils.FileReaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> registerUser(final UserModel userModel) throws Exception {
        User user = userRepository.findUserByEmail(userModel.getEmail());
        if (user == null) {
            User newUser = new User();
            newUser.setUsername(userModel.getUsername());
            newUser.setEmail(userModel.getEmail());
            // Encode the password before saving
            String encodedPassword = passwordEncoder.encode(userModel.getPassword());
            newUser.setPassword(encodedPassword);
            System.out.println("encoded" + encodedPassword);
            userRepository.save(newUser);
            return ResponseEntity.ok("User is added successfully");
        } else {
            throw new UserIsAlreadyExistException("User Is Already Exist!");
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
                    userModel.setPassword(user.getPassword());
                    return userModel;
                }).toList();
    }
}
