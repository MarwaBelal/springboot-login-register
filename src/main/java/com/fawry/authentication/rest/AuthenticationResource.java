package com.fawry.authentication.rest;

import com.fawry.authentication.common.model.RequestLoginModel;
import com.fawry.authentication.common.model.RequestRegisterModel;
import com.fawry.authentication.common.model.ResponseAuthenticationModel;
import com.fawry.authentication.common.model.UserModel;
import com.fawry.authentication.repository.UserRepository;
import com.fawry.authentication.repository.entity.User;
import com.fawry.authentication.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationResource {
  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseAuthenticationModel registerUser(
      @RequestBody RequestRegisterModel requestRegisterModel) {
    return authenticationService.registerUser(requestRegisterModel);
  }

  @GetMapping("/users")
  public List<ResponseAuthenticationModel> getUsers() throws Exception {
    return authenticationService.listUsers();
  }

  @PostMapping("/login")
  public ResponseAuthenticationModel loginUser(@RequestBody RequestLoginModel user) {

    return authenticationService.loginUser(user);
  }
}
