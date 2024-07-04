package com.fawry.authentication.rest;

import com.fawry.authentication.model.RequestLoginModel;
import com.fawry.authentication.model.ResponseAuthenticationModel;
import com.fawry.authentication.model.User;
import com.fawry.authentication.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationResource {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseAuthenticationModel registerUser(@RequestBody User user) throws Exception {

        return authenticationService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseAuthenticationModel loginUser(@RequestBody RequestLoginModel user)
            throws Exception {

        return authenticationService.loginUser(user);
    }
}
