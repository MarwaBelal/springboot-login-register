package com.fawry.authentication.rest;

import com.fawry.authentication.common.model.RequestLoginModel;
import com.fawry.authentication.common.model.ResponseAuthenticationModel;
import com.fawry.authentication.common.model.UserModel;
import com.fawry.authentication.repository.UserRepository;
import com.fawry.authentication.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthenticationResource {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel user) throws Exception {
        return authenticationService.registerUser(user);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserModel>> getUsers() throws Exception {
        return ResponseEntity.ok(authenticationService.listUsers());
    }

    @PostMapping("/login")
    public ResponseAuthenticationModel loginUser(@RequestBody RequestLoginModel user)
            throws Exception {

        return authenticationService.loginUser(user);
    }
}
