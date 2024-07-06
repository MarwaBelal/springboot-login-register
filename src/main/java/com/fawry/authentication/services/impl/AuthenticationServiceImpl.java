package com.fawry.authentication.services.impl;

import com.fawry.authentication.common.model.RequestLoginModel;
import com.fawry.authentication.common.model.RequestRegisterModel;
import com.fawry.authentication.common.model.ResponseAuthenticationModel;
import com.fawry.authentication.common.model.UserModel;
import com.fawry.authentication.exceptions.customExceptions.NotAuthenticatedException;
import com.fawry.authentication.exceptions.customExceptions.UserIsAlreadyExistException;
import com.fawry.authentication.repository.UserRepository;
import com.fawry.authentication.repository.entity.User;
import com.fawry.authentication.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public ResponseAuthenticationModel registerUser(final RequestRegisterModel requestRegisterModel) {

    if (userRepository.existsByEmail(requestRegisterModel.getEmail())) {

      throw new UserIsAlreadyExistException(
          "User Email(" + requestRegisterModel.getEmail() + ") is already exist");
    }

    User newUser =
        User.builder()
            .email(requestRegisterModel.getEmail())
            .password(passwordEncoder.encode(requestRegisterModel.getPassword()))
            .username(requestRegisterModel.getUsername())
            .build();

    userRepository.save(newUser);

    return ResponseAuthenticationModel.builder()
        .username(newUser.getUsername())
        .email(newUser.getEmail())
        .build();
  }

  @Override
  public ResponseAuthenticationModel loginUser(RequestLoginModel requestLoginModel) {

    User user =
        userRepository
            .findByEmail(requestLoginModel.getEmail())
            .orElseThrow(() -> new NotAuthenticatedException("Email or password is incorrect"));

    if (!passwordEncoder.matches(requestLoginModel.getPassword(), user.getPassword())) {

      throw new NotAuthenticatedException("Email or password is incorrect");
    }

    return ResponseAuthenticationModel.builder()
        .email(user.getEmail())
        .username(user.getUsername())
        .build();
  }

  @Override
  public List<ResponseAuthenticationModel> listUsers() {
    return userRepository.findAll().stream()
        .map(
            user ->
                ResponseAuthenticationModel.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .build())
        .toList();
  }
}
