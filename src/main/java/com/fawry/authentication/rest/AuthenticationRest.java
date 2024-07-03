package com.fawry.authentication.rest;

import com.fawry.authentication.model.RequestLoginModel;
import com.fawry.authentication.model.User;
import com.fawry.authentication.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
public class AuthenticationRest {
  @Autowired private AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<User> registerUser(@RequestBody User user) {
    try {
      authenticationService.registerUser(user);
      return ResponseEntity.ok(user);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/login")
  public ResponseEntity loginUser(@RequestBody RequestLoginModel user) {

    try {
      User authUser = authenticationService.loginUser(user);
      return ResponseEntity.status(200).body(authUser);
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    }
  }
}
